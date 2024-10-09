package org.prgrms.devconnect.domain.define.jobpost.repository.custom;


import org.prgrms.devconnect.api.controller.jobpost.dto.response.JobPostInfoResponseDto;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobPostRepositoryCustom {

  // 채용중인 공고 전체조회
  Page<JobPostInfoResponseDto> findAllRecruiting(Pageable pageable);

  // StackName 별 공고조회
  Page<JobPostInfoResponseDto> findAllByTechStackName(String techStackName, Pageable pageable);

  // JobCode 별 공고조회
  Page<JobPostInfoResponseDto> findAllByTechStackJobCode(String jobCode, Pageable pageable);

  // JobPostName 을 (제목별 공고 조회)
  Page<JobPostInfoResponseDto> findAllByJobPostNameContaining(String keyword, Pageable pageable);

  // 사용자 관심스택별 조회
  Page<JobPostInfoResponseDto> findAllByMemberInterests(Member member, Pageable pageable);
}
