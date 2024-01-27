package com.ssafy.umzip.domain.reviewreceiver.repository;

import com.ssafy.umzip.domain.reviewreceiver.entity.ReviewReceiver;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewReceiverRepository extends JpaRepository<ReviewReceiver, Long>, CustomReviewReceiverRepository{

    @Query("SELECT AVG(r.score) FROM Review r INNER JOIN ReviewReceiver receive ON r = receive.review" +
            " WHERE receive.member.id = :memberId")
    Optional<Double> findAverageScoreReceivedByMemberId(@Param("memberId") Long memberId);

}
