package com.ssafy.umzip.domain.delivery.controller;

import com.ssafy.umzip.domain.delivery.dto.DeliveryQuotationRequestDto;
import com.ssafy.umzip.domain.delivery.dto.DeliveryRejectionRequestDto;
import com.ssafy.umzip.domain.delivery.service.DeliveryCompanyService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery/company")
public class DeliveryCompanyController {
    private final DeliveryCompanyService deliveryCompanyService;
    @PutMapping("/rejection")
    public ResponseEntity<Object> rejectionDelivery(@RequestBody DeliveryRejectionRequestDto dto){
        deliveryCompanyService.rejectionDelivery(dto.getMappingId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));

    }
    @PutMapping("/quotation")
    public ResponseEntity<Object> quotationDelivery(@RequestBody DeliveryQuotationRequestDto dto){
        deliveryCompanyService.quotationDelivery(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));

    }
}
