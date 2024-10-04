package org.prgrms.devconnect.api.controller.comment.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

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
}
