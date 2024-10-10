package org.prgrms.devconnect.api.controller.member.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.prgrms.devconnect.api.controller.techstack.dto.response.TechStackResponseDto;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.member.entity.constant.Interest;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record MemberResponseDto(
        @Schema(description = "회원 ID", example = "1")
        Long memberId,

        @Schema(description = "회원 이메일", example = "user@example.com")
        String email,

        @Schema(description = "회원 닉네임", example = "DevUser")
        String nickname,

        @Schema(description = "회원 직업", example = "Software Engineer")
        String job,

        @Schema(description = "회원 소속", example = "Company Name")
        String affiliation,

        @Schema(description = "회원 경력", example = "5")
        int career,

        @Schema(description = "회원 자기소개", example = "안녕하세요! 저는 소프트웨어 엔지니어입니다.")
        String selfIntroduction,

        @Schema(description = "GitHub 링크", example = "https://github.com/user")
        String githubLink,

        @Schema(description = "블로그 링크", example = "https://userblog.com")
        String blogLink,

        @Schema(description = "회원 관심 분야", example = "TEAM_PROJECT")
        Interest interest,

        @Schema(description = "회원 기술 스택 목록")
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
