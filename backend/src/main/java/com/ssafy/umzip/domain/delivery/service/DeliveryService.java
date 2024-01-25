package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.dto.DeliveryCalRequestDto;
import com.ssafy.umzip.domain.delivery.dto.DeliveryReservationRequestDto;
import com.ssafy.umzip.domain.delivery.dto.MobilityDto;
import com.ssafy.umzip.domain.delivery.entity.Car;

import java.util.Optional;

public interface DeliveryService {
     //car Test
     Optional<Car> getCar(Long id);
     //사용자
     void createDelivery(DeliveryReservationRequestDto dto);
     Long calculateDelivery(MobilityDto mobilityDto, DeliveryCalRequestDto calDto, int OilPrice);
     void cancelDelivery();
     void companyListDelivery();
     void userReservationDelivery();
     //업체
     void rejectionDelivery();
     void quotationDelivery();
     void companyReservationDelivery();
}
