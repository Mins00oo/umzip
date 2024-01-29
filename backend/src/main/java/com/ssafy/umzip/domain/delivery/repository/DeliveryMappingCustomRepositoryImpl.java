package com.ssafy.umzip.domain.delivery.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.delivery.dto.DeliveryQuotationRequestDto;
import com.ssafy.umzip.domain.delivery.dto.UserDeliveryMappingRepoDto;
import com.ssafy.umzip.domain.delivery.dto.UserDeliveryMappingDto;
import com.ssafy.umzip.domain.delivery.dto.UserReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ssafy.umzip.domain.company.entity.QCompany.company;
import static com.ssafy.umzip.domain.delivery.entity.QDelivery.delivery;
import static com.ssafy.umzip.domain.delivery.entity.QDeliveryMapping.deliveryMapping;

@RequiredArgsConstructor
@Repository
public class DeliveryMappingCustomRepositoryImpl implements DeliveryMappingCustomRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<UserReservationDto> findUserReservationInfo(Long memberId) {

        List<UserReservationDto> result = queryFactory
                .select(Projections.constructor(
                        UserReservationDto.class,
                        delivery.id.as("id"),
                        delivery.createDt.as("createDt"),
                        delivery.startTime.as("startTime"),
                        delivery.sigungu.as("sigungu")
                ))
                .from(deliveryMapping)
                .join(deliveryMapping.delivery, delivery)
                .where(deliveryMapping.member.id.eq(memberId))
                .distinct()
                .fetch();

        List<UserDeliveryMappingRepoDto> mappingRepoDtoList = queryFactory
                .select(Projections.constructor(
                        UserDeliveryMappingRepoDto.class,
                        delivery.id.as("deliveryId"),
                        deliveryMapping.id.as("mappingId"),
                        deliveryMapping.detail.as("detail"),
                        deliveryMapping.codeSmall.id.as("codeSmallId"),
                        company.id.as("companyId"),
                        company.name.as("companyName"),
                        company.imageUrl.as("imageUrl")
                ))
                .from(deliveryMapping)
                .join(deliveryMapping.delivery, delivery)
                .join(deliveryMapping.company, company)
                .where(deliveryMapping.member.id.eq(memberId))
                .fetch();
            /*
            private Long mappingId;
    private Long companyId;
    private String companyName;
    private String imageUrl;
    private String detail;
    private Long codeSmallId;
             */
        Map<Long, List<UserDeliveryMappingDto>> mappingMap = mappingRepoDtoList.stream()
                .collect(Collectors.groupingBy(
                        UserDeliveryMappingRepoDto::getDeliveryId,
                        Collectors.mapping(dto ->
                                UserDeliveryMappingDto.builder()
                                        .mappingId(dto.getMappingId())
                                        .companyId(dto.getCompanyId())
                                        .companyName(dto.getCompanyName())
                                        .imageUrl(dto.getImageUrl())
                                        .detail(dto.getDetail())
                                        .codeSmallId(dto.getCodeSmallId())
                                        .build(), Collectors.toList())
                ));


        result.forEach(delivery ->{
            List<UserDeliveryMappingDto> list = mappingMap.get(delivery.getId());
            Long recentCode = 0L;
            for (UserDeliveryMappingDto userDeliveryMappingDto : list) {
                recentCode = Math.max(recentCode,userDeliveryMappingDto.getCodeSmallId());
            }
            delivery.setList(list);
            delivery.setStatus(recentCode);
        });
        return result;
    }

    @Override
    public Boolean updateDeliveryMappingDetailAndReissuingAndCodeSmall(DeliveryQuotationRequestDto dto,CodeSmall codeSmall) {
        long result = queryFactory
                .update(deliveryMapping)
                .set(deliveryMapping.detail, dto.getDetail())
                .set(deliveryMapping.reissuing, dto.getPrice())
                .set(deliveryMapping.codeSmall, codeSmall)  // assuming codeSmall is a foreign key in DeliveryMapping
                .where(deliveryMapping.id.eq(dto.getMappingId()))
                .execute();

        return result != 0L;

    }
}
