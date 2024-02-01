package com.ssafy.umzip.domain.clean.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CleanCancelRequestDto {
    private Long mappingId;

    public CleanCancelRequestDto(Long mappingId) {
        this.mappingId = mappingId;
    }
}
