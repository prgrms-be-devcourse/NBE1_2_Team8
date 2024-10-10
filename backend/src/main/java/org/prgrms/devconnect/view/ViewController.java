package org.prgrms.devconnect.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

  @GetMapping
  public String home() {
    return "index";
  }

  @GetMapping("login")
  public String login() {
    return "login";
  }

  @GetMapping("/boards/{boardId}")
  public String getBoardDetail() {
    return "board-detail";
  }

  @GetMapping("/job-posts/{jobPostId}")
  public String getJobPostDetail() {
    return "job-detail";
  }
}
