package com.ssafy.umzip.domain.review.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CreateReviewRequest {
    private Long to;
    private Long from;
    private String role;
    private float score;
    private List<Long> tag;
    private String comment;
    private int point;
}
