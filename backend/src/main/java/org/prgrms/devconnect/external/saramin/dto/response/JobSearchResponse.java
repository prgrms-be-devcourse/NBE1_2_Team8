package org.prgrms.devconnect.external.saramin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobSearchResponse {

  @JacksonXmlProperty(localName = "jobs")
  private JobsXmlResponse jobs;
}
