package com.ssafy.umzip.domain.clean.dto.company;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CleanCompanyReservationResponseDto {
    private Long mappingId;
    private Long cleanId;
    private LocalDateTime createDt;
    private LocalDateTime reservationTime;
    private Long price;
    private Long reissuing;
    private Long codeSmallId;
    private Long memberId;
    private String memberName;
    private String memberImg;
    @Builder
    public CleanCompanyReservationResponseDto(Long mappingId, Long cleanId, LocalDateTime createDt, LocalDateTime reservationTime, Long price, Long reissuing, Long codeSmallId, Long memberId, String memberName, String memberImg) {
        this.mappingId = mappingId;
        this.cleanId = cleanId;
        this.createDt = createDt;
        this.reservationTime = reservationTime;
        this.price = price;
        this.reissuing = reissuing;
        this.codeSmallId = codeSmallId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberImg = memberImg;
    }
}
