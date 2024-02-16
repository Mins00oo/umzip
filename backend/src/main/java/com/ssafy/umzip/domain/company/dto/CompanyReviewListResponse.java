package com.ssafy.umzip.domain.company.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class CompanyReviewListResponse {
    private Long reviewId;

    private String writerName;

    private String writerProfileImage;

    @Setter
    private List<MostTagResponseDto> tagList;

    private String content;

    private String createDt;

    private float score;

    public CompanyReviewListResponse(Long reviewId, String writerName, String writerProfileImage, String content,
                                     LocalDateTime createDt, float score) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.reviewId = reviewId;
        this.writerName = writerName;
        this.writerProfileImage = writerProfileImage;
        this.content = content;
        this.createDt = createDt.format(formatter);
        this.score = score;
    }

}
