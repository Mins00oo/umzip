package com.ssafy.umzip.domain.trade.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ListRequestDto {

    private Long memberId;
    private int sigungu;
    private String keyword;

    @Builder
    public ListRequestDto(Long memberId, int sigungu,
                          String keyword) {
        this.memberId = memberId;
        this.sigungu = sigungu;
        this.keyword = keyword;
    }
}
