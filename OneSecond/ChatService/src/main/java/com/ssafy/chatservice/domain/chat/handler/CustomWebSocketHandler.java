package com.ssafy.chatservice.domain.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.chatservice.domain.chat.dto.ChatRequest;
import com.ssafy.chatservice.domain.chat.dto.ChatResponse;
import com.ssafy.chatservice.domain.chat.service.ChatService;
import com.ssafy.chatservice.global.kafka.KafkaService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomWebSocketHandler implements WebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final KafkaService kafkaService;
    private static final Map<String, String> sessionRegistry = new ConcurrentHashMap<>();
    private static Sinks.Many<String> sink;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        log.debug("CustomWebSocketHandler.handle() is called.");
        log.info("Socket connected");

        String invitationCode = extractInvitationCode(session);
        sessionRegistry.put(session.getId(), invitationCode);
        log.info("Success to save Session Information: ID={}, invitationCode={}", session.getId(), invitationCode);

        Mono<Void> receive = receive(session);
        Mono<Void> send = send(session);

        log.info("Success to create chat publisher");

        return Mono.zip(receive, send).then()
                .doFinally(message -> {
                    sessionRegistry.remove(session.getId());
                    session.close(CloseStatus.NORMAL);
                    log.info("Socket disconnected: message={}", message);
                });
    }

    // 메시지를 받기
    public Mono<Void> receive(WebSocketSession session) {

        return session.receive() // 인바운드 받기
                .map(WebSocketMessage::getPayloadAsText) // JSON 문자열로 메시지 변환
                .filter(message -> !message.trim().isEmpty()) // 빈 메시지 필터링
                .doOnNext(message -> log.info("Received message: {}", message)) // 로깅
                // 받은 메시지를 DTO로 변환
                .flatMap(message -> Mono.fromCallable(() -> objectMapper.readValue(message, ChatRequest.class))
                        .onErrorResume(e -> {
                            log.error("Failed to parse ChatRequest message.");
                            return Mono.error(new RuntimeException("Failed to parse ChatRequest message"));
                        })).doOnNext(message -> log.info("Success to parse message."))
                // TODO: MongoDB, Kafka 데이터 저장 Transaction 처리 필요
                // MongoDB에 chat 저장
                .flatMap(chatService::save)
                .doOnNext(message -> log.debug("Success to save request."))
                // chat 내용을 Kafka에 Publish
                .flatMap(savedMessage -> Mono.fromCallable(() -> objectMapper.writeValueAsString(savedMessage))
                        .onErrorResume(e -> {
                            log.error("Failed to convert Chat to JSON", e);
                            return Mono.error(new RuntimeException("Failed to parse JSON"));
                        }).flatMap(json -> kafkaService.sendMessage("chat", "key", json)))
                .doOnNext(message -> log.info("Success to run receive()."))
                // 이전의 결과를 무시하고 Mono<Void> 반환, 작업의 완료를 나타냄
                .then();
    }

    // 데이터 전송
    public Mono<Void> send(WebSocketSession session) {
        // Sink: 여러 건의 데이터를 emit, asFlux() Flux로 변환
        Sinks.Many<String> sink = KafkaService.getSink(); //

        return session.send(sink.asFlux()
                .doOnNext(message -> log.info("Success to convert flux from sinks"))
                // JSON 데이터를 ChatResponse로 변경
                .flatMap(message -> Mono.fromCallable(() -> objectMapper.readValue(message, ChatResponse.class))
                        .onErrorResume(e -> {
                            log.error("Failed to parse ChatResponse message.");
                            return Mono.error(new RuntimeException("Failed to parse ChatResponse message."));
                        }))
                // 현재 나의 SessionID가 메시지의 InvitationCode에 해당하는지 판별
                .filter(chatMessage -> {
                    String invitationCode = sessionRegistry.get(session.getId());
                    log.info("Filter: sessionId= {}, invitationCode= {}", sessionRegistry.get(session.getId()),
                            chatMessage.getInvitationCode());
                    return invitationCode.equals(chatMessage.getInvitationCode());
                })
                // ChatResponse를 JSON으로 전송
                .flatMap(message -> Mono.fromCallable(
                        () -> session.textMessage(objectMapper.writeValueAsString(message))).onErrorResume(e -> {
                    log.error("Failed to serialize message.");
                    return Mono.error(new RuntimeException("Failed to serialize message."));
                })).doOnNext(webSocketSessionMessage -> log.info("WebSocket sent: sessionId={}, content={}",
                        session.getId(), webSocketSessionMessage.getPayloadAsText())));
    }

    // InvitationCode 추출
    private String extractInvitationCode(WebSocketSession session) {
        String path = session.getHandshakeInfo().getUri().getPath();
        String[] segments = path.split("/");
        if (segments.length > 0) {
            return segments[segments.length - 1]; // 마지막 경로 세그먼트 추출
        }
        return null;
    }
}
