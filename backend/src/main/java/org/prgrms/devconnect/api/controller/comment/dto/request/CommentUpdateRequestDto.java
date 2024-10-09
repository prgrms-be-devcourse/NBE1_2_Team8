package org.prgrms.devconnect.api.controller.comment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CommentUpdateRequestDto(
        @Schema(description = "댓글 내용", example = "이 댓글을 수정합니다.")
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
}
