package com.ssafy.umzip.domain.delivery.dto;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryQuotationRequestDto {
    private Long mappingId;
    private Long reissuing;
    private String detail;
    @Builder
    public DeliveryQuotationRequestDto(Long mappingId, Long reissuing, String detail) {
        this.mappingId = mappingId;
        this.reissuing = reissuing;
        this.detail = detail;
    }
}
