package com.ssafy.umzip.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatTradeMessageRequestDto {
    private String tradeItemTitle;

    private Long tradeId;

    private Long tradeItemPrice;

    private String tradeItemImage;


    @Builder
    public ChatTradeMessageRequestDto(String tradeItemTitle, Long tradeId, Long tradeItemPrice, String tradeItemImage) {
        this.tradeItemTitle = tradeItemTitle;
        this.tradeId = tradeId;
        this.tradeItemPrice = tradeItemPrice;
        this.tradeItemImage = tradeItemImage;
    }
}
