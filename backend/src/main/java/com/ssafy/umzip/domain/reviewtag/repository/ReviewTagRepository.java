package com.ssafy.umzip.domain.reviewtag.repository;

import com.ssafy.umzip.domain.reviewtag.entity.ReviewTag;
import com.ssafy.umzip.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {
    // 리뷰ID로 매칭되는 TagID 받아오기
    Optional<List<ReviewTag>> findByReview_Id(Long reviewId);
}
