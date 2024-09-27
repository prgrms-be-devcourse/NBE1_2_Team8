package org.prgrms.devconnect.chatting.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.devconnect.chatting.entity.constant.ChattingRoomStatus;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ChattingRoomTest {
  @Test
  @DisplayName("채팅방 비활성화 정상 동작 테스트")
  void 채팅방_비활성화_정상동작() throws Exception {
    // given
    ChattingRoom chattingRoom = new ChattingRoom(ChattingRoomStatus.ACTIVE);

    // when
    chattingRoom.closeChatRoom();

    // then
    assertEquals(ChattingRoomStatus.INACTIVE, chattingRoom.getStatus(), "채팅방 상태는 INACTIVE여야 합니다.");
  }

  @Test
  @DisplayName("채팅방 비활성화 예외처리 테스트")
  void 채팅방_비활성화_예외처리() throws Exception {
    //given
    ChattingRoom chattingRoom = new ChattingRoom(ChattingRoomStatus.INACTIVE);
    //when
    assertThrows(RuntimeException.class, chattingRoom::closeChatRoom, "Already INACTIVE Chatting Room");
  }
}
