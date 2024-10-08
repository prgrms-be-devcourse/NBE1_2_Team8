package org.prgrms.devconnect.domain.define.techstack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tech_stack")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TechStack {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tech_stack_id")
  private Long techStackId;

  @Column(name = "name", length = 50)
  private String name;

  @Column(name = "job_code", length = 50)
  private String code;

  @Builder
  public TechStack(String name, String code) {
    this.name = name;
    this.code = code;
  }

}
