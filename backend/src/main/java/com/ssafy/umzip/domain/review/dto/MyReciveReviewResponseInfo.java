package com.ssafy.umzip.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class MyReciveReviewResponseInfo {
    private int board_cnt;
    private int offset;
    private int limit;
    private List<MyReceiveReviewResponse> reviews;
}
