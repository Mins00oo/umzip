package com.ssafy.umzip.domain.clean.dto.user;

import com.ssafy.umzip.domain.dashboard.dto.ReservationDto;
import com.ssafy.umzip.global.common.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString//삭제해야함
@NoArgsConstructor
public class UserCleanReservationResponseDto implements ReservationDto {
    private Long cleanId;
    private LocalDateTime createDt;
    private LocalDateTime reservationTime;
    private String address;
    private Long status;
    private List<UserCleanMappingDto> list =new ArrayList<>();
    @Builder
    public UserCleanReservationResponseDto(Long cleanId, LocalDateTime createDt, LocalDateTime reservationTime, String address) {
        this.cleanId = cleanId;
        this.createDt = createDt;
        this.reservationTime = reservationTime;
        this.address = address;
    }
    public void settingStatus(){
        Long now = 0L;
        for(UserCleanMappingDto dto:list){
            now = Math.max(dto.getCodeSmallId(),now);
        }
    }

    @Override
    public Role getRole() {
        return Role.CLEAN;
    }
}
