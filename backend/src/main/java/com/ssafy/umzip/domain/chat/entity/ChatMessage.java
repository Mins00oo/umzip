package com.ssafy.umzip.domain.chat.entity;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.BaseTimeDocument;
import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document
@ToString
public class ChatMessage extends BaseTimeDocument {
    @Id
    private String id;

    private Long chatRoomId;

    private String senderId;

    private String senderName;

    private String senderProfileImage;

    private String content;

    @Builder
    public ChatMessage(Long chatRoomId, String senderId, String senderName, String senderProfileImage, String content) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderProfileImage = senderProfileImage;
        this.content = content;
    }
}
