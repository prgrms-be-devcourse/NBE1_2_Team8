package org.prgrms.devconnect.domain.define.fixture;

import java.time.LocalDateTime;
import java.util.List;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.JobType;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.Status;

public class JobPostFixture {

  //JobPost
  public static JobPost createJobPost() {
    return JobPost.builder()
        .postId(1L)
        .jobPostName("임시 공고")
        .jobPostLink("임시 링크")
        .companyName("페이퍼컴퍼니")
        .companyLink("임시 회사 링크")
        .companyAddress("임시 회사 주소")
        .postDate(LocalDateTime.now())
        .openDate(LocalDateTime.now())
        .endDate(LocalDateTime.now())
        .experienceLevel("경력무관")
        .requiredEducation("대졸")
        .salary("임시 급여")
        .jobType(JobType.REGULAR)
        .status(Status.RECRUITING)
        .jobPostTechStackMappings(List.of())
        .build();
  }

}
