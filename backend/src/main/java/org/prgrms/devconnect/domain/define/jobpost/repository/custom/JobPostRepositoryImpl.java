package org.prgrms.devconnect.domain.define.jobpost.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.jobpost.dto.response.JobPostInfoResponseDto;
import org.prgrms.devconnect.api.controller.techstack.dto.response.TechStackResponseDto;
import org.prgrms.devconnect.domain.define.jobpost.entity.QJobPost;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class JobPostRepositoryImpl implements JobPostRepositoryCustom {

  private final JPAQueryFactory queryFactory;


  @Override
  public Page<JobPostInfoResponseDto> findAllRecruiting(Pageable pageable) {
    return PageableExecutionUtils.getPage(
            queryFactory
                    .selectFrom(QJobPost.jobPost)
                    .where(QJobPost.jobPost.status.eq(Status.RECRUITING))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch()
                    .stream()
                    .map(post -> JobPostInfoResponseDto.builder()
                            .jobPostId(post.getJobPostId())
                            .postId(post.getPostId())
                            .jobPostName(post.getJobPostName())
                            .companyName(post.getCompanyName())
                            .companyAddress(post.getCompanyAddress())
                            .companyLink(post.getCompanyLink())
                            .jobType(post.getJobType())
                            .status(post.getStatus())
                            .postDate(post.getPostDate())
                            .openDate(post.getOpenDate())
                            .endDate(post.getEndDate())
                            .salary(post.getSalary())
                            .likes(post.getLikes())
                            .views(post.getViews())
                            .techStacks(post.getJobPostTechStackMappings().stream()
                                    .map(mapping -> TechStackResponseDto.from(mapping.getTechStack()))
                                    .toList())
                            .build())
                    .toList(),
            pageable,
            () -> queryFactory
                    .selectFrom(QJobPost.jobPost)
                    .where(QJobPost.jobPost.status.eq(Status.RECRUITING))
                    .fetchCount()
    );
  }
}
