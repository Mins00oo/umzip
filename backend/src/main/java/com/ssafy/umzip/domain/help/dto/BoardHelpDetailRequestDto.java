package com.ssafy.umzip.domain.help.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardHelpDetailRequestDto {

    private Long boardId;
    private Long memberId;

    @Builder
    public BoardHelpDetailRequestDto(Long boardId, Long memberId) {
        this.boardId = boardId;
        this.memberId = memberId;
    }

}
