package org.prgrms.devconnect.common.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("springdoc-openapi")
            .version("1.0")
            .description("DevConnector swagger-ui 화면입니다.")
        )
        .components(new Components()
            .addSecuritySchemes("bearer-jwt",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")))
        .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
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
        .group("job-posts")
        .pathsToMatch("/api/v1/job-posts/**")
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
  public GroupedOpenApi getTechStackAPI() {
    return GroupedOpenApi.builder()
        .group("tech-stacks")
        .pathsToMatch("/api/v1/tech-stacks/**")
        .build();
  }
}
