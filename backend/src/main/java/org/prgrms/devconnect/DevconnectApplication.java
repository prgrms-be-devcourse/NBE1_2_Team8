package org.prgrms.devconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling // 스케줄러 활성화
public class DevconnectApplication {

  public static void main(String[] args) {
    SpringApplication.run(DevconnectApplication.class, args);
  }

}
