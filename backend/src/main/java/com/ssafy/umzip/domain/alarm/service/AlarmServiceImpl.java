package com.ssafy.umzip.domain.alarm.service;

import com.ssafy.umzip.domain.alarm.dto.AlarmResponse;
import com.ssafy.umzip.domain.alarm.repository.AlarmCustomRepository;
import com.ssafy.umzip.domain.alarm.repository.AlarmRepository;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmCustomRepository alarmCustomRepository;

    @Override
    public ResponseEntity<List<AlarmResponse>> alarmList(int limit, int offset, Long memberId) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<AlarmResponse> alarmPage = alarmRepository.findAlarmResponseByMemberId(memberId, pageable);

        List<Long> alarmIdList = alarmPage.getContent().stream()
                .map(AlarmResponse::getAlarmId)
                .toList();

        alarmCustomRepository.updateAlarm(alarmIdList);
        return ResponseEntity.status(HttpStatus.OK).body(alarmPage.getContent());
    }
}
