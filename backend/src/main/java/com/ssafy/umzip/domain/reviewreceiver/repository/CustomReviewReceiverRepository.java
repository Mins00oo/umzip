package com.ssafy.umzip.domain.reviewreceiver.repository;

import com.ssafy.umzip.domain.company.dto.CompanyReviewListResponse;
import com.ssafy.umzip.global.common.Role;

import java.util.List;

public interface CustomReviewReceiverRepository {
    List<String> findTopTagsByMemberId(Long memberId, int limit, Role role);

    List<CompanyReviewListResponse> findReviewByMemberIdAndRole(Long memberId, int limit, Role role);

}
