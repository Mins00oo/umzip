package com.ssafy.umzip.domain.trade.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DetailRequestDto {

    private Long memberId;
    private Long boardId;

    @Builder
    public DetailRequestDto(Long memberId, Long boardId) {
        this.memberId = memberId;
        this.boardId = boardId;
    }
}
