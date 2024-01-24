package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.delivery.dto.DeliveryCalRequestDto;
import com.ssafy.umzip.domain.delivery.dto.DeliveryReservationRequestDto;
import com.ssafy.umzip.domain.delivery.dto.MobilityDto;
import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.entity.Delivery;
import com.ssafy.umzip.domain.delivery.repository.CarRepository;
import com.ssafy.umzip.domain.delivery.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final CarRepository carRepository;
    private final DeliveryRepository deliveryRepository;
    @Override
    public Optional<Car> getCar(Long id) {
        return carRepository.findById(id);
    }

//departure, String destination, boolean packaging, boolean move, boolean elevator, boolean parking, String movelist, int sigungu, String departureDetail, String destinationDetail) {
    /*
        예약 신청
     */
    @Override
    public void createDelivery(DeliveryReservationRequestDto dto) {
        //Delivery Entity 생성
        Optional<Car> optionalCar = carRepository.findById(dto.getCarId());
        if(!optionalCar.isPresent()){
            //예외처리
        }
        Car car = optionalCar.get();

        dto.setCar(car);//흠냐링

        Delivery delivery = DeliveryReservationRequestDto.toEntity(dto);
        //저장
        deliveryRepository.save(delivery);
    }
    /*
        계산기
     */
    @Override
    public Long calculateDelivery(MobilityDto mobilityDto,DeliveryCalRequestDto calDto,int OilPrice) {
        Car car = carRepository.findById(calDto.getCarId()).get();
        Long price = car.getPrice()*10000; //대여비
        double distanceKm = Math.ceil(mobilityDto.getDistance()/1000); //몇 Km?
        Long distancePrice = (long) (( distanceKm / car.getMileage() ) * OilPrice); //거리 주유비
        price+= distancePrice;
        System.out.println("distanceKm = " + distanceKm);
        System.out.println("distancePrice = " + distancePrice);
        System.out.println("price = " + price);
        if(calDto.isMove()){ //같이 이동시 30000원 추가
            price += 30000;
        }
        if(calDto.isPackaging()){ //포장 필요시 10000원 추가
            price += 10000;
        }
        if(!calDto.isParking()){ //주차 없으면 10000원 추가
            price += 10000;
        }
        price += distancePrice;

        // % 수수료 계산
        if(!calDto.isElevator()){ //엘베 없으면
            price =Math.round(price*1.15);
        }
        String time = calDto.getStartTime().split(" ")[1].split(":")[0];
        Integer startTime = Integer.valueOf(time);
        //출퇴근이면 5%
        if(startTime==7||startTime==8||startTime==18||startTime==19){
            price = Math.round(price*1.05);
        //야간 10%
        }else if(startTime>=22||(startTime>=0&&startTime<=5)){
            price = Math.round(price*1.10);
        }
        long result = Math.round((double) price / 100) * 100;
        return result;
    }

    @Override
    public void cancelDelivery() {

    }

    @Override
    public void companyListDelivery() {

    }

    @Override
    public void userReservationDelivery() {

    }

    @Override
    public void rejectionDelivery() {

    }

    @Override
    public void quotationDelivery() {

    }

    @Override
    public void companyReservationDelivery() {

    }
}
