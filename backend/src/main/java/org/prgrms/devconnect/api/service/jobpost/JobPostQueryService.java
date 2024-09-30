package org.prgrms.devconnect.api.service.jobpost;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.common.exception.jobpost.JobPostException;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.jobpost.repository.JobPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobPostQueryService {

  private final JobPostRepository jobPostRepository;

  public JobPost getJobPostByIdOrThrow(Long jobPostId) {
    return jobPostRepository.findById(jobPostId)
            .orElseThrow(() -> new JobPostException(ExceptionCode.NOT_FOUND_JOB_POST));
  }
}

