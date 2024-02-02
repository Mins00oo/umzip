package com.ssafy.umzip.domain.clean.service;

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
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.s3.S3Service;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final CustomReviewReceiverRepository reviewReceiverRepository;

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
        setCleanMappings(companys, price, memberId, clean);
        //clean Image 세팅
        setImages(imageFileList, clean);

        cleanRepository.save(clean);
    }
    /*
        유저 : 예약 정보 확인
     */

    @Override
    public List<UserCleanReservationResponseDto> userReservationClean(Long memberId) {
        return cleanCustomRepository.findUserReservationInfo(memberId);
    }
    /*
        유저 : 예약 취소 API
     */
    @Override
    public Boolean cancelClean(Long mappingId, Long memberId) {
        CleanMapping cleanMapping = cleanMappingRepository.findById(mappingId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CLEAN_MAPPING));
        if(cleanMapping.getMember().getId()!=memberId){
            throw new BaseException(StatusCode.INVALID_ACCESS_CLEAN_MAPPING);
        }
        CodeSmall codeSmall = codeSmallRepository.findById(205L).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CODE));
        cleanMapping.setCodeSmall(codeSmall);
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

    private void setCleanMappings(List<CleanReservationCompanyDto> companys, Long price, Long memberId, Clean clean) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));
        CodeSmall codeSmall = codeSmallRepository.findById(201L).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CODE));
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
        }
    }

    //s3
    private S3UploadDto uploadFile(MultipartFile file, String fileNamePrefix) {
        if (file != null && !file.isEmpty()) {
            return s3Service.upload(file, "umzip-service", fileNamePrefix);
        }
        return null;
    }
}
