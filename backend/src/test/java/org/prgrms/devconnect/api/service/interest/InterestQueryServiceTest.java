package org.prgrms.devconnect.api.service.interest;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.prgrms.devconnect.domain.define.fixture.BoardFixture.createBoard;
import static org.prgrms.devconnect.domain.define.fixture.InterestFixture.createInterestBoard;
import static org.prgrms.devconnect.domain.define.fixture.MemberFixture.createMember;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardInfoResponseDto;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.interest.InterestException;
import org.prgrms.devconnect.common.exception.member.MemberException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.interest.repository.InterestBoardRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@ExtendWith(MockitoExtension.class)
class InterestQueryServiceTest {

  @Mock
  private InterestBoardRepository interestBoardRepository;

  @Mock
  private MemberQueryService memberQueryService;

  @InjectMocks
  private InterestQueryService interestQueryService;

  private Member member;
  private Board board;
  private InterestBoard interestBoard;

  @BeforeEach
  void setup() {
    member = createMember("test");
    board = createBoard(member);
    interestBoard = createInterestBoard(member, board);
  }

  @DisplayName("유효한_멤버아이디가_주어지면_관심게시글을_반환한다")
  @Test
  void 유효한_멤버아이디가_주어지면_관심게시글을_반환한다() {
    // given
    Long validMemberId = 1L;
    when(memberQueryService.getMemberByIdOrThrow(validMemberId)).thenReturn(member);

    // when
    interestQueryService.getInterestBoardsByMemberId(validMemberId);

    // then
    verify(interestBoardRepository, times(1)).findAllByMemberWithBoard(member);
  }

  @DisplayName("유효하지않는_멤버아이디가_주어지면_에러가_발생한다")
  @Test
  void 유효하지않는_멤버아이디가_주어지면_에러가_발생한다() {
    // given
    Long invalidMemberId = 999L;

    when(memberQueryService.getMemberByIdOrThrow(invalidMemberId)).thenThrow(
        new MemberException(ExceptionCode.NOT_FOUND_MEMBER)
    );
    // when & then
    assertThatThrownBy(
        () -> interestQueryService.getInterestBoardsByMemberId(invalidMemberId))
        .isInstanceOf(MemberException.class)
        .hasMessage(ExceptionCode.NOT_FOUND_MEMBER.getMessage());
  }

  @DisplayName("유효한_멤버와_게시글이_주어지면_관심게시글을_반환한다")
  @Test
  void 유효한_멤버와_게시글이_주어지면_관심게시글을_반환한다() {
    // given
    when(interestBoardRepository.findByMemberAndBoard(member, board)).thenReturn(
        Optional.of(interestBoard));
    // when
    InterestBoard findInterestBoard = interestQueryService.getInterestBoardByMemberAndBoardOrThrow(
        member, board);

    // then
    verify(interestBoardRepository, times(1)).findByMemberAndBoard(member, board);

  }

  @DisplayName("유효하지않는_멤버와_게시글이_주어지면_에러가_발생한다")
  @Test
  void 유효하지않는_멤버와_게시글이_주어지면_에러가_발생한다() {
    // given
    when(interestBoardRepository.findByMemberAndBoard(member, board)).thenReturn(
        Optional.empty());
    // when & then
    assertThatThrownBy(
        () -> interestQueryService.getInterestBoardByMemberAndBoardOrThrow(member, board))
        .isInstanceOf(InterestException.class)
        .hasMessage(ExceptionCode.NOT_FOUND_INTEREST_BOARD.getMessage());
  }

  @DisplayName("중복_관심게시글이_없으면_에러가_발생하지_않는다")
  @Test
  void 중복_관심게시글이_없으면_에러가_발생하지_않는다() {
    // given
    when(interestBoardRepository.existsByMemberAndBoard(member, board))
        .thenReturn(false);
    // when & then
    assertThatCode(
        () -> interestQueryService.validateDuplicatedInterestBoard(member, board)
    ).doesNotThrowAnyException();
  }

  @DisplayName("중복_관심게시글이_있으면_에러가_발생한다")
  @Test
  void 중복_관심게시글이_있으면_에러가_발생한다() {
    // given
    when(interestBoardRepository.existsByMemberAndBoard(member, board))
        .thenReturn(true);
    // when & then
    assertThatThrownBy(
        () -> interestQueryService.validateDuplicatedInterestBoard(member, board))
        .isInstanceOf(InterestException.class)
        .hasMessage(ExceptionCode.DUPLICATED_INTEREST_BOARD.getMessage());
  }
}