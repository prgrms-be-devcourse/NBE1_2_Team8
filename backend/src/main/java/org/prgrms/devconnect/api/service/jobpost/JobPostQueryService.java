package org.prgrms.devconnect.api.service.jobpost;


import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.jobpost.dto.response.JobPostInfoResponseDto;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.jobpost.JobPostException;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.jobpost.repository.JobPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobPostQueryService {

  private final JobPostRepository jobPostRepository;


  public boolean isJobPostByPostId(Long postId) {
    return jobPostRepository.existsByPostId(postId);
  }


  public JobPost getJobPostByIdOrThrow(Long jobPostId) {
    return jobPostRepository.findById(jobPostId)
            .orElseThrow(() -> new JobPostException(ExceptionCode.NOT_FOUND_JOB_POST));
  }


  // 공고 전체 조회
  public Page<JobPostInfoResponseDto> getAllJobPosts(Pageable pageable) {
    return jobPostRepository.findAllRecruiting(pageable);
  }

  // 공고 개별 조회
  public JobPostInfoResponseDto getJobPost(Long jobPostId) {

    // 공고 조회
    JobPost jobPost = getJobPostByIdOrThrow(jobPostId);
    return JobPostInfoResponseDto.from(jobPost);
  }
}

