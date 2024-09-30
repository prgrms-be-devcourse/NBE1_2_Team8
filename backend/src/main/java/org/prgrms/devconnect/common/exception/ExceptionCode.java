package org.prgrms.devconnect.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

  //Alarm Error
  NOT_FOUND_ALARM(404, "존재하지 않는 알람입니다."),

  //Board Error
  NOT_FOUND_BOARD(404, "존재하지 않는 게시물입니다."),

  //BoardTechStack Error
  NOT_FOUND_BOARD_TECH_STACK(404, "존재하지 않는 테크스택입니다."),

  //Comment Error
  NOT_FOUND_COMMENT(404, "존재하지 않는 댓글입니다."),

  //BugReport Error
  NOT_FOUND_BUG_REPORT(404, "존재하지 않는 버그리포트입니다."),

  //JobPost Error
  NOT_FOUND_JOB_POST(404, "존재하지 않는 채용공고입니다."),

  //MEMBER ERROR
  NOT_FOUND_MEMBER(404, "존재하지 않는 멤버입니다."),
  DUPLICATED_MEMBER_EMAIL(400, "이미 존재하는 이메일입니다."),
  INVALID_PASSWORD(400, "일치하지 않는 패스워드입니다."),

  //CHATTINGROOM ERROR
  NOT_FOUND_CHATROOM(404, "존재하지 않는 채팅방 입니다"),
  CHATROOM_ALREADY_INACTIVE(400, "채팅방은 이미 INACTIVE 상태입니다"),

  //TECH_STACK ERROR
  NOT_FOUND_TECH_STACK(404, "존재하지 않는 기술 스택입니다.");

  private final int code;
  private final String message;

}
