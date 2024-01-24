package com.ssafy.umzip.domain.company.repository;

import com.ssafy.umzip.domain.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
