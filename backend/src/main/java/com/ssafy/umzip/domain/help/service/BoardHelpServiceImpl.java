package com.ssafy.umzip.domain.help.service;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.domain.help.dto.*;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.entity.BoardHelpComment;
import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import com.ssafy.umzip.domain.help.repository.BoardHelpImageRepository;
import com.ssafy.umzip.domain.help.repository.BoardHelpRepository;
import com.ssafy.umzip.domain.help.repository.CommentRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.s3.S3Service;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardHelpServiceImpl implements BoardHelpService {


    private final MemberRepository memberRepository;
    private final BoardHelpRepository boardHelpRepository;
    private final BoardHelpImageRepository boardHelpImageRepository;
    private final CodeSmallRepository codeSmallRepository;
    private final CommentRepository commentRepository;

    private final S3Service s3Service;

    @Transactional
    @Override
    public void postBoardHelp(Long memberId, int sigungu, BoardHelpPostRequestDto requestDto, List<MultipartFile> files) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_MEMBER_PK));

        CodeSmall codeSmall = codeSmallRepository.findById(requestDto.getCodeSmallId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CODE));

        BoardHelp boardHelp = requestDto.toEntity(requestDto, member, sigungu, codeSmall);
        boardHelpRepository.save(boardHelp);

        S3UploadDto s3UploadDto;
        for (MultipartFile file : files) {
            s3UploadDto = s3Service.upload(file, "umzip-service", "boardHelp");
            BoardHelpImage boardHelpImage = new BoardHelpImage(s3UploadDto, boardHelp);
            boardHelpImageRepository.save(boardHelpImage);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BoardHelpListDto> listBoardHelp(
            BoardHelpListRequestDto requestDto,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable) {

        memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_MEMBER_PK));

        int curPage = pageable.getPageNumber() - 1;
        int size = pageable.getPageSize();
        int sigungu = requestDto.getSigungu();
        String keyword = requestDto.getKeyword();
        Long codeSmallId = requestDto.getCodeSmallId(); // 0, 401, 402

        Page<BoardHelp> boards = boardHelpRepository
                .findPageByTitleContainingAndSigunguAndCodeSmall(keyword, sigungu, codeSmallId,
                        PageRequest.of(curPage, size, Sort.Direction.DESC, "id"));

        Page<BoardHelpListDto> boardDtoList = BoardHelpListDto.toDto(boards);

        boardDtoList.getContent().forEach(dto -> {
            Long id = dto.getId();
            Long commentCnt = commentRepository.countAllByBoardIdGroupBy(id);
            if (commentCnt == null) {
                commentCnt = 0L;
            }
            dto.setCommentCnt(commentCnt);
        });

        return boardDtoList;
    }

    @Transactional
    @Override
    public void postComment(CommentRequestDto requestDto) {

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_MEMBER_PK));

        BoardHelp boardHelp = boardHelpRepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_BOARD_PK));

        if (boardHelp.getIsAdopted()) {
            throw new BaseException(StatusCode.NOT_POST_COMMENT);
        }

        BoardHelpComment comment = requestDto.toEntity(requestDto, boardHelp, member);
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public BoardHelpDetailDto detailBoardHelp(BoardHelpDetailRequestDto requestDto) {

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_MEMBER_PK));

        BoardHelp boardHelp = boardHelpRepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_BOARD_PK));

        boardHelp.setReadCnt(boardHelp.getReadCnt() + 1);

        List<BoardHelpImage> boardHelpImages = boardHelpImageRepository.findAllById(requestDto.getBoardId());
        List<String> imageList = new ArrayList<>();
        for (BoardHelpImage image : boardHelpImages) {
            imageList.add(image.getImagePath());
        }

        boolean isSameMember = false;
        if (Objects.equals(member.getId(), boardHelp.getMember().getId())) {
            isSameMember = true;
        }

        List<BoardHelpComment> commentList = commentRepository.findAllByBoardHelpId(requestDto.getBoardId());

        return BoardHelpDetailDto.builder()
                .isSameMember(isSameMember)
                .boardHelp(boardHelp)
                .imagePathList(imageList)
                .boardHelpComment(commentList)
                .build();
    }

    @Transactional
    @Override
    public void adoptedBoardHelp(BoardHelpAdopt requestDto) {
        memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_MEMBER_PK));

        BoardHelpComment boardHelpComment = commentRepository.findById(requestDto.getCommentId())
                .orElseThrow(()-> new BaseException(StatusCode.NOT_EXIST_COMMENT_PK));

        if (boardHelpComment.getBoardHelp().getIsAdopted()) {
            throw new BaseException(StatusCode.ALREADY_ADOPT_BOARD);
        }

        BoardHelp boardHelp = boardHelpRepository.findById(boardHelpComment.getBoardHelp().getId())
                        .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_BOARD_PK));

        Long COMPLETE_HELP_CODE_ID = 403L;
        CodeSmall codeSmall = codeSmallRepository.findById(COMPLETE_HELP_CODE_ID)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CODE));

        boardHelp.setIsAdopted(true);
        boardHelp.setCodeSmall(codeSmall);

        boardHelpRepository.save(boardHelp);
    }


}
