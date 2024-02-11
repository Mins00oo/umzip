package com.ssafy.umzip.domain.review.service;

import com.ssafy.umzip.domain.review.dto.*;
import com.ssafy.umzip.domain.reviewreceiver.dto.TopTagListRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    ResponseEntity<Object> creaetReviwe(CreateReviewRequest createReviewRequest);

    ResponseEntity<List<MyReceiveReviewResponse>> myReceiveReviewRequest(MyReceiveReviewRequest myReceiveReviewRequest);
    public ResponseEntity<MyReciveReviewResponseInfo> myWriteReviewRequest(MyReceiveReviewRequest myReceiveReviewRequest);
}

