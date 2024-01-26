package com.ssafy.umzip.domain.code.repository;

import com.ssafy.umzip.domain.code.entity.CodeLarge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

public interface CodeLargeRepository extends JpaRepository<CodeLarge, Long> {
    Optional<CodeLarge>  findIdByName(String name);

}
