package com.ssafy.umzip.domain.reviewreceiver.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ScoreInfoDto {
    private Long memberId;
    private Double score;
    @Builder
    public ScoreInfoDto(Long memberId, Double score) {
        this.memberId = memberId; //company의 memberId임
        this.score = score;
    }
}
