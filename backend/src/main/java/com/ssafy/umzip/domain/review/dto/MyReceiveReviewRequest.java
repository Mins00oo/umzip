package com.ssafy.umzip.domain.review.dto;

import com.ssafy.umzip.global.common.Role;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyReceiveReviewRequest {
    private long memberId;
    private String role;
    private int offset;
    private int limit;
}
