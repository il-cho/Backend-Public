package com.ssafy.mapservice.place.repository;

import com.ssafy.mapservice.place.entity.InvitationInfoEntity;
import com.ssafy.mapservice.place.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Long>, PlaceRepositoryCustom {

    @Modifying
    @Query("delete from PlaceEntity p where p.invitationInfoEntity in :invitationInfoList")
    int deleteAllByInvitationInfos(@Param("invitationInfoList") List<InvitationInfoEntity> invitationInfoList);
}
