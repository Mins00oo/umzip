package com.ssafy.umzip.domain.company.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CompanyReviewListResponse {
    private Long reviewId;

    private String writerName;

    private String writerProfileImage;

    private List<String> tagList;

    private String content;

    private String createDt;

    private float score;

    @Builder
    public CompanyReviewListResponse(Long reviewId, String writerName, String writerProfileImage, String content,
                                     String createDt, float score, List<String> tagList) {
        this.reviewId = reviewId;
        this.writerName = writerName;
        this.writerProfileImage = writerProfileImage;
        this.content = content;
        this.createDt = createDt;
        this.score = score;
        this.tagList = tagList;
    }
}
