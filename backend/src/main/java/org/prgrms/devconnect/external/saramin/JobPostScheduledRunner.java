package org.prgrms.devconnect.external.saramin;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.service.jobpost.JobPostCommandService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostScheduledRunner {

  private final JobPostCommandService jobPostCommandService;


  // 매일 12시 정각에 실행
  @Scheduled(cron = "0 0 12 * * ?")
  public void run() throws Exception {
    jobPostCommandService.saveJobPosts();
  }

}

