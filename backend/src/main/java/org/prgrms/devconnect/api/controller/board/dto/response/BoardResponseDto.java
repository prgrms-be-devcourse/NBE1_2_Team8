package org.prgrms.devconnect.api.controller.board.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.prgrms.devconnect.api.controller.techstack.dto.response.TechStackResponseDto;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.BoardTechStackMapping;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record BoardResponseDto(
        @Schema(description = "게시물 ID", example = "1")
        Long boardId,

        @Schema(description = "작성자 ID", example = "42")
        Long authorId,

        @Schema(description = "작성자 닉네임", example = "john_doe")
        String author,

        @Schema(description = "게시물 제목", example = "새로운 프로젝트 모집")
        String title,

        @Schema(description = "게시물 내용", example = "프로젝트 설명을 작성합니다.")
        String content,

        @Schema(description = "게시물 카테고리", example = "PROJECT")
        BoardCategory category,

        @Schema(description = "모집 인원", example = "5")
        int recruitNum,

        @Schema(description = "진행 방식", example = "ONLINE")
        ProgressWay progressWay,

        @Schema(description = "진행 기간", example = "2개월")
        String progressPeriod,

        @Schema(description = "게시물 종료 날짜", example = "2024-12-31T23:59:59")
        LocalDateTime endDate,

        @Schema(description = "좋아요 수", example = "150")
        int likes,

        @Schema(description = "조회수", example = "500")
        int views,

        @Schema(description = "생성일자", example = "2024-01-01T12:00:00")
        LocalDateTime createdDate,

        @Schema(description = "수정일자", example = "2024-01-02T14:00:00")
        LocalDateTime updatedDate,

        @Schema(description = "게시물 상태", example = "RECRUITING")
        BoardStatus status,

        @Schema(description = "기술 스택 리스트")
        List<TechStackResponseDto> techStacks
) {
  public static BoardResponseDto from(Board board) {
    List<TechStackResponseDto> techStackDtos = board.getBoardTechStacks().stream()
            .map(BoardTechStackMapping::getTechStack)
            .map(TechStackResponseDto::from)
            .collect(Collectors.toList());

    return BoardResponseDto.builder()
            .boardId(board.getBoardId())
            .authorId(board.getMember().getMemberId())
            .author(board.getMember().getNickname())
            .title(board.getTitle())
            .content(board.getContent())
            .category(board.getCategory())
            .recruitNum(board.getRecruitNum())
            .progressWay(board.getProgressWay())
            .progressPeriod(board.getProgressPeriod())
            .endDate(board.getEndDate())
            .likes(board.getLikes())
            .views(board.getViews())
            .createdDate(board.getCreatedAt())
            .updatedDate(board.getUpdatedAt())
            .status(board.getStatus())
            .techStacks(techStackDtos)
            .build();
  }
}
