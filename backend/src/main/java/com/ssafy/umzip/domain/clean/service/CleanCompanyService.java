package com.ssafy.umzip.domain.clean.service;

import com.ssafy.umzip.domain.clean.dto.company.CleanQuotationRequestDto;

public interface CleanCompanyService {
    Boolean rejectionClean(Long mappingId,Long companyId);
    Boolean quotationClean(CleanQuotationRequestDto dto, Long companyId);
}
