package org.prgrms.devconnect.external.saramin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class CompanyXmlResponse {

  @JacksonXmlProperty(isAttribute = true, localName = "name href")
  private String href;  // href 속성

  @JacksonXmlProperty(isAttribute = true, localName = "name")
  private String name;  // CDATA로 감싸진 회사 이름
}
