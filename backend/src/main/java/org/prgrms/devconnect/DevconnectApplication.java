package org.prgrms.devconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling // 스케줄러 활성화
@EnableAspectJAutoProxy
@EnableAsync(proxyTargetClass = true)
public class DevconnectApplication {

  public static void main(String[] args) {
    SpringApplication.run(DevconnectApplication.class, args);
  }

}
