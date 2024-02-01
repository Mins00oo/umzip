package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.dto.ChatRoomListResponseDto;
import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import com.ssafy.umzip.domain.chat.entity.ChatParticipant;
import com.ssafy.umzip.domain.chat.entity.ChatRoom;
import com.ssafy.umzip.domain.chat.repository.ChatMessageRepository;
import com.ssafy.umzip.domain.chat.repository.ChatParticipantRepository;
import com.ssafy.umzip.domain.chat.repository.ChatRoomRepository;
import com.ssafy.umzip.domain.chat.repository.CustomChatParticipantRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatParticipantRepository chatParticipantRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final CustomChatParticipantRepository customChatParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;

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

        ChatParticipant senderParticipant = ChatParticipant.builder()
                .chatRoom(chatRoom)
                .member(sender)
                .role(senderRole)
                .build();

        chatParticipantRepository.save(senderParticipant);

        ChatParticipant receiverParticipant = ChatParticipant.builder()
                .chatRoom(chatRoom)
                .member(receiver)
                .role(role)
                .build();

        chatParticipantRepository.save(receiverParticipant);
        return chatRoom;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ChatRoomListResponseDto> retrieveChatRoom(Long memberId, String role) {

        List<ChatRoomListResponseDto> chatRooms = customChatParticipantRepository.findChatRoomDetailsByMemberIdAndRole(memberId, role);
        // 채팅방 ID 목록 가져오기
        List<Long> chatRoomIds = chatRooms.stream()
                .map(ChatRoomListResponseDto::getChatRoomId)
                .collect(Collectors.toList());

        // 채팅방 ID 목록으로 최근 메시지 가져오기
        List<ChatMessage> recentMessages = customChatParticipantRepository.findRecentMessagesByChatRoomIds(chatRoomIds);

        for (ChatMessage cm : recentMessages) {
            System.out.println("cm = " + cm);
        }

        // 최근 메시지를 채팅방 ID를 기준으로 매핑
        Map<Long, ChatMessage> recentMessageMap = recentMessages.stream()
                .collect(Collectors.toMap(ChatMessage::getChatRoomId, Function.identity()));



        // 채팅방 정보와 최근 메시지 병합
        chatRooms.forEach(chatRoom -> {
            ChatMessage recentMessage = recentMessageMap.get(chatRoom.getChatRoomId());
            if (recentMessage != null) {
                chatRoom.setLastContent(recentMessage.getContent());
            }
        });
        return chatRooms;
    }

    @Transactional
    @Override
    public void leaveChatRoom(Long chatRoomId, Long requestId) {
        ChatParticipant chatParticipant = chatParticipantRepository.findByChatRoomAndMember(chatRoomId, requestId);
        log.info(String.valueOf(chatParticipant.getId()));
        chatParticipant.leaveChatRoom();
    }
}
