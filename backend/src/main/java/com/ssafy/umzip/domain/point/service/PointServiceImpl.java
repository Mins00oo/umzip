package com.ssafy.umzip.domain.point.service;

import com.ssafy.umzip.domain.point.dto.PointUsageDto;
import com.ssafy.umzip.domain.point.dto.PointUsageResponseDto;
import com.ssafy.umzip.domain.point.entity.Point;
import com.ssafy.umzip.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;

    @Override
    public void savePoint() {

    }

    @Override
    public void userPoint() {

    }

    @Override
    public PointUsageResponseDto retrieveMyPointUsage(Long memberId, Pageable pageable) {
        List<Point> pointList = pointRepository.findAllByMemberId(memberId, pageable);

        List<PointUsageDto> usageDtoList = pointList.stream()
                .map(PointUsageDto::fromEntity)
                .toList();

        return PointUsageResponseDto.builder()
                .pointUsageDtoList(usageDtoList)
                .build();
    }
}
