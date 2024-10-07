package org.prgrms.devconnect.domain.define.board.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.BoardFilterDto;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.QBoard;
import org.prgrms.devconnect.domain.define.board.entity.QBoardTechStackMapping;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;
import org.prgrms.devconnect.domain.define.techstack.entity.QTechStack;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
  private final JPAQueryFactory queryFactory;
  @Override
  public List<Board> findTop10PopularBoardsThisWeek(LocalDateTime startOfWeek, LocalDateTime endOfWeek) {
    QBoard board = QBoard.board;
    return queryFactory
            .selectFrom(board)
            .where(board.status.eq(BoardStatus.RECRUITING)
                    .and(board.createdAt.between(startOfWeek,endOfWeek)))
            .orderBy(board.views.desc())
            .limit(10)
            .fetch();
  }

  @Override
  public List<Board> findAllByTechStacks(List<TechStack> techStacks) {
    QBoard board = QBoard.board;
    QBoardTechStackMapping boardTechStackMapping= QBoardTechStackMapping.boardTechStackMapping;
    QTechStack techStack = QTechStack.techStack;

    return queryFactory
            .select(board)
            .from(board)
            .join(board.boardTechStacks,boardTechStackMapping)
            .join(boardTechStackMapping.techStack,techStack)
            .where(techStack.in(techStacks).and(board.status.eq(BoardStatus.RECRUITING)))
            .distinct()
            .fetch();

  }

  @Override
  public Page<Board> findByFilter(BoardFilterDto filterDto, Pageable pageable) {
    QBoard board=QBoard.board;
    JPAQuery<Board> query = queryFactory
            .selectFrom(board)
            .where(
                    board.status.ne(BoardStatus.DELETED),
                    filterByCategory(filterDto.category()),
                    filterByStatus(filterDto.status()),
                    filterByTechStack(filterDto.techStackIds()),
                    filterByProgressWay(filterDto.progressWay())
            );
    return PageableExecutionUtils.getPage(query.fetch(), pageable, query::fetchCount);
  }
  private BooleanExpression filterByCategory(BoardCategory category) {
    return category != null ? QBoard.board.category.eq(category) : null;
  }

  private BooleanExpression filterByStatus(BoardStatus status) {
    return status != null ? QBoard.board.status.in(status) : null;
  }

  private BooleanExpression filterByTechStack(List<Long> techStackIds) {
    if (techStackIds == null || techStackIds.isEmpty()) {
      return null;
    }
    return QBoard.board.boardTechStacks.any().techStack.techStackId.in(techStackIds);
  }

  private BooleanExpression filterByProgressWay(ProgressWay progressWay) {
    return progressWay != null ? QBoard.board.progressWay.eq(progressWay) : null;
  }

}
