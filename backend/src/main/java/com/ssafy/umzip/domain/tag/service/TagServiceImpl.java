package com.ssafy.umzip.domain.tag.service;

import com.ssafy.umzip.domain.code.entity.CodeLarge;
import com.ssafy.umzip.domain.code.repository.CodeLargeRepository;
import com.ssafy.umzip.domain.tag.dto.TagListByLargeCodeResponce;
import com.ssafy.umzip.domain.tag.entity.Tag;
import com.ssafy.umzip.domain.tag.repository.TagRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final CodeLargeRepository codeLargeRepository;
    @Override
    public List<TagListByLargeCodeResponce> findByCodeLargeName(String role) {
        return tagRepository.findByCodeLarge_CodeLargeName(role);
    }

}
