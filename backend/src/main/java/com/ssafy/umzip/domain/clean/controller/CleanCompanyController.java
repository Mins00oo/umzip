package com.ssafy.umzip.domain.clean.controller;

import com.ssafy.umzip.domain.clean.dto.company.CleanCompanyReservationResponseDto;
import com.ssafy.umzip.domain.clean.dto.company.CleanQuotationRequestDto;
import com.ssafy.umzip.domain.clean.dto.company.CleanRejectionRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanDetailResponseDto;
import com.ssafy.umzip.domain.clean.service.CleanCompanyService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clean/company")
public class CleanCompanyController {
    private final CleanCompanyService cleanCompanyService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     *  업체 : 거절 API
     */
    @PutMapping("/rejection")
    public ResponseEntity<Object> rejectionClean(@RequestBody CleanRejectionRequestDto rejectionRequestDto,
                                                 HttpServletRequest request
    ){
        ResponseEntity<Object> BAD_REQUEST = checkRole(request); // Role check
        if (BAD_REQUEST != null) {
            return BAD_REQUEST;
        }
        Long companyId = jwtTokenProvider.getId(request);

        Boolean result = cleanCompanyService.rejectionClean(rejectionRequestDto.getMappingId(),companyId);
        return result?ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS)):
                ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.FAIL_TO_REJECTION));
    }



    /**
     업체 : 견적 제안 API
     */
    @PutMapping("/quotation")
    public ResponseEntity<Object> quotationClean(@RequestBody CleanQuotationRequestDto requestDto,
                                                 HttpServletRequest request){
        ResponseEntity<Object> BAD_REQUEST = checkRole(request); // Role check
        if (BAD_REQUEST != null) {
            return BAD_REQUEST;
        }
        Long id = jwtTokenProvider.getId(request);

        Boolean result = cleanCompanyService.quotationClean(requestDto, id);
        return result?ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS))
                :ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.FAIL_TO_QUOTATION));
    }

    /**
     * 업체 : 업체 예약 확인 API
     */
    @GetMapping("/reservation")
    public ResponseEntity<Object> companyReservationClean(HttpServletRequest request){
        Long companyId = jwtTokenProvider.getId(request);
        ResponseEntity<Object> BAD_REQUEST = checkRole(request);  // Role check
        if (BAD_REQUEST != null) {
            return BAD_REQUEST;
        }
        List<CleanCompanyReservationResponseDto> list = cleanCompanyService.companyReservationClean(companyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(list));
    }

    /**
     * 업체 : 예약 상세 조회 API
     *
     */
    @GetMapping("/reservation/{cleanId}")
    public ResponseEntity<Object> companyReservationDetailClean(
            @PathVariable Long cleanId,
            HttpServletRequest request){
        Long companyId = jwtTokenProvider.getId(request);
        ResponseEntity<Object> BAD_REQUEST = checkRole(request);  // Role check
        if (BAD_REQUEST != null) {
            return BAD_REQUEST;
        }
        CleanDetailResponseDto cleanDetail = cleanCompanyService.getCleanDetail(companyId, cleanId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(cleanDetail));
    }
    @Nullable
    private ResponseEntity<Object> checkRole(HttpServletRequest request) {
        String role = jwtTokenProvider.getRole(request);
        if(!role.equals("CLEAN")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(StatusCode.FORBIDDEN_REQUEST));
        }
        return null;
    }

}
