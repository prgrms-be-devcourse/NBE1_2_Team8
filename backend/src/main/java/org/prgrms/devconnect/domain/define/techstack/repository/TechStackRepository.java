package org.prgrms.devconnect.domain.define.techstack.repository;

import java.util.List;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
  List<TechStack> findAllByTechStackIdIn(List<Long> techStackIds);
}
