package com.ssafy.umzip.domain.clean.service;

import com.ssafy.umzip.domain.clean.dto.company.CleanCompanyReservationResponseDto;
import com.ssafy.umzip.domain.clean.dto.company.CleanQuotationRequestDto;

import java.util.List;

public interface CleanCompanyService {
    Boolean rejectionClean(Long mappingId,Long companyId);
    Boolean quotationClean(CleanQuotationRequestDto dto, Long companyId);
    List<CleanCompanyReservationResponseDto> companyReservationClean(Long companyId);
}
