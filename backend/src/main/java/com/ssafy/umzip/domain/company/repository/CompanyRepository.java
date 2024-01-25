package com.ssafy.umzip.domain.company.repository;

import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.global.common.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByMemberIdAndRole(Long memberId, Role role);
}
