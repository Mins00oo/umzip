package com.ssafy.umzip.domain.company.repository;

import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @EntityGraph(attributePaths = {"member"})
    List<Company> findAllByMemberId(Long memberId);
    Optional<Company> findByMemberIdAndRole(Long memberId, Role role);

    Boolean existsByMember(Member member);
}
