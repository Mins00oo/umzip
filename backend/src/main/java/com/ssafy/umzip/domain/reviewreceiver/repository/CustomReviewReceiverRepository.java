package com.ssafy.umzip.domain.reviewreceiver.repository;

import com.ssafy.umzip.domain.company.dto.CompanyReviewListResponse;
import com.ssafy.umzip.domain.reviewreceiver.dto.ScoreInfoDto;
import com.ssafy.umzip.domain.reviewreceiver.dto.TopTagListRequest;
import com.ssafy.umzip.domain.reviewreceiver.dto.TopTagListResponse;
import com.ssafy.umzip.global.common.Role;

import java.util.List;

public interface CustomReviewReceiverRepository {
    List<String> findTopTagsByMemberId(Long memberId, int limit, Role role);

    List<CompanyReviewListResponse> findReviewByMemberIdAndRole(Long memberId, int limit, Role role);
    List<TopTagListResponse> findTopTagsListByMemberIdAndRole(TopTagListRequest companyList);
    List<ScoreInfoDto> findScoreByCompanyMemberId(List<Long> companyList,Role role);
}
