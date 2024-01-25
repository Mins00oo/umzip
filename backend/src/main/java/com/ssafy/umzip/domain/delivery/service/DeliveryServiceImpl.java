package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.dto.DeliveryRequestDto;
import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.entity.Delivery;
import com.ssafy.umzip.domain.delivery.repository.CarRepository;
import com.ssafy.umzip.domain.delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final CarRepository carRepository;
    private final DeliveryRepository deliveryRepository;
    @Override
    public Optional<Car> getCarList(Long id) {
        return carRepository.findById(id);
    }

//departure, String destination, boolean packaging, boolean move, boolean elevator, boolean parking, String movelist, int sigungu, String departureDetail, String destinationDetail) {
    @Override
    public void createDelivery(DeliveryRequestDto dto) {
        Delivery delivery = new Delivery(dto.getDeparture(),
                dto.getDestination(),
                dto.isPackaging(),
                dto.isMove(),
                dto.isElevator(),
                dto.isParking(),
                dto.getMovelist(),
                dto.getSigungu(),
                dto.getDepartureDetail(),
                dto.getDestinationDetail()
                );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(dto.getStartTime(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(dto.getEndTime(), formatter);
        delivery.setStartTime(startTime);
        delivery.setEndTime(endTime);
        deliveryRepository.save(delivery);


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
