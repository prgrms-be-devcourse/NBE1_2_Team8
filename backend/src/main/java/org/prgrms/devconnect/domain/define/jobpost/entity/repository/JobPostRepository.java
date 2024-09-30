package org.prgrms.devconnect.domain.define.jobpost.entity.repository;

import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
