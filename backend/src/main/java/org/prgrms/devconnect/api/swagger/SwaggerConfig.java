package org.prgrms.devconnect.api.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

  @Bean
  public OpenAPI getOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("springdoc-openapi")
                    .version("1.0")
                    .description("DevConnector swagger-ui 화면입니다.")
            );
  }

  @Bean
  public GroupedOpenApi getAllAPI() {
    return GroupedOpenApi.builder()
            .group("all")
            .pathsToMatch("/api/v1/**")
            .build();
  }

  @Bean
  public GroupedOpenApi getAlarmAPI() {
    return GroupedOpenApi.builder()
            .group("alarms")
            .pathsToMatch("/api/v1/alarms/**")
            .build();
  }

  @Bean
  public GroupedOpenApi getBoardAPI() {
    return GroupedOpenApi.builder()
            .group("boards")
            .pathsToMatch("/api/v1/boards/**")
            .build();
  }

  @Bean
  public GroupedOpenApi getChattingAPI() {
    return GroupedOpenApi.builder()
            .group("chatting")
            .pathsToMatch("/api/v1/chat/**")
            .build();
  }

  @Bean
  public GroupedOpenApi getInterestAPI() {
    return GroupedOpenApi.builder()
            .group("interests")
            .pathsToMatch("/api/v1/interests/**")
            .build();
  }


  @Bean
  public GroupedOpenApi getJobpostAPI() {
    return GroupedOpenApi.builder()
            .group("jobpost")
            .pathsToMatch("/api/v1/jobPosts/**")
            .build();
  }

  @Bean
  public GroupedOpenApi getMemberAPI() {
    return GroupedOpenApi.builder()
            .group("members")
            .pathsToMatch("/api/v1/members/**")
            .build();
  }

  @Bean
  public GroupedOpenApi getTechstackAPI() {
    return GroupedOpenApi.builder()
            .group("tech-stack")
            .pathsToMatch("/api/v1/tech-stacks/**")
            .build();
  }

}
