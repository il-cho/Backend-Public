package com.ssafy.chatservice.domain.chat.repository;

import com.ssafy.chatservice.domain.chat.collection.Chat;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
    // 채팅 내역 전체 조회
    Flux<Chat> findAllByInvitationCode(String invitationCode, Pageable pageable);

    // 특정 ChatId 이전의 데이터만 조회 (ObjectId 기준으로 페이징 가능)
    @Query("{ 'invitationCode': ?0, '_id': { '$lt': ?1 } }")
    Flux<Chat> findByInvitationCodeAndIdLessThan(String invitationCode, ObjectId id, Pageable pageable);
}
