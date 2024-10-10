package org.prgrms.devconnect.api.controller.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.member.entity.constant.Interest;

import java.util.ArrayList;
import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "회원 정보 수정 요청 DTO")
public record MemberUpdateRequestDto(

        @NotBlank(message = "닉네임은 필수입니다.")
        @Schema(description = "회원의 닉네임", example = "김철수")
        String nickname,

        @NotBlank(message = "직업은 필수입니다.")
        @Schema(description = "회원의 직업", example = "백엔드 엔지니어")
        String job,

        @NotBlank(message = "소속은 필수입니다.")
        @Schema(description = "회원의 소속", example = "Grep")
        String affiliation,

        @PositiveOrZero(message = "경력은 0 이상이어야 합니다.")
        @Schema(description = "회원의 경력(년)", example = "5")
        int career,

        @NotBlank(message = "자기소개는 필수입니다.")
        @Schema(description = "회원의 자기소개", example = "자기 소개 예시 ....")
        String selfIntroduction,

        @NotBlank(message = "유효한 GitHub 링크를 입력하세요.")
        @Schema(description = "GitHub 링크", example = "https://github.com/")
        String githubLink,

        @NotBlank(message = "유효한 블로그 링크를 입력하세요.")
        @Schema(description = "블로그 링크", example = "https://blog.johndoe.com")
        String blogLink,

        @NotNull(message = "관심 분야는 필수입니다.")
        @Schema(description = "관심 분야", example = "TEAM_PROJECT")
        Interest interest,

        @Schema(description = "추가할 기술 스택 ID 목록", example = "[1, 2, 3]")
        List<Long> addTechStacks,

        @Schema(description = "삭제할 기술 스택 ID 목록", example = "[4, 5]")
        List<Long> deleteTechStacks
) {}
