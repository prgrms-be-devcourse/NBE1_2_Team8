package org.prgrms.devconnect.domain.define.member.repository;

import java.util.Optional;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  @Query("SELECT m FROM Member m left join fetch m.memberTechStacks WHERE m.memberId = :memberId")
  Optional<Member> findByMemberId(Long memberId);

  boolean existsByEmail(String email);
}
