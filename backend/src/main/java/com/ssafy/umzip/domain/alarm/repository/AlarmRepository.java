package com.ssafy.umzip.domain.alarm.repository;

import com.ssafy.umzip.domain.alarm.dto.AlarmResponse;
import com.ssafy.umzip.domain.alarm.entity.Alarm;
import com.ssafy.umzip.domain.reviewtag.entity.ReviewTag;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

    public interface AlarmRepository extends JpaRepository<Alarm, Long> {
        @Query("SELECT new com.ssafy.umzip.domain.alarm.dto.AlarmResponse(a.id, a.content, a.isRead, a.createDt, a.imgPath) FROM Alarm a WHERE a.member.id = :memberId Order By a.createDt desc")
        Page<AlarmResponse> findAlarmResponseByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}
