package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import com.ssafy.umzip.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void saveMessage(ChatMessageRequestDto message, Long chatRoomId, Long requestId) {
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(chatRoomId)
                .content(message.getContent())
                .senderId(String.valueOf(requestId))
                .build();

        log.info(String.valueOf(chatMessage));
        chatMessageRepository.save(chatMessage);
    }
}
