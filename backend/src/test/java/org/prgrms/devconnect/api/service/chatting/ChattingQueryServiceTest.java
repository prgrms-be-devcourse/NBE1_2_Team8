package org.prgrms.devconnect.api.service.chatting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatPartResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageFullResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageResponse;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;
import org.prgrms.devconnect.domain.define.fixture.MemberFixture;
import org.prgrms.devconnect.domain.define.fixture.TechStackFixture;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ChattingQueryServiceTest {

  @Autowired
  private ChattingCommandService chattingCommandService;
  @Autowired
  private ChattingQueryService chattingQueryService;
  @Autowired
  private MemberRepository memberRepository;
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
  @DisplayName("사용자 ID로 채팅방 조회")
  void 사용자_채팅방_전체조회() throws Exception {
    //given
    List<Member> results = memberRepository.findAll();
    Member sender = results.get(0);
    Member receiver = results.get(1);

    ChatPartResponse response1 = chattingCommandService.createNewChatting(sender.getMemberId(), receiver.getMemberId());
    ChatPartResponse response2 = chattingCommandService.createNewChatting(sender.getMemberId(), receiver.getMemberId());

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

  @Test
  @DisplayName("채팅방 메세지 조회")
  void 채팅방_메세지_조회() throws Exception {
    //given
    List<Member> members = memberRepository.findAll();
    Member sender = members.get(0);
    Member receiver = members.get(1);

    ChatPartResponse sendchatpart = chattingCommandService.createNewChatting(sender.getMemberId(), receiver.getMemberId());

    List<ChatRoomListResponse> findAll = chattingQueryService.findAllActivateChattingsByMemberId(receiver.getMemberId());
    Long receiverchatpartId = findAll.get(0).chatpartId();

    String text = "test";
    chattingCommandService.sendMessage(sendchatpart.chatpartId(), text);
    chattingCommandService.sendMessage(sendchatpart.chatpartId(), text);
    chattingCommandService.sendMessage(receiverchatpartId, text);


    //when
    MessageFullResponse results = chattingQueryService.getAllMessagebyRoomId(sendchatpart.roomId(), PageRequest.of(0, 10));

    System.out.println(results);
    for (MessageResponse messageResponse : results.messageList()) {
      System.out.println(messageResponse);
    }

    //then
    assertEquals(3, results.messageList().getTotalElements(), "조회한 메세지가 3개 이어야 합니다.");
  }
}
