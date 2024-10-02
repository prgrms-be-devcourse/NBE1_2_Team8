package org.prgrms.devconnect.api.service.jobpost;


import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.domain.define.jobpost.repository.JobPostRepository;
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
}
