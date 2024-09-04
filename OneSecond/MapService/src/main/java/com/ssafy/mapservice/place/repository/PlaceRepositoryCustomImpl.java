package com.ssafy.mapservice.place.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mapservice.place.entity.PlaceEntity;
import com.ssafy.mapservice.place.enumeration.PlaceType;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.ssafy.mapservice.place.entity.QInvitationInfoEntity.*;
import static com.ssafy.mapservice.place.entity.QPlaceEntity.*;

public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Autowired
    public PlaceRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public List<PlaceEntity> getPlacesByCond(String invitationCode, String userId, PlaceType placeType){
        return queryFactory
                .select(placeEntity)
                .from(placeEntity)
                .join(placeEntity.invitationInfoEntity, invitationInfoEntity).fetchJoin()
                .where(
                        invitationCodeEq(invitationCode),
                        userIdEq(userId),
                        placeTypeEq(placeType)
                )
                .fetch();
    }

    private BooleanExpression invitationCodeEq(String invitationCode){
        return invitationCode != null ? invitationInfoEntity.invitationCode.eq(invitationCode) : null;
    }
    private BooleanExpression userIdEq(String userId){
        return userId != null ? invitationInfoEntity.userId.eq(userId) : null;
    }
    private BooleanExpression placeTypeEq(PlaceType placeType){
        return placeType != null ? placeEntity.placeType.eq(placeType) : null;
    }


}
