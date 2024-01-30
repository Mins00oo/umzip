package com.ssafy.umzip.domain.reviewreceiver.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopTagListRequest {
    private int limit;
    private List<Long> memberId;
    private String role;

}
