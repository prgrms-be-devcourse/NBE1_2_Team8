package org.prgrms.devconnect.domain.define.board.repository;

import org.prgrms.devconnect.domain.define.board.entity.BoardTechStackMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTechStackMappingRepository extends JpaRepository<BoardTechStackMapping, Long> {

  List<BoardTechStackMapping> findAllByBoard_BoardIdAndTechStack_TechStackIdIn(Long boardId, List<Long> techStackIds);
}
