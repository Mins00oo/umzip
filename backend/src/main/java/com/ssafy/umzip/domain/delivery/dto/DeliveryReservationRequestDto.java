package com.ssafy.umzip.domain.delivery.dto;

import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.entity.Delivery;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.ssafy.umzip.global.common.CommonMethods.getLocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeliveryReservationRequestDto {
    private Long price;
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
    @Builder
    public DeliveryReservationRequestDto(Long price, Long carId, String departure, String destination, boolean packaging, boolean move, boolean elevator, boolean parking, String startTime, String endTime, String departureDetail, String destinationDetail, String movelist, int sigungu) {
        this.price = price;
        this.carId = carId;
        this.departure = departure;
        this.destination = destination;
        this.packaging = packaging;
        this.move = move;
        this.elevator = elevator;
        this.parking = parking;
        this.startTime = startTime;
        this.endTime = endTime;
        this.departureDetail = departureDetail;
        this.destinationDetail = destinationDetail;
        this.movelist = movelist;
        this.sigungu = sigungu;
    }

    public static Delivery toEntity(DeliveryReservationRequestDto dto){
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
                .startTime(getLocalDateTime(dto.getStartTime()))
                .endTime(getLocalDateTime(dto.getEndTime()))
                .movelist(dto.getMovelist())
                .sigungu(dto.getSigungu())
                .car(dto.getCar())
                .build();
    }
}
