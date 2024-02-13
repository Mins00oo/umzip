package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.dto.ChatRoomListResponseDto;
import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import com.ssafy.umzip.domain.chat.entity.ChatParticipant;
import com.ssafy.umzip.domain.chat.entity.ChatRoom;
import com.ssafy.umzip.domain.chat.entity.ChatRoomType;
import com.ssafy.umzip.domain.chat.repository.ChatParticipantRepository;
import com.ssafy.umzip.domain.chat.repository.ChatRoomRepository;
import com.ssafy.umzip.domain.chat.repository.CustomChatParticipantRepository;
import com.ssafy.umzip.domain.company.repository.CompanyRepository;
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
import java.util.Optional;
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
    private final CompanyRepository companyRepository;

    @Transactional
    @Override
    public ChatRoom createChatRoom(Long senderId, String senderRole, Long receiverId, Long tradeId, String role) {
        Member sender = findMember(senderId);
        Member receiver = findMember(receiverId);

        ChatRoom chatRoom = buildChatRoom(role, tradeId);

        chatRoomRepository.save(chatRoom);

        saveChatParticipant(chatRoom, sender, senderRole);
        saveChatParticipant(chatRoom, receiver, role);

        return chatRoom;
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));
    }

    private ChatRoom buildChatRoom(String role, Long tradeId) {
        ChatRoomType roomType = getRoomType(role);
        ChatRoom chatRoom = ChatRoom.builder().roomType(roomType).build();

        if (tradeId != null && roomType == ChatRoomType.TRADE) {
            chatRoom.setTradeId(tradeId);
        }
        return chatRoom;
    }

    private ChatRoomType getRoomType(String role) {
        return switch (role) {
            case "DELIVER" -> ChatRoomType.DELIVER;
            case "CLEAN" -> ChatRoomType.CLEAN;
            case "TRADE" -> ChatRoomType.TRADE;
            default -> ChatRoomType.HELP;
        };
    }

    private void saveChatParticipant(ChatRoom chatRoom, Member member, String role) {
        String r = role;
        if (role.equals("TRADE") || role.equals("HELP")) {
            r = "USER";
        }
        ChatParticipant participant = ChatParticipant.builder()
                .chatRoom(chatRoom)
                .member(member)
                .role(r)
                .build();
        chatParticipantRepository.save(participant);
    }


    @Transactional(readOnly = true)
    @Override
    public List<ChatRoomListResponseDto> retrieveChatRoom(Long memberId, String role) {
        Long id = resolveMemberIdByRole(memberId, role);
        List<ChatRoomListResponseDto> chatRooms = customChatParticipantRepository.findChatRoomDetailsByMemberIdAndRole(id, role);

        List<Long> chatRoomIds = chatRooms.stream()
                .map(ChatRoomListResponseDto::getChatRoomId)
                .collect(Collectors.toList());

        Map<Long, String> lastReadMessageIdByRoomId = getLastReadMessageIds(id, chatRoomIds);

        List<ChatMessage> recentMessages = customChatParticipantRepository.findRecentMessagesByChatRoomIds(chatRoomIds);

        Map<Long, ChatMessage> recentMessageMap = recentMessages.stream()
                .collect(Collectors.toMap(ChatMessage::getChatRoomId, Function.identity()));

        chatRooms.forEach(chatRoom -> {
            long messagesCount;
            ChatMessage recentMessage = recentMessageMap.get(chatRoom.getChatRoomId());
            if (recentMessage != null) {
                chatRoom.setLastContent(recentMessage.getContent());
            }
            String lastReadMessageId = lastReadMessageIdByRoomId.get(chatRoom.getChatRoomId());
            if (lastReadMessageId.equals("0")) {
                messagesCount = customChatParticipantRepository.getAllUnReadMessageCount(chatRoom.getChatRoomId());
            } else {
                messagesCount = getNewMessagesCount(chatRoom.getChatRoomId(), lastReadMessageId);
            }
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
                        participant -> Optional.ofNullable(participant.getMessageId()).orElse("0")
                ));
    }

    private long getNewMessagesCount(Long chatRoomId, String lastReadMessageId) {
        return customChatParticipantRepository.getNewMessageCount(chatRoomId, lastReadMessageId);
    }

    @Transactional
    @Override
    public void leaveChatRoom(Long chatRoomId, Long requestId, String role) {
        Long id = resolveMemberIdByRole(requestId, role);
        ChatParticipant chatParticipant = chatParticipantRepository.findByChatRoomAndMember(chatRoomId, id)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_CHATROOM_WITH_USER));
        chatParticipant.leaveChatRoom();
    }

    private Long resolveMemberIdByRole(Long id, String role) {
        if (role.equals("DELIVER") || role.equals("CLEAN")) {
            return companyRepository.findById(id)
                    .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_COMMENT_PK))
                    .getMember().getId();
        }
        return id;
    }
}
