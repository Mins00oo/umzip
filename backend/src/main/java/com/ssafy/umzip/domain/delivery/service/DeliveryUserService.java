package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.dto.*;
import com.ssafy.umzip.domain.delivery.entity.Car;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DeliveryUserService {
     //사용자
     void createDelivery(DeliveryReservationRequestDto deliveryReservationRequestDto,
                         List<DeliveryRequestCompanyDto> deliveryRequestCompanyDtoList,
                         List<MultipartFile> deliveryImages,
                         Long price);
     DeliveryCalResponseDto calculateDelivery(MobilityDto mobilityDto, DeliveryCalRequestDto calDto, Double OilPrice);
     void cancelDelivery(Long mappingId);
     void companyListDelivery(DeliveryCompanyListRequestDto dto);
     List<UserReservationDto> userReservationDelivery(Long memberId);

}
