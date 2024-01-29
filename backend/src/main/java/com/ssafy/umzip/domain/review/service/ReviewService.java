package com.ssafy.umzip.domain.review.service;

import com.ssafy.umzip.domain.review.dto.CreateReviewRequest;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<Object> creaetReviwe(CreateReviewRequest createReviewRequest);



}

