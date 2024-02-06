package com.ssafy.umzip.domain.point.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PointUsageResponseDto {
    private int totalPage;

    List<PointUsageDto> pointUsageDtoList;
}
