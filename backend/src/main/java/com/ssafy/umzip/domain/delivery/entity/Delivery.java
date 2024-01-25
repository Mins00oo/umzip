package com.ssafy.umzip.domain.delivery.entity;

import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Delivery extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;
    //연관관계  car
    @ManyToOne(fetch = FetchType.LAZY) // 1
    @JoinColumn(name = "car_id")
    private Car car;
    //연관관계 image
    // Cascade : Delivery 엔터티를 저장할 때 자동으로 DeliveryImage 엔터티도 저장
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.PERSIST)
    private List<DeliveryImage> images = new ArrayList<>();
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
    public Delivery(Long id, Car car, LocalDateTime startTime, LocalDateTime endTime, String departure, String destination, String departureDetail, String destinationDetail, boolean packaging, boolean move, boolean elevator, boolean parking, String movelist, int sigungu) {
        this.id = id;
        this.car = car;
        this.startTime = startTime;
        this.endTime = endTime;
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
    //add Image
    public void addImage(final DeliveryImage image) {
        images.add(image);
    }
}
