package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.domain.delivery.dto.CompanyReservationDto;
import com.ssafy.umzip.domain.delivery.dto.DeliveryQuotationRequestDto;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import com.ssafy.umzip.domain.delivery.repository.DeliveryCustomRepository;
import com.ssafy.umzip.domain.delivery.repository.DeliveryMappingRepository;
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
    @Override
    public void rejectionDelivery(Long mappingId,Long companyId) {
        DeliveryMapping deliveryMapping = deliveryMappingRepository.findById(mappingId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MAPPING));
        if(!Objects.equals(deliveryMapping.getCompany().getId(), companyId)){
            throw new BaseException(StatusCode.NOT_EXIST_COMPANY);
        }
        CodeSmall codeSmall = codeSmallRepository.findById(104L).orElseThrow(() -> new BaseException(StatusCode.CODE_DOES_NOT_EXIST));
        deliveryMapping.setCodeSmall(codeSmall);
    }

    @Override
    public Boolean quotationDelivery(DeliveryQuotationRequestDto dto,Long companyId) {
        CodeSmall codeSmall = codeSmallRepository.findById(102L).orElseThrow(() -> new BaseException(StatusCode.CODE_DOES_NOT_EXIST));
        DeliveryMapping deliveryMapping = deliveryMappingRepository.findById(dto.getMappingId()).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MAPPING));
        if(!Objects.equals(deliveryMapping.getCompany().getId(), companyId)){
            throw new BaseException(StatusCode.NOT_EXIST_COMPANY);
        }
        return deliveryCustomRepository.updateDeliveryMappingDetailAndReissuingAndCodeSmall(dto,codeSmall);
    }

    @Override
    public List<CompanyReservationDto> companyReservationDelivery(Long companyId) {
        return deliveryCustomRepository.findCompanyReservationInfo(companyId);
    }
}

