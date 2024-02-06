package com.ssafy.umzip.domain.clean.service;

import com.ssafy.umzip.domain.alarm.dto.AlarmDto;
import com.ssafy.umzip.domain.alarm.dto.AlarmType;
import com.ssafy.umzip.domain.alarm.entity.Alarm;
import com.ssafy.umzip.domain.alarm.repository.AlarmRepository;
import com.ssafy.umzip.domain.clean.dto.user.*;
import com.ssafy.umzip.domain.clean.entity.Clean;
import com.ssafy.umzip.domain.clean.entity.CleanImage;
import com.ssafy.umzip.domain.clean.entity.CleanMapping;
import com.ssafy.umzip.domain.clean.repository.CleanCustomRepository;
import com.ssafy.umzip.domain.clean.repository.CleanCustomRepositoryImpl;
import com.ssafy.umzip.domain.clean.repository.CleanMappingRepository;
import com.ssafy.umzip.domain.clean.repository.CleanRepository;
import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.company.repository.CompanyRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.domain.reviewreceiver.dto.TopTagListRequest;
import com.ssafy.umzip.domain.reviewreceiver.dto.TopTagListResponse;
import com.ssafy.umzip.domain.reviewreceiver.repository.CustomReviewReceiverRepository;
import com.ssafy.umzip.domain.reviewreceiver.repository.ReviewReceiverRepository;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.s3.S3Service;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssafy.umzip.global.common.Role.CLEAN;

@Service
@Transactional
@RequiredArgsConstructor
public class CleanUserServiceImpl implements CleanUserService{
    private final S3Service s3Service;
    private final CleanRepository cleanRepository;
    private final CodeSmallRepository codeSmallRepository;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final CleanMappingRepository cleanMappingRepository;
    private final CleanCustomRepository cleanCustomRepository;
    private final ReviewReceiverRepository reviewReceiverRepository;
    private final AlarmRepository alarmRepository;
    /*
        청소 예약 신청 ( 101 )
     */
    @Override
    public void createClean(List<CleanReservationCompanyDto> companys,
                            List<MultipartFile> imageFileList,
                            Long price,
                            CleanReservationRequestDto reservationRequestDto,
                            Long memberId
    ) {
        /*  1. 넘어온 정보들로 Delivery Entity 만든다.

            2.1 CompanyId 리스트에서 for문을 돌면서 mapping 만들어서 add해줌
            2.2 Image Setting
         */
        Clean clean = CleanReservationRequestDto.toEntity(reservationRequestDto);
        //clean Mapping 세팅
        List<Alarm> alarms = setCleanMappings(companys, price, memberId, clean);
        //clean Image 세팅
        setImages(imageFileList, clean);
        // clean 예약 신청 저장
        cleanRepository.save(clean);
        // 알람 저장
        alarmRepository.saveAll(alarms);
    }
    /*
        유저 : 예약 정보 확인
     */

    @Override
    public List<UserCleanReservationResponseDto> userReservationClean(Long memberId) {
        return cleanCustomRepository.findUserReservationInfo(memberId);
    }
    /*
        유저 : 예약 취소 API ( 105 )
     */
    @Override
    public Boolean cancelClean(Long mappingId, Long memberId) {
        CleanMapping cleanMapping = cleanMappingRepository.findById(mappingId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CLEAN_MAPPING));
        if(cleanMapping.getMember().getId()!=memberId){
            throw new BaseException(StatusCode.INVALID_ACCESS_CLEAN_MAPPING);
        }
        CodeSmall codeSmall = codeSmallRepository.findById(205L).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CODE));
        cleanMapping.setCodeSmall(codeSmall);
        //알람
        AlarmDto alarm = AlarmDto.builder()
                .alarmType(AlarmType.CLEAN)
                .read(false)
                .codeSmallId(codeSmall.getId())
                .member(cleanMapping.getMember())
                .build();
        Alarm companyAlarm = alarm.toCompanyDeliveryAndCleanAlarmEntity(cleanMapping.getCompany());
        alarmRepository.save(companyAlarm);

        return true;
    }


    /**
     * 유저 : 매칭 API
     */
    @Override
    public List<CleanMatchingCompanyDto> companyListClean(CleanCompanyListRequestDto dto) {
        List<CleanMatchingCompanyDto> companys = cleanCustomRepository.findCompanyMatchingList(dto);
        List<Long> memberIdList = companys.stream()
                .map(CleanMatchingCompanyDto::getMemberId)
                .toList();

        TopTagListRequest request = TopTagListRequest.builder()
                .memberId(memberIdList)
                .role(CLEAN.toString())
                .limit(3)
                .build();
        List<TopTagListResponse> tagList = reviewReceiverRepository.findTopTagsListByMemberIdAndRole(request);

        Map<Long, List<String>> tagGroupedByCompanyId = tagList.stream()
                .collect(Collectors.groupingBy(TopTagListResponse::getCompanyId,
                        Collectors.mapping(TopTagListResponse::getTag, Collectors.flatMapping(List::stream, Collectors.toList()))));
        for(CleanMatchingCompanyDto companyDto : companys){
            List<String> tags = tagGroupedByCompanyId.get(companyDto.getCompanyId());
            companyDto.setTags(tags);
        }

        return companys;
    }
    /*
        유저 : 유저 청소 예약 상세
     */
    @Override
    public CleanDetailResponseDto getCleanDetail(Long memberId, Long cleanId) {
        Clean clean = cleanRepository.findById(cleanId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CLEAN));
        if(!cleanMappingRepository.existsByCleanIdAndMemberId(cleanId, memberId)){
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



    private void setImages(List<MultipartFile> imageFileList, Clean clean) {
        for(MultipartFile file: imageFileList){
            S3UploadDto cleanImg = uploadFile(file, "cleanImg");
            CleanImage cleanImage = CleanImage.builder()
                    .clean(clean)
                    .uploadDto(cleanImg)
                    .build();
            clean.addImage(cleanImage);
        }
    }

    private List<Alarm> setCleanMappings(List<CleanReservationCompanyDto> companys, Long price, Long memberId, Clean clean) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));
        CodeSmall codeSmall = codeSmallRepository.findById(201L).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CODE));
        AlarmDto alarm = AlarmDto.builder()
                .read(false)
                .codeSmallId(codeSmall.getId())
                .alarmType(AlarmType.CLEAN)
                .member(member)
                .build();
        for(CleanReservationCompanyDto companyDto: companys){
            Company company = companyRepository.findByMemberIdAndRole(companyDto.getMemberId(), CLEAN).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_COMPANY));
            CleanMapping cleanMapping = CleanMapping.builder()
                    .clean(clean)
                    .member(member)
                    .company(company)
                    .price(price)
                    .codeSmall(codeSmall)
                    .reissuing(0L)
                    .build();
            clean.addMapping(cleanMapping);
            //알림에 company추가
            alarm.addCompany(company);
        }
        List<Alarm> alarmList = new ArrayList<>();
        for(Company company: alarm.getCompanyList()){
            Alarm companyAlarmEntity = alarm.toCompanyDeliveryAndCleanAlarmEntity(company);
            alarmList.add(companyAlarmEntity);
        }
        return alarmList;
    }

    //s3
    private S3UploadDto uploadFile(MultipartFile file, String fileNamePrefix) {
        if (file != null && !file.isEmpty()) {
            return s3Service.upload(file, "umzip-service", fileNamePrefix);
        }
        return null;
    }
}
