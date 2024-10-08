package org.prgrms.devconnect.api.service.interest;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestBoardRequestDto;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestJobPostRequestDto;
import org.prgrms.devconnect.api.service.board.BoardCommandService;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.api.service.jobpost.JobPostCommandService;
import org.prgrms.devconnect.api.service.jobpost.JobPostQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.interest.entity.InterestJobPost;
import org.prgrms.devconnect.domain.define.interest.repository.InterestBoardRepository;
import org.prgrms.devconnect.domain.define.interest.repository.InterestJobPostRepository;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InterestCommandService {

  private final InterestBoardRepository interestBoardRepository;
  private final InterestJobPostRepository interestJobPostRepository;
  private final MemberQueryService memberQueryService;
  private final BoardQueryService boardQueryService;
  private final BoardCommandService boardCommandService;
  private final JobPostQueryService jobPostQueryService;
  private final JobPostCommandService jobPostCommandService;
  private final InterestQueryService interestQueryService;

  public void addInterestBoard(InterestBoardRequestDto requestDto, Long memberId) {
    Member member = memberQueryService.getMemberByIdOrThrow(memberId);
    Board board = boardQueryService.getBoardByIdOrThrow(requestDto.boardId());

    interestQueryService.validateDuplicatedInterestBoard(member, board);

    // 좋아요 증가
    boardCommandService.likeBoard(board.getBoardId());

    InterestBoard interestBoard = requestDto.toEntity(member, board);
    interestBoardRepository.save(interestBoard);
  }

  public void removeInterestBoard(Long memberId, Long boardId) {
    InterestBoard interestBoard = interestQueryService.getInterestBoardByMemberIdAndBoardIdOrThrow(
        memberId, boardId);
    Board board = boardQueryService.getBoardByIdOrThrow(boardId);

    // 좋아요 감소
    boardCommandService.unlikeBoard(board.getBoardId());

    interestBoardRepository.delete(interestBoard);
  }

  public void addInterestJobPost(InterestJobPostRequestDto requestDto, Long memberId) {
    Member member = memberQueryService.getMemberByIdOrThrow(memberId);
    JobPost jobPost = jobPostQueryService.getJobPostByIdOrThrow(requestDto.jobPostId());

    interestQueryService.validateDuplicatedInterestJobPost(member, jobPost);

    // 좋아요 증가
    jobPostCommandService.likeJobPost(jobPost.getJobPostId());

    InterestJobPost interestJobPost = requestDto.toEntity(member, jobPost);
    interestJobPostRepository.save(interestJobPost);
  }

  public void removeInterestJobPost(Long memberId, Long jobPostId) {
    InterestJobPost interestJobPost = interestQueryService
        .getInterestJobPostByMemberIdAndJobPostIdOrThrow(memberId, jobPostId);
    JobPost jobPost = jobPostQueryService.getJobPostByIdOrThrow(jobPostId);

    // 좋아요 감소
    jobPostCommandService.unlikeJobPost(jobPost.getPostId());

    interestJobPostRepository.delete(interestJobPost);

  }
}
