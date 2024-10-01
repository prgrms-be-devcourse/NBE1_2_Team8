package org.prgrms.devconnect.api.controller.board.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record BoardCreateRequestDto(
        @NotNull(message = "회원 ID는 필수입니다.")
        Long memberId,

        Long jobPostId,

        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @NotNull(message = "카테고리는 필수입니다.")
        BoardCategory category,

        @NotNull(message = "모집 인원은 필수입니다.")
        int recruitNum,

        @NotNull(message = "진행 방식은 필수입니다.")
        ProgressWay progressWay,

        @NotBlank(message = "진행 기간은 필수입니다.")
        String progressPeriod,

        @NotNull(message = "종료 날짜는 필수입니다.")
        LocalDateTime endDate,

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
