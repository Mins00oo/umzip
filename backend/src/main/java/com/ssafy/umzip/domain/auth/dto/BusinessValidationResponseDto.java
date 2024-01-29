package com.ssafy.umzip.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusinessValidationResponseDto {
    private int request_cnt;
    private String status_code;
    private List<BusinessResponseData> data;

}
