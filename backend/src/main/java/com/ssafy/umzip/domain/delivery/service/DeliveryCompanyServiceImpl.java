package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.alarm.dto.AlarmDto;
import com.ssafy.umzip.domain.alarm.dto.AlarmType;
import com.ssafy.umzip.domain.alarm.entity.Alarm;
import com.ssafy.umzip.domain.alarm.repository.AlarmRepository;
import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.domain.delivery.dto.CompanyReservationDto;
import com.ssafy.umzip.domain.delivery.dto.DeliveryDetailResponseDto;
import com.ssafy.umzip.domain.delivery.dto.DeliveryQuotationRequestDto;
import com.ssafy.umzip.domain.delivery.entity.Delivery;
import com.ssafy.umzip.domain.delivery.entity.DeliveryImage;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import com.ssafy.umzip.domain.delivery.repository.DeliveryCustomRepository;
import com.ssafy.umzip.domain.delivery.repository.DeliveryMappingRepository;
import com.ssafy.umzip.domain.delivery.repository.DeliveryRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCompanyServiceImpl implements DeliveryCompanyService{
    private final DeliveryMappingRepository deliveryMappingRepository;
    private final CodeSmallRepository codeSmallRepository;
    private final DeliveryCustomRepository deliveryCustomRepository;
    private final DeliveryRepository deliveryRepository;
    private final AlarmRepository alarmRepository;
    /*
        거절 ( 104 )
     */
    @Override
    public void rejectionDelivery(Long mappingId,Long companyId) {
        DeliveryMapping deliveryMapping = deliveryMappingRepository.findById(mappingId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MAPPING));
        if(!Objects.equals(deliveryMapping.getCompany().getId(), companyId)){
            throw new BaseException(StatusCode.NOT_EXIST_COMPANY);
        }

        CodeSmall codeSmall = codeSmallRepository.findById(104L).orElseThrow(() -> new BaseException(StatusCode.CODE_DOES_NOT_EXIST));
        // member에게 알람
        deliveryMapping.setCodeSmall(codeSmall);
        //알람
        saveAlarm(codeSmall, deliveryMapping);
    }



    /*
        견적 제안 ( 102 )
     */
    @Override
    public Boolean quotationDelivery(DeliveryQuotationRequestDto dto,Long companyId) {
        CodeSmall codeSmall = codeSmallRepository.findById(102L).orElseThrow(() -> new BaseException(StatusCode.CODE_DOES_NOT_EXIST));
        DeliveryMapping deliveryMapping = deliveryMappingRepository.findById(dto.getMappingId()).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MAPPING));
        if(!Objects.equals(deliveryMapping.getCompany().getId(), companyId)){
            throw new BaseException(StatusCode.NOT_EXIST_COMPANY);
        }
        //알람
        saveAlarm(codeSmall, deliveryMapping);
        return deliveryCustomRepository.updateDeliveryMappingDetailAndReissuingAndCodeSmall(dto,codeSmall);
    }

    @Override
    public List<CompanyReservationDto> companyReservationDelivery(Long companyId) {
        return deliveryCustomRepository.findCompanyReservationInfo(companyId);
    }
    /*
        예약 한 건 상세 조회
     */

    @Override
    public DeliveryDetailResponseDto getDeliveryDetail(Long deliveryId,Long companyId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_DELIVERY));
        if (!deliveryMappingRepository.existsByDeliveryIdAndCompanyId(deliveryId, companyId)) {
            throw new BaseException(StatusCode.INVALID_GET_DELIVER);
        }
        List<String> images = delivery.getImages().stream().map(DeliveryImage::getPath)
                .toList();

        DeliveryDetailResponseDto deliveryDetail = DeliveryDetailResponseDto.builder()
                .id(delivery.getId())
                .carName(delivery.getCar().getName())
                .startTime(delivery.getStartTime())
                .endTime(delivery.getEndTime())
                .departure(delivery.getDeparture())
                .departureDetail(delivery.getDepartureDetail())
                .destination(delivery.getDestination())
                .destinationDetail(delivery.getDestinationDetail())
                .packaging(delivery.isPackaging())
                .move(delivery.isMove())
                .elevator(delivery.isElevator())
                .parking(delivery.isParking())
                .movelist(delivery.getMovelist())
                .build();
        deliveryDetail.setDeliveryImages(images);
        return deliveryDetail;
    }

    private void saveAlarm(CodeSmall codeSmall, DeliveryMapping deliveryMapping) {
        AlarmDto alarm = AlarmDto.builder()
                .alarmType(AlarmType.DELIVER)
                .read(false)
                .codeSmallId(codeSmall.getId())
                .member(deliveryMapping.getMember())
                .company(deliveryMapping.getCompany())
                .build();
        Alarm companyAlarm = alarm.toMemberDeliveryAndCleanAlarmEntity();
        alarmRepository.save(companyAlarm);
    }
}

