package org.prgrms.devconnect.external.saramin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class JobXmlResponse {

  @JacksonXmlProperty(localName = "id")
  private long id;

  @JacksonXmlProperty(localName = "url")
  private String url;

  @JacksonXmlProperty(localName = "posting-timestamp")
  private long postingTimestamp;

  @JacksonXmlProperty(localName = "opening-timestamp")
  private long openingTimestamp;

  @JacksonXmlProperty(localName = "expiration-timestamp")
  private long expirationTimestamp;

  @JacksonXmlProperty(localName = "company")
  private CompanyXmlResponse company;

  @JacksonXmlProperty(localName = "position")
  private PositionXmlResponse position;

  @JacksonXmlProperty(localName = "salary")
  private String salary;
}
