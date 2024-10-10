package org.prgrms.devconnect.domain.define.member.repository;

import java.util.Optional;

import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  @Query("SELECT m FROM Member m LEFT JOIN FETCH m.memberTechStacks mts LEFT JOIN FETCH mts.techStack WHERE m.memberId = :memberId")
  Optional<Member> findByMemberIdWithTechStack(@Param("memberId") Long memberId);

  boolean existsByEmail(String email);
}
