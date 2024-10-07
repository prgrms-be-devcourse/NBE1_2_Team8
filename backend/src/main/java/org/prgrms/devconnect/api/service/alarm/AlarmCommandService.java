package org.prgrms.devconnect.api.service.alarm;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.prgrms.devconnect.domain.define.alarm.repository.AlarmRepository;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmCommandService {

  private final AlarmRepository alarmRepository;
  private final AlarmQueryService alarmQueryService;

  public Alarm createWelcomeAlarmWhenSignIn(Member member) {

    //TODO: 프론트 메인페이지 url 추가
    String mainPage = "";
    String welcomeMessage =
            member.getNickname() + "님, 만나서 반가워요👋\n 1만명의 DevConnector들이 " + member.getNickname() + "님에 대해 알고싶대요!";

    Alarm alarm = Alarm.builder()
            .member(member)
            .alertText(welcomeMessage)
            .relatedUrl(mainPage)
            .build();
    alarmRepository.save(alarm);

    return alarm;
  }

  public Void deleteAlarmByAlarmIdAndMemberId(Long alarmId, Long memberId) {
    alarmQueryService.getAlarmByAlarmIdAndMemberIdOrThrow(alarmId, memberId);
    alarmRepository.deleteByAlarmIdAndMemberMemberId(alarmId, memberId);
    return null;
  }

  public Void deleteAlarmsByMemberId(Long memberId) {
    alarmQueryService.getAlarmsByMemberIdOrThrow(memberId);
    alarmRepository.deleteAllByMemberMemberId(memberId);
    return null;
  }

  public Alarm createCommentPostedMessageToBoardPoster(Comment comment) {

    Board postedBoard = comment.getBoard();
    Member boardedPoster = postedBoard.getMember();
    Member commenter = comment.getMember();

    String linkedPage = "";
    String commentPostedMessage = boardedPoster + "님이 포스팅 한 "+ postedBoard.getTitle()+ "에 "+ commenter.getNickname() + "이 댓글을 남겼습니다!";

    Alarm alarm = Alarm.builder()
            .member(boardedPoster)
            .alertText(commentPostedMessage)
            .relatedUrl(linkedPage)
            .build();

    alarmRepository.save(alarm);

    return alarm;
  }

  public Alarm createReplyCommentReceivedAlarmToParentCommenter(Comment comment) {

    Member parentCommenter = comment.getParent().getMember();
    Member replier = comment.getMember();

    String likedPage = "";
    String replyMessage = parentCommenter.getNickname() + "님이 작성한 댓글 \""+ comment.getContent() + "\"에 답글이 달렸어요!";

    Alarm alarm = Alarm.builder()
            .member(parentCommenter)
            .alertText(replyMessage)
            .relatedUrl(likedPage)
            .build();

    alarmRepository.save(alarm);

    return alarm;
  }

  public Alarm createUrgentAlarmAboutInterestBoard(InterestBoard interestBoard) {
    Member member = interestBoard.getMember();
    Board board = interestBoard.getBoard();
    long remainingDate = LocalDate.now().until(board.getEndDate().toLocalDate(), ChronoUnit.DAYS);
    String likedPage = "";
    String urgentMessage = member.getNickname() + "님이 관심 표시한 " +  board.getTitle() + "의 마감 기한까지" + remainingDate + "일 남았습니다. 얼른 지원해보세요!";

    Alarm alarm = Alarm.builder()
            .member(member)
            .alertText(urgentMessage)
            .relatedUrl(likedPage)
            .build();

    alarmRepository.save(alarm);

    return alarm;
  }
}
