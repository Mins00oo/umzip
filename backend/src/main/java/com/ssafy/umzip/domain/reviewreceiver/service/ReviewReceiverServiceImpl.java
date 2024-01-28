package com.ssafy.umzip.domain.reviewreceiver.service;

import com.ssafy.umzip.domain.reviewreceiver.repository.ReviewReceiverRepository;
import com.ssafy.umzip.global.common.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewReceiverServiceImpl implements ReviewReceiverService {
    private final ReviewReceiverRepository reviewReceiverRepository;
    @Override
    public String receiverReviewScore(Long memberId, Role role) {
        Double averageScore = reviewReceiverRepository.findAverageScoreReceivedByMemberIdAndReceiverRole(memberId,role)
                .orElse(0.0);

        BigDecimal formattedAverageScore = new BigDecimal(String.valueOf(averageScore))
                .setScale(2, RoundingMode.HALF_UP);
        return formattedAverageScore.toString();
    }
}
