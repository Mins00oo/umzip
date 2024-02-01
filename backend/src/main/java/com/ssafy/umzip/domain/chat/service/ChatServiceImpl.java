package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.umzip.domain.chat.dto.ChatMessageResponseDto;
import com.ssafy.umzip.domain.chat.dto.ChatTradeMessageRequestDto;
import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import com.ssafy.umzip.domain.chat.entity.TradeChat;
import com.ssafy.umzip.domain.chat.repository.ChatMessageRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    @Override
    public ChatMessageResponseDto saveMessage(ChatMessageRequestDto message, Long chatRoomId, Long requestId) {
        Member member = memberRepository.findById(requestId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));
        ChatMessage chatMessage = ChatMessageRequestDto.toEntity(message, member, chatRoomId);

        chatMessageRepository.save(chatMessage);

        return ChatMessageRequestDto.toResponseDto(message.getContent(), member, LocalDateTime.now());
    }

    @Override
    public void saveTradeMessage(ChatTradeMessageRequestDto message, Long chatRoomId, Long requestId) {
        TradeChat.builder()

                .build();
    }

    @Override
    public List<ChatMessageResponseDto> retrieveChatRoomMessages(Long chatRoomId, Long requestId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatRoomId(chatRoomId);

        return chatMessages.stream()
                .map(message -> new ChatMessageResponseDto(
                        message.getContent(),
                        requestId,
                        message.getSenderName(),
                        message.getSenderId(),
                        message.getSenderProfileImage(),
                        message.getUpdateDt()
                )).sorted(Comparator.comparing(ChatMessageResponseDto::getSendTime)).collect(Collectors.toList());
    }
}
