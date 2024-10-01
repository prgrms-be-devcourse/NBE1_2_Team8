package org.prgrms.devconnect.api.service.interest;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardInfoResponseDto;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.favoriteboard.FavoriteBoard;
import org.prgrms.devconnect.domain.define.member.repository.FavoriteBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestQueryService {

  private final FavoriteBoardRepository favoriteBoardRepository;
  private final MemberQueryService memberQueryService;

  public List<BoardInfoResponseDto> getInterestBoards(Long memberId) {
    Member member = memberQueryService.getMemberByIdOrThrow(memberId);
    List<FavoriteBoard> favoriteBoards = favoriteBoardRepository.findAllByMemberWithBoard(
        member);

    return favoriteBoards.stream().map(FavoriteBoard::getBoard)
        .map(BoardInfoResponseDto::from)
        .collect(Collectors.toList());
  }
}
