package com.ssafy.umzip.domain.delivery.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDeliveryMappingDto {
    private Long mappingId;
    private Long companyId;
    private Long memberId;
    private String companyName;
    private String imageUrl;
    private String detail;
    private Long codeSmallId;
    private Long price;
    private Long reissuing;

    @Builder

    public UserDeliveryMappingDto(Long mappingId, Long companyId,Long memberId, String companyName, String imageUrl, String detail, Long codeSmallId, Long price, Long reissuing) {
        this.mappingId = mappingId;
        this.companyId = companyId;
        this.memberId = memberId;
        this.companyName = companyName;
        this.imageUrl = imageUrl;
        this.detail = detail;
        this.codeSmallId = codeSmallId;
        this.price = price;
        this.reissuing = reissuing;
    }
}
