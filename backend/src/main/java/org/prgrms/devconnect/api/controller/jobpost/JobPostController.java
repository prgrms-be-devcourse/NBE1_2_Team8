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
@RequestMapping("/api/v1/jobPosts")
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

    jobPostQueryService.jobPostLikes(jobPostId);
    return ResponseEntity.ok().build();
  }

  // 기술 스택 name으로 공고 조회
  @GetMapping("/techstack-name/{name}")
  public ResponseEntity<Page<JobPostInfoResponseDto>> getJobPostsByTechStackName(@PathVariable String name, Pageable pageable) {

    Page<JobPostInfoResponseDto> jobPosts = jobPostQueryService.getJobPostsByTechStackName(name, pageable);
    return ResponseEntity.ok().body(jobPosts);
  }

  // 기술 스택 job_code로 공고 조회
  @GetMapping("/techstack-code/{code}")
  public ResponseEntity<Page<JobPostInfoResponseDto>> getJobPostsByTechStackCode(@PathVariable String code, Pageable pageable) {

    Page<JobPostInfoResponseDto> jobPosts = jobPostQueryService.getJobPostsByTechStackJobCode(code, pageable);
    return ResponseEntity.ok().body(jobPosts);
  }

  // JobPostName 을 (제목별 공고 조회)
  @GetMapping("/search")
  public ResponseEntity<Page<JobPostInfoResponseDto>> getJobPostsByJobPostNameContaining(@RequestParam String keyword, Pageable pageable) {

    Page<JobPostInfoResponseDto> jobPosts = jobPostQueryService.getJobPostsByJobPostNameContaining(keyword, pageable);
    return ResponseEntity.ok().body(jobPosts);

}
