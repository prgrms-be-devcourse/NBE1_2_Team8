package org.prgrms.devconnect.external.saramin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class PositionXmlResponse {

  @JacksonXmlProperty(localName = "title")
  private String title;

  @JacksonXmlProperty(localName = "location")
  private String location;

  @JacksonXmlProperty(localName = "job-type")
  private String jobType;

  @JacksonXmlProperty(localName = "job-code")
  private String jobCode;

  @JacksonXmlProperty(localName = "experience-level")
  private String experienceLevel;

  @JacksonXmlProperty(localName = "required-education-level")
  private String requiredEducation;
}