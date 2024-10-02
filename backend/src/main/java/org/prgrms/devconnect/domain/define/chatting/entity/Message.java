package org.prgrms.devconnect.domain.define.chatting.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.domain.define.CreateTimestamp;

@Entity
@Table(name = "message")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends CreateTimestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "message_id")
  private Long messageId;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_part_id", nullable = false)
  @JsonBackReference
  private ChatParticipation chatParticipation;


  @Builder
  public Message(String content, ChatParticipation chatParticipation) {
    this.content = content;
    this.chatParticipation = chatParticipation;
  }

  // 연관관계 편의 메서드
  public void setChatParticipation(ChatParticipation chatParticipation) {
    this.chatParticipation = chatParticipation;
  }
}
