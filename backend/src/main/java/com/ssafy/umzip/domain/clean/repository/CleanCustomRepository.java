package com.ssafy.umzip.domain.clean.repository;

import com.ssafy.umzip.domain.clean.dto.company.CleanQuotationRequestDto;
import com.ssafy.umzip.domain.code.entity.CodeSmall;

public interface CleanCustomRepository {
    Boolean updateCodeAndReissuingAndDetail(CleanQuotationRequestDto dto, CodeSmall codeSmall);
}
