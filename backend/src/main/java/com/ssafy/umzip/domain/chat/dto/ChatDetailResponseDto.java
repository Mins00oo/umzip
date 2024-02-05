package com.ssafy.umzip.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatDetailResponseDto {
    List<ChatMessageResponseDto> chatMessages;

    ChatTradeMessageRequestDto tradeItem;

    @Builder
    public ChatDetailResponseDto(List<ChatMessageResponseDto> chatMessages, ChatTradeMessageRequestDto tradeItem) {
        this.chatMessages = chatMessages;
        this.tradeItem = tradeItem;
    }
}
