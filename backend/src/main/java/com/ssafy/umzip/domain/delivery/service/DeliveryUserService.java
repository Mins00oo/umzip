package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DeliveryUserService {
     //프론트
     List<CarResponseDto> getCarInfo();
     //사용자
     void createDelivery(DeliveryReservationRequestDto deliveryReservationRequestDto,
                         List<DeliveryRequestCompanyDto> deliveryRequestCompanyDtoList,
                         List<MultipartFile> deliveryImages,
                         Long price,
                         Long memberId);
     DeliveryCalResponseDto calculateDelivery(MobilityDto mobilityDto, DeliveryCalRequestDto calDto, Double OilPrice);
     void cancelDelivery(Long mappingId,Long memberId);
     List<DeliveryMatchingCompanyDto> companyMatchingListDelivery(DeliveryCompanyListRequestDto dto);
     List<UserDeliveryReservationDto> userReservationDelivery(Long memberId);
     Boolean completeReservation(Long mappingId, Long memberId);
     DeliveryDetailResponseDto getDeliveryDetail(Long deliveryId, Long memberId);

}
