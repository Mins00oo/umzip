package com.ssafy.umzip.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ChatRoomListResponseDto {
    private Long chatRoomId;

    private Long receiverId;

    private String receiverProfileImage;

    private String receiverName;

    private String updateDt;

    // 최근 메시지가 추가되어야 함

    @Builder
    public ChatRoomListResponseDto(Long chatRoomId,Long receiverId, String receiverProfileImage, String receiverName, LocalDateTime updateDt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.chatRoomId = chatRoomId;
        this.receiverId = receiverId;
        this.receiverProfileImage = receiverProfileImage;
        this.receiverName = receiverName;
        this.updateDt = updateDt.format(formatter);
    }
}
