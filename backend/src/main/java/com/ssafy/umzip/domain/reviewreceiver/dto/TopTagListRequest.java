package com.ssafy.umzip.domain.reviewreceiver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class TopTagListRequest {
    private int limit;
    private List<Long> memberId;
    private String role;
}
