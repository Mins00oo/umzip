package com.ssafy.umzip.domain.delivery.entity;

import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Delivery extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 1
    @JoinColumn(name = "car_id")
    private Car car;
    @Column(name = "delivery_start_time")
    private LocalDateTime startTime;
    @Column(name = "delivery_end_time")
    private LocalDateTime endTime;
    @Column(name = "delivery_departure")
    private String departure;
    @Column(name = "delivery_destination")
    private String destination;
    @Column(name = "delivery_departure_detail")
    private String departureDetail;
    @Column(name = "delivery_destination_detail")
    private String destinationDetail;
    @Column(name = "delivery_packaging")
    private boolean packaging;
    @Column(name = "delivery_move")
    private boolean move;
    @Column(name = "delivery_elevator")
    private boolean elevator;
    @Column(name = "delivery_parking")
    private boolean parking;
    @Column(name = "delivery_movelist")
    private String movelist;
    @Column(name = "delivery_sigungu")
    private int sigungu;
    @Builder
    public Delivery(String departure, String destination, boolean packaging, boolean move, boolean elevator, boolean parking, String movelist, int sigungu, String departureDetail, String destinationDetail) {
        this.departure = departure;
        this.destination = destination;
        this.departureDetail = departureDetail;
        this.destinationDetail = destinationDetail;
        this.packaging = packaging;
        this.move = move;
        this.elevator = elevator;
        this.parking = parking;
        this.movelist = movelist;
        this.sigungu = sigungu;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
