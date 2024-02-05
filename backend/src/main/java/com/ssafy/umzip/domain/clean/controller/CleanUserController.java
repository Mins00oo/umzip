package com.ssafy.umzip.domain.clean.controller;

import com.ssafy.umzip.domain.clean.dto.user.*;
import com.ssafy.umzip.domain.clean.service.CleanUserService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clean/user")
public class CleanUserController {
    private final CleanUserService cleanUserService;
    private final JwtTokenProvider jwtTokenProvider;
    /**
        유저 : 청소 계산기 API
     */
    @PostMapping("/calculation")
    public ResponseEntity<Object> calculateClean(@RequestBody CleanCalRequestDto cleanCalRequestDto) {
        int roomSize = cleanCalRequestDto.getRoomSize();
        //평당 기본 가격
        Long calResult = getDefaultPrice(roomSize);
        // 곰팡이
        calResult += applyAddCondition(cleanCalRequestDto.isMold(), this::addMoldCost);
        // 복층
        calResult += applyAddCondition(cleanCalRequestDto.isDuplex(), this::addDuplexCost);
        // 발코니 비용 ( 20평 보다 클때만 청구됨. / 원룸 : 청구 X )
        calResult += applyAddCondition(cleanCalRequestDto.isBalconyExistence() && roomSize>20, this::addBalconyCost);
        // 스티커제거
        calResult += applyAddCondition(cleanCalRequestDto.isRemoveSticker(), this::addRemoveStickerCost);
        // 새집증후군
        calResult += applyAddCondition(cleanCalRequestDto.isHouseSyndrome(), () -> addHouseSyndromeCost(roomSize));
        // 외창 청소
        Long finalCalResult = calResult;
        calResult += applyAddCondition(cleanCalRequestDto.isExternalWindow(), () -> doubleCleaningCost(finalCalResult));
        //야간 계산
        int hour = Integer.parseInt(cleanCalRequestDto.getReservationTime().split(" ")[1].substring(0, 2));
        Long finalCalResult1 = calResult;
        calResult += applyAddCondition(hour>=22||(hour>=0&&hour<=5),()->addNightCost(finalCalResult1));


        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(calResult));
    }
    /**
         유저 : 청소 예약 API
     */
    @PostMapping("/reservation")
    public ResponseEntity<Object> createClean(@RequestPart(name = "companys") List<CleanReservationCompanyDto> companys,
                                              @RequestPart(name = "imageFileList",required = false) List<MultipartFile> imageFileList,
                                              @RequestPart(name = "price") Long price,
                                              @RequestPart(name = "clean")CleanReservationRequestDto reservationRequestDto,
                                              HttpServletRequest request
                                              ){

        Long memberId = jwtTokenProvider.getId(request);
        if(imageFileList==null){ // check null
            imageFileList = new ArrayList<>();
        }
        cleanUserService.createClean(companys,imageFileList,price,reservationRequestDto,memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }
    /**
        유저 : 예약 전체 조회 API
     */
    @GetMapping("/reservation")
    public ResponseEntity<Object> userReservationClean(HttpServletRequest request){
        Long memberId = jwtTokenProvider.getId(request);
        List<UserCleanReservationResponseDto> cleans = cleanUserService.userReservationClean(memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(cleans));
    }
    /*
        유저 : 예약 상세조회 API
     */
    @GetMapping("/reservation/{cleanId}")
    public ResponseEntity<Object> userDetailReservationClean(@PathVariable Long cleanId, HttpServletRequest request){
        Long memberId = jwtTokenProvider.getId(request);
        CleanDetailResponseDto cleanDetail = cleanUserService.getCleanDetail(memberId, cleanId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(cleanDetail));
    }
    /*
        유저 : 취소 API
     */
    @PutMapping("/cancel")
    public ResponseEntity<Object> cancelClean(@RequestBody CleanCancelRequestDto dto,
                                              HttpServletRequest request
    ){
        Long memberId = jwtTokenProvider.getId(request);
        cleanUserService.cancelClean(dto.getMappingId(),memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }
    /**
     * 유저 : 매칭 API
     */
    @PostMapping("/company-list")
    public ResponseEntity<Object> companyListClean(@RequestBody CleanCompanyListRequestDto dto){
        List<CleanMatchingCompanyDto> cleans = cleanUserService.companyListClean(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(cleans));
    }
    /*
        유저 : 예약 상세조회 API
     */
    @GetMapping("/reservation/{cleanId}")
    public ResponseEntity<Object> userDetailReservationClean(@PathVariable Long cleanId, HttpServletRequest request){
        Long memberId = jwtTokenProvider.getId(request);
        CleanDetailResponseDto cleanDetail = cleanUserService.getCleanDetail(memberId, cleanId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(cleanDetail));
    }

    /**
        계산기 필요 메서드들
     */
    private static Long getDefaultPrice(int roomSize) {
        if (roomSize <= 7) {
            return 150000L;
        } else if (roomSize <= 9) {
            return 170000L;
        } else {
            return roomSize * 11000L;
        }
    }

    private long applyAddCondition(boolean condition, LongSupplier action) {
        return condition ? action.getAsLong() : 0L;
    }

    private long addMoldCost() {
        return 50000L;
    }

    private long addDuplexCost() {
        return 20000L;
    }

    private long addBalconyCost() {
        return 11000L * 3;
    }

    private long addRemoveStickerCost() {
        return 30000L;
    }

    private long addHouseSyndromeCost(long roomSize) {
        return roomSize * 5000L;
    }

    private long doubleCleaningCost(long cost) {
        return cost;
    }
    private long addNightCost(long cost) {
        return (long) (cost*0.15);
    }
}
