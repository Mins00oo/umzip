package com.ssafy.umzip.domain.delivery.dto;

import com.ssafy.umzip.domain.company.dto.CompanyReviewListResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryMatchingCompanyDto {
    private Long companyId;
    private Long memberId;
    private LocalDateTime experience;
    private String imageUrl;
    private String ceo;
    private String companyName;
    private List<String> topTagList;
    private Double score;
    public DeliveryMatchingCompanyDto(Long companyId, Long memberId, LocalDateTime experience, String imageUrl, String ceo,String companyName) {
        this.companyId = companyId;
        this.memberId = memberId;
        this.experience = experience;
        this.imageUrl = imageUrl;
        this.ceo = ceo;
        this.companyName = companyName;
    }


}
