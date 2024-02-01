package com.ssafy.umzip.domain.clean.dto.user;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
public class UserCleanMappingRepoDto {
    private Long cleanId;
    private Long mappingId;
    private Long companyId;
    private String companyName;
    private String imageUrl;
    private String detail;
    private Long codeSmallId;
    private Long price;
    private Long reissuing;
    @Builder
    public UserCleanMappingRepoDto(Long cleanId, Long mappingId, Long companyId, String companyName, String imageUrl, String detail, Long codeSmallId, Long price, Long reissuing) {
        this.cleanId = cleanId;
        this.mappingId = mappingId;
        this.companyId = companyId;
        this.companyName = companyName;
        this.imageUrl = imageUrl;
        this.detail = detail;
        this.codeSmallId = codeSmallId;
        this.price = price;
        this.reissuing = reissuing;
    }
}
