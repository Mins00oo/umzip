package com.ssafy.umzip.domain.review.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public class CreateReviewRequest {

    private float score;
    private String content;
    private LocalDateTime create_dt;
    @Builder
    public CreateReviewRequest(float score, String content, LocalDateTime time) {
        this.score = score;
        this.content = content;
        this.create_dt = time;
    }

}
