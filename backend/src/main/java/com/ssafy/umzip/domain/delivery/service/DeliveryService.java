package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.entity.Car;

import java.util.List;

public interface DeliveryService {
     //car Test
     List<Car> getCarList();
     //사용자
     void createDelivery();
     void calculateDelivery();
     void cancelDelivery();
     void companyListDelivery();
     void userReservationDelivery();
     //업체
     void rejectionDelivery();
     void quotationDelivery();
     void companyReservationDelivery();

}
