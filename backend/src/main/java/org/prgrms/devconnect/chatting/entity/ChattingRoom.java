package org.prgrms.devconnect.chatting.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.chatting.entity.constant.ChattingRoomStatus;

@Entity
@Table(name = "chatting_room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChattingRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "room_id")
  private Long roomId;


  @Column(name = "status", columnDefinition = "VARCHAR(50)")
  @Enumerated(value = EnumType.STRING)
  private ChattingRoomStatus status = ChattingRoomStatus.ACTIVE;

  public ChattingRoom(ChattingRoomStatus status) {
    this.status = status;
  }

  // 채팅방 비활성화 메서드
  public void closeChatRoom() {
    if(this.status == ChattingRoomStatus.INACTIVE)
      throw new RuntimeException("Already INACTIVE Chatting Room");

    this.status = ChattingRoomStatus.INACTIVE;
  }
}