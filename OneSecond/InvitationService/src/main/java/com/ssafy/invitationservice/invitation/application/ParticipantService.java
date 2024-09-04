package com.ssafy.invitationservice.invitation.application;

import com.ssafy.invitationservice.invitation.adaptor.in.web.dto.ParticipantOutput;
import com.ssafy.invitationservice.invitation.adaptor.out.client.UserClient;
import com.ssafy.invitationservice.invitation.adaptor.out.client.dto.ChatUsersRequest;
import com.ssafy.invitationservice.invitation.adaptor.out.client.dto.ChatUsersResponse;
import com.ssafy.invitationservice.invitation.application.port.in.AttendUseCase;
import com.ssafy.invitationservice.invitation.application.port.in.ViewParticipantUseCase;
import com.ssafy.invitationservice.invitation.application.port.in.ViewParticipantListUseCase;
import com.ssafy.invitationservice.invitation.application.port.out.ParticipantPort;
import com.ssafy.invitationservice.invitation.domain.Participant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService implements ViewParticipantListUseCase, ViewParticipantUseCase, AttendUseCase {
    private final ParticipantPort participantPort;
    private final UserClient userClient;

    @Override
    public List<Participant> viewParticipantList(String invitationCode) {
        return participantPort.viewParticipantList(invitationCode);
    }

    @Override
    public List<ParticipantOutput> viewParticipantOutputList(List<Participant> participants) {
        List<Integer> userIds = participants.stream()
                .map(Participant::getUserId)
                .collect(Collectors.toUnmodifiableList());
        log.info("참여자 목록 조회 -> {}", Arrays.toString(userIds.toArray()));

        ChatUsersRequest request = new ChatUsersRequest(userIds);
        ChatUsersResponse response = userClient.chatUserList(request);

        Map<Integer, List<String>> userNamesMap = response.getChatUserList();

        return participants.stream()
                .map(participant -> {
                    List<String> names = userNamesMap.get(participant.getUserId());

                    return ParticipantOutput.builder()
                            .userId(participant.getUserId())
                            .username(names != null ? names.get(0) : "알 수 없음")
                            .attend(participant.isAttend())
                            .paid(participant.isPaid())
                            .profile(names != null ? Integer.valueOf(names.get(1)) : 0)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Boolean viewParticipantUseCase(String invitationCode, int loginId) {
        return participantPort.viewParticipant(invitationCode, loginId);
    }

    @Override
    @Transactional
    public boolean attend(int userId, String invitationCode, boolean attend) {
        Participant participant = Participant.builder()
                .userId(userId)
                .invitationCode(invitationCode)
                .attend(attend)
                .build();
        log.info("service 초대장 코드 -> {}", invitationCode);
        return participantPort.modifyAttend(participant).isAttend();
    }
}
