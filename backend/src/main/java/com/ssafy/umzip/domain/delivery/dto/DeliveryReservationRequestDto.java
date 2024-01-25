package com.ssafy.umzip.domain.delivery.dto;

import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.entity.Delivery;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
@NoArgsConstructor
public class DeliveryReservationRequestDto {

    private Long carId;
    private Car car;
    private String departure;
    private String destination;
    private boolean packaging;
    private boolean move;
    private boolean elevator;
    private boolean parking;
    private String startTime;
    private String endTime;
    private String departureDetail;
    private String destinationDetail;
    private String movelist;
    private int sigungu;
    public static Delivery toEntity(DeliveryReservationRequestDto dto){
        //날짜 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(dto.getStartTime(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(dto.getEndTime(), formatter);
        //build
        return Delivery.builder()
                .departure(dto.getDeparture())
                .departureDetail(dto.getDepartureDetail())
                .destination(dto.getDestination())
                .destinationDetail(dto.getDestinationDetail())
                .packaging(dto.isPackaging())
                .move(dto.isMove())
                .elevator(dto.isElevator())
                .parking(dto.isParking())
                .startTime(startTime)
                .endTime(endTime)
                .movelist(dto.getMovelist())
                .sigungu(dto.getSigungu())
                .car(dto.getCar())
                .build();
    }
}
