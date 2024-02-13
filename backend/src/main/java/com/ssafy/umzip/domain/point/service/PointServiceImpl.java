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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;

    @Transactional
    @Override
    public void savePointByUsingClean(Member member, int point) {
        savePoint(member, point, PointChangeType.SAVE_BY_CLEAN);
    }

    @Transactional
    @Override
    public void savePointByUsingDeliver(Member member, int point) {
        savePoint(member, point, PointChangeType.SAVE_BY_DELIVER);
    }

    @Transactional
    @Override
    public void savePointByTradeReview(Member member, int point) {
        savePoint(member, point, PointChangeType.SAVE_BY_TRADE_REVIEW);
    }

    @Transactional
    @Override
    public void savePointByDeliverReview(Member member, int point) {
        savePoint(member, point, PointChangeType.SAVE_BY_DELIVER_REVIEW);
    }

    @Transactional
    @Override
    public void savePointByCleanReview(Member member, int point) {
        savePoint(member, point, PointChangeType.SAVE_BY_CLEAN_REVIEW);
    }

    @Transactional
    @Override
    public void savePointByHelpPeople(Member member, int point) {
        savePoint(member, point, PointChangeType.SAVE_BY_HELP);
    }

    @Override
    public void usePointByDeliver(Member member, int point) {
        usePoint(member, point, PointChangeType.USE_BY_DELIVER);
    }

    @Override
    public void usePointByClean(Member member, int point) {
        usePoint(member, point, PointChangeType.USE_BY_CLEAN);
    }

    @Override
    public void usePointByHelpMe(Member member, int point) {
        usePoint(member, point, PointChangeType.USE_BY_HELP_ME);
    }

    @Override
    public void usePointByHelpedByPeople(Member member, int point) {
        usePoint(member, point, PointChangeType.USE_BY_HELP_BY_PEOPLE);
    }

    private void savePoint(Member member, int point, PointChangeType changeType) {
        Point entity = Point.builder()
                .member(member)
                .change(point)
                .message(changeType)
                .build();

        pointRepository.save(entity);

        member.savePoint(point);
    }

    private void usePoint(Member member, int point, PointChangeType changeType) {
        Point entity = Point.builder()
                .member(member)
                .change(-point)
                .message(changeType)
                .build();

        pointRepository.save(entity);

        member.usePoint(point);
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
