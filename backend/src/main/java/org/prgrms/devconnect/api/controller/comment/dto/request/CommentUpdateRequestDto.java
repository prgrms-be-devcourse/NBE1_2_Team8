package org.prgrms.devconnect.api.controller.comment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CommentUpdateRequestDto(
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
}
