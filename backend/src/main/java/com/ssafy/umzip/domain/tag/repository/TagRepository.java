package com.ssafy.umzip.domain.tag.repository;

import com.ssafy.umzip.domain.tag.dto.TagListByLargeCodeResponce;
import com.ssafy.umzip.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

//    Optional<List<Tag>>  findAllByCodeLargeId(Long largeCode);
    Optional<List<Tag>>  findAllByCodeLargeId(Long largeCode);
}
