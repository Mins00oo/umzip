package com.ssafy.umzip.domain.delivery.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;
    @Column(name = "car_name")
    private String name;
    @Column(name = "car_price")
    private Long price;
    @Column(name = "car_description")
    private String description;
    @Column(name = "car_fuel")
    private String fuel;
    @Column(name = "car_type")
    private int type;
    @Column(name = "car_mileage")
    private double mileage;


}
