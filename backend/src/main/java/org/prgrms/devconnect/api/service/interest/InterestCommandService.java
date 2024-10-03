package org.prgrms.devconnect.api.service.interest;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestBoardRequestDto;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.interest.repository.InterestBoardRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InterestCommandService {

  private final InterestBoardRepository interestBoardRepository;
  private final MemberQueryService memberQueryService;
  private final BoardQueryService boardQueryService;
  private final InterestQueryService interestQueryService;

  public void addInterestBoard(InterestBoardRequestDto requestDto) {
    Member member = memberQueryService.getMemberByIdOrThrow(requestDto.memberId());
    Board board = boardQueryService.getBoardByIdOrThrow(requestDto.boardId());

    interestQueryService.validateDuplicatedInterestBoard(member, board);

    InterestBoard interestBoard = requestDto.toEntity(member, board);
    interestBoardRepository.save(interestBoard);
  }

  public void removeInterestBoard(Long memberId, Long boardId) {
    Member member = memberQueryService.getMemberByIdOrThrow(memberId);
    Board board = boardQueryService.getBoardByIdOrThrow(boardId);

    InterestBoard interestBoard = interestQueryService.getInterestBoardByMemberAndBoardOrThrow(
        member, board);

    interestBoardRepository.delete(interestBoard);
  }
}
