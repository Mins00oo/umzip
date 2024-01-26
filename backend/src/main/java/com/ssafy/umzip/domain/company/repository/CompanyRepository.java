package com.ssafy.umzip.domain.company.repository;

import com.ssafy.umzip.domain.company.entity.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @EntityGraph(attributePaths = {"member"})
    List<Company> findAllByMemberId(Long memberId);
}
