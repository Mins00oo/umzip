package com.ssafy.umzip.domain.clean.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserCleanMappingDto {
    private Long mappingId;
    private Long companyId;
    private Long companyMemberId;
    private String companyName;
    private String imageUrl;
    private String detail;
    private Long codeSmallId;
    private Long price;
    private Long reissuing;
    @Builder
    public UserCleanMappingDto(Long mappingId, Long companyId, Long companyMemberId,String companyName, String imageUrl, String detail, Long codeSmallId, Long price, Long reissuing) {
        this.mappingId = mappingId;
        this.companyId = companyId;
        this.companyMemberId = companyMemberId;
        this.companyName = companyName;
        this.imageUrl = imageUrl;
        this.detail = detail;
        this.codeSmallId = codeSmallId;
        this.price = price;
        this.reissuing = reissuing;
    }
}
