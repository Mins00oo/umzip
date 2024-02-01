package com.ssafy.umzip.domain.clean.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.clean.dto.company.CleanQuotationRequestDto;
import com.ssafy.umzip.domain.clean.entity.QCleanMapping;
import com.ssafy.umzip.domain.code.entity.CodeSmall;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.ssafy.umzip.domain.clean.entity.QCleanMapping.cleanMapping;
import static com.ssafy.umzip.domain.delivery.entity.QDeliveryMapping.deliveryMapping;

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
}
