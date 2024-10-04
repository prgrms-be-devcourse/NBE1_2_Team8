package org.prgrms.devconnect.external.saramin;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.prgrms.devconnect.api.service.jobpost.JobPostQueryService;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPostTechStackMapping;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.JobType;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.Status;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.external.saramin.dto.response.CompanyHtmlResponse;
import org.prgrms.devconnect.external.saramin.dto.response.JobSearchResponse;
import org.prgrms.devconnect.external.saramin.dto.response.JobXmlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * 사람인 채용공고 API
 */
@Component
@RequiredArgsConstructor
public class JobPostApi {

  private final JobPostQueryService jobPostQueryService;

  @Value("${api.key}")
  private String accessKey;

  // total 값을 반환하는 메서드
  @Getter
  private int total = 0; // total 값을 저장할 변수

  public List<JobPost> fetchJobPosts(int start) {
    List<JobPost> jobPosts = new ArrayList<>();

    try {
      // URL 인코딩
      String keyword = "";  // 키워드별로 저장할때
      String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
      String apiURL = "https://oapi.saramin.co.kr/job-search?access-key=" + accessKey
              + "&job_cd=2232+84+92+291+277+213+235+236+302+272+87+195"  // 사람인에서 간추린 job code
              + "&job_mid_cd=2"
              // + "&keyword=" + encodedKeyword
              + "&start=" + start
              + "&count=100";  // 100개씩 가져옴

      StringBuilder response = xmlParser(apiURL);

      // XmlMapper로 전체 api XML 데이터를 파싱
      XmlMapper xmlMapper = new XmlMapper();
      JobSearchResponse jobSearchResponse = xmlMapper.readValue(response.toString(), JobSearchResponse.class);


      // 파싱된 데이터를 JobPost 엔티티로 변환
      for (JobXmlResponse job : jobSearchResponse.getJobs().getJobList()) {

        Long postId = job.getId();

        // JobPostQueryService를 사용하여 중복 여부 확인
        if (!jobPostQueryService.isJobPostByPostId(postId)) {

          String companyPageLink = extractCompanyInfoFromXml(response.toString(), postId);

          // 회사 정보 파싱 메서드 호출
          CompanyHtmlResponse companyInfo = extractCompanyInfoFromHtml(companyPageLink);

          // Jpa 엔티티 변환
          JobPost jobPost = JobPost.builder()
                  .postId(postId)
                  .jobPostName(job.getPosition().getTitle())
                  .jobPostLink(job.getUrl())
                  .companyName(job.getCompany().getName())
                  .companyLink(companyInfo.getHomepage())
                  .companyAddress(companyInfo.getAddress())
                  .postDate(convertLocalDateTime(job.getPostingTimestamp()))
                  .openDate(convertLocalDateTime(job.getOpeningTimestamp()))
                  .endDate(convertLocalDateTime(job.getExpirationTimestamp()))
                  .experienceLevel(job.getPosition().getExperienceLevel())
                  .requiredEducation(job.getPosition().getRequiredEducation())
                  .salary(job.getSalary())
                  .jobType(convertJobType(job.getPosition().getJobType()))
                  .status(Status.RECRUITING)
                  .likes(0)
                  .views(0)
                  .build();

          // job-code를 기반으로 TechStack 리스트를 생성하여 매핑
          List<TechStack> techStacks = saveTechStackByJobCode(job.getPosition().getJobCode());

          for (TechStack techStack : techStacks) {
            JobPostTechStackMapping mapping = JobPostTechStackMapping.builder()
                    .jobPost(jobPost)
                    .techStack(techStack)
                    .build();

            jobPost.addTechStackMapping(mapping);  // 매핑 추가
          }

          // jobPost 리스트에 추가
          jobPosts.add(jobPost);
        }
      }

      return jobPosts;

    } catch (Exception e) {
      System.out.println(e);
      return jobPosts;
    }
  }


  public int getTotal(int start) {

    try {
      // URL 인코딩
      String keyword = "";  // 키워드별로 저장할때
      String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
      String apiURL = "https://oapi.saramin.co.kr/job-search?access-key=" + accessKey
              + "&job_cd=2232+84+92+291+277+213+235+236+302+272+87+195"  // 사람인에서 간추린 job code
              + "&job_mid_cd=2"
              // + "&keyword=" + encodedKeyword
              + "&start=" + start
              + "&count=1";  // 100개씩 가져옴

      StringBuilder response = xmlParser(apiURL);

      // XmlMapper로 전체 api XML 데이터를 파싱
      XmlMapper xmlMapper = new XmlMapper();
      JobSearchResponse jobSearchResponse = xmlMapper.readValue(response.toString(), JobSearchResponse.class);


      this.total = jobSearchResponse.getJobs().getTotal(); // total 값 저장

      return total;

    } catch (Exception e) {
      System.out.println(e);
      return total;
    }
  }

  private StringBuilder xmlParser(String apiURL) throws IOException {

    // HTTP 연결 설정
    URL url = new URL(apiURL);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Accept", "application/xml");

    int responseCode = con.getResponseCode();
    BufferedReader br;

    // 응답 데이터 처리
    if (responseCode == 200) { // 정상 호출
      br = new BufferedReader(new InputStreamReader(con.getInputStream()));
    } else {  // 에러 발생
      br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
    }

    StringBuilder response = new StringBuilder();
    String inputLine;
    while ((inputLine = br.readLine()) != null) {
      response.append(inputLine);
    }
    br.close();

    return response;
  }

