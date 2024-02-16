package com.ssafy.umzip.domain.help.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdoptCommentRequestDto {

    private Long memberId;
    private Long commentId;

    @Builder
    AdoptCommentRequestDto(Long memberId, Long commentId) {
        this.memberId = memberId;
        this.commentId = commentId;
    }
}
