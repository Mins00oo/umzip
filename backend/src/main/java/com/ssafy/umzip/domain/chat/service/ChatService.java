package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.dto.ChatDetailResponseDto;
import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.umzip.domain.chat.dto.ChatMessageResponseDto;

import java.util.List;

public interface ChatService {
    ChatMessageResponseDto saveMessage(ChatMessageRequestDto message, Long chatRoomId, Long requestId, String role);

    ChatDetailResponseDto retrieveChatRoomMessages(Long chatRoomId, Long requestId, String role);
}