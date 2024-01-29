package com.ssafy.umzip.domain.reviewreceiver.service;

import com.ssafy.umzip.global.common.Role;

public interface ReviewReceiverService {
    String receiverReviewScore(Long memberId, Role role);
}
