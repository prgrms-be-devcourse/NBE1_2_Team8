package org.prgrms.devconnect.api.service.techstack;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.techstack.TechStackException;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TechStackQueryService {

  private final TechStackRepository techStackRepository;

  public List<TechStack> getTechStacksByIdsOrThrow(List<Long> techStackIds) {
    List<TechStack> techStacks = techStackRepository.findAllByTechStackIdIn(techStackIds);

    if (techStacks.size() != techStackIds.size()) {
      throw new TechStackException(ExceptionCode.NOT_FOUND_TECH_STACK);
    }
    return techStacks;
  }

  public TechStack getTechStackByIdOrThrow(Long techStackId) {
    return techStackRepository.findById(techStackId).orElseThrow(
        () -> new TechStackException(ExceptionCode.NOT_FOUND_TECH_STACK)
    );
  }
}
