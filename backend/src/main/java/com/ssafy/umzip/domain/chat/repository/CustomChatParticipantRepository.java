package com.ssafy.umzip.domain.chat.repository;

import com.ssafy.umzip.domain.chat.dto.ChatRoomListResponseDto;
import com.ssafy.umzip.domain.chat.entity.ChatMessage;

import java.util.List;

public interface CustomChatParticipantRepository {
    List<ChatRoomListResponseDto> findChatRoomDetailsByMemberIdAndRole(Long memberId, String role);

    List<ChatMessage> findRecentMessagesByChatRoomIds(List<Long> chatRoomIds);

    long getNewMessageCount(Long chatRoomId, String lastReadMessageId);
}
