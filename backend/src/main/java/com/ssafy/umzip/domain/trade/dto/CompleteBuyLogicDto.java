package com.ssafy.umzip.domain.trade.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CompleteBuyLogicDto {

    private Long boardTradeId;
    private Long buyerId;
    private boolean isActive;

    @Builder
    public CompleteBuyLogicDto(Long boardTradeId, Long buyerId, boolean isActive) {
        this.boardTradeId = boardTradeId;
        this.buyerId = buyerId;
        this.isActive = isActive;
    }
}
