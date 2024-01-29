package com.ssafy.umzip.domain.delivery.dto;

import com.ssafy.umzip.domain.delivery.entity.Delivery;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserReservationDto {
    private Long id;
    private LocalDateTime createDt;
    private LocalDateTime startTime;
    private int sigungu;
    private Long status;
    private List<UserDeliveryMappingDto> list= new ArrayList<>();
    @Builder
    public UserReservationDto(Delivery delivery) {
        this.id = delivery.getId();
        this.createDt = delivery.getCreateDt();
        this.startTime = delivery.getStartTime();
        this.sigungu = delivery.getSigungu();
    }
    @Builder
    public UserReservationDto(Long id, LocalDateTime createDt, LocalDateTime startTime, int sigungu, Long status) {
        this.id = id;
        this.createDt = createDt;
        this.startTime = startTime;
        this.sigungu = sigungu;
        this.status = status;
    }
    @Builder
    public UserReservationDto(Long id, LocalDateTime createDt, LocalDateTime startTime, int sigungu) {
        this.id = id;
        this.createDt = createDt;
        this.startTime = startTime;
        this.sigungu = sigungu;
    }
    public void addUserDeliveryMapping(UserDeliveryMappingDto dto){
        list.add(dto);
    }
}

