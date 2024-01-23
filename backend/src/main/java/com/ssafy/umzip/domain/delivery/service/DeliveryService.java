package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.dto.DeliveryRequestDto;
import com.ssafy.umzip.domain.delivery.entity.Car;

import java.util.Optional;

public interface DeliveryService {
     //car Test
     Optional<Car> getCarList(Long id);
     //사용자
     void createDelivery(DeliveryRequestDto dto);
     void calculateDelivery();
     void cancelDelivery();
     void companyListDelivery();
     void userReservationDelivery();
     //업체
     void rejectionDelivery();
     void quotationDelivery();
     void companyReservationDelivery();

}
