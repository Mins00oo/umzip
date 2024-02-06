package com.ssafy.umzip.domain.trade.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CompleteSaleRequestDto {

    private Long boardId;
    @Setter
    private Long memberId;

    @Builder
    public CompleteSaleRequestDto(Long boardId, Long memberId) {
        this.boardId = boardId;
        this.memberId = memberId;
    }
}
