package com.ssafy.umzip.domain.review.controller;

import com.ssafy.umzip.domain.member.service.MemberService;
import com.ssafy.umzip.domain.review.dto.CreateReviewRequest;
import com.ssafy.umzip.domain.review.service.ReviewService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Object> createReview(CreateReviewRequest createReviewRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }
}
