package com.ssafy.umzip.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllReviewRequest {
    private long memberId;
    private String role;
    private int offset;
    private int limit;
}
