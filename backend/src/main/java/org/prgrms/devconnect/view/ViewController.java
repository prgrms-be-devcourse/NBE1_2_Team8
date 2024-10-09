package org.prgrms.devconnect.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public String getBoardDetailPage(@PathVariable("boardId") Long boardId) {
    // 페이지 이동만 처리
    return "board-detail";  // board-detail.html 페이지 반환
  }

  @GetMapping("/job-posts/{jobPostId}")
  public String getJobPostDetailPage(@PathVariable("jobPostId") Long jobPostId) {
    // 페이지 이동만 처리
    return "job-detail"; // job-detail.html 반환
  }
}
