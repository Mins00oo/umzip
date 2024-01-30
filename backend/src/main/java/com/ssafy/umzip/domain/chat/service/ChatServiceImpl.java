package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.entity.ChatParticipant;
import com.ssafy.umzip.domain.chat.entity.ChatRoom;
import com.ssafy.umzip.domain.chat.repository.ChatParticipantRepository;
import com.ssafy.umzip.domain.chat.repository.ChatRoomRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    @Transactional
    @Override
    public ChatRoom createChatRoom(Long senderId, String senderRole, Long receiverId, String role) {
        Member sender = memberRepository.findById(senderId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));


        ChatRoom chatRoom = ChatRoom.builder()
                .build();

        chatRoomRepository.save(chatRoom);

        ChatParticipant senderParticipant  = ChatParticipant.builder()
                .chatRoom(chatRoom)
                .member(sender)
                .role(senderRole)
                .build();

        chatParticipantRepository.save(senderParticipant);

        ChatParticipant receiverParticipant  = ChatParticipant.builder()
                .chatRoom(chatRoom)
                .member(receiver)
                .role(role)
                .build();

        chatParticipantRepository.save(receiverParticipant);
        return chatRoom;
    }
}
