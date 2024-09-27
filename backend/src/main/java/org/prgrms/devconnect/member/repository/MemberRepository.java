package org.prgrms.devconnect.member.repository;


import java.util.Optional;
import org.prgrms.devconnect.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByMemberId(Long memberId);

  Optional<Member> findByEmail(String email);

}
