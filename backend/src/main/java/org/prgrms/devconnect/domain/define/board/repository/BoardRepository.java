package org.prgrms.devconnect.domain.define.board.repository;


import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
