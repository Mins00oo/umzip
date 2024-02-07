package com.ssafy.umzip.domain.company.dto;

import com.ssafy.umzip.domain.company.entity.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CompanyResponseDto {
    private String name;

    private String averageScore;

    private String introduction;

    private List<String> mostTag;

    private List<CompanyReviewListResponse> reviewList;

    private String imageUrl;

    @Builder
    public CompanyResponseDto(String name, String averageScore, String introduction,
                              List<String> mostTag,
                              List<CompanyReviewListResponse> reviewList, String imageUrl) {
        this.name = name;
        this.averageScore = averageScore;
        this.introduction = introduction;
        this.mostTag = mostTag;
        this.reviewList = reviewList;
        this.imageUrl = imageUrl;
    }

    public static CompanyResponseDto fromEntity(Company company, String formattedAverageScore,
                                                List<String> tagList, List<CompanyReviewListResponse> companyReviewList) {
        return CompanyResponseDto.builder()
                .name(company.getName())
                .averageScore(formattedAverageScore)
                .introduction(company.getIntroduction())
                .mostTag(tagList)
                .reviewList(companyReviewList)
                .imageUrl(company.getImageUrl())
                .build();
    }
}
