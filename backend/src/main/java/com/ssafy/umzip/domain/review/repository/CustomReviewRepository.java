package com.ssafy.umzip.domain.review.repository;

import com.ssafy.umzip.domain.review.dto.MyReceiveReviewRequest;
import com.ssafy.umzip.domain.review.dto.MyReceiveReviewResponse;

import java.util.List;

public interface CustomReviewRepository {
    public List<MyReceiveReviewResponse> findReviewsForUser(MyReceiveReviewRequest request);
}
