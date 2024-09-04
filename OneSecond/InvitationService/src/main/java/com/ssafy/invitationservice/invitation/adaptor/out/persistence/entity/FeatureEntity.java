package com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity;

import com.ssafy.invitationservice.invitation.domain.Feature;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "feature")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeatureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id")
    private Long id;

    @Column(name = "invitation_code")
    private String invitationCode;

    private boolean schedule;
    private boolean place;
    private boolean account;
    private boolean chat;
    private boolean chatbot;

    public FeatureEntity(String invitationCode, boolean schedule, boolean place, boolean account, boolean chat, boolean chatbot) {
        this.invitationCode = invitationCode;
        this.schedule = schedule;
        this.place = place;
        this.account = account;
        this.chat = chat;
        this.chatbot = chatbot;
    }

    public void changeInfo(Feature feature){
        if(feature.isAccount()){
            this.account = true;
        }
        if(feature.isSchedule()){
            this.schedule = true;
        }
        if(feature.isPlace()){
            this.place = true;
        }
        if(feature.isChat()){
            this.chat = true;
        }
        if(feature.isChatbot()){
            this.chatbot = true;
        }
    }
}
