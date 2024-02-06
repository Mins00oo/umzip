package com.ssafy.umzip.domain.alarm.dto;

import com.ssafy.umzip.domain.alarm.entity.Alarm;
import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AlarmDto {
    private Member member;
    private List<Company> companyList = new ArrayList<>();
    private Company company;
    private String memberContent;
    private String companyContent;
    private Boolean read;
    private AlarmType alarmType;
    private Long codeSmallId;

    @Builder
    public AlarmDto(Member member, Company company, Boolean read, AlarmType alarmType, Long codeSmallId) {
        this.member = member;
        this.company = company;
        this.read = read;
        this.alarmType = alarmType;
        this.codeSmallId = codeSmallId;
    }

    public Alarm toMemberDeliveryAndCleanAlarmEntity() {
        StringBuilder sb = new StringBuilder();
        Long status = this.codeSmallId % 100;
        checkAlarmType(sb);
        generateMemberContentByStatus(status, sb);
        return Alarm.builder()
                .member(this.member)
                .isRead(false)
                .content(sb.toString())
                .build();
    }

    public Alarm toCompanyDeliveryAndCleanAlarmEntity(Company company) {
        StringBuilder sb = new StringBuilder();
        Long status = this.codeSmallId % 100;
        checkAlarmType(sb);
        generateCompanyContentByStatus(status, sb);
        return Alarm.builder()
                .member(company.getMember())
                .isRead(false)
                .content(sb.toString())
                .build();
    }

    private void generateCompanyContentByStatus(Long status, StringBuilder sb) {
        switch (status.intValue()) {
            case 1 -> { //신청중
                sb.append(member.getName()+" 님의 예약 신청이 도착했어요 !");
            }
            case 3 -> { //예약 완료
                sb.append(member.getName()+" 님이 예약을 확정했어요 !");
            }
            case 5 -> {//취소
                sb.append(member.getName()+" 님이 예약을 취소했어요.");
            }
        }
    }

    private void generateMemberContentByStatus(Long status, StringBuilder sb) {
        switch (status.intValue()) {
            case 2 -> { // 검토중
                sb.append("업체 " + company.getName() + " 이 견적제안을 보냈어요!");
            }
            case 4 -> { // 거절
                sb.append("업체 " + company.getName() + " 이 회원님의 예약건을 거절했어요.");
            }
        }
    }

    private void checkAlarmType(StringBuilder sb) {
        switch (this.alarmType) {
            case DELIVER -> {
                sb.append("[ 용달 ] ");
            }
            case CLEAN -> {
                sb.append("[ 청소 ] ");
            }
            case HELP -> {
                sb.append("[ 중고 ] ");
            }
            case TRADE -> {
                sb.append("[ 도움 ] ");
            }
        }
    }
    public void addCompany(Company company){
        companyList.add(company);
    }


}
