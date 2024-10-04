package org.prgrms.devconnect.domain.define.jobpost.repository;


import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {

  boolean existsByPostId(Long postId);
}
