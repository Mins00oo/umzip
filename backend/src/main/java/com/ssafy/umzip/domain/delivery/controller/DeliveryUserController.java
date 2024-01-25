package com.ssafy.umzip.domain.delivery.controller;

import com.ssafy.umzip.domain.delivery.dto.DeliveryRequestDto;
import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery/user")
public class DeliveryUserController {
    private final DeliveryService deliveryService;
    @PostMapping("/test")
    public int testMethod(@RequestBody DeliveryRequestDto dto){
        deliveryService.createDelivery(dto);
        return 0;
    }
}
