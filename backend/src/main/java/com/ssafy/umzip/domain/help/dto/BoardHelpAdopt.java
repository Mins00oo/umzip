package com.ssafy.umzip.domain.help.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardHelpAdopt {

    private Long memberId;
    private Long commentId;

    @Builder
    BoardHelpAdopt(Long memberId, Long commentId) {
        this.memberId = memberId;
        this.commentId = commentId;
    }
}
