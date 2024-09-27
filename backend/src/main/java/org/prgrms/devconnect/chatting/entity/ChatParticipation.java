package org.prgrms.devconnect.chatting.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_participation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatParticipation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_part_id")
  private Long chatPartId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id", nullable = false)
  private ChattingRoom chattingRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @OneToMany(mappedBy = "chatParticipation", fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<Message> messages = new ArrayList<>();


//   채팅방 생성,입장 생성자
  @Builder
  public ChatParticipation(ChattingRoom chattingRoom, Member member) {
    this.chattingRoom = chattingRoom;
    this.member = member;
  }

  // 연관관계 편의 메소드
  public void addMessage(Message message){
    message.setChatParticipation(this);
    messages.add(message);
  }

  // 연관관계 편의 메서드
  public void setMember(Member member) {
    this.member = member;
  }
}
