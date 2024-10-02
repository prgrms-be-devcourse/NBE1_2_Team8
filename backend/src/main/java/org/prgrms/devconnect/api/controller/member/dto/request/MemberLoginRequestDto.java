package org.prgrms.devconnect.api.controller.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemberLoginRequestDto(
    @Email
    String email,

    @NotBlank
    String password
) {

}
