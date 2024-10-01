package org.prgrms.devconnect.domain.define.member.repository;

import java.util.List;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.favoriteboard.FavoriteBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteBoardRepository extends JpaRepository<FavoriteBoard, Long> {

  @Query("SELECT fb FROM FavoriteBoard fb left join fetch fb.board WHERE fb.member = :member")
  List<FavoriteBoard> findAllByMemberWithBoard(Member member);

}

