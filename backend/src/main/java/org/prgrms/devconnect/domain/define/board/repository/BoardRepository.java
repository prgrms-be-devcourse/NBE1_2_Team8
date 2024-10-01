package org.prgrms.devconnect.domain.define.board.repository;


import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
  @Query("SELECT b FROM Board b WHERE b.endDate< :currentDate AND b.status=:status")
  List<Board> findAllByEndDateAndStatus(@Param("currentDate") LocalDateTime currentDate,
                                        @Param("status") BoardStatus status);
}
