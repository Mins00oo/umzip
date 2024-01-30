package com.ssafy.umzip.domain.delivery.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryCompanyListRequestDto {
    private String startTime;
    private String endTime;
    private int sigungu;
}
