package org.prgrms.devconnect.api.controller.board;

import lombok.*;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateDTO {
  private Long memberId;
  private Long jobPostId;
  private String title;
  private String content;
  private String category;
  private int recruitNum;
  private String progressWay;
  private String progressPeriod;
  private LocalDateTime endDate;
  private List<Long> techStackIds;

  public Board toEntity(Member member, JobPost jobPost, List<TechStack> techStacks) {
    // Board 엔티티 생성
    Board board = Board.builder()
            .member(member)
            .jobPost(jobPost)
            .title(title)
            .content(content)
            .category(category)
            .recruitNum(recruitNum)
            .progressWay(progressWay)
            .progressPeriod(progressPeriod)
            .endDate(endDate)
            .build();
    //    기술 스택 매핑 추가
    techStacks.forEach(techStack -> {
      board.addTechStack(techStack);
    });
    return board;
  }
}
