package com.ssafy.umzip.domain.delivery.service;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.company.repository.CompanyRepository;
import com.ssafy.umzip.domain.delivery.dto.*;
import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.entity.Delivery;
import com.ssafy.umzip.domain.delivery.entity.DeliveryImage;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import com.ssafy.umzip.domain.delivery.repository.*;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.s3.S3Service;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ssafy.umzip.global.common.CommonMethods.getLocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryUserServiceImpl implements DeliveryUserService {
    private final CarRepository carRepository;
    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final S3Service s3Service;
    private final CodeSmallRepository codeSmallRepository;
    private final DeliveryMappingRepository deliveryMappingRepository;
    private final DeliveryMappingCustomRepository deliveryMappingCustomRepository;


    //departure, String destination, boolean packaging, boolean move, boolean elevator, boolean parking, String movelist, int sigungu, String departureDetail, String destinationDetail) {
    /*
        예약 신청
     */
    @Override
    public void createDelivery(DeliveryReservationRequestDto deliveryReservationRequestDto,
                               List<DeliveryRequestCompanyDto> deliveryRequestCompanyDtoList,
                               List<MultipartFile> deliveryImages,
                               Long price
    ) {
        deliveryReservationRequestDto.setPrice(price);
        //Delivery Entity 생성
        //car 가져옴.
        Car car = carRepository.findById(deliveryReservationRequestDto.getCarId()).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CAR));
        deliveryReservationRequestDto.setCar(car);//car 세팅
        //Delivery 생성
        Delivery delivery = DeliveryReservationRequestDto.toEntity(deliveryReservationRequestDto);

        //image Setting
        deliveryImgSetting(deliveryImages, delivery);
        //mapping Setting
        deliveryMappingSetting(deliveryRequestCompanyDtoList, price, delivery);

        //저장
        deliveryRepository.save(delivery);
    }
    /*
        Mapping 연결 저장
     */
    private void deliveryMappingSetting(List<DeliveryRequestCompanyDto> deliveryRequestCompanyDtoList, Long price, Delivery delivery) {
        // code small 용달은 항상 101(신청중)
        CodeSmall codeSmall = codeSmallRepository.findById(101L).orElseThrow(() -> new BaseException(StatusCode.CODE_DOES_NOT_EXIST));
        // member 가져오기-- 추후 jwt 도입시 변경 필요
        Member member = memberRepository.findById(1L).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));

        for(DeliveryRequestCompanyDto company : deliveryRequestCompanyDtoList){
            Company resultCompany = companyRepository.findByMemberIdAndRole(company.getMemberId(), Role.DELIVER).orElseThrow(()->new BaseException(StatusCode.NOT_EXIST_COMPANY));
            DeliveryMapping deliveryMapping = DeliveryMapping.builder()
                    .company(resultCompany)
                    .member(member) //member 임시
                    .codeSmall(codeSmall)
                    .price(price)
                    .delivery(delivery)
                    .reissuing(0L) // 초기값 0
                    .build();

            delivery.addMapping(deliveryMapping);
        }
    }
    /*
        delivery Image 세팅
     */
    private void deliveryImgSetting(List<MultipartFile> deliveryImages, Delivery delivery) {
        for(MultipartFile file: deliveryImages){
            S3UploadDto deliveryImg = uploadFile(file, "deliverImg");
            DeliveryImage deliveryImage = DeliveryImage.builder()
                    .dto(deliveryImg).delivery(delivery)
                    .build();
            delivery.addImage(deliveryImage);
        }
    }

    /*
        계산기
     */
    @Override
    public DeliveryCalResponseDto calculateDelivery(MobilityDto mobilityDto, DeliveryCalRequestDto calDto, Double OilPrice) {
        //end Time 구하기
        LocalDateTime end = getEndTime(mobilityDto, calDto);
        //car 조회
        Car car = carRepository.findById(calDto.getCarId()).orElseThrow(()->new BaseException(StatusCode.NOT_EXIST_CAR));
        Long price = car.getPrice()*10000; //대여비
        price += getDistancePrice(mobilityDto, OilPrice, car); //거리 당 비용 ( 주유 가격 고려 + 톨게이트 비용 )

        if(calDto.isMove()){ //같이 이동시 30000원 추가
            price += 30000;
        }
        if(calDto.isPackaging()){ //포장 필요시 10000원 추가
            price += 10000;
        }
        if(!calDto.isParking()){ //주차 없으면 10000원 추가
            price += 10000;
        }

        // % 수수료 계산
        if(!calDto.isElevator()){ //엘베 없으면
            price =Math.round(price*1.15);
        }
        price = getTimeFee(calDto, price); //시간당 수수료 계산.

        long result = Math.round((double) price / 100) * 100; // 반올림

        return new DeliveryCalResponseDto(result,end);
    }
    /*
        시간당 부가세 계산
     */
    private static Long getTimeFee(DeliveryCalRequestDto calDto, Long price) {
        String time = calDto.getStartTime().split(" ")[1].split(":")[0];
        Integer startTime = Integer.valueOf(time);
        //출퇴근이면 5%
        if(startTime==7||startTime==8||startTime==18||startTime==19){
            price = Math.round(price *1.05);
            //야간 10%
        }else if(startTime>=22||(startTime>=0&&startTime<=5)){
            price = Math.round(price *1.10);
        }
        return price;
    }
    /*
        현재 평균 유가 당 거리 가격 계산
     */
    @NotNull
    private static Long getDistancePrice(MobilityDto mobilityDto, double OilPrice, Car car) {
        double distanceKm = Math.ceil((double) mobilityDto.getDistance() /1000); //몇 Km?
        return (long) (( distanceKm / car.getMileage() ) * OilPrice)+ mobilityDto.getToll();
    }
    /*
        거리 계산 결과를 가지고 endTime 계산
     */
    @NotNull
    private static LocalDateTime getEndTime(MobilityDto mobilityDto, DeliveryCalRequestDto calDto) {
        //모빌리티 API로 End Time 계산
        Long hour = (long) Math.ceil(mobilityDto.getDuration() / 3600.0);
        //날짜 포맷
        LocalDateTime start = getLocalDateTime(calDto.getStartTime());
        return start.plusHours(hour);
    }

    @Override
    public void cancelDelivery(Long mappingId) {
        //1. delivery_mapping 가져옴.
        DeliveryMapping deliveryMapping = deliveryMappingRepository.findById(mappingId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MAPPING));

        //2. 상태 105로 변경 ( 105 : 취소 )
        CodeSmall codeSmall = codeSmallRepository.findById(105L).orElseThrow(() -> new BaseException(StatusCode.CODE_DOES_NOT_EXIST));
        deliveryMapping.setCodeSmall(codeSmall);//업데이트
    }

    @Override
    public void companyListDelivery() {

    }
    /*
        용달 유저 예약 전체 조회
     */
    @Override
    public List<UserReservationDto> userReservationDelivery(Long memberId) {

        //distinct한 deliveryId
        List<UserReservationDto> userReservationInfo = deliveryMappingCustomRepository.findUserReservationInfo(memberId);

        return userReservationInfo;
    }

    //s3
    private S3UploadDto uploadFile(MultipartFile file, String fileNamePrefix) {
        if (file != null && !file.isEmpty()) {
            return s3Service.upload(file, "umzip-service", fileNamePrefix);
        }
        return null;
    }
}
