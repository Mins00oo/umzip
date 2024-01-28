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
    /*
    {
        "mappingId": Long,
        "price" : Long,
        "detail": String
}
     */
    private Long mappingId;
    private Long price;
    private String detail;
    private CodeSmall codeSmall;
    @Builder
    public DeliveryQuotationRequestDto(Long mappingId, Long price, String detail) {
        this.mappingId = mappingId;
        this.price = price;
        this.detail = detail;
    }
}
