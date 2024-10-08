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

    // 조회수 증가
    jobPost.incrementViews();
    return JobPostInfoResponseDto.from(jobPost);
  }


  // TechStack name으로 공고 조회
  public Page<JobPostInfoResponseDto> getJobPostsByTechStackName(String name, Pageable pageable) {
    return jobPostRepository.findAllByTechStackName(name, pageable);
  }

  // TechStack job_code로 공고 조회
  public Page<JobPostInfoResponseDto> getJobPostsByTechStackJobCode(String code, Pageable pageable) {
    return jobPostRepository.findAllByTechStackJobCode(code, pageable);
  }

  // JobPostName 을 (제목별 공고 조회)
  public Page<JobPostInfoResponseDto> getJobPostsByJobPostNameContaining(String keyword, Pageable pageable) {
    return jobPostRepository.findAllByJobPostNameContaining(keyword, pageable);
  }

  // 공고 좋아요 증가
  public void jobPostLikes(Long jobPostId) {

    // 공고 조회
    JobPost jobPost = getJobPostByIdOrThrow(jobPostId);

    jobPost.incrementLikes();
  }
}

