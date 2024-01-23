package com.ssafy.umzip.domain.delivery.controller;

import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery/user")
public class DeliveryUserController {
    private final DeliveryService deliveryService;
    @GetMapping("/test")
    public List<Car> testMethod(){
        return deliveryService.getCarList();
    }
}
