package org.prgrms.devconnect.api.controller.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.member.entity.constant.Interest;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MemberUpdateRequestDto(
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
    Interest interest
) {

}
