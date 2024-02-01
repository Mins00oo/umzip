package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.umzip.domain.chat.dto.ChatMessageResponseDto;
import com.ssafy.umzip.domain.chat.dto.ChatTradeMessageRequestDto;

import java.util.List;

public interface ChatService {
    ChatMessageResponseDto saveMessage(ChatMessageRequestDto message, Long chatRoomId, Long requestId);

    void saveTradeMessage(ChatTradeMessageRequestDto message, Long chatRoomId, Long requestId);

    List<ChatMessageResponseDto> retrieveChatRoomMessages(Long chatRoomId, Long requestId);
}