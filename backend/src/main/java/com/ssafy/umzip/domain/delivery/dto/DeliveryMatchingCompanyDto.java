package com.ssafy.umzip.domain.delivery.dto;

import com.ssafy.umzip.domain.company.dto.CompanyReviewListResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/*
{
    "companys" : [
        {
        "companyId":Long,
        "experience" : LocalDateTime,
        "imageUrl" : String,
        "ceo" : string
        "tags" : [ //3개
                int
        ]
        }
//buisiness Authentication
    ]
}
 */
@Getter
@Setter
@ToString//지워야함
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryMatchingCompanyDto {
    private Long companyId;
    private Long memberId;
    private LocalDateTime experience;
    private String imageUrl;
    private String ceo;
    private List<String> topTagList;
    public DeliveryMatchingCompanyDto(Long companyId, Long memberId, LocalDateTime experience, String imageUrl, String ceo) {
        this.companyId = companyId;
        this.memberId = memberId;
        this.experience = experience;
        this.imageUrl = imageUrl;
        this.ceo = ceo;
    }


}
