package com.ssafy.chatservice.global.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {
    private final ReactiveKafkaProducerTemplate<String, String> kafkaProducerTemplate;
    private final ReactiveKafkaConsumerTemplate<String, String> kafkaConsumerTemplate;
    private static volatile Sinks.Many<String> sink;

    // TODO: atomic 방식을 고려해보기
    public static Sinks.Many<String> getSink() {
        Sinks.Many<String> instance = sink; // 메인 메모리 접근 비용을 줄이기 위한 로컬 변수

        // Double-Checked Locking
        if (instance == null || isSinkNotOk(instance)) {
            synchronized (KafkaService.class) {
                instance = sink; // synchronized 블록 내부에서 재확인
                if (instance == null || isSinkNotOk(instance)) {
                    instance = Sinks.many().multicast().onBackpressureBuffer();
                    sink = instance;
                    log.info("Create or reinitialize sink.");
                }
            }
        }
        return instance;
    }

    // Kafka에 데이터 Publish
    public Mono<Void> sendMessage(String topic, String key, String message) {
        log.info("KafkaService.sendMessage() is called.");

        return kafkaProducerTemplate.send(new ProducerRecord<>(topic, key, message))
                .doOnSuccess(result -> log.info("Sent message to kafka='{}' with offset-={}", message,
                        result.recordMetadata().offset()))
                .then()
                .onErrorResume(e -> {
                    log.error("Failed to send message: {}", e.getMessage());
                    return Mono.empty();
                });
    }

    // Kafka에서 데이터 받아 Sinks에 Emit
    @EventListener(ApplicationStartedEvent.class)
    public void receiveMessage() {
        log.info("CustomWebSocketHandler.receiveMessage() is called.");
        kafkaConsumerTemplate.receiveAutoAck()
                .doOnNext(record -> log.info("Received Message from Kafka: {}", record.value()))
                .doOnNext(record -> {
                    EmitResult emitResult = sink.tryEmitNext(record.value());
                    if (emitResult.isFailure()) {
                        log.error("Failed to emit message to sink. EmitResult: {}", emitResult);
                    }
                })
                .doOnError(error -> log.error("Error receiving message from Kafka: ", error))
                .retry()
                .subscribe();
    }

    private static boolean isSinkNotOk(Sinks.Many<String> instance) {
        // Sink가 정상적인 상태가 아닌지 확인
        return Boolean.TRUE.equals(instance.scan(Scannable.Attr.TERMINATED)) ||  // 종료된 상태
                Boolean.TRUE.equals(instance.scan(Scannable.Attr.CANCELLED)) ||  // 취소된 상태
                Boolean.TRUE.equals(instance.scan(Scannable.Attr.ERROR));  // 오류 발생 상태
    }
}
