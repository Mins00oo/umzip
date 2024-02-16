package com.ssafy.umzip.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ChatRoomListResponseDto {
    private Long chatRoomId;

    private Long receiverId;

    private String receiverProfileImage;

    private String receiverName;

    @Setter
    private String updateDt;

    @Setter
    private String lastContent;

    @Setter
    private long unReadCount;

    @Builder
    public ChatRoomListResponseDto(Long chatRoomId,Long receiverId, String receiverProfileImage, String receiverName,
                                   LocalDateTime updateDt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.chatRoomId = chatRoomId;
        this.receiverId = receiverId;
        this.receiverProfileImage = receiverProfileImage;
        this.receiverName = receiverName;
        this.updateDt = updateDt.format(formatter);
    }
}
