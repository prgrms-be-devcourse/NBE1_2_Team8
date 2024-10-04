package org.prgrms.devconnect.domain.define.interest.repository;

import java.util.List;
import org.prgrms.devconnect.domain.define.interest.entity.InterestJobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterestJobPostRepository extends JpaRepository<InterestJobPost, Long> {

  @Query("SELECT ij FROM InterestJobPost ij LEFT JOIN FETCH ij.jobPost WHERE ij.member = :member")
  List<InterestJobPost> findAllByMemberWithJobPost(Member member);
}
