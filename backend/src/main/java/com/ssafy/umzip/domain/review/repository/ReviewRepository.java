package com.ssafy.umzip.domain.review.repository;

import com.ssafy.umzip.domain.review.dto.MyReceiveReviewResponse;
import com.ssafy.umzip.domain.review.entity.Review;
import com.ssafy.umzip.global.common.Role;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
    @Query("SELECT NEW com.ssafy.umzip.domain.review.dto.MyReceiveReviewResponse(r.id, m.name, m.imageUrl, rr.receiverRole, r.content, r.score, r.createDt) " +
            "FROM ReviewReceiver rr " +
            "JOIN rr.review r ON r.id = rr.review.id " +
            "JOIN r.member m ON m.id = r.member.id " +
            "WHERE rr.member.id = :memberId AND rr.receiverRole = :receiverRole " +
            "ORDER BY r.createDt DESC")
    Page<MyReceiveReviewResponse> findReviewDetailsByMemberIdAndRole(
            @Param("memberId") Long memberId,
            @Param("receiverRole") Role receiverRole,
            Pageable pageable
    );

    @Query("SELECT NEW com.ssafy.umzip.domain.review.dto.MyReceiveReviewResponse(" +
            "r.id, " +
            "COALESCE(c.name, m.name), " +
            "COALESCE(c.imageUrl, m.imageUrl), " +
            "rr.receiverRole, " +
            "r.content, " +
            "r.score, " +
            "r.createDt) " +
            "FROM Review r " +
            "JOIN ReviewReceiver rr ON r.id = rr.review.id " +
            "LEFT JOIN Company c ON rr.member.id = c.member.id  AND c.role = rr.receiverRole " +
            "LEFT JOIN Member m ON rr.member.id = m.id AND c.role IS NULL " +
            "WHERE r.member.id = :memberId " +
            "ORDER BY r.createDt DESC")
    Page<MyReceiveReviewResponse> findWriteReviewDetailsByMemberIdAndRole(
            @Param("memberId") Long memberId,
            Pageable pageable
    );



}
