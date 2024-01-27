package com.ssafy.umzip.domain.reviewreceiver.repository;

import java.util.List;

public interface CustomReviewReceiverRepository {
    List<String> findTopTagsByMemberId(Long memberId, int limit);

}
