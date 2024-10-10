package org.prgrms.devconnect.api.controller.comment.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.Comment;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record CommentResponseDto(
        @Schema(description = "댓글 ID", example = "1")
        Long commentId,

        @Schema(description = "작성자 ID", example = "2")
        Long memberId,

        @Schema(description = "작성자 닉네임", example = "JohnDoe")
        String author,

        @Schema(description = "댓글 내용", example = "이것은 댓글입니다.")
        String content,

        @Schema(description = "댓글 수정 날짜", example = "2024-12-31T23:59:59")
        LocalDateTime updatedAt,

        @Schema(description = "부모 댓글 ID (대댓글인 경우)", example = "2")
        Long parentId
) {
  public static CommentResponseDto from(Comment comment) {
    return CommentResponseDto.builder()
            .commentId(comment.getCommentId())
            .memberId(comment.getMember().getMemberId())
            .author(comment.getMember().getNickname())
            .content(comment.getContent())
            .updatedAt(comment.getUpdatedAt())
            .parentId(comment.getParent() != null ? comment.getParent().getCommentId() : null)
            .build();
  }
}
