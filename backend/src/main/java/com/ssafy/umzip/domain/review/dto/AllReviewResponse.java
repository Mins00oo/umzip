package com.ssafy.umzip.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllReviewResponse {
    private int totalReviews;
    private int offset;
    private int limit;
    private List<AllTagResponse> reviews;
}
