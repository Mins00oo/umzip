package com.ssafy.umzip.domain.clean.dto.company;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
public class CleanQuotationRequestDto {
    private Long reissuing;
    private String detail;
    @Builder
    public CleanQuotationRequestDto(Long reissuing, String detail) {
        this.reissuing = reissuing;
        this.detail = detail;
    }
}
