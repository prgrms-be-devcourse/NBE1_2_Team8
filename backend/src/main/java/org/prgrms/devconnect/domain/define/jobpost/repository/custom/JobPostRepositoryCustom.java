package org.prgrms.devconnect.domain.define.jobpost.repository.custom;


import org.prgrms.devconnect.api.controller.jobpost.dto.response.JobPostInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobPostRepositoryCustom {

  Page<JobPostInfoResponseDto> findAllRecruiting(Pageable pageable);
}
