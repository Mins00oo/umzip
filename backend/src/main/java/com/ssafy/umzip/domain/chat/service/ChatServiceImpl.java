package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.umzip.domain.chat.dto.ChatMessageResponseDto;
import com.ssafy.umzip.domain.chat.dto.ChatTradeMessageRequestDto;
import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import com.ssafy.umzip.domain.chat.entity.ChatParticipant;
import com.ssafy.umzip.domain.chat.entity.ChatRoomStatus;
import com.ssafy.umzip.domain.chat.entity.TradeChat;
import com.ssafy.umzip.domain.chat.repository.ChatMessageRepository;
import com.ssafy.umzip.domain.chat.repository.ChatParticipantRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public ChatMessageResponseDto saveMessage(ChatMessageRequestDto message, Long chatRoomId, Long requestId) {
        Member member = memberRepository.findById(requestId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));
        ChatMessage chatMessage = ChatMessageRequestDto.toEntity(message, member, chatRoomId);

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        List<ChatParticipant> chatParticipantList = chatParticipantRepository.findByChatRoomId(chatRoomId);
        for (ChatParticipant cp : chatParticipantList) {
            log.info(String.valueOf(cp.getMember().getId()));
            if (!cp.getStatus().equals(ChatRoomStatus.TALK)){
                cp.talkWithChatRoom();
            }
        }

        return ChatMessageRequestDto.toResponseDto(message.getContent(), member, LocalDateTime.now());
    }

    @Override
    public void saveTradeMessage(ChatTradeMessageRequestDto message, Long chatRoomId, Long requestId) {
        TradeChat.builder()

                .build();
    }

    @Transactional
    @Override
    public List<ChatMessageResponseDto> retrieveChatRoomMessages(Long chatRoomId, Long requestId) {
        Member requester = memberRepository.findById(requestId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));

        Member anotherMember = chatParticipantRepository.findByChatRoomAndMemberId(chatRoomId, requestId);
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatRoomId(chatRoomId);

        return chatMessages.stream()
                .map(message -> {
                    Member userInfo = message.getSenderId().equals(requestId.toString()) ? requester : anotherMember;

                    return new ChatMessageResponseDto(
                            message.getContent(),
                            requestId,
                            userInfo.getName(),
                            message.getSenderId(),
                            userInfo.getImageUrl(),
                            message.getUpdateDt()
                    );
                })
                .sorted(Comparator.comparing(ChatMessageResponseDto::getSendTime))
                .collect(Collectors.toList());
    }
}
