package org.prgrms.devconnect.api.controller.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequestDto(
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
}
