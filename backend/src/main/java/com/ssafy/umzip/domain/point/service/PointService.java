package com.ssafy.umzip.domain.point.service;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.point.dto.PointUsageResponseDto;
import org.springframework.data.domain.Pageable;

public interface PointService {
    void savePointByUsingClean(Member member, int point);

    void savePointByUsingDeliver(Member member, int point);


    void usePoint();

    PointUsageResponseDto retrieveMyPointUsage(Long memberId, Pageable pageable);
}
