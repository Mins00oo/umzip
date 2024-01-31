package com.ssafy.umzip.domain.chat.repository;

import com.ssafy.umzip.domain.chat.dto.ChatRoomListResponseDto;

import java.util.List;

public interface CustomChatParticipantRepository {
    List<ChatRoomListResponseDto> findChatRoomDetailsByMemberIdAndRole(Long memberId, String role);
}
