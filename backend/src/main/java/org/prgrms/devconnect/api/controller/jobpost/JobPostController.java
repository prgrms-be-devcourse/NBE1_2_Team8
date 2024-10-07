package org.prgrms.devconnect.api.controller.jobpost;


import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.service.jobpost.JobPostCommandService;
import org.prgrms.devconnect.api.service.jobpost.JobPostQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobPosts")
public class JobPostController {

  private final JobPostQueryService jobPostQueryService;
  private final JobPostCommandService jobPostCommandService;

  @DeleteMapping("/{jobPostId}")
  public ResponseEntity<Void> deleteJobPost(@PathVariable Long jobPostId) {

    jobPostCommandService.deleteJobPost(jobPostId);
    return ResponseEntity.ok().build();
  }
}
