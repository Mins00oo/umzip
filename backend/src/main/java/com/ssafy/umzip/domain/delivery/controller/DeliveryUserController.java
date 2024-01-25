package com.ssafy.umzip.domain.delivery.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.umzip.domain.delivery.dto.DeliveryCalRequestDto;
import com.ssafy.umzip.domain.delivery.dto.MobilityDto;
import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.service.DeliveryService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery/user")
public class DeliveryUserController {
    private final DeliveryService deliveryService;
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
        Long resultPrice = deliveryService.calculateDelivery(mobilityAPI, dto, OilPrice);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(resultPrice));
    }
    /*
        KAKAO Mobility 길찾기 API
     */
    public MobilityDto getMobilityAPI(DeliveryCalRequestDto dto){
        /*  가져올 값
            departure x,y좌표 & destination x,y좌표 & 차종(car_type) & car_fuel
            받을 값
            routes - fare - taxi
            routes - fare - toll
            routes - distance
            routes - duration
         */

        Optional<Car> optionalCar = deliveryService.getCar(Long.valueOf(dto.getCarId()));

        if(!optionalCar.isPresent()){
            //예외 처리 필요
        }
        // car이 있으면 가져옴. 근데 이게맞나
        Car car = optionalCar.get();
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
        int taxi = summary.getAsJsonObject("fare")
                .getAsJsonPrimitive("taxi").getAsInt();
        int toll = summary.getAsJsonObject("fare")
                .getAsJsonPrimitive("toll").getAsInt();
        int duration = summary.getAsJsonPrimitive("duration").getAsInt();//초
        int distance = summary.getAsJsonPrimitive("distance").getAsInt();//미터
        return new MobilityDto(taxi, duration, toll, distance);
    }
    /*
        유가 정보 API
     */
    public int getFuelPrice(){
        return 900;
    }
}
