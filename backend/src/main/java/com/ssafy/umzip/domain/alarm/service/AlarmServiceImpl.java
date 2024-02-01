package com.ssafy.umzip.domain.alarm.service;

import com.ssafy.umzip.domain.alarm.dto.AlarmResponse;
import com.ssafy.umzip.domain.alarm.repository.AlarmRepository;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;

    @Override
    public ResponseEntity<List<AlarmResponse>> alarmList(int limit, int offset, Long memberId) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<AlarmResponse> alarmPage = alarmRepository.findAlarmResponseByMemberId(memberId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(alarmPage.getContent());
    }
}
