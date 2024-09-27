package org.prgrms.devconnect.jobpost.entity.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobType {

  REGULAR("정규직"),
  IRREGULAR("비정규직"),
  CONTRACT("인턴");

  private final String text;

}
