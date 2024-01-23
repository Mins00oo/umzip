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
    @Builder
    public Car(Long id, String name, Long price, String description, String fuel, int type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.fuel = fuel;
        this.type = type;
    }
}
