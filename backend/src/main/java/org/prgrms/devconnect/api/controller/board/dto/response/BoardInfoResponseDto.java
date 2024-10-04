package org.prgrms.devconnect.api.controller.board.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BoardInfoResponseDto(
    Long boardId,
    String title,
    BoardCategory category,
    ProgressWay progressWay,
    LocalDateTime endDate,
    int views,
    int likes,
    String author,
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
