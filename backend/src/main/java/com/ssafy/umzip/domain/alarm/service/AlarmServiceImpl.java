package com.ssafy.umzip.domain.alarm.service;

import com.ssafy.umzip.domain.alarm.dto.AlarmResponse;
import com.ssafy.umzip.domain.alarm.repository.AlarmCustomRepository;
import com.ssafy.umzip.domain.alarm.repository.AlarmRepository;
import com.ssafy.umzip.domain.company.repository.CompanyRepository;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
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
    private final CompanyRepository companyRepository;

    @Override
    public ResponseEntity<List<AlarmResponse>> alarmList(int limit, int offset, Long memberId, String role) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<AlarmResponse> alarmPage = alarmRepository.findAlarmResponseByMemberId(resolveMemberIdByRole(memberId, role), pageable);

        List<Long> alarmIdList = alarmPage.getContent().stream()
                .map(AlarmResponse::getAlarmId)
                .toList();

        alarmCustomRepository.updateAlarm(alarmIdList);
        return ResponseEntity.status(HttpStatus.OK).body(alarmPage.getContent());
    }

    private Long resolveMemberIdByRole(Long id, String role) {
        if (role.equals("DELIVER") || role.equals("CLEAN")) {
            return companyRepository.findById(id)
                    .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_COMMENT_PK))
                    .getMember().getId();
        }
        return id;
    }
}
