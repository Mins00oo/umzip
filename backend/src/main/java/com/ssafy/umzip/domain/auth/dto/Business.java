package com.ssafy.umzip.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Business {
    @JsonProperty("b_no")
    private String b_no;

    @JsonProperty("start_dt")
    private String start_dt;

    @JsonProperty("p_nm")
    private String p_nm;

    @JsonProperty("p_nm2")
    private String p_nm2;

    @JsonProperty("b_nm")
    private String b_nm;

    @JsonProperty("corp_no")
    private String corp_no;

    @JsonProperty("b_sector")
    private String b_sector;

    @JsonProperty("b_type")
    private String b_type;

    @JsonProperty("b_adr")
    private String b_adr;

    @Builder
    public Business(String b_no, String start_dt, String p_nm, String p_nm2, String b_nm, String corp_no,
                    String b_sector, String b_type, String b_adr) {
        this.b_no = b_no;
        this.start_dt = start_dt;
        this.p_nm = p_nm;
        this.p_nm2 = p_nm2;
        this.b_nm = b_nm;
        this.corp_no = corp_no;
        this.b_sector = b_sector;
        this.b_type = b_type;
        this.b_adr = b_adr;
    }
}
