package com.ssafy.umzip.domain.reviewtag.service;

import com.ssafy.umzip.domain.reviewtag.dto.TagIdByReviewIdResponse;
import com.ssafy.umzip.domain.reviewtag.entity.ReviewTag;

import java.util.List;
import java.util.Optional;

public interface ReviewTagService {
    // 리뷰ID로 매칭되는 TagID 받아오기
    List<TagIdByReviewIdResponse> findMatchTag(long reviewId);
}
