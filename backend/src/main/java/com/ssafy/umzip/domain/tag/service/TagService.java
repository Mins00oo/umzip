package com.ssafy.umzip.domain.tag.service;


import com.ssafy.umzip.domain.tag.dto.TagListByLargeCodeResponce;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagService {
    List<TagListByLargeCodeResponce> findByCodeLargeName(String name);


}
