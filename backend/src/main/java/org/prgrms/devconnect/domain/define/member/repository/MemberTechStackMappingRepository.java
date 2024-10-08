package org.prgrms.devconnect.domain.define.member.repository;

import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberTechStackMappingRepository extends JpaRepository<MemberTechStackMapping, Long> {

  List<MemberTechStackMapping> findAllByMember_MemberIdAndTechStack_TechStackIdIn(Long memberId, List<Long> techStackIds);

  @Modifying
  @Query("DELETE FROM MemberTechStackMapping m WHERE m.id IN :ids")
  void deleteAllByIds(@Param("ids") List<Long> ids);
}
