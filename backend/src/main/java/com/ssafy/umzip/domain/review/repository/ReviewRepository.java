package com.ssafy.umzip.domain.review.repository;

import com.ssafy.umzip.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<Review, Long> {

}
