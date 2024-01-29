package com.ssafy.umzip.domain.delivery.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyReservationDto {
    private Long mappingId;
    private Long deliveryId;
    private LocalDateTime createDt;
    private LocalDateTime startTime;
    private String memberName;
    private Long codeSmallId;
    private Long price;
    private Long reissuing;
    private String memberPhone;

}
