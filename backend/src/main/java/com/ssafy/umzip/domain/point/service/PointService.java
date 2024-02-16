package com.ssafy.umzip.domain.point.service;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.point.dto.PointUsageResponseDto;
import org.springframework.data.domain.Pageable;

public interface PointService {
    void savePointByUsingClean(Member member, int point);

    void savePointByUsingDeliver(Member member, int point);

    void savePointByTradeReview(Member member, int point);

    void savePointByDeliverReview(Member member, int point);

    void savePointByCleanReview(Member member, int point);

    void savePointByHelpPeople(Member member, int point);


    void usePointByDeliver(Member member, int point);

    void usePointByClean(Member member, int point);

    void usePointByHelpMe(Member member, int point);

    void usePointByHelpedByPeople(Member member, int point);

    PointUsageResponseDto retrieveMyPointUsage(Long memberId, Pageable pageable);
}
