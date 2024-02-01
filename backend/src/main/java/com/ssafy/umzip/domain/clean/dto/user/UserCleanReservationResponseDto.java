package com.ssafy.umzip.domain.clean.dto.user;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserCleanReservationResponseDto {
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

}
