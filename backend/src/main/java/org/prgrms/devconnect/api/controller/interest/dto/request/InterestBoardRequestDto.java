package org.prgrms.devconnect.api.controller.interest.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "관심 게시글 등록 요청 정보")
public record InterestBoardRequestDto(

        @NotNull(message = "게시물 ID는 필수입니다.")
        @Schema(description = "게시물 ID", example = "1")
        Long boardId
) {


  public InterestBoard toEntity(Member member, Board board) {
    return InterestBoard.builder()
            .member(member)
            .board(board)
            .build();
  }
}
