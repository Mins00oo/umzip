package com.ssafy.umzip.domain.help.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GivePointRequestDto {

    private Long giverMemberId;
    private Long boardId;

    @Builder
    public GivePointRequestDto(Long giverMemberId, Long boardId) {
        this.giverMemberId = giverMemberId;
        this.boardId = boardId;
    }
}
