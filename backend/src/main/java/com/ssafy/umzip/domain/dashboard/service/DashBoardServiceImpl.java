package com.ssafy.umzip.domain.dashboard.service;

import com.ssafy.umzip.domain.clean.dto.user.UserCleanReservationResponseDto;
import com.ssafy.umzip.domain.clean.repository.CleanCustomRepository;
import com.ssafy.umzip.domain.dashboard.dto.DashBoardReservationResponseDto;
import com.ssafy.umzip.domain.dashboard.dto.ReservationDto;
import com.ssafy.umzip.domain.dashboard.dto.ReservationDtoComparator;
import com.ssafy.umzip.domain.delivery.dto.CompanyReservationDto;
import com.ssafy.umzip.domain.delivery.dto.UserDeliveryReservationDto;
import com.ssafy.umzip.domain.delivery.repository.DeliveryCustomRepository;
import com.ssafy.umzip.global.common.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class DashBoardServiceImpl implements DashBoardService{
    private final DeliveryCustomRepository deliveryCustomRepository;
    private final CleanCustomRepository cleanCustomRepository;
    @Override
    public List<DashBoardReservationResponseDto> getAllReservationList(Long memberId) {
        //결과 두개 받아오기
        List<UserDeliveryReservationDto> deliveryReservations = deliveryCustomRepository.findUserReservationInfo(memberId);
        List<UserCleanReservationResponseDto> cleanReservations = cleanCustomRepository.findUserReservationInfo(memberId);
        //합친 후 sort하기
        List<ReservationDto> concatResult = new ArrayList<>(Stream.concat(
                        deliveryReservations.stream(),
                        cleanReservations.stream()
                )
                .toList());
        concatResult.sort(new ReservationDtoComparator());

        return setRole(concatResult);
    }

    private static List<DashBoardReservationResponseDto> setRole(List<ReservationDto> concatResult) {
        List<DashBoardReservationResponseDto> result = new ArrayList<>();
        for(ReservationDto reservation: concatResult){ //DTO 대로 ROLE 세팅
            Role role = reservation.getRole();
            if(role==Role.DELIVER){
                result.add(new DashBoardReservationResponseDto(role, (UserDeliveryReservationDto) reservation));
            }else  {
                result.add(new DashBoardReservationResponseDto(role, (UserCleanReservationResponseDto) reservation));
            }

        }
        return result;
    }
}