  // 회사 정보 (홈페이지 링크 및 주소) 추출 메서드
  private CompanyHtmlResponse extractCompanyInfoFromHtml(String companyPageLink) {
    String homepage = null;
    String address = null;

    try {
      if (companyPageLink != null) {
        // HTML 페이지를 파싱
        Document companyPage = Jsoup.connect(companyPageLink)
                .timeout(10000)
                .followRedirects(true)  // 리다이렉션 허용
                .get();

        // 홈페이지 링크 추출
        Element homepageElement = companyPage.selectFirst("dd.desc a[target=_blank]");
        if (homepageElement != null) {
          homepage = homepageElement.attr("href");
        }

        // 회사 주소 정보 추출 (주소가 있는 경우만 처리)
        Element addressElement = companyPage.selectFirst("div.company_details_group dt.tit:contains(주소) + dd.desc p.ellipsis");
        if (addressElement != null) {
          address = addressElement.attr("title").trim();  // title 속성에서 주소 추출
        }
      }
    } catch (Exception e) {

    }

    return CompanyHtmlResponse.builder()
            .homepage(homepage)
            .address(address)
            .build();
  }


  // 사람인 API에서 회사 정보를 파싱하는 메서드 (name 태그에서 href와 회사 이름을 추출)
  private String extractCompanyInfoFromXml(String xmlResponse, Long postId) {
    Document document = Jsoup.parse(xmlResponse, "", org.jsoup.parser.Parser.xmlParser());

    // postId에 맞는 job 태그를 찾아 href 추출
    Element jobElement = document.selectFirst("job:has(id:contains(" + postId + "))");
    if (jobElement != null) {
      Element nameElement = jobElement.selectFirst("company > name");
      if (nameElement != null) {
        return nameElement.attr("href");  // href 속성 추출
      }
    }
    return null;
  }


  // 사람인 api는 UnixTimeStamp로 숫자로 저장하기 때문에 LocalDateTime으로 변경
  private LocalDateTime convertLocalDateTime(long timestamp) {
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
  }

  // jobType 변환 메서드 정의 -> 1=정규직 4=인턴 나머지=비정규직
  private JobType convertJobType(String jobTypeCode) {

    String[] jobTypeArray = jobTypeCode.split(",");

    // 첫 번째 값만 추출해서 처리
    String jobType = jobTypeArray[0].trim();  // 앞뒤 공백 제거

    if ("정규직".equals(jobType)) {
      return JobType.REGULAR;
    } else if ("인턴".equals(jobType)) {
      return JobType.CONTRACT;
    } else {
      return JobType.IRREGULAR;
    }
  }

  // 사람인 api는 기술스택을 문자열 숫자로 저장하기 때문에 이를 분리해서 저장하는 로직
  public List<TechStack> saveTechStackByJobCode(String jobCode) {
    List<TechStack> techStacks = new ArrayList<>();

    // jobCode가 null인지 체크
    if (jobCode == null || jobCode.trim().isEmpty()) {
      return techStacks; // jobCode가 null이면 빈 리스트 반환
    }

    // 쉼표(,)로 구분된 jobCode 문자열을 분리
    String[] jobCodes = jobCode.split(",");

    // 각 jobCode에 대해 TechStack 생성
    for (String code : jobCodes) {
      TechStack techStack = switch (code.trim()) {  // 공백 제거 후 처리
        case "웹개발" -> TechStack.builder()
                .name("웹개발")
                .code("87")
                .build();
        case "풀스택" -> TechStack.builder()
                .name("풀스택")
                .code("2232")  // -> 이부분을 변경시 DevConnect 고유 기술코드로 변경가능
                .build();
        case "백엔드/서버개발" -> TechStack.builder()
                .name("백엔드")
                .code("84")
                .build();
        case "프론트엔드" -> TechStack.builder()
                .name("프론트엔드")
                .code("92")
                .build();
        case "Android" -> TechStack.builder()
                .name("Android")
                .code("195")
                .build();
        case "Spring" -> TechStack.builder()
                .name("Spring")
                .code("291")
                .build();
        case "JPA" -> TechStack.builder()
                .name("JPA")
                .code("238")
                .build();
        case "React" -> TechStack.builder()
                .name("React")
                .code("277")
                .build();
        case "Django" -> TechStack.builder()
                .name("Django")
                .code("213")
                .build();
        case "Java" -> TechStack.builder()
                .name("Java")
                .code("235")
                .build();
        case "Javascript" -> TechStack.builder()
                .name("Javascript")
                .code("236")
                .build();
        case "TypeScript" -> TechStack.builder()
                .name("TypeScript")
                .code("302")
                .build();
        case "Python" -> TechStack.builder()
                .name("Python")
                .code("272")
                .build();
        case "C++" -> TechStack.builder()
                .name("C++")
                .code("205")
                .build();
        case "Kotlin" -> TechStack.builder()
                .name("kotlin")
                .code("243")
                .build();
        case "AWS" -> TechStack.builder()
                .name("AWS")
                .code("201")
                .build();
        case "Linux" -> TechStack.builder()
                .name("Linux")
                .code("246")
                .build();
        default -> null;
      };

      // techStack이 null이 아닐 때만 리스트에 추가
      if (techStack != null) {
        techStacks.add(techStack);
      }
    }

    return techStacks;
  }
}