package com.ssafy.umzip.domain.alarm.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.umzip.domain.alarm.entity.QAlarm.alarm;

@RequiredArgsConstructor
@Repository
public class AlarmCustomRepositoryImpl implements AlarmCustomRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public boolean updateAlarm(List<Long> alarmIdList) {
        long size = queryFactory.update(alarm)
                .set(alarm.isRead, true)
                .where(alarm.id.in(alarmIdList))
                .execute();
        return size == alarmIdList.size();

    }
}
