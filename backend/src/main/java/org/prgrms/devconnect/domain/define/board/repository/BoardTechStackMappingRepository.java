package org.prgrms.devconnect.domain.define.board.repository;

import org.prgrms.devconnect.domain.define.board.entity.BoardTechStackMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardTechStackMappingRepository extends JpaRepository<BoardTechStackMapping, Long> {

  List<BoardTechStackMapping> findAllByBoard_BoardIdAndTechStack_TechStackIdIn(Long boardId, List<Long> techStackIds);

  @Modifying
  @Query("DELETE FROM BoardTechStackMapping b where b.id IN :ids")
  void deleteAllByIds(@Param("ids") List<Long> ids);

}
