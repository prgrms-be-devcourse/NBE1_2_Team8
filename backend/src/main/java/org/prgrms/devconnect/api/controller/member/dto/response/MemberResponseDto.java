package org.prgrms.devconnect.api.controller.member.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import org.prgrms.devconnect.api.controller.techstack.dto.response.TechStackResponseDto;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.member.entity.constant.Interest;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record MemberResponseDto(
    Long memberId,
    String email,
    String nickname,
    String job,
    String affiliation,
    int career,
    String selfIntroduction,
    String githubLink,
    String blogLink,
    Interest interest,
    List<TechStackResponseDto> techStacks

) {
  public static MemberResponseDto from(Member member) {
    return MemberResponseDto.builder()
        .memberId(member.getMemberId())
        .email(member.getEmail())
        .nickname(member.getNickname())
        .job(member.getJob())
        .affiliation(member.getAffiliation())
        .career(member.getCareer())
        .selfIntroduction(member.getSelfIntroduction())
        .githubLink(member.getGithubLink())
        .blogLink(member.getBlogLink())
        .interest(member.getInterest())
        .techStacks(member.getMemberTechStacks()
            .stream()
            .map(MemberTechStackMapping::getTechStack)
            .map(TechStackResponseDto::from)
            .collect(Collectors.toList()))
        .build();
  }
}
