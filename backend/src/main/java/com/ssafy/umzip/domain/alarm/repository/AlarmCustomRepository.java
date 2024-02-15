package com.ssafy.umzip.domain.alarm.repository;

import java.util.List;

public interface AlarmCustomRepository {
    boolean updateAlarm(List<Long> alarmIdList);
}
