package com.ssafy.umzip.domain.clean.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.clean.dto.company.CleanCompanyReservationResponseDto;
import com.ssafy.umzip.domain.clean.dto.company.CleanQuotationRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanReservationRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.UserCleanMappingDto;
import com.ssafy.umzip.domain.clean.dto.user.UserCleanMappingRepoDto;
import com.ssafy.umzip.domain.clean.dto.user.UserCleanReservationResponseDto;
import com.ssafy.umzip.domain.clean.entity.QCleanMapping;
import com.ssafy.umzip.domain.code.entity.CodeSmall;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ssafy.umzip.domain.clean.entity.QClean.clean;
import static com.ssafy.umzip.domain.clean.entity.QCleanMapping.cleanMapping;
import static com.ssafy.umzip.domain.code.entity.QCodeSmall.codeSmall;
import static com.ssafy.umzip.domain.company.entity.QCompany.company;
import static com.ssafy.umzip.domain.delivery.entity.QDeliveryMapping.deliveryMapping;
import static com.ssafy.umzip.domain.member.entity.QMember.member;

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
