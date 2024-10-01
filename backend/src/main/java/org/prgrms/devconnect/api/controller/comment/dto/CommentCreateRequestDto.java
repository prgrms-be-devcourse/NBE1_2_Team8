package org.prgrms.devconnect.api.controller.comment.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CommentCreateRequestDto(
        @NotNull(message = "회원 ID는 필수입니다.")
        Long memberId,

        @NotNull(message = "게시판 ID는 필수입니다.")
        Long boardId,

        Long parentId,

        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
  public Comment toEntity(Member member, Board board, Comment parentComment) {
    return Comment.builder()
            .member(member)
            .board(board)
            .parent(parentComment)
            .content(content)
            .build();
  }
}
