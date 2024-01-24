package com.ssafy.umzip.domain.tag.service;


import com.ssafy.umzip.domain.tag.dto.TagListByLargeCodeResponce;

import java.util.List;

public interface TagService {
    List<TagListByLargeCodeResponce> receiveTagType(long largeCode);
}
