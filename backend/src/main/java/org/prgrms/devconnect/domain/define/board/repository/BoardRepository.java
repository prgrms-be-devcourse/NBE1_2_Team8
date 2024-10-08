package org.prgrms.devconnect.domain.define.board.repository;


import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.prgrms.devconnect.domain.define.board.repository.custom.BoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
  @Query("SELECT b FROM Board b WHERE b.endDate< :currentDate AND b.status=:status")
  List<Board> findAllByEndDateAndStatus(@Param("currentDate") LocalDateTime currentDate,
                                        @Param("status") BoardStatus status);

  // DELETED 상태가 아닌 게시글만 조회하는 메서드
  @Query("SELECT b FROM Board b LEFT JOIN FETCH b.boardTechStacks ts LEFT JOIN FETCH ts.techStack WHERE b.status != 'DELETED'")
  Page<Board> findAllWithTechStackByStatusNotDeleted(Pageable pageable);

  @Query("SELECT b FROM Board b WHERE b.boardId = :boardId AND b.status != 'DELETED'")
  Optional<Board> findByIdAndStatusNotDeleted(@Param("boardId") Long boardId);

  @Query("SELECT b FROM Board b WHERE b.jobPost.jobPostId = :jobPostId AND b.status != 'DELETED'")
  List<Board> findAllByJobPostId(@Param("jobPostId") Long jobPostId);

  @Query("SELECT b FROM Board b WHERE b.createdAt BETWEEN :startOfWeek AND :endOfWeek AND b.views >= 500 AND b.status != 'DELETED'")
  List<Board> findBoardsWithPopularTagCondition(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

  @Query("SELECT b FROM Board b WHERE b.endDate BETWEEN :currentDate AND :deadlineDate AND b.status != 'DELETED'")
  List<Board> findBoardsWithDeadlineApproaching(@Param("currentDate") LocalDateTime currentDate, @Param("deadlineDate") LocalDateTime deadlineDate);

  @Query("SELECT DISTINCT b FROM Board b JOIN b.boardTechStacks ts JOIN ts.techStack t WHERE t IN :techStacks AND b.status = :status")
  List<Board> findAllByTechStacks(
          @Param("techStacks") List<TechStack> techStacks,
          @Param("status") BoardStatus status
  );

  @Modifying
  @Query("UPDATE Board b SET b.views = b.views + 1 WHERE b.boardId = :boardId")
  void incrementViews(@Param("boardId") Long boardId);
}
