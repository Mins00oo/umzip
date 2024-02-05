package com.ssafy.umzip.domain.chat.entity;

import com.ssafy.umzip.global.common.BaseTimeDocument;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@NoArgsConstructor
public class ChatMessage extends BaseTimeDocument {
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
