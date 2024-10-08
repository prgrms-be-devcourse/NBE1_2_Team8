package org.prgrms.devconnect.api.controller.jobpost;


import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.jobpost.dto.response.JobPostInfoResponseDto;
import org.prgrms.devconnect.api.service.jobpost.JobPostCommandService;
import org.prgrms.devconnect.api.service.jobpost.JobPostQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job-posts")
public class JobPostController {

  private final JobPostQueryService jobPostQueryService;
  private final JobPostCommandService jobPostCommandService;

  @DeleteMapping("/{jobPostId}")
  public ResponseEntity<Void> deleteJobPost(@PathVariable Long jobPostId) {

    jobPostCommandService.deleteJobPost(jobPostId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/")
  public ResponseEntity<Page<JobPostInfoResponseDto>> getAllJobPosts(Pageable pageable) {

    Page<JobPostInfoResponseDto> jobPosts = jobPostQueryService.getAllJobPosts(pageable);
    return ResponseEntity.ok().body(jobPosts);
  }

  @GetMapping("/{jobPostId}")
  public ResponseEntity<JobPostInfoResponseDto> getJobPost(@PathVariable Long jobPostId) {

    JobPostInfoResponseDto jobPost = jobPostQueryService.getJobPost(jobPostId);
    return ResponseEntity.ok().body(jobPost);
  }

  @PatchMapping("/{jobPostId}/likes")
  public ResponseEntity<Void> JobPostLikes(@PathVariable Long jobPostId) {

    jobPostCommandService.jobPostLikes(jobPostId);
    return ResponseEntity.ok().build();
  }
}
