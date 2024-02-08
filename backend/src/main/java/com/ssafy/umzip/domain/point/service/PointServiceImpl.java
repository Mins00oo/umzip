package com.ssafy.umzip.domain.point.service;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.point.dto.PointUsageDto;
import com.ssafy.umzip.domain.point.dto.PointUsageResponseDto;
import com.ssafy.umzip.domain.point.entity.Point;
import com.ssafy.umzip.domain.point.entity.PointChangeType;
import com.ssafy.umzip.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;

    @Override
    public void savePointByUsingClean(Member member, int point) {
        Point.builder()
                .member(member)
                .change(point)
                .message(PointChangeType.SAVE_BY_CLEAN)
                .build();
    }

    @Override
    public void savePointByUsingDeliver(Member member, int point) {
        Point.builder()
                .member(member)
                .change(point)
                .message(PointChangeType.SAVE_BY_DELIVERY)
                .build();

    }

    @Override
    public void usePoint() {

    }

    @Override
    public PointUsageResponseDto retrieveMyPointUsage(Long memberId, Pageable pageable) {
        Page<Point> pointList = pointRepository.findAllByMemberId(memberId, pageable);

        List<PointUsageDto> usageDtoList = pointList.stream()
                .map(PointUsageDto::fromEntity)
                .toList();

        return PointUsageResponseDto.builder()
                .pointUsageDtoList(usageDtoList)
                .totalPage(pointList.getTotalPages())
                .build();
    }
}
