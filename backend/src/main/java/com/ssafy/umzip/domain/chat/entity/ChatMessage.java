package com.ssafy.umzip.domain.chat.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor
public class ChatMessage {
    @Id
    private String id;

    private Long chatRoomId;

    private String senderId;

    private String content;

    @Builder
    public ChatMessage(Long chatRoomId, String senderId, String content) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
    }
}
