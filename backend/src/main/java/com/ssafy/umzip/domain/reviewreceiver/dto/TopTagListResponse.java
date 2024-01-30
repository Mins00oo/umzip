package com.ssafy.umzip.domain.reviewreceiver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TopTagListResponse {
    private List<String> tag;
    private Long companyId;
}
