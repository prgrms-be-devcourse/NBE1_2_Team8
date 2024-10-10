package org.prgrms.devconnect.domain.define.jobpost.repository;

import org.prgrms.devconnect.domain.define.jobpost.entity.JobPostTechStackMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostTechStackRepository extends JpaRepository<JobPostTechStackMapping, Long> {
}
