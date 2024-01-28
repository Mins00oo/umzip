package com.ssafy.umzip.domain.delivery.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.delivery.dto.UserDeliveyMappingDto;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.umzip.domain.company.entity.QCompany.company;
import static com.ssafy.umzip.domain.delivery.entity.QDeliveryMapping.deliveryMapping;

@RequiredArgsConstructor
@Repository
public class DeliveryMappingCustomRepositoryImpl implements DeliveryMappingCustomRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<Long> findDistinctDeliveryIdsByMemberId(Long memberId) {
        return queryFactory
                .selectDistinct(deliveryMapping.delivery.id)
                .from(deliveryMapping)
                .where(deliveryMapping.member.id.eq(memberId))
                .fetch();
    }
    @Override
    public List<UserDeliveyMappingDto> findAllDeliveryMappingsWithCompany(Long deliveryId) {
        return queryFactory
                .select(Projections.constructor(UserDeliveyMappingDto.class,
                                deliveryMapping.id,
                                company.id,
                                company.name,
                                company.imageUrl,
                                deliveryMapping.detail,
                                deliveryMapping.codeSmall.id))
                .from(deliveryMapping)
                .leftJoin(deliveryMapping.company, company)
                .where(deliveryMapping.delivery.id.eq(deliveryId))
                .fetch();
    }

    @Override
    public long updateDeliveryMappingDetailAndReissuingAndCodeSmall(long mappingId, String detail, long reissuing, long codeSmallId) {
//        return queryFactory
//                .update(deliveryMapping)
//                .set(deliveryMapping.detail, detail)
//                .set(deliveryMapping.reissuing, reissuing)
//                .set(deliveryMapping.codeSmall, codeSmallId)  // assuming codeSmall is a foreign key in DeliveryMapping
//                .where(deliveryMapping.id.eq(mappingId))
//                .execute();
    }
}
