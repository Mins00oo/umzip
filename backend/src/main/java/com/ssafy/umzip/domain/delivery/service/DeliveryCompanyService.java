package com.ssafy.umzip.domain.delivery.service;

public interface DeliveryCompanyService {
    //업체
    void rejectionDelivery(Long mappingId);
    void quotationDelivery();
    void companyReservationDelivery();
}
