package org.prgrms.devconnect.domain.define.chatting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;

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


  // 채팅방 비활성화 메서드
  public void closeChatRoom() {
    if(this.status == ChattingRoomStatus.INACTIVE)
      throw new RuntimeException("Already INACTIVE Chatting Room");

    this.status = ChattingRoomStatus.INACTIVE;
  }
}