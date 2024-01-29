package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.dto.DeliveryQuotationRequestDto;

public interface DeliveryCompanyService {
    //업체
    void rejectionDelivery(Long mappingId);
    Boolean quotationDelivery(DeliveryQuotationRequestDto dto);
    void companyReservationDelivery();
}
