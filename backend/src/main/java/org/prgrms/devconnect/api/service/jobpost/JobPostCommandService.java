package org.prgrms.devconnect.api.service.jobpost;


import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPostTechStackMapping;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.Status;
import org.prgrms.devconnect.domain.define.jobpost.repository.JobPostRepository;
import org.prgrms.devconnect.domain.define.jobpost.repository.JobPostTechStackRepository;
import org.prgrms.devconnect.external.saramin.JobPostApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JobPostCommandService {

  private final JobPostRepository jobPostRepository;
  private final JobPostTechStackRepository jobPostTechStackRepository;
  private final JobPostQueryService jobPostQueryService;
  private final JobPostApi jobPostAPI;


  private static final int BATCH_SIZE = 100; // 한 번에 가져올 데이터 수

  // 채용공고 저장 로직
  public void saveJobPosts() {
    int start = 0;

    int total = jobPostAPI.getTotal(start);

    while (start < total) {
      List<JobPost> jobPosts = jobPostAPI.fetchJobPosts(start);

      if (jobPosts.isEmpty()) {
        break; // 더 이상 가져올 공고가 없을 경우 종료
      }

      // JobPost와 JobPostTechStackMapping을 저장
      saveJobPostsAndMappings(jobPosts);

      // start 값을 증가시켜 다음 배치로 이동
      start += BATCH_SIZE;
    }
  }

  private void saveJobPostsAndMappings(List<JobPost> jobPosts) {
    // JobPost 저장
    jobPostRepository.saveAll(jobPosts);
    jobPostRepository.flush();

    // JobPost와 연결된 JobPostTechStackMapping 저장
    List<JobPostTechStackMapping> mappings = new ArrayList<>();
    for (JobPost jobPost : jobPosts) {
      mappings.addAll(jobPost.getJobPostTechStackMappings());
    }

    jobPostTechStackRepository.saveAll(mappings);
  }

  // 공고 삭제
  public void deleteJobPost(Long jobPostId) {

    // 공고 조회
    JobPost jobPost = jobPostQueryService.getJobPostByIdOrThrow(jobPostId);

    jobPost.updateStatus(Status.DELETED);
  }

  // 공고 좋아요 증가
  public void likeJobPost(Long jobPostId) {

    // 공고 조회
    JobPost jobPost = jobPostQueryService.getJobPostByIdOrThrow(jobPostId);

    jobPost.increaseLikes();
  }

  // 공고 좋아요 감소
  public void unlikeJobPost(Long jobPostId) {

    // 공고 조회
    JobPost jobPost = jobPostQueryService.getJobPostByIdOrThrow(jobPostId);

    jobPost.decreaseLikes();
  }
}