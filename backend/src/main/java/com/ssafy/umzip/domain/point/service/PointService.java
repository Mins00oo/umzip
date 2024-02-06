package com.ssafy.umzip.domain.point.service;

import com.ssafy.umzip.domain.point.dto.PointUsageResponseDto;
import org.springframework.data.domain.Pageable;

public interface PointService {
    void savePoint();

    void userPoint();

    PointUsageResponseDto retrieveMyPointUsage(Long memberId, Pageable pageable);
}
