package org.prgrms.devconnect.api.controller.jobpost;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "채용 공고 API", description = "채용 관련 공고를 관리하는 API")
public class JobPostController {

  private final JobPostQueryService jobPostQueryService;
  private final JobPostCommandService jobPostCommandService;

  @Operation(summary = "채용 공고 삭제", description = "특정 채용 공고를 삭제합니다.")
  @ApiResponse(responseCode = "200", description = "채용 공고가 성공적으로 삭제되었습니다.")
  @DeleteMapping("/{jobPostId}")
  public ResponseEntity<Void> deleteJobPost(@PathVariable Long jobPostId) {

    jobPostCommandService.deleteJobPost(jobPostId);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "모든 채용 공고 조회", description = "모든 채용 공고를 페이징 처리하여 조회합니다.")
  @ApiResponse(responseCode = "200", description = "채용 공고 목록을 성공적으로 반환합니다.")
  @GetMapping("/")
  public ResponseEntity<Page<JobPostInfoResponseDto>> getAllJobPosts(Pageable pageable) {

    Page<JobPostInfoResponseDto> jobPosts = jobPostQueryService.getAllJobPosts(pageable);
    return ResponseEntity.ok().body(jobPosts);
  }

  @Operation(summary = "특정 채용 공고 조회", description = "특정 ID에 해당하는 채용 공고의 상세 정보를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "채용 공고 상세 정보를 성공적으로 반환합니다.")
  @GetMapping("/{jobPostId}")
  public ResponseEntity<JobPostInfoResponseDto> getJobPost(@PathVariable Long jobPostId) {

    JobPostInfoResponseDto jobPost = jobPostQueryService.getJobPost(jobPostId);
    return ResponseEntity.ok().body(jobPost);
  }

  @Operation(summary = "채용 공고 좋아요", description = "특정 채용 공고에 좋아요를 추가합니다.")
  @ApiResponse(responseCode = "200", description = "채용 공고에 좋아요가 성공적으로 추가되었습니다.")
  @PatchMapping("/{jobPostId}/likes")
  public ResponseEntity<Void> JobPostLikes(@PathVariable Long jobPostId) {

    jobPostQueryService.jobPostLikes(jobPostId);
    return ResponseEntity.ok().build();
  }
}
