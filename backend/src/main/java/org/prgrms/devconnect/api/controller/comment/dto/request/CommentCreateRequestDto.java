package org.prgrms.devconnect.api.controller.comment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CommentCreateRequestDto(

    @Schema(description = "게시판 ID", example = "1")
    @NotNull(message = "게시판 ID는 필수입니다.")
    Long boardId,

    @Schema(description = "부모 댓글 ID (대댓글인 경우)", example = "2")
    Long parentId,

    @Schema(description = "댓글 내용", example = "이 게시물 정말 유익하네요!")
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
