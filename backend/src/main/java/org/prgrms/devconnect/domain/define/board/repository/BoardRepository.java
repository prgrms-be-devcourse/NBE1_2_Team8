package org.prgrms.devconnect.domain.define.board.repository;


import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
  @Query("SELECT b FROM Board b WHERE b.endDate< :currentDate AND b.status=:status")
  List<Board> findAllByEndDateAndStatus(@Param("currentDate") LocalDateTime currentDate,
                                        @Param("status") BoardStatus status);

  // DELETED 상태가 아닌 게시글만 조회하는 메서드
  @Query("SELECT b FROM Board b LEFT JOIN FETCH b.boardTechStacks ts LEFT JOIN FETCH ts.techStack WHERE b.status != 'DELETED'")
  Page<Board> findAllWithTechStackByStatusNotDeleted(Pageable pageable);

  @Query("SELECT b FROM Board b WHERE b.boardId = :boardId AND b.status != 'DELETED'")
  Optional<Board> findByIdAndStatusNotDeleted(@Param("boardId") Long boardId);
}
