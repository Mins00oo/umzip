package com.ssafy.umzip.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatTradeMessageRequestDto {
    private String content;

    private String type;

    private Long tradeId;

}
