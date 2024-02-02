package com.ssafy.umzip.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ChatMessageResponseDto {
    private String content;

    private Long requesterId;

    private String senderName;

    private String senderId;

    private String senderProfileImage;

    private String sendTime;

    @Builder
    public ChatMessageResponseDto(String content, Long requesterId, String senderName, String senderId, String senderProfileImage, LocalDateTime sendTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.content = content;
        this.requesterId = requesterId;
        this.senderName = senderName;
        this.senderId = senderId;
        this.senderProfileImage = senderProfileImage;
        this.sendTime = sendTime.format(formatter);
    }

}
