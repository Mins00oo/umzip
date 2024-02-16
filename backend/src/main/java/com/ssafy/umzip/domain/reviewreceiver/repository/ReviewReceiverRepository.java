package com.ssafy.umzip.domain.reviewreceiver.repository;

import com.ssafy.umzip.domain.reviewreceiver.entity.ReviewReceiver;
import com.ssafy.umzip.domain.reviewtag.entity.ReviewTag;
import com.ssafy.umzip.global.common.Role;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewReceiverRepository extends JpaRepository<ReviewReceiver, Long>, CustomReviewReceiverRepository {

    @Query("SELECT AVG(r.score) FROM Review r INNER JOIN ReviewReceiver receive ON r = receive.review" +
            " WHERE receive.member.id = :memberId and receive.receiverRole = :role")
    Optional<Double> findAverageScoreReceivedByMemberIdAndReceiverRole(@Param("memberId") Long memberId,
                                                                       @Param("receiverRole") Role role);

}
