package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.dto.ChatRoomListResponseDto;
import com.ssafy.umzip.domain.chat.entity.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    ChatRoom createChatRoom(Long senderId, String senderRole, Long receiverId, Long tradeId, String role);

    List<ChatRoomListResponseDto> retrieveChatRoom(Long memberId, String role);

    void leaveChatRoom(Long chatRoomId, Long requestId, String role);
}
