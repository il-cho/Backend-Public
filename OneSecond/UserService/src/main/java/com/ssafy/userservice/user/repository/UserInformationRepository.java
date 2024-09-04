package com.ssafy.userservice.user.repository;

import com.ssafy.userservice.user.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {

}
