package com.ssafy.umzip.domain.delivery.dto;

import com.ssafy.umzip.domain.delivery.entity.Delivery;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserReservationDto {
    private Long id;
    private LocalDateTime createDt;
    private LocalDateTime startTime;
    private int sigungu;
    private Long status;
    private List<UserDeliveyMappingDto> list= new ArrayList<>();
    @Builder
    public UserReservationDto(Delivery delivery) {
        this.id = delivery.getId();
        this.createDt = delivery.getCreateDt();
        this.startTime = delivery.getStartTime();
        this.sigungu = delivery.getSigungu();
    }
    public void addUserDeliveryMapping(UserDeliveyMappingDto dto){
        list.add(dto);
    }
}

