package com.ssafy.umzip.domain.dashboard.dto;

import com.ssafy.umzip.global.common.Role;

import java.time.LocalDateTime;

public interface ReservationDto {
    LocalDateTime getCreateDt();
    Role getRole();
}
