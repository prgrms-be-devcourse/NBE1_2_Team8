package org.prgrms.devconnect.api.controller.board.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BoardInfoResponseDto(
        @Schema(description = "게시물 ID", example = "1")
        Long boardId,

        @Schema(description = "게시물 제목", example = "새로운 게시물 제목")
        String title,

        @Schema(description = "게시물 카테고리", example = "STUDY")
        BoardCategory category,

        @Schema(description = "진행 방식", example = "ONLINE")
        ProgressWay progressWay,

        @Schema(description = "게시물 종료 날짜", example = "2024-12-31T23:59:59")
        LocalDateTime endDate,

        @Schema(description = "조회수", example = "150")
        int views,

        @Schema(description = "좋아요 수", example = "20")
        int likes,

        @Schema(description = "작성자 닉네임", example = "john_doe")
        String author,

        @Schema(description = "작성자 ID", example = "42")
        Long authorId
) {

  public static BoardInfoResponseDto from(Board board) {
    return BoardInfoResponseDto.builder()
        .boardId(board.getBoardId())
        .title(board.getTitle())
        .category(board.getCategory())
        .progressWay(board.getProgressWay())
        .endDate(board.getEndDate())
        .views(board.getViews())
        .likes(board.getLikes())
        .author(board.getMember().getNickname())
        .authorId(board.getMember().getMemberId())
        .build();
  }
}
