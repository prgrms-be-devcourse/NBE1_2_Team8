package org.prgrms.devconnect.domain.define.member.repository;

import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberTechStackMappingRepository extends JpaRepository<MemberTechStackMapping, Long> {

  List<MemberTechStackMapping> findAllByMember_MemberIdAndTechStack_TechStackIdIn(Long memberId, List<Long> techStackIds);

}
