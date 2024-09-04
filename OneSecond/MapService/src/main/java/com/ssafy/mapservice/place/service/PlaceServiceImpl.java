package com.ssafy.mapservice.place.service;

import com.ssafy.mapservice.place.entity.InvitationInfoEntity;
import com.ssafy.mapservice.place.entity.PlaceEntity;
import com.ssafy.mapservice.place.enumeration.PlaceType;
import com.ssafy.mapservice.place.repository.PlaceRepository;
import com.ssafy.mapservice.place.repository.InvitationInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final InvitationInfoRepository invitationInfoRepository;

    @Override
    public Long deletePlaceById(Long id) {
        placeRepository.deleteById(id);
        return id;
    }

    @Override
    public List<PlaceEntity> getAllPlaces(String invitationCode, String userId, PlaceType placeType) {
        return placeRepository.getPlacesByCond(invitationCode, userId, placeType);
    }


    @Transactional
    @Override
    public List<PlaceEntity> savePlaceWithUserInfo(InvitationInfoEntity invitationInfoEntity, List<PlaceEntity> placeEntityList) {
        InvitationInfoEntity findInvitationInfoEntity = saveOrFindUserEntity(invitationInfoEntity);
        placeEntityList.forEach(placeEntity -> placeEntity.saveUserInfo(findInvitationInfoEntity));
        return placeRepository.saveAll(placeEntityList);
    }

    @Transactional
    @Override
    public void deleteByInvitationCode(String invitationCode) {
        List<InvitationInfoEntity> invitationInfoList = invitationInfoRepository.findAllByInvitationCode(invitationCode);
        placeRepository.deleteAllByInvitationInfos(invitationInfoList);
        invitationInfoRepository.deleteAllInBatch(invitationInfoList);
    }


    private InvitationInfoEntity saveOrFindUserEntity(InvitationInfoEntity invitationInfoEntity) {
        return invitationInfoRepository
                .findUserByUserIdAndInvitationCode(
                        invitationInfoEntity.getUserId(),
                        invitationInfoEntity.getInvitationCode()
                )
                .orElseGet(() -> invitationInfoRepository.save(invitationInfoEntity));
    }
}
