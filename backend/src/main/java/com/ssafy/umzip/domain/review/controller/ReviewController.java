package com.ssafy.umzip.domain.review.controller;

import com.ssafy.umzip.domain.member.service.MemberService;
import com.ssafy.umzip.domain.review.dto.AllReviewRequest;
import com.ssafy.umzip.domain.review.dto.CreateReviewRequest;
import com.ssafy.umzip.domain.review.service.ReviewService;
import com.ssafy.umzip.domain.reviewreceiver.dto.TopTagListRequest;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final JwtTokenProvider jwtTokenProvider;

    /*
    리뷰 작성
    * */

    @PostMapping("/insert")
    public ResponseEntity<Object> createReview(@RequestBody CreateReviewRequest createReviewRequest, HttpServletRequest request) {
        // token값에서 작성자 ID 획득
        Long userId = jwtTokenProvider.getId(request);
        createReviewRequest.setFrom(userId);
        return reviewService.creaetReviwe(createReviewRequest);
    }

    @PostMapping("/all")
    public ResponseEntity<Object> resiveReviewPage(@RequestBody AllReviewRequest allReviewRequest) {
        return reviewService.resiveReviewPage(allReviewRequest);
    }
}
