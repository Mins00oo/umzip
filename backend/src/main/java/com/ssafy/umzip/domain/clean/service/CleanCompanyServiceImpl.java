package com.ssafy.umzip.domain.clean.service;

import com.ssafy.umzip.domain.alarm.dto.AlarmDto;
import com.ssafy.umzip.domain.alarm.dto.AlarmType;
import com.ssafy.umzip.domain.alarm.entity.Alarm;
import com.ssafy.umzip.domain.alarm.repository.AlarmRepository;
import com.ssafy.umzip.domain.clean.dto.company.CleanCompanyReservationResponseDto;
import com.ssafy.umzip.domain.clean.dto.company.CleanQuotationRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanDetailResponseDto;
import com.ssafy.umzip.domain.clean.entity.Clean;
import com.ssafy.umzip.domain.clean.entity.CleanImage;
import com.ssafy.umzip.domain.clean.entity.CleanMapping;
import com.ssafy.umzip.domain.clean.repository.CleanCustomRepository;
import com.ssafy.umzip.domain.clean.repository.CleanMappingRepository;
import com.ssafy.umzip.domain.clean.repository.CleanRepository;
import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CleanCompanyServiceImpl implements CleanCompanyService{
    private final CleanMappingRepository cleanMappingRepository;
    private final CleanRepository cleanRepository;
    private final CleanCustomRepository cleanCustomRepository;
    private final CodeSmallRepository codeSmallRepository;
    private final AlarmRepository alarmRepository;
    /*
        업체 : 사용자 거절 API ( 204 )
     */
    @Override
    public Boolean rejectionClean(Long mappingId,Long companyId) {
        CleanMapping cleanMapping = cleanMappingRepository.findById(mappingId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CLEAN_MAPPING));

        if(!cleanMapping.getCompany().getId().equals(companyId)){
            throw new BaseException(StatusCode.INVALID_ACCESS_CLEAN);
        }

        CodeSmall codeSmall = codeSmallRepository.findById(204L).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CODE));

        try{
            cleanMapping.setCodeSmall(codeSmall);
        }catch (OptimisticLockException e){
            return false;
        }
        //알람
        saveAlarm(codeSmall, cleanMapping);
        return true;
    }
    /*
        업체 : 사용자 견적 제안 API ( 202 )
     */

    @Override
    public Boolean quotationClean(CleanQuotationRequestDto dto, Long companyId) {

        CleanMapping cleanMapping = cleanMappingRepository.findById(dto.getMappingId()).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MAPPING));

        if(!cleanMapping.getCompany().getId().equals(companyId)){
            throw new BaseException(StatusCode.INVALID_ACCESS_CLEAN);
        }
        CodeSmall codeSmall = codeSmallRepository.findById(202L).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CODE));
        //견적 제안 알람
        saveAlarm(codeSmall, cleanMapping);

        return cleanCustomRepository.updateCodeAndReissuingAndDetail(dto, codeSmall);
    }



    /*
        업체 : 예약 리스트 API
     */
    @Override
    public List<CleanCompanyReservationResponseDto> companyReservationClean(Long companyId) {
        List<CleanCompanyReservationResponseDto> list = cleanCustomRepository.findCompanyReservationInfo(companyId);
        return list;
    }
    /*
        업체 : 청소 예약 상세 API
     */

    @Override
    public CleanDetailResponseDto getCleanDetail(Long companyId, Long cleanId) {
        Clean clean = cleanRepository.findById(cleanId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CLEAN));
        if(!cleanMappingRepository.existsByCleanIdAndCompanyId(cleanId, companyId)){
            throw new BaseException(StatusCode.INVALID_GET_CLEAN);
        }

        List<String> images = clean.getCleanImages().stream()
                .map(CleanImage::getPath)
                .collect(Collectors.toList());
        CleanDetailResponseDto cleanDetail = CleanDetailResponseDto.builder()
                .id(clean.getId())
                .reservationTime(clean.getReservationTime())
                .roomSize(clean.getRoomSize())
                .balconyExistence(clean.getBalconyExistence())
                .windowCount(clean.getWindowCount())
                .duplexRoom(clean.getDuplexRoom())
                .mold(clean.getMold())
                .externalWindow(clean.getExternalWindow())
                .houseSyndrome(clean.getHouseSyndrome())
                .removeSticker(clean.getRemoveSticker())
                .sigungu(clean.getSigungu())
                .address(clean.getAddress())
                .addressDetail(clean.getAddressDetail())
                .build();
        cleanDetail.setCleanImages(images);
        return cleanDetail;
    }
    private void saveAlarm(CodeSmall codeSmall, CleanMapping cleanMapping) {
        AlarmDto alarm = AlarmDto.builder()
                .alarmType(AlarmType.CLEAN)
                .read(false)
                .codeSmallId(codeSmall.getId())
                .member(cleanMapping.getMember())
                .company(cleanMapping.getCompany())
                .build();
        Alarm companyAlarm = alarm.toMemberDeliveryAndCleanAlarmEntity();
        alarmRepository.save(companyAlarm);
    }
}
