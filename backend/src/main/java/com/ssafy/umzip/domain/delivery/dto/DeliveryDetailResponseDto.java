package com.ssafy.umzip.domain.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryDetailResponseDto {
    private Long id;
    private String carName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String departure;
    private String departureDetail;
    private String destination;
    private String destinationDetail;
    private Boolean packaging;
    private Boolean move;
    private Boolean elevator;
    private Boolean parking;
    private String movelist;
    private List<String> deliveryImages = new ArrayList<>();
    @Builder

    public DeliveryDetailResponseDto(Long id, String carName, LocalDateTime startTime, LocalDateTime endTime, String departure, String departureDetail, String destination, String destinationDetail, Boolean packaging, Boolean move, Boolean elevator, Boolean parking, String movelist) {
        this.id = id;
        this.carName = carName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.departure = departure;
        this.departureDetail = departureDetail;
        this.destination = destination;
        this.destinationDetail = destinationDetail;
        this.packaging = packaging;
        this.move = move;
        this.elevator = elevator;
        this.parking = parking;
        this.movelist = movelist;
    }
}
