package com.ssafy.umzip.domain.dashboard.dto;

import com.ssafy.umzip.domain.clean.dto.user.UserCleanReservationResponseDto;
import com.ssafy.umzip.domain.delivery.dto.UserDeliveryReservationDto;
import com.ssafy.umzip.global.common.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashBoardReservationResponseDto {
    private Role role;
    private UserCleanReservationResponseDto cleanReservationDto;
    private UserDeliveryReservationDto deliveryReservationDto;
    @Builder
    public DashBoardReservationResponseDto(Role role, UserCleanReservationResponseDto cleanReservationDto) {
        this.role = role;
        this.cleanReservationDto = cleanReservationDto;
    }
    @Builder
    public DashBoardReservationResponseDto(Role role, UserDeliveryReservationDto deliveryReservationDto) {
        this.role = role;
        this.deliveryReservationDto = deliveryReservationDto;
    }

}
