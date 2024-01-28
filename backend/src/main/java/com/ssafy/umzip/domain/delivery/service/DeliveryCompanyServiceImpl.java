package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import com.ssafy.umzip.domain.delivery.repository.DeliveryMappingRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCompanyServiceImpl implements DeliveryCompanyService{
    private final DeliveryMappingRepository deliveryMappingRepository;
    private final CodeSmallRepository codeSmallRepository;
    @Override
    public void rejectionDelivery(Long mappingId) {
        DeliveryMapping deliveryMapping = deliveryMappingRepository.findById(mappingId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MAPPING));
        CodeSmall codeSmall = codeSmallRepository.findById(104L).orElseThrow(() -> new BaseException(StatusCode.CODE_DOES_NOT_EXIST));
        deliveryMapping.setCodeSmall(codeSmall);
    }

    @Override
    public void quotationDelivery() {

    }

    @Override
    public void companyReservationDelivery() {

    }
}
