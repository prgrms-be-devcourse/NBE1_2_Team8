package org.prgrms.devconnect.external.saramin.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CompanyHtmlResponse {

  private String homepage;

  private String address;
}
