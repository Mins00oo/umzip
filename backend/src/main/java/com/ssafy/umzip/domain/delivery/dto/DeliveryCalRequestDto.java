package com.ssafy.umzip.domain.delivery.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class DeliveryCalRequestDto {
    private Long carId;
    private String departureX;
    private String departureY;
    private String destinationX;
    private String destinationY;
    private boolean packaging;
    private boolean move;
    private boolean elevator;
    private boolean parking;
    private String startTime;

    public DeliveryCalRequestDto(Long carId, String departureX, String departureY, String destinationX, String destinationY, boolean packaging, boolean move, boolean elevator, boolean parking, String startTime) {
        this.carId = carId;
        this.departureX = departureX;
        this.departureY = departureY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.packaging = packaging;
        this.move = move;
        this.elevator = elevator;
        this.parking = parking;
        this.startTime = startTime;
    }
}

