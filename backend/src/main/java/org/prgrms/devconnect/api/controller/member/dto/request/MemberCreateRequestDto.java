package org.prgrms.devconnect.api.controller.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.member.entity.constant.Interest;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MemberCreateRequestDto(

    @Email(message = "유효한 이메일 형식을 입력하세요.")
    String email,

    @NotBlank(message = "비밀번호는 필수입니다.")
    String password,

    @NotBlank(message = "닉네임은 필수입니다.")
    String nickname,

    @NotBlank(message = "직업은 필수입니다.")
    String job,

    @NotBlank(message = "소속은 필수입니다.")
    String affiliation,

    @PositiveOrZero(message = "경력은 0 이상이어야 합니다.")
    int career,

    @NotBlank(message = "자기소개는 필수입니다.")
    String selfIntroduction,

    @NotBlank(message = "유효한 GitHub 링크를 입력하세요.")
    String githubLink,

    @NotBlank(message = "유효한 블로그 링크를 입력하세요.")
    String blogLink,

    @NotNull(message = "관심 분야는 필수입니다.")
    Interest interest,

    @NotEmpty(message = "기술 스택 목록은 비어있을 수 없습니다.")
    @Size(min = 1, message = "기술 스택 ID는 최소 하나 이상이어야 합니다.")
    List<@Positive(message = "기술 스택 ID는 0보다 커야 합니다.") Long> techStackIds
) {

  public Member toEntity(List<MemberTechStackMapping> memberTechStacks) {
    return Member.builder()
        .email(email)
        .password(password)
        .nickname(nickname)
        .job(job)
        .affiliation(affiliation)
        .career(career)
        .selfIntroduction(selfIntroduction)
        .githubLink(githubLink)
        .blogLink(blogLink)
        .interest(interest)
        .memberTechStacks(memberTechStacks)
        .build();
  }

}
