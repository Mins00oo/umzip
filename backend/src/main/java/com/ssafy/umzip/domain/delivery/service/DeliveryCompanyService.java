package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.dto.CompanyReservationDto;
import com.ssafy.umzip.domain.delivery.dto.DeliveryQuotationRequestDto;

import java.util.List;

public interface DeliveryCompanyService {
    //업체
    void rejectionDelivery(Long mappingId,Long companyId);
    Boolean quotationDelivery(DeliveryQuotationRequestDto dto,Long companyId);
    List<CompanyReservationDto> companyReservationDelivery(Long companyId);
}
