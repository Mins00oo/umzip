package com.ssafy.umzip.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AuthBusinessRequest {
    private List<Business> businesses;

    @Builder
    public AuthBusinessRequest(List<Business> businesses) {
        this.businesses = businesses;
    }
}
