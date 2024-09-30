package org.prgrms.devconnect.board;

import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

public class TechStackFixture {
  public static TechStack createTechStack(){
    return TechStack.builder()
            .name("Spring")
            .code("1234")
            .build();
  }

}
