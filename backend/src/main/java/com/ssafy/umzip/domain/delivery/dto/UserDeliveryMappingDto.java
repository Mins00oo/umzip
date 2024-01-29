package com.ssafy.umzip.domain.delivery.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDeliveryMappingDto {
    private Long mappingId;
    private Long companyId;
    private String companyName;
    private String imageUrl;
    private String detail;
    private Long codeSmallId;

    @Builder
    public UserDeliveryMappingDto(Long mappingId, Long companyId, String companyName, String imageUrl, String detail, Long codeSmallId) {
        this.mappingId = mappingId;
        this.companyId = companyId;
        this.companyName = companyName;
        this.imageUrl = imageUrl;
        this.detail = detail;
        this.codeSmallId = codeSmallId;
    }
}
