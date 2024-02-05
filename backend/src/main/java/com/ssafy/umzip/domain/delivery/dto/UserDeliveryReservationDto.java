package com.ssafy.umzip.domain.delivery.dto;

import com.ssafy.umzip.domain.dashboard.dto.ReservationDto;
import com.ssafy.umzip.domain.delivery.entity.Delivery;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString//삭제해야함.
@NoArgsConstructor
public class UserDeliveryReservationDto implements ReservationDto {
    private Long id;
    private LocalDateTime createDt;
    private LocalDateTime startTime;
    private String departure;
    private Long status;
    private List<UserDeliveryMappingDto> list= new ArrayList<>();
    @Builder
    public UserDeliveryReservationDto(Delivery delivery) {
        this.id = delivery.getId();
        this.createDt = delivery.getCreateDt();
        this.startTime = delivery.getStartTime();
        this.departure = delivery.getDeparture();
    }
    @Builder
    public UserDeliveryReservationDto(Long id, LocalDateTime createDt, LocalDateTime startTime, String departure, Long status) {
        this.id = id;
        this.createDt = createDt;
        this.startTime = startTime;
        this.departure = departure;
        this.status = status;
    }
    @Builder
    public UserDeliveryReservationDto(Long id, LocalDateTime createDt, LocalDateTime startTime, String departure) {
        this.id = id;
        this.createDt = createDt;
        this.startTime = startTime;
        this.departure = departure;
    }
    public void addUserDeliveryMapping(UserDeliveryMappingDto dto){
        list.add(dto);
    }
}

