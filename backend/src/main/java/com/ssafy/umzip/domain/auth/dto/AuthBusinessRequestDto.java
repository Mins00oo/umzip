package com.ssafy.umzip.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AuthBusinessRequestDto {
    private String businessNumber;
    private String startDate;
    private String personName;
    private String personName2;
    private String businessName;
    private String corporationNumber;
    private String businessSector;
    private String businessType;
    private String businessAddress;

    public AuthBusinessRequest toAuthBusinessRequest() {
        List<Business> businesses = new ArrayList<>();


        Business business = Business.builder()
                .b_no(this.businessNumber)
                .start_dt(this.startDate)
                .p_nm(this.personName)
                .p_nm2(this.personName2)
                .b_nm(this.businessName)
                .corp_no(this.corporationNumber)
                .b_sector(this.businessSector)
                .b_type(this.businessType)
                .b_adr(this.businessAddress)
                .build();

        businesses.add(business);


        return AuthBusinessRequest.builder()
                .businesses(businesses)
                .build();
    }
}
