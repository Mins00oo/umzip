package com.ssafy.umzip.domain.review.service;

import com.ssafy.umzip.domain.review.dto.AllReviewRequest;
import com.ssafy.umzip.domain.review.dto.CreateReviewRequest;
import com.ssafy.umzip.domain.reviewreceiver.dto.TopTagListRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    ResponseEntity<Object> creaetReviwe(CreateReviewRequest createReviewRequest);


    ResponseEntity<Object> resiveReviewPage(AllReviewRequest allReviewRequest);

}

