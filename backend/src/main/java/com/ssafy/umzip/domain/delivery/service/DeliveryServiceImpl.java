package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final CarRepository carRepository;

    @Override
    public List<Car> getCarList() {
        return carRepository.findAll();
    }

    @Override
    public void createDelivery() {

    }

    @Override
    public void calculateDelivery() {

    }

    @Override
    public void cancelDelivery() {

    }

    @Override
    public void companyListDelivery() {

    }

    @Override
    public void userReservationDelivery() {

    }

    @Override
    public void rejectionDelivery() {

    }

    @Override
    public void quotationDelivery() {

    }

    @Override
    public void companyReservationDelivery() {

    }
}
