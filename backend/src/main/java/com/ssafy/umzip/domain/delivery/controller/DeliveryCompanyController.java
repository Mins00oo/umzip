package com.ssafy.umzip.domain.delivery.controller;

import com.ssafy.umzip.domain.delivery.dto.DeliveryQuotationRequestDto;
import com.ssafy.umzip.domain.delivery.dto.DeliveryRejectionRequestDto;
import com.ssafy.umzip.domain.delivery.service.DeliveryCompanyService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery/company")
public class DeliveryCompanyController {
    private final DeliveryCompanyService deliveryCompanyService;
    /*
        업체 : 신청 거절
     */
    @PutMapping("/rejection")
    public ResponseEntity<Object> rejectionDelivery(@RequestBody DeliveryRejectionRequestDto dto){
        deliveryCompanyService.rejectionDelivery(dto.getMappingId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));

    }
    /*
        업체 : 견적 제안
     */
    @PutMapping("/quotation")
    public ResponseEntity<Object> quotationDelivery(@RequestBody DeliveryQuotationRequestDto dto){

        return deliveryCompanyService.quotationDelivery(dto)?
                ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS))
                :ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.FAIL_TO_QUOTATION));

    }
    /*
        업체 : 본인 예약 리스트(전체)
     */
    @GetMapping("/reservation")
    public ResponseEntity<Object> companyReservationDelivery(){
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }

}
