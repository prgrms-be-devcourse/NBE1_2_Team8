package org.prgrms.devconnect.domain.define.interest.repository;

import java.util.List;
import java.util.Optional;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterestBoardRepository extends JpaRepository<InterestBoard, Long> {

  @Query("SELECT ib FROM InterestBoard ib left join fetch ib.board WHERE ib.member = :member")
  List<InterestBoard> findAllByMemberWithBoard(Member member);

  Optional<InterestBoard> findByMemberAndBoard(Member member, Board board);

  boolean existsByMemberAndBoard(Member member, Board board);
}
