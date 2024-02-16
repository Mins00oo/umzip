package com.ssafy.umzip.domain.trade.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CompleteRequestDto {

    private Long boardId;
    @Setter
    private Long memberId;

    @Builder
    public CompleteRequestDto(Long boardId, Long memberId) {
        this.boardId = boardId;
        this.memberId = memberId;
    }
}
