package org.prgrms.devconnect.api.service.techstack;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TechStackQueryService {

  private final TechStackRepository techStackRepository;

  // TODO: techStackIds를 가지고 List<TechStack> 반환
  /*
  * @Param List<Long> techStackIds
  * @Return List<TechStack>
  * @Throw TechStackException() -> 사이즈가 다를 경우
  * */
  public List<TechStack> getTechStacksByIdsOrThrow(List<Long> techStackIds) {
    return new ArrayList<TechStack>();
  }
}
