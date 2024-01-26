package com.ssafy.umzip.domain.delivery.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.umzip.domain.delivery.dto.*;
import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.repository.CarRepository;
import com.ssafy.umzip.domain.delivery.service.DeliveryUserService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery/user")
public class DeliveryUserController {
    private final DeliveryUserService deliveryUserService;
    // car Service가 없어도 될정도 이므로 바로 Repository 소환
    private final CarRepository carRepository;
    /*
        예약 신청
     */
    @PostMapping("/reservation")
    public ResponseEntity<Object> createDelivery(@RequestPart(value = "delivery") DeliveryReservationRequestDto delivery,
                                                 @RequestPart(value = "companys") List<DeliveryRequestCompanyDto> companys,
                                                 @RequestPart(value="imageFileList",required = false) List<MultipartFile> imageFileList,
                                                 @RequestPart(value="price") Long price
    ){
        deliveryUserService.createDelivery(delivery,companys,imageFileList,price);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    /*
        계산기
     */
    @PostMapping("/calculation")
    public ResponseEntity<Object> calculateDelivery(@RequestBody DeliveryCalRequestDto dto){
        //---- 모빌리티 API
        MobilityDto mobilityAPI = getMobilityAPI(dto);

        //---- 유가정보 API ( 가격만 가져옴 )
        int OilPrice = getFuelPrice();
        //유가, 차량, 수수료, 거리, 출퇴근 및 주야간
        DeliveryCalResponseDto resultDto = deliveryUserService.calculateDelivery(mobilityAPI, dto, OilPrice);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(resultDto));
    }
    /*
        고객 취소 API
     */
    @PutMapping("/cancel")
    public ResponseEntity<Object> cancelDelivery(@RequestBody DeliveryCancleRequestDto cancleRequestDto){
        deliveryUserService.cancelDelivery(cancleRequestDto.getMappingId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
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
        Car car = carRepository.findById(Long.valueOf(dto.getCarId())).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CAR));
        // API 엔드포인트 주소
        String apiUrl = "https://apis-navi.kakaomobility.com/v1/directions?"+
                "origin="+dto.getDepartureX()+","+dto.getDepartureY()+"&"+
                "destination="+dto.getDestinationX()+","+dto.getDestinationY()+"&"+
                "car_fuel="+car.getFuel()+"&"+
                "car_type="+car.getType();

        HttpHeaders headers = new HttpHeaders();
        String API_KEY = "efbbf48d809a4e2001e31b17724e640c"; //key
        // 헤더 추가
        headers.set("Authorization", "KakaoAK " + API_KEY);
        headers.set("Content-Type", "application/json");

        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();

        // RequestEntity를 생성하여 헤더를 설정
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(apiUrl));

        // ResponseEntity를 사용하여 응답 저장
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseEntity.getBody()).getAsJsonObject();

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
    public int getFuelPrice(){
        String keyCode = "F240126029";
        String out = "json";
        String yesterday = LocalDateTime.now().toString().substring(0, 10).replaceAll("-", "");
        String prodcd = "K015";
        String apiUrl = "http://www.opinet.co.kr/api/avgRecentPrice.do?"+
                "code="+keyCode+"&"+
//                "date="+yesterday+"&"+ date의 기준이 뭔데요.. 알려줘야할거아닙니까.....
                "out="+out+"&"+
                "prodcd="+prodcd;

        System.out.println(yesterday);

        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();
        // 요청 보냄
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseEntity.getBody()).getAsJsonObject();


        return 900;
    }
}
