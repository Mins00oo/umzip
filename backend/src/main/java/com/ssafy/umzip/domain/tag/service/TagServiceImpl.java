package com.ssafy.umzip.domain.tag.service;

import com.ssafy.umzip.domain.code.entity.CodeLarge;
import com.ssafy.umzip.domain.code.repository.CodeLargeRepository;
import com.ssafy.umzip.domain.tag.dto.TagListByLargeCodeResponce;
import com.ssafy.umzip.domain.tag.entity.Tag;
import com.ssafy.umzip.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
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
    // 코드 이름
    public List<TagListByLargeCodeResponce> receiveTagType(long largeCode) {
        List<TagListByLargeCodeResponce> tagListByLargeCode = new ArrayList<>();
        Optional<List<Tag>> optionalTagList = tagRepository.findAllByCodeLargeId(largeCode);

        if (optionalTagList.isPresent()) {
            List<Tag> tagList = optionalTagList.get();
            List<TagListByLargeCodeResponce> tagDTOList = new ArrayList<>();

            for (Tag tag : tagList) {
                tagDTOList.add(convertToDTO(tag));
            }

            return tagDTOList;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<TagListByLargeCodeResponce> receiveTagByRole(String role) {
        // 1) CodeLarge에서 역할값으로 ID값 가져오는 과정
        Optional<CodeLarge> codeLarge = codeLargeRepository.findIdByName(role);

        long largeCode = 0L;
        if (!codeLarge.isEmpty()) {
            CodeLarge code = codeLarge.get();
            largeCode = code.getId();
        }

        // 2) CodeLarge ID로 관련 태그값 가져오는 과정
        List<TagListByLargeCodeResponce> tagListByLargeCode = new ArrayList<>();
        Optional<List<Tag>> optionalTagList = tagRepository.findAllByCodeLargeId(largeCode);

        if(!optionalTagList.isEmpty()){
            List<Tag> tagList = optionalTagList.get();
            List<TagListByLargeCodeResponce> tagDTOList = new ArrayList<>();

            for (Tag tag : tagList) {
                tagDTOList.add(convertToDTO(tag));
            }

            return tagDTOList;

        } else {
            return Collections.emptyList();
        }
    }

    private TagListByLargeCodeResponce convertToDTO(Tag tag) {
        TagListByLargeCodeResponce dto = new TagListByLargeCodeResponce();
        dto.setTagId(tag.getTagId());
        dto.setTagName(tag.getTagName());
        return dto;
    }


}
