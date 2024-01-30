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
    long memberId;
    String role;
    int offest;
    int limit;
}
