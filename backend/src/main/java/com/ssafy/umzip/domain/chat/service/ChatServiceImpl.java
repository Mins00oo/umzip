package com.ssafy.umzip.domain.chat.service;

import com.ssafy.umzip.domain.chat.dto.ChatDetailResponseDto;
import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.umzip.domain.chat.dto.ChatMessageResponseDto;
import com.ssafy.umzip.domain.chat.dto.ChatTradeMessageRequestDto;
import com.ssafy.umzip.domain.chat.entity.*;
import com.ssafy.umzip.domain.chat.repository.ChatMessageRepository;
import com.ssafy.umzip.domain.chat.repository.ChatParticipantRepository;
import com.ssafy.umzip.domain.chat.repository.ChatRoomRepository;
import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.company.repository.CompanyRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.domain.trade.entity.BoardTradeImage;
import com.ssafy.umzip.domain.trade.repository.BoardTradeImageRepository;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final BoardTradeImageRepository boardTradeImageRepository;
    private final CompanyRepository companyRepository;


    @Transactional
    @Override
    public ChatMessageResponseDto saveMessage(ChatMessageRequestDto message, Long chatRoomId, Long requestId, String role) {
        Long id = resolveMemberIdByRole(requestId, role);

        Member member = findMemberById(id);

        ChatMessage chatMessage = ChatMessageRequestDto.toEntity(message, member, chatRoomId);
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        List<ChatParticipant> chatParticipantList = chatParticipantRepository.findByChatRoomId(chatRoomId);

        boolean isSenderCompany = companyRepository.existsByMember(member);
        Company senderCompany;
        if (isSenderCompany) {
            senderCompany = companyRepository.findByMemberIdAndRole(member.getId(), Role.valueOf(role))
                    .orElseThrow(() -> new BaseException(StatusCode.COMPANY_ROLE_NOT_MATCH));
        } else senderCompany = null;

        for (ChatParticipant cp : chatParticipantList) {
            if (cp.getMember().getId().equals(id)) {
                cp.updateLastReadMessage(savedMessage.getId());
            }
            if (!cp.getStatus().equals(ChatRoomStatus.TALK) && !cp.getStatus().equals(ChatRoomStatus.LEAVE)) {
                cp.talkWithChatRoom();
            }
        }

        if (senderCompany != null) {
            return ChatMessageRequestDto.toResponseDto(message.getContent(), id, id, senderCompany.getName(), senderCompany.getImageUrl(),
                    LocalDateTime.now());
        }

        return ChatMessageRequestDto.toResponseDto(message.getContent(), id, id, member.getName(), member.getImageUrl(), LocalDateTime.now());
    }


    @Transactional
    @Override
    public ChatDetailResponseDto retrieveChatRoomMessages(Long chatRoomId, Long requestId, String role) {
        Long id = resolveMemberIdByRole(requestId, role);
        Member requester = findMemberById(id);
        ChatParticipant cp = chatParticipantRepository.findByChatRoomAndMemberId(chatRoomId, id);
        Member anotherMember = cp.getMember();

        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatRoomId(chatRoomId);

        boolean isRequesterCompany = companyRepository.existsByMember(requester);
        boolean isAnotherMemberCompany = companyRepository.existsByMember(anotherMember);

        Company requesterCompany;
        Company anotherMemberCompany;

        if (isRequesterCompany) {
            log.info("requester is company");
            requesterCompany = companyRepository.findByMemberIdAndRole(requester.getId(), Role.valueOf(role))
                    .orElseThrow(() -> new BaseException(StatusCode.COMPANY_ROLE_NOT_MATCH));
        } else {
            requesterCompany = null;
        }

        if (isAnotherMemberCompany) {
            log.info("another is company");
            anotherMemberCompany = companyRepository.findByMemberIdAndRole(anotherMember.getId(), Role.valueOf(cp.getRole()))
                    .orElseThrow(() -> new BaseException(StatusCode.COMPANY_ROLE_NOT_MATCH));
        } else {
            anotherMemberCompany = null;
        }

        List<ChatMessageResponseDto> responseDto = chatMessages.stream()
                .map(message -> {
                    boolean isRequesterSender = message.getSenderId().equals(id.toString());
                    String name;
                    String imageUrl;

                    if (isRequesterSender) {
                        if (isRequesterCompany && requesterCompany != null) {
                            name = requesterCompany.getName();
                            imageUrl = requesterCompany.getImageUrl();
                        } else {
                            name = requester.getName();
                            imageUrl = requester.getImageUrl();
                        }
                    } else {
                        if (isAnotherMemberCompany && anotherMemberCompany != null) {
                            name = anotherMemberCompany.getName();
                            imageUrl = anotherMemberCompany.getImageUrl();
                        } else {
                            name = anotherMember.getName();
                            imageUrl = anotherMember.getImageUrl();
                        }
                    }

                    return new ChatMessageResponseDto(
                            message.getContent(),
                            requestId,
                            name,
                            message.getSenderId(),
                            imageUrl,
                            message.getUpdateDt()
                    );
                })
                .sorted(Comparator.comparing(ChatMessageResponseDto::getSendTime))
                .toList();

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new BaseException(StatusCode.CANNOT_FIND_CHATROOM_WITH_PK));

        ChatTradeMessageRequestDto chatTradeMessageRequestDto;
        ChatDetailResponseDto response;

        if (chatRoom.getRoomType().equals(ChatRoomType.TRADE)) {
            BoardTradeImage boardTrade = boardTradeImageRepository.findAllById(chatRoom.getTradeId()).get(0);
            chatTradeMessageRequestDto = ChatTradeMessageRequestDto.builder()
                    .tradeItemTitle(boardTrade.getBoardTrade().getTitle())
                    .tradeId(boardTrade.getBoardTrade().getId())
                    .memberId(boardTrade.getBoardTrade().getMember().getId())
                    .tradeItemPrice(boardTrade.getBoardTrade().getPrice())
                    .tradeItemImage(boardTrade.getPath())
                    .build();

            response = ChatDetailResponseDto.builder()
                    .chatMessages(responseDto)
                    .build();
            response.setTradeItem(chatTradeMessageRequestDto);

            return response;

        }

        return ChatDetailResponseDto.builder()
                .chatMessages(responseDto)
                .build();
    }

    private Long resolveMemberIdByRole(Long id, String role) {
        if (role.equals("DELIVER") || role.equals("CLEAN")) {
            return companyRepository.findById(id)
                    .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_COMMENT_PK))
                    .getMember().getId();
        }
        return id;
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));
    }
}