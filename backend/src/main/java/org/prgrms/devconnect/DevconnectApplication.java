package org.prgrms.devconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DevconnectApplication {

  public static void main(String[] args) {
    SpringApplication.run(DevconnectApplication.class, args);
  }

}
