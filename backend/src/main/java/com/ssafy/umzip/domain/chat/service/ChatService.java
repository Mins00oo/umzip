package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;

public interface ChatService {
    void saveMessage(ChatMessageRequestDto message, Long chatRoomId, Long requestId);
}