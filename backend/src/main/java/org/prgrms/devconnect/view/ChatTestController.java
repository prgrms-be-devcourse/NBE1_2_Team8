package org.prgrms.devconnect.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatTestController {

  //TODO 삭제

  @GetMapping("/chat-test")
  public String testChat(){
    return "SimpleChatTest";
  }
}
