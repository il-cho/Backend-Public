package com.ssafy.chatservice.global.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Bean
    public SenderOptions<String, String> senderOptions() {
        System.out.println(bootstrapServers);
        // Kafka Producer 설정
        Map<String, Object> producerProps = new HashMap<>();

        // bootstrap-servers 설정
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // Producer가 메시지의 key 값을 바이트로 변환하는데 사용할 클래스 지정
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // Producer가 메시지의 value 값을 바이트로 변환하는데 사용할 클래스 지정
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return SenderOptions.create(producerProps);
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate() {
        // Kafka Producer 설정
        return new ReactiveKafkaProducerTemplate<>(senderOptions());
    }
}
