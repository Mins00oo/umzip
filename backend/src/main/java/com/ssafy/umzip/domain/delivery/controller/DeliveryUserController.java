package com.ssafy.umzip.domain.delivery.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.umzip.domain.clean.dto.user.CleanCompleteReservationDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanDetailResponseDto;
import com.ssafy.umzip.domain.delivery.dto.*;
import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.repository.CarRepository;
import com.ssafy.umzip.domain.delivery.service.DeliveryUserService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery/user")
public class DeliveryUserController {
    private final DeliveryUserService deliveryUserService;
    // car Service가 없어도 될정도 이므로 바로 Repository 소환
    private final CarRepository carRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private static String kakaoApiKey;
    private static String fuelApiKey;
    @Value("${kakao.api.key}")
    public void setKakaoApiKey(String kakaoApiKey) {
        DeliveryUserController.kakaoApiKey = kakaoApiKey;
    }
    @Value("${fuel.api.key}")
    public void setFuelApiKey(String fuelApiKey) {
        DeliveryUserController.fuelApiKey = fuelApiKey;
    }
    /*
        프론트 : 차 List
     */
    @GetMapping("/car")
    public ResponseEntity<Object> getCarInfo(){
        List<CarResponseDto> carInfo = deliveryUserService.getCarInfo();
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(carInfo));
    }

    /*
        고객 : 예약 신청
     */
    @PostMapping("/reservation")
    public ResponseEntity<Object> createDelivery(@RequestPart(value = "delivery") DeliveryReservationRequestDto delivery,
                                                 @RequestPart(value = "companys") List<DeliveryRequestCompanyDto> companys,
                                                 @RequestPart(value="imageFileList",required = false) List<MultipartFile> imageFileList,
                                                 @RequestPart(value="price") Long price,
                                                 HttpServletRequest request
    ){
        Long memberId = jwtTokenProvider.getId(request);
        if(imageFileList==null){ // check null
            imageFileList = new ArrayList<>();
        }
        deliveryUserService.createDelivery(delivery,companys,imageFileList,price,memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    /*
        고객 : 계산기
     */
    @PostMapping("/calculation")
    public ResponseEntity<Object> calculateDelivery(@RequestBody DeliveryCalRequestDto dto){
        //---- 모빌리티 API
        MobilityDto mobilityAPI = getMobilityAPI(dto);

        //---- 유가정보 API ( 가격만 가져옴 )
        Double OilPrice = getFuelPrice(dto.getCarId());
        //유가, 차량, 수수료, 거리, 출퇴근 및 주야간
        DeliveryCalResponseDto resultDto = deliveryUserService.calculateDelivery(mobilityAPI, dto, OilPrice);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(resultDto));
    }
    /*
        고객 : 취소 API
     */
    @PutMapping("/cancel")
    public ResponseEntity<Object> cancelDelivery(@RequestBody DeliveryCancelRequestDto cancelRequestDto,
                                                 HttpServletRequest request){
        Long memberId = jwtTokenProvider.getId(request);
        deliveryUserService.cancelDelivery(cancelRequestDto.getMappingId(),memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }
    /*
        고객 : 매칭 API
     */
    @PostMapping("/company-list")
    public ResponseEntity<Object> companyListDelivery(@RequestBody DeliveryCompanyListRequestDto deliveryCompanyListRequestDto){
        List<DeliveryMatchingCompanyDto> result = deliveryUserService.companyMatchingListDelivery(deliveryCompanyListRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(result));
    }
    /*
        고객 : 용달 유저 예약 확인 API
     */
    @GetMapping("/reservation")
    public ResponseEntity<Object>  userReservationDelivery(HttpServletRequest request){
        //memberID는 JWT토큰에서 가져온다.
        Long memberId = jwtTokenProvider.getId(request);
        List<UserDeliveryReservationDto> deliveryList = deliveryUserService.userReservationDelivery(memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(deliveryList));
    }
    /*
        고객 : 용달 예약 확정 API
     */
    @PostMapping("/reservation-complete")
    public ResponseEntity<Object> completeReservation(@RequestBody DeliveryCompleteReservationDto dto,
                                                      HttpServletRequest request){
        Long memberId = jwtTokenProvider.getId(request);
        Boolean result = deliveryUserService.completeReservation(dto.getMappingId(), memberId);
        if(!result){
            throw new BaseException(StatusCode.FAIL_TO_RESERVATION);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }
    /*
        유저 : 예약 상세조회 API
     */
    @GetMapping("/reservation/{deliveryId}")
    public ResponseEntity<Object> userDetailReservationDelivery(@PathVariable Long deliveryId, HttpServletRequest request){
        Long memberId = jwtTokenProvider.getId(request);
        DeliveryDetailResponseDto deliveryDetail = deliveryUserService.getDeliveryDetail(deliveryId, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(deliveryDetail));
    }

    /*
        KAKAO Mobility 길찾기 API
     */
    public MobilityDto getMobilityAPI(DeliveryCalRequestDto dto){
        /*  가져올 값
            departure x,y좌표 & destination x,y좌표 & 차종(car_type) & car_fuel
            받을 값
            routes - fare - toll
            routes - distance
            routes - duration
         */
        // car Service가 없어도 될정도 이므로 바로 Repository 소환
        Car car = carRepository.findById(dto.getCarId()).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CAR));
        // API 엔드포인트 주소 세팅
        StringBuilder apiUrl = new StringBuilder("https://apis-navi.kakaomobility.com/v1/directions?");
        apiUrl.append("origin=").append(dto.getDepartureX()).append(",").append(dto.getDepartureY()).append("&")
                .append("destination=").append(dto.getDestinationX()).append(",").append(dto.getDestinationY()).append("&")
                .append("car_fuel=").append(car.getFuel()).append("&")
                .append("car_type=").append(car.getType());

        HttpHeaders headers = new HttpHeaders();
        // 헤더 추가
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        headers.set("Content-Type", "application/json");

        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();

        // RequestEntity를 생성하여 헤더를 설정
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(apiUrl.toString()));

        // ResponseEntity를 사용하여 응답 저장
        String responseBody = restTemplate.exchange(requestEntity, String.class).getBody();
        if(responseBody==null) throw new BaseException(StatusCode.FAIL_API_REQUEST);

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();

        //summary
        JsonObject summary = jsonObject.getAsJsonArray("routes")
                .get(0).getAsJsonObject()
                .getAsJsonObject("summary");

        //원하는 값
        int toll = summary.getAsJsonObject("fare")
                .getAsJsonPrimitive("toll").getAsInt();
        int duration = summary.getAsJsonPrimitive("duration").getAsInt();//초
        int distance = summary.getAsJsonPrimitive("distance").getAsInt();//미터
        return new MobilityDto( duration, toll, distance);
    }
    /*
        유가 정보 API
     */
    public Double getFuelPrice(Long carId){
        // API 요청 PARAM 세팅
        String out = "json";
        String yesterday = LocalDateTime.now().minusDays(1).toString().substring(0, 10).replaceAll("-", "").trim();
        // 해당 CAR에 맞춰서 유가 정보 알아옴.
        String prodcd=getProdcd(carId);
        // API BUILD
        StringBuilder apiUrl = new StringBuilder("http://www.opinet.co.kr/api/avgRecentPrice.do?");
        apiUrl.append("code=").append(fuelApiKey).append("&")
                .append("out=").append(out).append("&")
                .append("prodcd=").append(prodcd).append("&")
                .append("date=").append(yesterday);

        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();
        // 요청 보냄
        String responseBody = restTemplate.getForEntity(apiUrl.toString(), String.class).getBody();
        // 반환값이 없으면 Exception
        if(responseBody==null) throw new BaseException(StatusCode.FAIL_API_REQUEST);
        //responseBody parse
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();

        return jsonObject.getAsJsonObject("RESULT")
                .getAsJsonArray("OIL")
                .get(0).getAsJsonObject()
                .getAsJsonPrimitive("PRICE").getAsDouble();
    }
    /*
        연료에 따른 API 요청 파라미터 변경 (Prodcd)
     */
    private String getProdcd(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CAR));
//        B034:고급휘발유, B027:보통휘발유, D047:자동차경유, C004:실내등유, K015:자동차부탄
        return switch (car.getFuel()) {
            case "LPG" -> "K015";
            case "GASOLINE" -> "B027";
            case "DIESEL" -> "D047";
            default -> throw new BaseException(StatusCode.NOT_EXIST_FUEL);
        };
    }
}
