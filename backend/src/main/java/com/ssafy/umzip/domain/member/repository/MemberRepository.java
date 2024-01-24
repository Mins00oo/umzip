package com.ssafy.umzip.domain.member.repository;

import com.ssafy.umzip.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);
}
