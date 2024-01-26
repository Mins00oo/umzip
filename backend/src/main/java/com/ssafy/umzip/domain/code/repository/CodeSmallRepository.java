package com.ssafy.umzip.domain.code.repository;

import com.ssafy.umzip.domain.code.entity.CodeLarge;
import com.ssafy.umzip.domain.code.entity.CodeSmall;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeSmallRepository extends JpaRepository<CodeSmall, Long> {

    @NotNull
    public Optional<CodeSmall> findById(@NotNull Long id);
    Optional<CodeSmall>  findIdByName(String name);
}
