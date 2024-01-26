package com.ssafy.umzip.domain.delivery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryRequestCompanyDto {
    private Long memberId;

    public DeliveryRequestCompanyDto(Long memberId) {
        this.memberId = memberId;
    }
}
