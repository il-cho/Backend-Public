package com.ssafy.userservice.user.repository;

import com.ssafy.userservice.user.entity.NaverUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NaverUserRepository extends JpaRepository<NaverUser, String> {

    Optional<NaverUser> findByUserId(int userId);
}
