package com.ssafy.umzip.domain.tag.repository;

import com.ssafy.umzip.domain.tag.dto.TagListByLargeCodeResponce;
import com.ssafy.umzip.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<List<Tag>>  findAllByCodeLargeId(Long largeCode);
    @Query("SELECT new com.ssafy.umzip.domain.tag.dto.TagListByLargeCodeResponce(t.tagId, t.tagName, t.tagType) " +
            "FROM Tag t JOIN t.codeLarge cl " +
            "WHERE cl.name = :name")
    List<TagListByLargeCodeResponce> findByCodeLarge_CodeLargeName(String name);
}
