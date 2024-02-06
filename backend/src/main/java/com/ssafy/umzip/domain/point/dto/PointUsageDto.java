package com.ssafy.umzip.domain.point.dto;

import com.ssafy.umzip.domain.point.entity.Point;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class PointUsageDto {
    private String message;

    private int change;

    private String date;

    public static PointUsageDto fromEntity(
            Point point
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return PointUsageDto.builder()
                .message(point.getMessage().getMessage())
                .change(point.getChange())
                .date(point.getCreateDt().format(formatter))
                .build();
    }

}
