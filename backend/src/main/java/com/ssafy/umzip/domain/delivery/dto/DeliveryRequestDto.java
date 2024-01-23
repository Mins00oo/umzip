package com.ssafy.umzip.domain.delivery.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class DeliveryRequestDto {
    private String departure;
    private String destination;
    private String departureDetail;
    private String destinationDetail;
    private boolean packaging;
    private boolean move;
    private boolean elevator;
    private boolean parking;
    private String movelist;
    private int sigungu;

    private String startTime;
    private String endTime;



}

