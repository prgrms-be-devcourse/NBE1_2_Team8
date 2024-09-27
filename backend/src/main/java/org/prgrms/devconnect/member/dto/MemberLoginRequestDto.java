package org.prgrms.devconnect.member.dto;

public record MemberLoginRequestDto(
    String email,
    String password
) {

}
