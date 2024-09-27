package org.prgrms.devconnect.chatting.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.member.entity.Member;

@Entity
@Table(name = "chat_participation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatParticipation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_part_id")
  private Long chatPartId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id", nullable = false)
  private ChattingRoom chattingRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @OneToMany(mappedBy = "chatParticipation", fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<Message> messages = new ArrayList<>();


  // 채팅방 생성,입장 생성자
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
