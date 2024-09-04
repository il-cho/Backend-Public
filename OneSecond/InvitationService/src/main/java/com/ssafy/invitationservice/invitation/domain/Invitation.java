package com.ssafy.invitationservice.invitation.domain;

import com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity.InvitationEntity;
import lombok.Builder;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Invitation {
    private String inviter;
    private String title;
    private String description;
    private LocalDate confirmedDate;
    private String invitationCode;

    @Builder
    public Invitation(String inviter, String title, String description, LocalDate confirmedDate, String invitationCode) {
        this.inviter = inviter;
        this.title = title;
        this.description = description;
        this.confirmedDate = confirmedDate;
        this.invitationCode = invitationCode;
    }

    public static Invitation of(InvitationEntity entity){

        return Invitation.builder()
                .inviter(entity.getInviter())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .confirmedDate(entity.getConfirmedDate())
                .invitationCode(entity.getInvitationCode())
                .build();
    }

    //TODO UUID 겹치는 경우 Exception에서 처리하기
    public static String createShortUUID(){
        String uuidString = UUID.randomUUID().toString();
        byte[] uuidStringBytes = uuidString.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes;

        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            hashBytes = messageDigest.digest(uuidStringBytes);
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        for(int j=0;j<4;j++){
            sb.append(String.format("%02x",hashBytes[j]));
        }

        return sb.toString();
    }
}
