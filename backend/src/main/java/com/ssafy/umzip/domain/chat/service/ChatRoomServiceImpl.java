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

        List<Long> chatRoomIds = chatRooms.stream()
                .map(ChatRoomListResponseDto::getChatRoomId)
                .collect(Collectors.toList());

        Map<Long, String> lastReadMessageIdByRoomId = getLastReadMessageIds(memberId, chatRoomIds);

        List<ChatMessage> recentMessages = customChatParticipantRepository.findRecentMessagesByChatRoomIds(chatRoomIds);

        Map<Long, ChatMessage> recentMessageMap = recentMessages.stream()
                .collect(Collectors.toMap(ChatMessage::getChatRoomId, Function.identity()));

        chatRooms.forEach(chatRoom -> {
            ChatMessage recentMessage = recentMessageMap.get(chatRoom.getChatRoomId());
            if (recentMessage != null) {
                chatRoom.setLastContent(recentMessage.getContent());
            }
            String lastReadMessageId = lastReadMessageIdByRoomId.get(chatRoom.getChatRoomId());
            long messagesCount = getNewMessagesCount(chatRoom.getChatRoomId(), lastReadMessageId);
            chatRoom.setUnReadCount(messagesCount);
        });
        return chatRooms;
    }

    private Map<Long, String> getLastReadMessageIds(Long memberId, List<Long> chatRoomIds) {
        List<ChatParticipant> participants = chatParticipantRepository.
                findByMemberIdAndChatRoomIdIn(memberId, chatRoomIds);
        return participants.stream()
                .collect(Collectors.toMap(
                        participant -> participant.getChatRoom().getId(),
                        ChatParticipant::getMessageId
                ));
    }

    private long getNewMessagesCount(Long chatRoomId, String lastReadMessageId) {
        return customChatParticipantRepository.getNewMessageCount(chatRoomId, lastReadMessageId);
    }

    @Transactional
    @Override
    public void leaveChatRoom(Long chatRoomId, Long requestId) {
        ChatParticipant chatParticipant = chatParticipantRepository.findByChatRoomAndMember(chatRoomId, requestId);
        chatParticipant.leaveChatRoom();
    }
}
