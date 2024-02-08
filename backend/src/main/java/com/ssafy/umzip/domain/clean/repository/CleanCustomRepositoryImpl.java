package com.ssafy.umzip.domain.clean.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.clean.dto.company.CleanCompanyReservationResponseDto;
import com.ssafy.umzip.domain.clean.dto.company.CleanQuotationRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.*;
import com.ssafy.umzip.domain.clean.entity.QCleanMapping;
import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.global.common.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ssafy.umzip.domain.clean.entity.QClean.clean;
import static com.ssafy.umzip.domain.clean.entity.QCleanMapping.cleanMapping;
import static com.ssafy.umzip.domain.code.entity.QCodeSmall.codeSmall;
import static com.ssafy.umzip.domain.company.entity.QCompany.company;
import static com.ssafy.umzip.domain.member.entity.QMember.member;
import static com.ssafy.umzip.global.common.CommonMethods.getLocalDateTime;

@RequiredArgsConstructor
@Repository
public class CleanCustomRepositoryImpl implements CleanCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Boolean updateCodeAndReissuingAndDetail(CleanQuotationRequestDto dto, CodeSmall codeSmall) {
        long result = queryFactory
                .update(cleanMapping)
                .set(cleanMapping.detail, dto.getDetail())
                .set(cleanMapping.codeSmall, codeSmall)
                .set(cleanMapping.reissuing, dto.getReissuing())
                .where(cleanMapping.id.eq(dto.getMappingId()))
                .execute();
        return result > 0;
    }

    @Override
    public List<UserCleanReservationResponseDto> findUserReservationInfo(Long memberId) {
        //clean 가져옴
        List<UserCleanReservationResponseDto> cleans = queryFactory.select(
                        Projections.constructor(
                                UserCleanReservationResponseDto.class,
                                clean.id.as("cleanId"),
                                clean.createDt.as("createDt"),
                                clean.reservationTime.as("reservationTime"),
                                clean.address.as("address")
                        )
                ).from(cleanMapping)
                .join(cleanMapping.clean, clean)
                .where(cleanMapping.member.id.eq(memberId))
                .distinct()
                .fetch();
        //mapping들 가져옴.
        List<UserCleanMappingRepoDto> mappings = queryFactory.select(
                        Projections.constructor(
                                UserCleanMappingRepoDto.class,
                                clean.id.as("cleanId"),
                                cleanMapping.id.as("mappingId"),
                                company.id.as("companyId"),
                                company.name.as("companyName"),
                                company.imageUrl.as("imageUrl"),
                                cleanMapping.detail.as("detail"),
                                codeSmall.id.as("codeSmallId"),
                                cleanMapping.price.as("price"),
                                cleanMapping.reissuing.as("reissuing")
                        )
                ).from(cleanMapping)
                .join(cleanMapping.clean, clean)
                .join(cleanMapping.company, company)
                .join(cleanMapping.codeSmall, codeSmall)
                .where(cleanMapping.member.id.eq(memberId))
                .distinct()
                .fetch();
        Map<Long, List<UserCleanMappingDto>> cleanCompanyMap = mappings.stream()
                .collect(Collectors.groupingBy(UserCleanMappingRepoDto::getCleanId,
                        Collectors.mapping(dto ->
                                UserCleanMappingDto.builder()
                                        .mappingId(dto.getMappingId())
                                        .companyId(dto.getCompanyId())
                                        .companyName(dto.getCompanyName())
                                        .imageUrl(dto.getImageUrl())
                                        .detail(dto.getDetail())
                                        .codeSmallId(dto.getCodeSmallId())
                                        .price(dto.getPrice())
                                        .reissuing(dto.getReissuing())
                                        .build(), Collectors.toList())
                ));
        for(UserCleanReservationResponseDto reservation:cleans){
            List<UserCleanMappingDto> mappingDtos = cleanCompanyMap.get(reservation.getCleanId());
            reservation.setList(mappingDtos);
            Long recentStatus = 0L;
            for(UserCleanMappingDto dto:mappingDtos){
                recentStatus = Math.max(recentStatus,dto.getCodeSmallId());
            }
            reservation.setStatus(recentStatus);
        }
        return cleans;
    }

    /**
     * 유저 : 매칭 Repository
     */
    @Override
    public List<CleanMatchingCompanyDto> findCompanyMatchingList(CleanCompanyListRequestDto dto) {
        // 1. cleanMapping을 기준으로 시간에 해당하지 않는 company찾기
        LocalDateTime startTime = getLocalDateTime(dto.getReservationTime());
        LocalDateTime endTime = startTime.plusHours(4);
        LocalDateTime startTimeMinus4 = startTime.minusHours(4);
        // 2. 시간이 겹치는 Mapping
        List<Long> invalidMapping = queryFactory.select(
                            cleanMapping.id
                ).from(cleanMapping)
                .join(cleanMapping.clean, clean)
                .where(clean.reservationTime.between(startTime,endTime)
                        .or(clean.reservationTime.loe(startTime)
                                .and(clean.reservationTime.gt(startTimeMinus4))
                        )
                ).distinct().fetch();

        List<CleanMatchingCompanyDto> ans = queryFactory.select(
                        Projections.constructor(
                                CleanMatchingCompanyDto.class,
                                company.id.as("companyId"),
                                company.member.id.as("memberId"),
                                company.experience.as("experience"),
                                company.imageUrl.as("imageUrl"),
                                company.ceo.as("ceo"),
                                company.name.as("companyName")
                        )
                ).from(company)
                .leftJoin(cleanMapping).on(cleanMapping.company.id.eq(company.id))
                .where(company.sigungu.eq(dto.getSigungu())
                        .and(company.role.eq(Role.CLEAN))
                        .and(company.id.notIn(
                                queryFactory.select( // 포함되면 안됨.
                                        company.id
                                ).from(cleanMapping)
                                .join(cleanMapping.company, company)
                                .where(cleanMapping.id.in(invalidMapping), //시간이 겹치고
                                        cleanMapping.codeSmall.id.eq(203L)  // 예약 완료인 상태인 companyId는
                                )
                                .distinct()
                                .fetch()
                                )
                        )
                )
                .orderBy(company.experience.asc())
                .distinct()
                .limit(dto.getLimit())
                .fetch();


        return ans;
    }

    @Override
    public List<CleanCompanyReservationResponseDto> findCompanyReservationInfo(Long companyId) {
        return queryFactory.selectDistinct(
                        Projections.constructor(
                                CleanCompanyReservationResponseDto.class,
                                cleanMapping.id.as("mappingId"),
                                clean.id.as("cleanId"),
                                clean.createDt.as("createDt"),
                                clean.reservationTime.as("reservationTime"),
                                cleanMapping.price.as("price"),
                                cleanMapping.reissuing.as("reissuing"),
                                cleanMapping.codeSmall.id.as("codeSmallId"),
                                member.name.as("memberName"),
                                member.imageUrl.as("memberImg")
                        )
                ).from(cleanMapping)
                .join(cleanMapping.clean, clean)
                .join(cleanMapping.member, member)
                .where(
                        cleanMapping.company.id.eq(companyId)
                ).distinct()
                .fetch();
    }
}
