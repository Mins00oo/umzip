package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.entity.ChatRoom;

public interface ChatService {
    ChatRoom createChatRoom(Long senderId, String senderRole, Long receiverId, String role);
}
