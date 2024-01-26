package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.dto.*;
import com.ssafy.umzip.domain.delivery.entity.Car;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeliveryService {
     //car Test
     Optional<Car> getCar(Long id);
     //사용자
     void createDelivery(DeliveryReservationRequestDto deliveryReservationRequestDto,
                         List<DeliveryRequestCompanyDto> deliveryRequestCompanyDtoList,
                         List<MultipartFile> deliveryImages,
                         Long price);
     DeliveryCalResponseDto calculateDelivery(MobilityDto mobilityDto, DeliveryCalRequestDto calDto, int OilPrice);
     void cancelDelivery();
     void companyListDelivery();
     void userReservationDelivery();
     //업체
     void rejectionDelivery();
     void quotationDelivery();
     void companyReservationDelivery();
}
