package com.ssafy.umzip.domain.chat.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor
public class TradeChat {
    @Id
    private String id;

    private Long chatRoomId;

    private String senderId;

    private String content;

    private String itemName;

    private int itemPrice;

    private String itemImageUrl;

    @Builder
    public TradeChat(Long chatRoomId, String senderId, String content, String itemName, int itemPrice, String itemImageUrl) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImageUrl = itemImageUrl;
    }
}
