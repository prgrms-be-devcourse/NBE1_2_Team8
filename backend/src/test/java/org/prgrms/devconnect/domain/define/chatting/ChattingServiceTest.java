package org.prgrms.devconnect.domain.define.chatting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.api.service.chatting.ChattingCommandService;
import org.prgrms.devconnect.api.service.chatting.ChattingQueryService;
import org.prgrms.devconnect.domain.define.chatting.entity.ChatParticipation;
import org.prgrms.devconnect.domain.define.chatting.entity.ChattingRoom;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;
import org.prgrms.devconnect.domain.define.chatting.repository.ChatParticipationRepository;
import org.prgrms.devconnect.domain.define.chatting.repository.ChattingRoomRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.member.entity.constant.Interest;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ChattingServiceTest {

  @Autowired
  private ChattingCommandService chattingCommandService;
  @Autowired
  private ChattingQueryService chattingQueryService;
  @Autowired
  private ChattingRoomRepository chattingRoomRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ChatParticipationRepository chatParticipationRepository;
  @Autowired
  private TechStackRepository techStackRepository;

  @BeforeEach
  void initData() throws Exception {
    TechStack techStack = TechStack.builder()
            .code("100")
            .name("test")
            .build();
    techStackRepository.save(techStack);

    MemberTechStackMapping memberTechStackMapping1 = MemberTechStackMapping.builder()
            .techStack(techStack)
            .build();
    MemberTechStackMapping memberTechStackMapping2 = MemberTechStackMapping.builder()
            .techStack(techStack)
            .build();

    List<MemberTechStackMapping> list1 = new ArrayList<>();
    list1.add(memberTechStackMapping1);
    List<MemberTechStackMapping> list2 = new ArrayList<>();
    list2.add(memberTechStackMapping2);

    // 테스트 용 사용자 생성
    Member member1 = Member.builder()
            .email("test@naver.com")
            .password("1234")
            .nickname("test")
            .affiliation("소속")
            .career(0)
            .selfIntroduction("자기소개")
            .blogLink("blogLink")
            .githubLink("githubLink")
            .interest(Interest.JOBPOST)
            .memberTechStacks(list1)
            .build();
    Member member2 = Member.builder()
            .email("example@naver.com")
            .password("5678")
            .nickname("example")
            .affiliation("소속2")
            .career(1)
            .selfIntroduction("자기소개2")
            .blogLink("blogLink2")
            .githubLink("githubLink2")
            .interest(Interest.STUDY)
            .memberTechStacks(list2)
            .build();

    memberRepository.save(member1);
    memberRepository.save(member2);
  }

  @Test
  @DisplayName("채팅방 생성 테스트")
  void 채팅방_생성() throws Exception {
    List<Member> results = memberRepository.findAll();
    Member sender = results.get(0);
    Member receiver = results.get(0);

    Long roomId = chattingCommandService.createNewChatting(sender.getMemberId(), receiver.getMemberId());
    assertNotNull(roomId, "채팅방 ID는 null이 아니어야 합니다.");

    List<ChatParticipation> allByChattingRoomRoomId = chatParticipationRepository.findAllByChattingRoom_RoomId(roomId);
    assertEquals(2, allByChattingRoomRoomId.size(), "채팅방에 참여자가 2명이어야 합니다.");
  }

  @Test
  @DisplayName("채팅방 비활성화 테스트")
  void 채팅방_비활성화() throws Exception {
    // given
    // 테스트를 위한 채팅방 생성
    ChattingRoom chattingRoom = new ChattingRoom(ChattingRoomStatus.ACTIVE);
    chattingRoomRepository.save(chattingRoom);
    Long roomId = chattingRoom.getRoomId();

    // when
    chattingCommandService.closeChattingRoom(roomId);

    // then
    ChattingRoom updatedRoom = chattingRoomRepository.findById(roomId).orElseThrow();
    assertEquals(ChattingRoomStatus.INACTIVE, updatedRoom.getStatus(), "채팅방 상태는 INACTIVE여야 합니다.");
  }

  @Test
  @DisplayName("사용자 ID로 채팅방 조회")
  void 사용자_채팅방_전체조회() throws Exception {
      //given
    List<Member> results = memberRepository.findAll();
    Member sender = results.get(0);
    Member receiver = results.get(1);

    Long chattingRoomId1 = chattingCommandService.createNewChatting(sender.getMemberId(), receiver.getMemberId());
    Long chattingRoomId2 = chattingCommandService.createNewChatting(sender.getMemberId(), receiver.getMemberId());

    //when
    List<ChatRoomListResponse> allActivateChattingsByMemberId = chattingQueryService.findAllActivateChattingsByMemberId(sender.getMemberId());

    //then
    assertEquals(2, allActivateChattingsByMemberId.size(), "채팅방 개수는 2개여야 합니다.");

    // 2. 리스트 내 각 ChatRoomListResponse의 memberId가 sender의 memberId와 일치하는지 확인
    for (ChatRoomListResponse response : allActivateChattingsByMemberId) {
      System.out.println(response);
      assertEquals(sender.getMemberId(), response.memberId(), "채팅방의 memberId는 sender의 memberId와 일치해야 합니다.");
    }

    // 3. 상태가 ACTIVE인지 확인
    for (ChatRoomListResponse response : allActivateChattingsByMemberId) {
      assertEquals(ChattingRoomStatus.ACTIVE, response.status(), "채팅방 상태는 ACTIVE여야 합니다.");
    }
  }
}
