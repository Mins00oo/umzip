package com.ssafy.umzip.domain.delivery.dto;

import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDeliveyMappingDto {
    private Long mappingId;
    private Long companyId;
    private String companyName;
    private String imageUrl;
    private String detail;
    private Long codeSmallId;

    @Builder
    public UserDeliveyMappingDto(Long mappingId, Long companyId, String companyName, String imageUrl, String detail, Long codeSmallId) {
        this.mappingId = mappingId;
        this.companyId = companyId;
        this.companyName = companyName;
        this.imageUrl = imageUrl;
        this.detail = detail;
        this.codeSmallId = codeSmallId;
    }
}
