package org.prgrms.devconnect.api.controller.board.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.BoardTechStackMapping;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record BoardCreateRequestDto(
        @Schema(description = "회원 ID", example = "1", required = true)
        @NotNull(message = "회원 ID는 필수입니다.")
        Long memberId,

        @Schema(description = "직업 공고 ID", example = "1")
        Long jobPostId,

        @Schema(description = "게시물 제목", example = "Spring Boot 개발자 모집", required = true)
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @Schema(description = "게시물 내용", example = "우리는 Spring Boot 벡엔드 개발자를 찾고 있습니다.", required = true)
        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @Schema(description = "카테고리", example = "STUDY", required = true)
        @NotNull(message = "카테고리는 필수입니다.")
        BoardCategory category,

        @Schema(description = "모집 인원", example = "3", required = true)
        @NotNull(message = "모집 인원은 필수입니다.")
        int recruitNum,

        @Schema(description = "진행 방식", example = "ONLINE", required = true)
        @NotNull(message = "진행 방식은 필수입니다.")
        ProgressWay progressWay,

        @Schema(description = "진행 기간", example = "3개월", required = true)
        @NotBlank(message = "진행 기간은 필수입니다.")
        String progressPeriod,

        @Schema(description = "종료 날짜", example = "2024-12-31T23:59:59", required = true)
        @NotNull(message = "종료 날짜는 필수입니다.")
        LocalDateTime endDate,

        @ArraySchema(schema = @Schema(description = "기술 스택 목록", required = true))
        @NotEmpty(message = "적어도 하나 이상의 기술 스택을 선택해야 합니다.")
        List<BoardTechStackRequestDto> techStackRequests
) {
  public Board toEntity(Member member, JobPost jobPost, List<BoardTechStackMapping> boardTechStacks) {
    return Board.builder()
            .member(member)
            .jobPost(jobPost)
            .title(title)
            .content(content)
            .category(category)
            .recruitNum(recruitNum)
            .progressWay(progressWay)
            .progressPeriod(progressPeriod)
            .endDate(endDate)
            .boardTechStacks(boardTechStacks)
            .build();
  }
}
