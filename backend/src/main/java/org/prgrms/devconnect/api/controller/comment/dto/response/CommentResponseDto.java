package org.prgrms.devconnect.api.controller.comment.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.Comment;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record CommentResponseDto(
        Long commentId,
        Long memberId,
        String author,
        String content,
        LocalDateTime updatedAt,
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
