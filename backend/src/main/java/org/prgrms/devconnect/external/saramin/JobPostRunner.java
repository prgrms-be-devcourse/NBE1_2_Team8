package org.prgrms.devconnect.external.saramin;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.service.jobpost.JobPostCommandService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostRunner implements ApplicationRunner {

  private final JobPostCommandService jobPostCommandService;

  @Override // 애플리케이션 실행시
  public void run(ApplicationArguments args) throws Exception {
    // jobPostCommandService.saveJobPosts();
  }
}
