package org.prgrms.devconnect.chatting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.service.chatting.ChattingCommandService;
import org.prgrms.devconnect.api.service.techstack.TechStackQueryService;
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
  private ChattingCommandService chattingService;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ChatParticipationRepository chatParticipationRepository;
  @Autowired
  private ChattingRoomRepository chattingRoomRepository;
  @Autowired
  private TechStackRepository techStackRepository;



  private ChattingRoom chattingRoom;
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
            .build();

    memberRepository.save(member1);
    memberRepository.save(member2);

    // 테스트를 위한 채팅방 생성
    chattingRoom = new ChattingRoom(ChattingRoomStatus.ACTIVE);
    chattingRoomRepository.save(chattingRoom);
  }

  @Test
  @DisplayName("채팅방 생성 테스트")
  void 채팅방_생성() throws Exception {
    List<Member> results = memberRepository.findAll();
    Member sender = results.get(0);
    Member receiver = results.get(0);

    Long senderChatpartId = chattingService.createNewChatting(sender.getMemberId(), receiver.getMemberId());
    assertNotNull(senderChatpartId, "채팅 참여 ID는 null이 아니어야 합니다.");

    ChatParticipation result = chatParticipationRepository.findById(senderChatpartId).orElseThrow();
    assertNotNull(result, "채팅 참여 객체가 존재해야 합니다.");

    Long roomId = result.getChattingRoom().getRoomId();
    assertNotNull(roomId, "채팅방 ID는 null이 아니어야 합니다.");

    List<ChatParticipation> allByChattingRoomRoomId = chatParticipationRepository.findAllByChattingRoom_RoomId(roomId);
    assertEquals(2, allByChattingRoomRoomId.size(), "채팅방에 참여자가 2명이어야 합니다.");
  }

  @Test
  @DisplayName("채팅방 비활성화 테스트")
  void 채팅방_비활성화() throws Exception {
    // given
    Long roomId = chattingRoom.getRoomId();

    // when
    chattingService.closeChattingRoom(roomId);

    // then
    ChattingRoom updatedRoom = chattingRoomRepository.findById(roomId).orElseThrow();
    assertEquals(ChattingRoomStatus.INACTIVE, updatedRoom.getStatus(), "채팅방 상태는 INACTIVE여야 합니다.");
  }
}
