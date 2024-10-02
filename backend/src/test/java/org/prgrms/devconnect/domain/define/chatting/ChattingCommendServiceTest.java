package org.prgrms.devconnect.domain.define.chatting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatPartResponse;
import org.prgrms.devconnect.api.service.chatting.ChattingCommandService;
import org.prgrms.devconnect.domain.define.chatting.entity.ChatParticipation;
import org.prgrms.devconnect.domain.define.chatting.entity.ChattingRoom;
import org.prgrms.devconnect.domain.define.chatting.entity.Message;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;
import org.prgrms.devconnect.domain.define.chatting.repository.ChatParticipationRepository;
import org.prgrms.devconnect.domain.define.chatting.repository.ChattingRoomRepository;
import org.prgrms.devconnect.domain.define.chatting.repository.MessageRepository;
import org.prgrms.devconnect.domain.define.fixture.MemberFixture;
import org.prgrms.devconnect.domain.define.fixture.TechStackFixture;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ChattingCommendServiceTest {

  @Autowired
  private ChattingCommandService chattingCommandService;
  @Autowired
  private ChattingRoomRepository chattingRoomRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ChatParticipationRepository chatParticipationRepository;
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private TechStackRepository techStackRepository;

  @BeforeEach
  void initData() throws Exception {
    TechStack techStack = TechStackFixture.createTechStack();
    techStackRepository.save(techStack);

    Member member1 = MemberFixture.createMember(techStack);
    Member member2 = MemberFixture.createMember(techStack);

    memberRepository.save(member1);
    memberRepository.save(member2);
  }


  @Test
  @DisplayName("채팅방 생성 테스트")
  void 채팅방_생성() throws Exception {
    List<Member> results = memberRepository.findAll();
    Member sender = results.get(0);
    Member receiver = results.get(0);

    ChatPartResponse chatting = chattingCommandService.createNewChatting(sender.getMemberId(), receiver.getMemberId());
    assertNotNull(chatting, "ChatPartResponse는 null이 아니어야 합니다.");

    List<ChatParticipation> allByChattingRoomRoomId = chatParticipationRepository.findAllByChattingRoom_RoomId(chatting.roomId());
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
  @DisplayName("채팅 메세지 생성 테스트")
  void 채팅_메세지_생성_테스트() throws Exception {
    //given
    List<Member> results = memberRepository.findAll();
    Member sender = results.get(0);
    Member receiver = results.get(0);

    ChatPartResponse chatting = chattingCommandService.createNewChatting(sender.getMemberId(), receiver.getMemberId());
    //when
    String text = "test";
    chattingCommandService.sendMessage(chatting.chatpartId(), text);
    chattingCommandService.sendMessage(chatting.chatpartId(), text);

    //then
    List<Message> all = messageRepository.findAll();
    assertEquals(2, all.size(), "생성된 메세지가 2개 이어야 합니다.");
  }
}
