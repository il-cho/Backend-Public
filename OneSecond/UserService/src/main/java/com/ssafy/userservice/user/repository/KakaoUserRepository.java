package com.ssafy.userservice.user.repository;

import com.ssafy.userservice.user.entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KakaoUserRepository extends JpaRepository<KakaoUser, String> {
    Optional<KakaoUser> findByUserId(int userId);
}
