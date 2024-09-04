package com.ssafy.mapservice.place.entity;

import com.ssafy.mapservice.place.enumeration.PlaceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Table(name = "place")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;
    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    @JoinColumn(name = "invitation_info_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private InvitationInfoEntity invitationInfoEntity;

    @Column(nullable = false)
    private String placeName;

    @Column(nullable = false)
    private String placeAddress;

    public void saveUserInfo(InvitationInfoEntity invitationInfoEntity){
        this.invitationInfoEntity = invitationInfoEntity;
    }

}
