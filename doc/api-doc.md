---
title: DevConnect
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.23"

---

# DevConnect API Doc

Base URLs: http://localhost:8080/

## 목차
- [멤버 API](#멤버-api)
- [댓글 API](#댓글-api)
- [채팅 API](#채팅-api)
- [게시판 API](#게시판-api)
- [버그리포트 API](#버그리포트-api)
- [알림 API](#알림-api)
- [기술 스택 API](#기술-스택-api)
- [채용 공고 API](#채용-공고-api)
- [관심게시물 API](#관심게시물-api)
- [DTO](#data-schema)



# 멤버 API

<a id="opIdgetMember"></a>

## GET 회원 정보 조회

GET /api/v1/members

인증된 사용자의 회원 정보를 조회합니다.

> Response Examples

> 200 Response

```json
{
  "member_id": 1,
  "email": "user@example.com",
  "nickname": "DevUser",
  "job": "Software Engineer",
  "affiliation": "Company Name",
  "career": 5,
  "self_introduction": "안녕하세요! 저는 소프트웨어 엔지니어입니다.",
  "github_link": "https://github.com/user",
  "blog_link": "https://userblog.com",
  "interest": "TEAM_PROJECT",
  "tech_stacks": [
    {
      "tech_stack_id": 1,
      "name": "Java",
      "code": "200"
    }
  ]
}
```

> 401 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|회원 정보 조회 성공|[MemberResponseDto](#schemamemberresponsedto)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|인증 실패|[MemberResponseDto](#schemamemberresponsedto)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|[MemberResponseDto](#schemamemberresponsedto)|

<a id="opIdupdateMember"></a>

## PUT 회원 정보 수정

PUT /api/v1/members

현재 회원의 정보를 수정합니다.

> Body Parameters

```json
{
  "nickname": "김철수",
  "job": "백엔드 엔지니어",
  "affiliation": "Grep",
  "career": 5,
  "self_introduction": "자기 소개 예시 ....",
  "github_link": "https://github.com/",
  "blog_link": "https://blog.johndoe.com",
  "interest": "TEAM_PROJECT",
  "add_tech_stacks": [
    1,
    2,
    3
  ],
  "delete_tech_stacks": [
    4,
    5
  ]
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[MemberUpdateRequestDto](#schemamemberupdaterequestdto)| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|회원 정보 수정 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIdcreateMember"></a>

## POST 회원 가입

POST /api/v1/members/signup

새로운 사용자를 등록합니다.

> Body Parameters

```json
{
  "email": "user@example.com",
  "password": "password123!",
  "nickname": "DevUser",
  "job": "Software Engineer",
  "affiliation": "Company Name",
  "career": 5,
  "self_introduction": "안녕하세요! 저는 소프트웨어 엔지니어입니다.",
  "github_link": "https://github.com/user",
  "blog_link": "https://userblog.com",
  "interest": "TEAM_PROJECT",
  "tech_stack_ids": [
    1,
    2,
    3
  ]
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[MemberCreateRequestDto](#schemamembercreaterequestdto)| no |none|

> Response Examples

> 201 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|회원 가입 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIdlogout"></a>

## POST 로그아웃

POST /api/v1/members/logout

현재 로그인된 사용자를 로그아웃 처리.

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|로그아웃 성공|Inline|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|인증 실패|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIdlogin"></a>

## POST 로그인

POST /api/v1/members/login

사용자 로그인 처리.

> Body Parameters

```json
{
  "email": "user@example.com",
  "password": "password123!"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[MemberLoginRequestDto](#schemamemberloginrequestdto)| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|로그인 성공|Inline|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|인증 실패|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIdreissueAccessToken"></a>

## GET Access Token 재발급

GET /api/v1/members/reissue

Refresh Token을 사용하여 새로운 Access Token을 발급받습니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization-refresh|cookie|string| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Access Token 재발급 성공|Inline|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|인증 실패|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

# 댓글 API

<a id="opIdupdateComment"></a>

## PUT 댓글 수정

PUT /api/v1/comments/{commentId}

댓글 ID를 기반으로 댓글을 수정합니다.

> Body Parameters

```json
{
  "content": "이 댓글을 수정합니다."
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|commentId|path|string| yes |none|
|commentId|query|string| yes |댓글 ID|
|body|body|[CommentUpdateRequestDto](#schemacommentupdaterequestdto)| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|댓글 수정 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIddeleteComment"></a>

## DELETE 댓글 삭제

DELETE /api/v1/comments/{commentId}

특정 댓글을 삭제합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|commentId|path|string| yes |none|
|commentId|query|string| yes |댓글 ID|

> Response Examples

> 204 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|댓글 삭제 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIdcreateComment"></a>

## POST 댓글 생성

POST /api/v1/comments

새로운 댓글을 작성합니다.

> Body Parameters

```json
{
  "board_id": 1,
  "parent_id": 2,
  "content": "이 게시물 정말 유익하네요!"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CommentCreateRequestDto](#schemacommentcreaterequestdto)| no |none|

> Response Examples

> 201 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|댓글 생성 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIdgetComments"></a>

## GET 게시물 댓글 조회

GET /api/v1/comments/{boardId}

특정 게시물에 대한 모든 댓글을 페이징하여 조회합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|boardId|path|string| yes |none|
|boardId|query|string| yes |게시물 ID|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|sort|query|array[string]| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|댓글 조회 성공|[PageCommentResponseDto](#schemapagecommentresponsedto)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|[PageCommentResponseDto](#schemapagecommentresponsedto)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|[PageCommentResponseDto](#schemapagecommentresponsedto)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|[PageCommentResponseDto](#schemapagecommentresponsedto)|

# 채팅 API

<a id="opIdcloseChattingRoom"></a>

## PUT 채팅방 비활성화

PUT /api/v1/chat/{chatroomId}

특정 채팅방을 종료

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|chatroomId|path|integer(int64)| yes |채팅방 ID|

> Response Examples

> 204 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|채팅방 종료 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIdcreateChatting_1"></a>

## GET 활성화된 채팅 방 조회

GET /api/v1/chat/member/{memberId}

멤버의 모든 활성화된 채팅 방을 조회

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|memberId|path|integer(int64)| yes |멤버 ID|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|채팅방 조회 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[ChatRoomListResponse](#schemachatroomlistresponse)]|false|none||none|
|» member_id|integer(int64)|false|none||멤버 ID|
|» chatpart_id|integer(int64)|false|none||채팅 참여자 ID|
|» room_id|integer(int64)|false|none||채팅 방 ID|
|» status|string|false|none||채팅 방 상태|

#### Enum

|Name|Value|
|---|---|
|status|ACTIVE|
|status|INACTIVE|

HTTP Status Code **400**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[ChatRoomListResponse](#schemachatroomlistresponse)]|false|none||none|
|» member_id|integer(int64)|false|none||멤버 ID|
|» chatpart_id|integer(int64)|false|none||채팅 참여자 ID|
|» room_id|integer(int64)|false|none||채팅 방 ID|
|» status|string|false|none||채팅 방 상태|

#### Enum

|Name|Value|
|---|---|
|status|ACTIVE|
|status|INACTIVE|

HTTP Status Code **404**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[ChatRoomListResponse](#schemachatroomlistresponse)]|false|none||none|
|» member_id|integer(int64)|false|none||멤버 ID|
|» chatpart_id|integer(int64)|false|none||채팅 참여자 ID|
|» room_id|integer(int64)|false|none||채팅 방 ID|
|» status|string|false|none||채팅 방 상태|

#### Enum

|Name|Value|
|---|---|
|status|ACTIVE|
|status|INACTIVE|

HTTP Status Code **500**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[ChatRoomListResponse](#schemachatroomlistresponse)]|false|none||none|
|» member_id|integer(int64)|false|none||멤버 ID|
|» chatpart_id|integer(int64)|false|none||채팅 참여자 ID|
|» room_id|integer(int64)|false|none||채팅 방 ID|
|» status|string|false|none||채팅 방 상태|

#### Enum

|Name|Value|
|---|---|
|status|ACTIVE|
|status|INACTIVE|

<a id="opIdcreateChatting"></a>

## POST 새로운 채팅방 생성

POST /api/v1/chat/member/{memberId}

특정 사용자와의 새로운 채팅방 생성

> Body Parameters

```json
{
  "receiver_id": 2
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|memberId|path|integer(int64)| yes |멤버 ID|
|body|body|[ChatRoomRequest](#schemachatroomrequest)| no |none|

> Response Examples

> 201 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|채팅방 생성 성공|[ChatPartResponse](#schemachatpartresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|[ChatPartResponse](#schemachatpartresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|[ChatPartResponse](#schemachatpartresponse)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|[ChatPartResponse](#schemachatpartresponse)|

<a id="opIdgetMessages"></a>

## GET 채팅 메세지 조회

GET /api/v1/chat/rooms/{roomId}/messages

특정 채팅방의 메세지를 페이징하여 조회

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|roomId|path|integer(int64)| yes |채팅방 ID|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|sort|query|array[string]| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|채팅 메세지 조회 성공|[MessageFullResponse](#schemamessagefullresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|[MessageFullResponse](#schemamessagefullresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|[MessageFullResponse](#schemamessagefullresponse)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|[MessageFullResponse](#schemamessagefullresponse)|

# 버그리포트 API

<a id="opIdgetBugReportById"></a>

## GET 특정 버그 리포트 조회

GET /api/v1/bug-report/{reportId}

버그 리포트 ID로 버그 리포트의 상세 정보를 조회합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|reportId|path|integer(int64)| yes |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|버그 리포트 상세 정보를 성공적으로 반환합니다.|[BugReport](#schemabugreport)|

<a id="opIdupdateBugReport"></a>

## PUT 버그 리포트 수정

PUT /api/v1/bug-report/{reportId}

특정 버그 리포트를 수정합니다.

> Body Parameters

```json
{
  "url": "http://example.com",
  "content": "어떤 기능에서 문제가 발생했습니다.",
  "bug_type": "EXPIRED"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|reportId|path|integer(int64)| yes |수정할 버그 리포트의 ID|
|body|body|[BugReportUpdateRequest](#schemabugreportupdaterequest)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|버그 리포트가 성공적으로 수정되었습니다.|[BugReportResponse](#schemabugreportresponse)|

<a id="opIddeleteBugReport"></a>

## DELETE 버그 리포트 삭제

DELETE /api/v1/bug-report/{reportId}

특정 버그 리포트를 삭제합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|reportId|path|integer(int64)| yes |none|

> Response Examples

> 204 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|버그 리포트가 성공적으로 삭제되었습니다.|Inline|

### Responses Data Schema

<a id="opIdgetAllBugReports"></a>

## GET 모든 버그 리포트 조회

GET /api/v1/bug-report

모든 버그 리포트를 조회합니다.

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|버그 리포트 목록을 성공적으로 반환합니다.|[BugReportResponse](#schemabugreportresponse)|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|버그 리포트가 없습니다.|Inline|

### Responses Data Schema

HTTP Status Code **204**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BugReportResponse](#schemabugreportresponse)]|false|none||[버그 리포트 응답 정보]|
|» bug_report_id|integer(int64)|false|none||버그 리포트 ID|
|» member_id|integer(int64)|false|none||사용자 ID|
|» related_url|string|false|none||관련 URL|
|» bug_type|string|false|none||버그 타입|
|» content|string|false|none||버그 내용|
|» created_at|string(date-time)|false|none||생성 시간|
|» updated_at|string(date-time)|false|none||수정 시간|

#### Enum

|Name|Value|
|---|---|
|bug_type|NOT_FOUND|
|bug_type|EXPIRED|
|bug_type|OTHER|

<a id="opIdcreateBugReport"></a>

## POST 버그 리포트 생성

POST /api/v1/bug-report

새로운 버그 리포트를 생성합니다.

> Body Parameters

```json
{
  "url": "http://example.com",
  "content": "어떤 기능에서 문제가 발생했습니다.",
  "bug_type": "EXPIRED"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[BugReportRequest](#schemabugreportrequest)| no |none|

> Response Examples

> 201 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|버그 리포트가 성공적으로 생성되었습니다.|[BugReport](#schemabugreport)|

<a id="opIdgetAllBugReportsByMemberId"></a>

## GET 특정 사용자의 모든 버그 리포트 조회

GET /api/v1/bug-report/member

특정 사용자의 모든 버그 리포트를 조회합니다.

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|해당 사용자의 버그 리포트 목록을 성공적으로 반환합니다.|[BugReportResponse](#schemabugreportresponse)|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|해당 사용자의 버그 리포트가 없습니다.|Inline|

### Responses Data Schema

HTTP Status Code **204**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BugReportResponse](#schemabugreportresponse)]|false|none||[버그 리포트 응답 정보]|
|» bug_report_id|integer(int64)|false|none||버그 리포트 ID|
|» member_id|integer(int64)|false|none||사용자 ID|
|» related_url|string|false|none||관련 URL|
|» bug_type|string|false|none||버그 타입|
|» content|string|false|none||버그 내용|
|» created_at|string(date-time)|false|none||생성 시간|
|» updated_at|string(date-time)|false|none||수정 시간|

#### Enum

|Name|Value|
|---|---|
|bug_type|NOT_FOUND|
|bug_type|EXPIRED|
|bug_type|OTHER|

# 게시판 API

<a id="opIdgetBoardById"></a>

## GET 단일 게시물 조회

GET /api/v1/boards/{boardId}

특정 게시물의 조회수를 증가시키고 게시물 정보를 반환합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|boardId|path|integer(int64)| yes |게시물 ID|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|게시물 조회 성공|[BoardResponseDto](#schemaboardresponsedto)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|[BoardResponseDto](#schemaboardresponsedto)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|[BoardResponseDto](#schemaboardresponsedto)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|[BoardResponseDto](#schemaboardresponsedto)|

<a id="opIdupdateBoard"></a>

## PUT 게시물 수정

PUT /api/v1/boards/{boardId}

게시물 ID를 기반으로 게시물을 수정합니다.

> Body Parameters

```json
{
  "title": "프로젝트 참가자 모집",
  "content": "함께 프로젝트를 진행할 참가자를 모집합니다.",
  "category": "STUDY",
  "recruit_num": 5,
  "progress_way": "ONLINE",
  "progress_period": "3개월",
  "end_date": "2019-08-24T14:15:22Z",
  "add_tech_stacks": [
    1,
    2,
    3
  ],
  "delete_tech_stacks": [
    4,
    5
  ]
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|boardId|path|integer(int64)| yes |none|
|body|body|[BoardUpdateRequestDto](#schemaboardupdaterequestdto)| no |none|

> Response Examples

> 400 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIddeleteBoard"></a>

## DELETE 게시물 삭제

DELETE /api/v1/boards/{boardId}

특정 게시물을 삭제합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|boardId|path|integer(int64)| yes |게시물 ID|

> Response Examples

> 204 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|게시물 삭제 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIdcloseBoard"></a>

## PUT 게시물을 마감

PUT /api/v1/boards/{boardId}/close

특정 게시물을 수동으로 마감합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|boardId|path|integer(int64)| yes |게시물 ID|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|게시물 마감 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|이미 마감된 게시물입니다.|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|존재하지 않는 게시물 또는 삭제된 게시물입니다.|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIdgetAllBoards"></a>

## GET 전체 게시물 조회

GET /api/v1/boards

모든 게시물을 페이징하여 조회합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|sort|query|array[string]| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|게시물 조회 성공|[PageBoardResponseDto](#schemapageboardresponsedto)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|[PageBoardResponseDto](#schemapageboardresponsedto)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|[PageBoardResponseDto](#schemapageboardresponsedto)|

<a id="opIdcreateBoard"></a>

## POST 게시물 생성

POST /api/v1/boards

새로운 게시물을 생성합니다.

> Body Parameters

```json
{
  "job_post_id": 1,
  "title": "Spring Boot 개발자 모집",
  "content": "우리는 Spring Boot 벡엔드 개발자를 찾고 있습니다.",
  "category": "STUDY",
  "recruit_num": 3,
  "progress_way": "ONLINE",
  "progress_period": "3개월",
  "end_date": "2019-08-24T14:15:22Z",
  "tech_stack_requests": [
    {
      "tech_stack_id": 1
    }
  ]
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[BoardCreateRequestDto](#schemaboardcreaterequestdto)| no |none|

> Response Examples

> 201 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|게시물 생성 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

<a id="opIdgetPopularBoardsThisWeek"></a>

## GET 이번 주 인기 게시물 조회

GET /api/v1/boards/popular

이번 주 조회수가 높은 10개의 게시물을 조회합니다.

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|게시물 조회 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **400**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **500**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

<a id="opIdgetBoardsWithPopularTagCondition"></a>

## GET 인기 태그 게시물 조회

GET /api/v1/boards/popular-tag

이번 주에 작성되고 조회수가 500 이상인 게시물을 조회합니다.

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|인기 태그 게시물 조회 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **400**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **500**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

<a id="opIdgetBoardsByJobPostId"></a>

## GET 특정 구직 공고와 연관된 게시물 조회

GET /api/v1/boards/jobpost/{jobPostId}

특정 구직 공고와 연관된 게시물들을 조회합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|jobPostId|path|integer(int64)| yes |공고 ID|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|게시물 조회 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **400**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **404**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **500**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

<a id="opIdgetBoardsByMemberInterests"></a>

## GET 사용자 관심사 기반 게시물 조회

GET /api/v1/boards/interests

특정 사용자의 관심사를 기반으로 추천 게시물을 조회합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|memberId|query|string| yes |멤버 ID|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|게시물 조회 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|엔티티 NOT FOUND|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **400**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **404**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **500**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

<a id="opIdgetBoardsByFilter"></a>

## GET 필터로 게시물 조회

GET /api/v1/boards/filter

카테고리, 상태, 기술 스택 등의 조건으로 게시물을 필터링하여 조회합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|category|query|string| no |게시물 카테고리|
|status|query|string| no |게시물 상태|
|techStackIds|query|string| no |기술 스택 ID 목록|
|progressWay|query|string| no |진행 방식|
|arg0|query|string| no |none|
|arg1|query|string| no |none|
|arg2|query|array[integer]| no |none|
|arg3|query|string| no |none|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|sort|query|array[string]| no |none|

#### Enum

|Name|Value|
|---|---|
|arg0|STUDY|
|arg0|TEAM_PROJECT|
|arg1|RECRUITING|
|arg1|CLOSED|
|arg1|DELETED|
|arg3|ONLINE|
|arg3|OFFLINE|
|arg3|HYBRID|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|게시물 조회 성공|[PageBoardResponseDto](#schemapageboardresponsedto)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|[PageBoardResponseDto](#schemapageboardresponsedto)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|[PageBoardResponseDto](#schemapageboardresponsedto)|

<a id="opIdgetBoardsWithDeadlineApproaching"></a>

## GET 마감 임박 게시물 조회

GET /api/v1/boards/deadline-approaching

마감 2일 전 게시물을 조회합니다.

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|마감 임박 게시물 조회 성공|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|잘못된 요청|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **400**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

HTTP Status Code **500**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|» boardId|integer(int64)|false|none||게시물 ID|
|» authorId|integer(int64)|false|none||작성자 ID|
|» author|string|false|none||작성자 닉네임|
|» title|string|false|none||게시물 제목|
|» content|string|false|none||게시물 내용|
|» category|string|false|none||게시물 카테고리|
|» recruitNum|integer(int32)|false|none||모집 인원|
|» progressWay|string|false|none||진행 방식|
|» progressPeriod|string|false|none||진행 기간|
|» endDate|string(date-time)|false|none||게시물 종료 날짜|
|» likes|integer(int32)|false|none||좋아요 수|
|» views|integer(int32)|false|none||조회수|
|» createdDate|string(date-time)|false|none||생성일자|
|» updatedDate|string(date-time)|false|none||수정일자|
|» status|string|false|none||게시물 상태|
|» techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|
|»» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|»» name|string|false|none||기술 스택 이름|
|»» code|string|false|none||기술 스택 코드|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

# 관심게시물 API

<a id="opIdaddInterestJob"></a>

## POST 관심 채용공고으로 등록

POST /api/v1/interests/job-posts

관심 채용공고으로 등록합니다.

> Body Parameters

```json
{
  "job_post_id": 1
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[InterestJobPostRequestDto](#schemainterestjobpostrequestdto)| no |none|

> Response Examples

> 201 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|관심 채용공고 등록에 성공했습니다.|Inline|

### Responses Data Schema

<a id="opIdaddInterestBoard"></a>

## POST 관심 게시물로 등록

POST /api/v1/interests/boards

관심 게시물로 등록합니다.

> Body Parameters

```json
{
  "board_id": 1
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[InterestBoardRequestDto](#schemainterestboardrequestdto)| no |none|

> Response Examples

> 201 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|관심 게시물 등록에 성공하였습니다.|Inline|

### Responses Data Schema

<a id="opIdgetInterestBoards"></a>

## GET 전체 관심 게시물 조회

GET /api/v1/interests

관심 게시물로 등록된 모든 게시물을 조회합니다.

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|관심 게시물이 성공적으로 조회되었습니다.|[InterestResponseDto](#schemainterestresponsedto)|

<a id="opIdremoveInterestJobPost"></a>

## DELETE 관심 채용공고에서 해제

DELETE /api/v1/interests/job-posts/{jobPostId}

관심 채용공고에서 해제합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|jobPostId|path|string| yes |none|

> Response Examples

> 204 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|관심 채용공고에서 성공적으로 삭제되었습니다.|Inline|

### Responses Data Schema

<a id="opIdremoveInterestBoard"></a>

## DELETE 관심 게시물에서 해제

DELETE /api/v1/interests/boards/{boardId}

관심 게시물에서 해제합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|boardId|path|integer(int64)| yes |none|

> Response Examples

> 204 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|관심 게시물에서 성공적으로 삭제되었습니다.|Inline|

### Responses Data Schema

# 채용 공고 API

<a id="opIdJobPostLikes"></a>

## PATCH 채용 공고 좋아요

PATCH /api/v1/job-posts/{jobPostId}/likes

특정 채용 공고에 좋아요를 추가합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|jobPostId|path|string| yes |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|채용 공고에 좋아요가 성공적으로 추가되었습니다.|Inline|

### Responses Data Schema

<a id="opIdgetAllJobPosts"></a>

## GET 모든 채용 공고 조회

GET /api/v1/job-posts

모든 채용 공고를 페이징 처리하여 조회합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|sort|query|array[string]| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|채용 공고 목록을 성공적으로 반환합니다.|[PageJobPostInfoResponseDto](#schemapagejobpostinforesponsedto)|

<a id="opIdgetJobPost"></a>

## GET 특정 채용 공고 조회

GET /api/v1/job-posts/{jobPostId}

특정 ID에 해당하는 채용 공고의 상세 정보를 조회합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|jobPostId|path|integer(int64)| yes |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|채용 공고 상세 정보를 성공적으로 반환합니다.|[JobPostInfoResponseDto](#schemajobpostinforesponsedto)|

<a id="opIddeleteJobPost"></a>

## DELETE 채용 공고 삭제

DELETE /api/v1/job-posts/{jobPostId}

특정 채용 공고를 삭제합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|jobPostId|path|string| yes |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|채용 공고가 성공적으로 삭제되었습니다.|Inline|

### Responses Data Schema

<a id="opIdgetJobPostsByTechStackName"></a>

## GET getJobPostsByTechStackName

GET /api/v1/job-posts/techstack-name/{name}

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|name|path|string| yes |none|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|sort|query|array[string]| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PageJobPostInfoResponseDto](#schemapagejobpostinforesponsedto)|

<a id="opIdgetJobPostsByTechStackCode"></a>

## GET getJobPostsByTechStackCode

GET /api/v1/job-posts/techstack-code/{code}

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|code|path|string| yes |none|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|sort|query|array[string]| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PageJobPostInfoResponseDto](#schemapagejobpostinforesponsedto)|

<a id="opIdgetJobPostsByJobPostNameContaining"></a>

## GET getJobPostsByJobPostNameContaining

GET /api/v1/job-posts/search

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|arg0|query|string| yes |none|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|sort|query|array[string]| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PageJobPostInfoResponseDto](#schemapagejobpostinforesponsedto)|

# 기술 스택 API

<a id="opIdgetTechStacks"></a>

## GET 기술 스택 목록 조회

GET /api/v1/tech-stacks

모든 기술 스택 목록을 조회합니다.

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|기술 스택 목록 조회 성공|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|서버 오류|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||[직무 기술 스택 목록]|
|» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|» name|string|false|none||기술 스택 이름|
|» code|string|false|none||기술 스택 코드|

HTTP Status Code **500**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||[직무 기술 스택 목록]|
|» tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|» name|string|false|none||기술 스택 이름|
|» code|string|false|none||기술 스택 코드|

# 알림 API

<a id="opIdgetAlarms"></a>

## GET 알림 전체 조회

GET /api/v1/alarms

사용자 아이디별 수신된 모든 알림을 반환합니다.

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|수신된 모든 알림을 성공적으로 반환합니다.|[AlarmsGetResponse](#schemaalarmsgetresponse)|

<a id="opIddeleteAlarmsByMemberId"></a>

## DELETE 알림 전체 삭제

DELETE /api/v1/alarms

사용자 아이디별 수신된 모든 알림을 삭제합니다.

> Response Examples

> 204 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|수신된 전체 알림 목록을 성공적으로 삭제했습니다.|Inline|

### Responses Data Schema

<a id="opIdgetUnReadAlarmsCount"></a>

## GET 읽지 않은 알림 수 조회

GET /api/v1/alarms/counts

읽지 않은 알림의 수를 조회합니다.

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|읽지 않은 알림의 수를 성공적으로 조회했습니다.|integer|

<a id="opIddeleteAlarm"></a>

## DELETE 알림 단일 삭제

DELETE /api/v1/alarms/{alarmId}

특정 알림 id를 가진 알림을 삭제합니다.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|alarmId|path|string| yes |none|

> Response Examples

> 204 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|특정 알림 id를 가진 알림을 성공적으로 삭제했습니다.|Inline|

### Responses Data Schema

# Data Schema

<h2 id="tocS_Pet">Pet</h2>

<a id="schemapet"></a>
<a id="schema_Pet"></a>
<a id="tocSpet"></a>
<a id="tocspet"></a>

```json
{
  "id": 1,
  "category": {
    "id": 1,
    "name": "string"
  },
  "name": "doggie",
  "photoUrls": [
    "string"
  ],
  "tags": [
    {
      "id": 1,
      "name": "string"
    }
  ],
  "status": "available"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|true|none||Pet ID|
|category|[Category](#schemacategory)|true|none||group|
|name|string|true|none||name|
|photoUrls|[string]|true|none||image URL|
|tags|[[Tag](#schematag)]|true|none||tag|
|status|string|true|none||Pet Sales Status|

#### Enum

|Name|Value|
|---|---|
|status|available|
|status|pending|
|status|sold|

<h2 id="tocS_Category">Category</h2>

<a id="schemacategory"></a>
<a id="schema_Category"></a>
<a id="tocScategory"></a>
<a id="tocscategory"></a>

```json
{
  "id": 1,
  "name": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||Category ID|
|name|string|false|none||Category Name|

<h2 id="tocS_Tag">Tag</h2>

<a id="schematag"></a>
<a id="schema_Tag"></a>
<a id="tocStag"></a>
<a id="tocstag"></a>

```json
{
  "id": 1,
  "name": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||Tag ID|
|name|string|false|none||Tag Name|

<h2 id="tocS_MemberUpdateRequestDto">MemberUpdateRequestDto</h2>

<a id="schemamemberupdaterequestdto"></a>
<a id="schema_MemberUpdateRequestDto"></a>
<a id="tocSmemberupdaterequestdto"></a>
<a id="tocsmemberupdaterequestdto"></a>

```json
{
  "nickname": "김철수",
  "job": "백엔드 엔지니어",
  "affiliation": "Grep",
  "career": 5,
  "self_introduction": "자기 소개 예시 ....",
  "github_link": "https://github.com/",
  "blog_link": "https://blog.johndoe.com",
  "interest": "TEAM_PROJECT",
  "add_tech_stacks": [
    1,
    2,
    3
  ],
  "delete_tech_stacks": [
    4,
    5
  ]
}

```

회원 정보 수정 요청 DTO

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|nickname|string|true|none||회원의 닉네임|
|job|string|true|none||회원의 직업|
|affiliation|string|true|none||회원의 소속|
|career|integer(int32)|false|none||회원의 경력(년)|
|self_introduction|string|true|none||회원의 자기소개|
|github_link|string|true|none||GitHub 링크|
|blog_link|string|true|none||블로그 링크|
|interest|string|true|none||관심 분야|
|add_tech_stacks|[integer]|false|none||추가할 기술 스택 ID 목록|
|delete_tech_stacks|[integer]|false|none||삭제할 기술 스택 ID 목록|

#### Enum

|Name|Value|
|---|---|
|interest|STUDY|
|interest|TEAM_PROJECT|
|interest|JOBPOST|

<h2 id="tocS_CommentUpdateRequestDto">CommentUpdateRequestDto</h2>

<a id="schemacommentupdaterequestdto"></a>
<a id="schema_CommentUpdateRequestDto"></a>
<a id="tocScommentupdaterequestdto"></a>
<a id="tocscommentupdaterequestdto"></a>

```json
{
  "content": "이 댓글을 수정합니다."
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|content|string|true|none||댓글 내용|

<h2 id="tocS_BugReportUpdateRequest">BugReportUpdateRequest</h2>

<a id="schemabugreportupdaterequest"></a>
<a id="schema_BugReportUpdateRequest"></a>
<a id="tocSbugreportupdaterequest"></a>
<a id="tocsbugreportupdaterequest"></a>

```json
{
  "url": "http://example.com",
  "content": "어떤 기능에서 문제가 발생했습니다.",
  "bug_type": "EXPIRED"
}

```

버그리포트 수정 정보

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|url|string|true|none||관련 URL|
|content|string|true|none||버그 내용|
|bug_type|string|true|none||버그 타입|

#### Enum

|Name|Value|
|---|---|
|bug_type|NOT_FOUND|
|bug_type|EXPIRED|
|bug_type|OTHER|

<h2 id="tocS_BugReportResponse">BugReportResponse</h2>

<a id="schemabugreportresponse"></a>
<a id="schema_BugReportResponse"></a>
<a id="tocSbugreportresponse"></a>
<a id="tocsbugreportresponse"></a>

```json
{
  "bug_report_id": 123,
  "member_id": 456,
  "related_url": "http://example.com",
  "bug_type": "CRITICAL",
  "content": "어떤 기능에서 문제가 발생했습니다.",
  "created_at": "2019-08-24T14:15:22Z",
  "updated_at": "2019-08-24T14:15:22Z"
}

```

버그 리포트 응답 정보

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|bug_report_id|integer(int64)|false|none||버그 리포트 ID|
|member_id|integer(int64)|false|none||사용자 ID|
|related_url|string|false|none||관련 URL|
|bug_type|string|false|none||버그 타입|
|content|string|false|none||버그 내용|
|created_at|string(date-time)|false|none||생성 시간|
|updated_at|string(date-time)|false|none||수정 시간|

#### Enum

|Name|Value|
|---|---|
|bug_type|NOT_FOUND|
|bug_type|EXPIRED|
|bug_type|OTHER|

<h2 id="tocS_BoardUpdateRequestDto">BoardUpdateRequestDto</h2>

<a id="schemaboardupdaterequestdto"></a>
<a id="schema_BoardUpdateRequestDto"></a>
<a id="tocSboardupdaterequestdto"></a>
<a id="tocsboardupdaterequestdto"></a>

```json
{
  "title": "프로젝트 참가자 모집",
  "content": "함께 프로젝트를 진행할 참가자를 모집합니다.",
  "category": "STUDY",
  "recruit_num": 5,
  "progress_way": "ONLINE",
  "progress_period": "3개월",
  "end_date": "2019-08-24T14:15:22Z",
  "add_tech_stacks": [
    1,
    2,
    3
  ],
  "delete_tech_stacks": [
    4,
    5
  ]
}

```

게시물 수정 요청 DTO

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|title|string|true|none||게시물 제목|
|content|string|true|none||게시물 내용|
|category|string|true|none||게시물 카테고리|
|recruit_num|integer(int32)|true|none||모집 인원 수|
|progress_way|string|true|none||진행 방식|
|progress_period|string|true|none||진행 기간|
|end_date|string(date-time)|true|none||프로젝트 종료 날짜|
|add_tech_stacks|[integer]|false|none||추가할 기술 스택 ID 목록|
|delete_tech_stacks|[integer]|false|none||삭제할 기술 스택 ID 목록|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progress_way|ONLINE|
|progress_way|OFFLINE|
|progress_way|HYBRID|

<h2 id="tocS_MemberCreateRequestDto">MemberCreateRequestDto</h2>

<a id="schemamembercreaterequestdto"></a>
<a id="schema_MemberCreateRequestDto"></a>
<a id="tocSmembercreaterequestdto"></a>
<a id="tocsmembercreaterequestdto"></a>

```json
{
  "email": "user@example.com",
  "password": "password123!",
  "nickname": "DevUser",
  "job": "Software Engineer",
  "affiliation": "Company Name",
  "career": 5,
  "self_introduction": "안녕하세요! 저는 소프트웨어 엔지니어입니다.",
  "github_link": "https://github.com/user",
  "blog_link": "https://userblog.com",
  "interest": "TEAM_PROJECT",
  "tech_stack_ids": [
    1,
    2,
    3
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|email|string|true|none||회원 이메일|
|password|string|true|none||비밀번호|
|nickname|string|true|none||닉네임|
|job|string|true|none||회원 직업|
|affiliation|string|true|none||소속|
|career|integer(int32)|true|none||경력|
|self_introduction|string|true|none||자기소개|
|github_link|string|true|none||GitHub 링크|
|blog_link|string|true|none||블로그 링크|
|interest|string|true|none||회원의 관심 분야|
|tech_stack_ids|[integer]|true|none||기술 스택 ID 목록|

#### Enum

|Name|Value|
|---|---|
|interest|STUDY|
|interest|TEAM_PROJECT|
|interest|JOBPOST|

<h2 id="tocS_MemberLoginRequestDto">MemberLoginRequestDto</h2>

<a id="schemamemberloginrequestdto"></a>
<a id="schema_MemberLoginRequestDto"></a>
<a id="tocSmemberloginrequestdto"></a>
<a id="tocsmemberloginrequestdto"></a>

```json
{
  "email": "user@example.com",
  "password": "password123!"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|email|string|true|none||회원 이메일|
|password|string|true|none||비밀번호|

<h2 id="tocS_InterestJobPostRequestDto">InterestJobPostRequestDto</h2>

<a id="schemainterestjobpostrequestdto"></a>
<a id="schema_InterestJobPostRequestDto"></a>
<a id="tocSinterestjobpostrequestdto"></a>
<a id="tocsinterestjobpostrequestdto"></a>

```json
{
  "job_post_id": 1
}

```

관심 직업 등록 요청 정보

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|job_post_id|integer(int64)|true|none||채용공고 ID|

<h2 id="tocS_InterestBoardRequestDto">InterestBoardRequestDto</h2>

<a id="schemainterestboardrequestdto"></a>
<a id="schema_InterestBoardRequestDto"></a>
<a id="tocSinterestboardrequestdto"></a>
<a id="tocsinterestboardrequestdto"></a>

```json
{
  "board_id": 1
}

```

관심 게시글 등록 요청 정보

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|board_id|integer(int64)|true|none||게시물 ID|

<h2 id="tocS_CommentCreateRequestDto">CommentCreateRequestDto</h2>

<a id="schemacommentcreaterequestdto"></a>
<a id="schema_CommentCreateRequestDto"></a>
<a id="tocScommentcreaterequestdto"></a>
<a id="tocscommentcreaterequestdto"></a>

```json
{
  "board_id": 1,
  "parent_id": 2,
  "content": "이 게시물 정말 유익하네요!"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|board_id|integer(int64)|true|none||게시판 ID|
|parent_id|integer(int64)|false|none||부모 댓글 ID (대댓글인 경우)|
|content|string|true|none||댓글 내용|

<h2 id="tocS_ChatRoomRequest">ChatRoomRequest</h2>

<a id="schemachatroomrequest"></a>
<a id="schema_ChatRoomRequest"></a>
<a id="tocSchatroomrequest"></a>
<a id="tocschatroomrequest"></a>

```json
{
  "receiver_id": 2
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|receiver_id|integer(int64)|true|none||수신자 ID|

<h2 id="tocS_ChatPartResponse">ChatPartResponse</h2>

<a id="schemachatpartresponse"></a>
<a id="schema_ChatPartResponse"></a>
<a id="tocSchatpartresponse"></a>
<a id="tocschatpartresponse"></a>

```json
{
  "chatpart_id": 1,
  "room_id": 1
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|chatpart_id|integer(int64)|false|none||채팅 참여자 ID|
|room_id|integer(int64)|false|none||채팅 방 ID|

<h2 id="tocS_BugReportRequest">BugReportRequest</h2>

<a id="schemabugreportrequest"></a>
<a id="schema_BugReportRequest"></a>
<a id="tocSbugreportrequest"></a>
<a id="tocsbugreportrequest"></a>

```json
{
  "url": "http://example.com",
  "content": "어떤 기능에서 문제가 발생했습니다.",
  "bug_type": "EXPIRED"
}

```

버그리포트 생성 정보

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|url|string|true|none||관련 URL|
|content|string|true|none||버그 내용|
|bug_type|string|true|none||버그 타입|

#### Enum

|Name|Value|
|---|---|
|bug_type|NOT_FOUND|
|bug_type|EXPIRED|
|bug_type|OTHER|

<h2 id="tocS_BugReport">BugReport</h2>

<a id="schemabugreport"></a>
<a id="schema_BugReport"></a>
<a id="tocSbugreport"></a>
<a id="tocsbugreport"></a>

```json
{
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "bugReportId": 0,
  "member": {
    "createdAt": "2019-08-24T14:15:22Z",
    "updatedAt": "2019-08-24T14:15:22Z",
    "memberId": 0,
    "email": "string",
    "password": "string",
    "nickname": "string",
    "job": "string",
    "affiliation": "string",
    "career": 0,
    "memberTechStacks": [
      {
        "id": 0,
        "member": {
          "createdAt": "2019-08-24T14:15:22Z",
          "updatedAt": "2019-08-24T14:15:22Z",
          "memberId": 0,
          "email": "string",
          "password": "string",
          "nickname": "string",
          "job": "string",
          "affiliation": "string",
          "career": 0,
          "memberTechStacks": [
            null
          ],
          "selfIntroduction": "string",
          "blogLink": "string",
          "githubLink": "string",
          "interest": "["
        },
        "techStack": {
          "techStackId": 0,
          "name": "string",
          "code": "string"
        }
      }
    ],
    "selfIntroduction": "string",
    "blogLink": "string",
    "githubLink": "string",
    "interest": "STUDY"
  },
  "relatedUrl": "string",
  "content": "string",
  "bugType": "NOT_FOUND"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|createdAt|string(date-time)|false|none||none|
|updatedAt|string(date-time)|false|none||none|
|bugReportId|integer(int64)|false|none||none|
|member|[Member](#schemamember)|false|none||none|
|relatedUrl|string|false|none||none|
|content|string|false|none||none|
|bugType|string|false|none||none|

#### Enum

|Name|Value|
|---|---|
|bugType|NOT_FOUND|
|bugType|EXPIRED|
|bugType|OTHER|

<h2 id="tocS_Member">Member</h2>

<a id="schemamember"></a>
<a id="schema_Member"></a>
<a id="tocSmember"></a>
<a id="tocsmember"></a>

```json
{
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "memberId": 0,
  "email": "string",
  "password": "string",
  "nickname": "string",
  "job": "string",
  "affiliation": "string",
  "career": 0,
  "memberTechStacks": [
    {
      "id": 0,
      "member": {
        "createdAt": "2019-08-24T14:15:22Z",
        "updatedAt": "2019-08-24T14:15:22Z",
        "memberId": 0,
        "email": "string",
        "password": "string",
        "nickname": "string",
        "job": "string",
        "affiliation": "string",
        "career": 0,
        "memberTechStacks": [
          {
            "id": null,
            "member": null,
            "techStack": null
          }
        ],
        "selfIntroduction": "string",
        "blogLink": "string",
        "githubLink": "string",
        "interest": "STUDY"
      },
      "techStack": {
        "techStackId": 0,
        "name": "string",
        "code": "string"
      }
    }
  ],
  "selfIntroduction": "string",
  "blogLink": "string",
  "githubLink": "string",
  "interest": "STUDY"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|createdAt|string(date-time)|false|none||none|
|updatedAt|string(date-time)|false|none||none|
|memberId|integer(int64)|false|none||none|
|email|string|false|none||none|
|password|string|false|none||none|
|nickname|string|false|none||none|
|job|string|false|none||none|
|affiliation|string|false|none||none|
|career|integer(int32)|false|none||none|
|memberTechStacks|[[MemberTechStackMapping](#schemamembertechstackmapping)]|false|none||none|
|selfIntroduction|string|false|none||none|
|blogLink|string|false|none||none|
|githubLink|string|false|none||none|
|interest|string|false|none||none|

#### Enum

|Name|Value|
|---|---|
|interest|STUDY|
|interest|TEAM_PROJECT|
|interest|JOBPOST|

<h2 id="tocS_MemberTechStackMapping">MemberTechStackMapping</h2>

<a id="schemamembertechstackmapping"></a>
<a id="schema_MemberTechStackMapping"></a>
<a id="tocSmembertechstackmapping"></a>
<a id="tocsmembertechstackmapping"></a>

```json
{
  "id": 0,
  "member": {
    "createdAt": "2019-08-24T14:15:22Z",
    "updatedAt": "2019-08-24T14:15:22Z",
    "memberId": 0,
    "email": "string",
    "password": "string",
    "nickname": "string",
    "job": "string",
    "affiliation": "string",
    "career": 0,
    "memberTechStacks": [
      {
        "id": 0,
        "member": {
          "createdAt": "2019-08-24T14:15:22Z",
          "updatedAt": "2019-08-24T14:15:22Z",
          "memberId": 0,
          "email": "string",
          "password": "string",
          "nickname": "string",
          "job": "string",
          "affiliation": "string",
          "career": 0,
          "memberTechStacks": [
            null
          ],
          "selfIntroduction": "string",
          "blogLink": "string",
          "githubLink": "string",
          "interest": "["
        },
        "techStack": {
          "techStackId": 0,
          "name": "string",
          "code": "string"
        }
      }
    ],
    "selfIntroduction": "string",
    "blogLink": "string",
    "githubLink": "string",
    "interest": "STUDY"
  },
  "techStack": {
    "techStackId": 0,
    "name": "string",
    "code": "string"
  }
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|member|[Member](#schemamember)|false|none||none|
|techStack|[TechStack](#schematechstack)|false|none||none|

<h2 id="tocS_TechStack">TechStack</h2>

<a id="schematechstack"></a>
<a id="schema_TechStack"></a>
<a id="tocStechstack"></a>
<a id="tocstechstack"></a>

```json
{
  "techStackId": 0,
  "name": "string",
  "code": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|techStackId|integer(int64)|false|none||none|
|name|string|false|none||none|
|code|string|false|none||none|

<h2 id="tocS_BoardCreateRequestDto">BoardCreateRequestDto</h2>

<a id="schemaboardcreaterequestdto"></a>
<a id="schema_BoardCreateRequestDto"></a>
<a id="tocSboardcreaterequestdto"></a>
<a id="tocsboardcreaterequestdto"></a>

```json
{
  "job_post_id": 1,
  "title": "Spring Boot 개발자 모집",
  "content": "우리는 Spring Boot 벡엔드 개발자를 찾고 있습니다.",
  "category": "STUDY",
  "recruit_num": 3,
  "progress_way": "ONLINE",
  "progress_period": "3개월",
  "end_date": "2019-08-24T14:15:22Z",
  "tech_stack_requests": [
    {
      "tech_stack_id": 1
    }
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|job_post_id|integer(int64)|false|none||직업 공고 ID|
|title|string|true|none||게시물 제목|
|content|string|true|none||게시물 내용|
|category|string|true|none||카테고리|
|recruit_num|integer(int32)|true|none||모집 인원|
|progress_way|string|true|none||진행 방식|
|progress_period|string|true|none||진행 기간|
|end_date|string(date-time)|true|none||종료 날짜|
|tech_stack_requests|[[BoardTechStackRequestDto](#schemaboardtechstackrequestdto)]|true|none||[기술 스택 목록]|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progress_way|ONLINE|
|progress_way|OFFLINE|
|progress_way|HYBRID|

<h2 id="tocS_BoardTechStackRequestDto">BoardTechStackRequestDto</h2>

<a id="schemaboardtechstackrequestdto"></a>
<a id="schema_BoardTechStackRequestDto"></a>
<a id="tocSboardtechstackrequestdto"></a>
<a id="tocsboardtechstackrequestdto"></a>

```json
{
  "tech_stack_id": 1
}

```

기술 스택 목록

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|tech_stack_id|integer(int64)|false|none||기술 스택 ID|

<h2 id="tocS_TechStackResponseDto">TechStackResponseDto</h2>

<a id="schematechstackresponsedto"></a>
<a id="schema_TechStackResponseDto"></a>
<a id="tocStechstackresponsedto"></a>
<a id="tocstechstackresponsedto"></a>

```json
{
  "tech_stack_id": 1,
  "name": "Java",
  "code": "200"
}

```

직무 기술 스택 목록

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|tech_stack_id|integer(int64)|false|none||기술 스택 ID|
|name|string|false|none||기술 스택 이름|
|code|string|false|none||기술 스택 코드|

<h2 id="tocS_MemberResponseDto">MemberResponseDto</h2>

<a id="schemamemberresponsedto"></a>
<a id="schema_MemberResponseDto"></a>
<a id="tocSmemberresponsedto"></a>
<a id="tocsmemberresponsedto"></a>

```json
{
  "member_id": 1,
  "email": "user@example.com",
  "nickname": "DevUser",
  "job": "Software Engineer",
  "affiliation": "Company Name",
  "career": 5,
  "self_introduction": "안녕하세요! 저는 소프트웨어 엔지니어입니다.",
  "github_link": "https://github.com/user",
  "blog_link": "https://userblog.com",
  "interest": "TEAM_PROJECT",
  "tech_stacks": [
    {
      "tech_stack_id": 1,
      "name": "Java",
      "code": "200"
    }
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|member_id|integer(int64)|false|none||회원 ID|
|email|string|false|none||회원 이메일|
|nickname|string|false|none||회원 닉네임|
|job|string|false|none||회원 직업|
|affiliation|string|false|none||회원 소속|
|career|integer(int32)|false|none||회원 경력|
|self_introduction|string|false|none||회원 자기소개|
|github_link|string|false|none||GitHub 링크|
|blog_link|string|false|none||블로그 링크|
|interest|string|false|none||회원 관심 분야|
|tech_stacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||회원 기술 스택 목록|

#### Enum

|Name|Value|
|---|---|
|interest|STUDY|
|interest|TEAM_PROJECT|
|interest|JOBPOST|

<h2 id="tocS_Pageable">Pageable</h2>

<a id="schemapageable"></a>
<a id="schema_Pageable"></a>
<a id="tocSpageable"></a>
<a id="tocspageable"></a>

```json
{
  "page": 0,
  "size": 1,
  "sort": [
    "string"
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|page|integer(int32)|false|none||none|
|size|integer(int32)|false|none||none|
|sort|[string]|false|none||none|

<h2 id="tocS_JobPostInfoResponseDto">JobPostInfoResponseDto</h2>

<a id="schemajobpostinforesponsedto"></a>
<a id="schema_JobPostInfoResponseDto"></a>
<a id="tocSjobpostinforesponsedto"></a>
<a id="tocsjobpostinforesponsedto"></a>

```json
{
  "job_post_id": 123,
  "post_id": 456,
  "job_post_name": "Backend Developer",
  "company_name": "ABC Corp",
  "company_address": "서울시 강남구 테헤란로 123",
  "company_link": "http://example.com",
  "post_date": "2019-08-24T14:15:22Z",
  "open_date": "2019-08-24T14:15:22Z",
  "end_date": "2019-08-24T14:15:22Z",
  "salary": "회사 내규에 따름",
  "job_type": "REGULAR",
  "status": "RECRUITING",
  "views": 100,
  "likes": 100,
  "tech_stacks": [
    {
      "tech_stack_id": 1,
      "name": "Java",
      "code": "200"
    }
  ]
}

```

여러 개의 채용공고 게시글 정보

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|job_post_id|integer(int64)|false|none||채용 공고 ID|
|post_id|integer(int64)|false|none||게시물 ID|
|job_post_name|string|false|none||채용 공고 이름|
|company_name|string|false|none||회사 이름|
|company_address|string|false|none||회사 주소|
|company_link|string|false|none||회사 링크|
|post_date|string(date-time)|false|none||게시 날짜|
|open_date|string(date-time)|false|none||공고 시작 날짜|
|end_date|string(date-time)|false|none||공고 종료 날짜|
|salary|string|false|none||급여|
|job_type|string|false|none||직무 타입|
|status|string|false|none||상태|
|views|integer(int32)|false|none||조회수|
|likes|integer(int32)|false|none||좋아요 수|
|tech_stacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||직무 기술 스택 목록|

#### Enum

|Name|Value|
|---|---|
|job_type|REGULAR|
|job_type|IRREGULAR|
|job_type|CONTRACT|
|status|RECRUITING|
|status|DEADLINE|
|status|DELETED|

<h2 id="tocS_PageJobPostInfoResponseDto">PageJobPostInfoResponseDto</h2>

<a id="schemapagejobpostinforesponsedto"></a>
<a id="schema_PageJobPostInfoResponseDto"></a>
<a id="tocSpagejobpostinforesponsedto"></a>
<a id="tocspagejobpostinforesponsedto"></a>

```json
{
  "totalElements": 0,
  "totalPages": 0,
  "size": 0,
  "content": [
    {
      "job_post_id": 123,
      "post_id": 456,
      "job_post_name": "Backend Developer",
      "company_name": "ABC Corp",
      "company_address": "서울시 강남구 테헤란로 123",
      "company_link": "http://example.com",
      "post_date": "2019-08-24T14:15:22Z",
      "open_date": "2019-08-24T14:15:22Z",
      "end_date": "2019-08-24T14:15:22Z",
      "salary": "회사 내규에 따름",
      "job_type": "REGULAR",
      "status": "RECRUITING",
      "views": 100,
      "likes": 100,
      "tech_stacks": [
        {
          "tech_stack_id": 1,
          "name": "Java",
          "code": "200"
        }
      ]
    }
  ],
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": true,
    "unsorted": true
  },
  "first": true,
  "last": true,
  "numberOfElements": 0,
  "pageable": {
    "offset": 0,
    "sort": {
      "empty": true,
      "sorted": true,
      "unsorted": true
    },
    "paged": true,
    "pageNumber": 0,
    "pageSize": 0,
    "unpaged": true
  },
  "empty": true
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|totalElements|integer(int64)|false|none||none|
|totalPages|integer(int32)|false|none||none|
|size|integer(int32)|false|none||none|
|content|[[JobPostInfoResponseDto](#schemajobpostinforesponsedto)]|false|none||[여러 개의 채용공고 게시글 정보]|
|number|integer(int32)|false|none||none|
|sort|[SortObject](#schemasortobject)|false|none||none|
|first|boolean|false|none||none|
|last|boolean|false|none||none|
|numberOfElements|integer(int32)|false|none||none|
|pageable|[PageableObject](#schemapageableobject)|false|none||none|
|empty|boolean|false|none||none|

<h2 id="tocS_PageableObject">PageableObject</h2>

<a id="schemapageableobject"></a>
<a id="schema_PageableObject"></a>
<a id="tocSpageableobject"></a>
<a id="tocspageableobject"></a>

```json
{
  "offset": 0,
  "sort": {
    "empty": true,
    "sorted": true,
    "unsorted": true
  },
  "paged": true,
  "pageNumber": 0,
  "pageSize": 0,
  "unpaged": true
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|offset|integer(int64)|false|none||none|
|sort|[SortObject](#schemasortobject)|false|none||none|
|paged|boolean|false|none||none|
|pageNumber|integer(int32)|false|none||none|
|pageSize|integer(int32)|false|none||none|
|unpaged|boolean|false|none||none|

<h2 id="tocS_SortObject">SortObject</h2>

<a id="schemasortobject"></a>
<a id="schema_SortObject"></a>
<a id="tocSsortobject"></a>
<a id="tocssortobject"></a>

```json
{
  "empty": true,
  "sorted": true,
  "unsorted": true
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|empty|boolean|false|none||none|
|sorted|boolean|false|none||none|
|unsorted|boolean|false|none||none|

<h2 id="tocS_BoardInfoResponseDto">BoardInfoResponseDto</h2>

<a id="schemaboardinforesponsedto"></a>
<a id="schema_BoardInfoResponseDto"></a>
<a id="tocSboardinforesponsedto"></a>
<a id="tocsboardinforesponsedto"></a>

```json
{
  "board_id": 1,
  "title": "새로운 게시물 제목",
  "category": "STUDY",
  "progress_way": "ONLINE",
  "end_date": "2019-08-24T14:15:22Z",
  "views": 150,
  "likes": 20,
  "author": "john_doe",
  "author_id": 42
}

```

여러 개의 단일 게시글 정보

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|board_id|integer(int64)|false|none||게시물 ID|
|title|string|false|none||게시물 제목|
|category|string|false|none||게시물 카테고리|
|progress_way|string|false|none||진행 방식|
|end_date|string(date-time)|false|none||게시물 종료 날짜|
|views|integer(int32)|false|none||조회수|
|likes|integer(int32)|false|none||좋아요 수|
|author|string|false|none||작성자 닉네임|
|author_id|integer(int64)|false|none||작성자 ID|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progress_way|ONLINE|
|progress_way|OFFLINE|
|progress_way|HYBRID|

<h2 id="tocS_InterestResponseDto">InterestResponseDto</h2>

<a id="schemainterestresponsedto"></a>
<a id="schema_InterestResponseDto"></a>
<a id="tocSinterestresponsedto"></a>
<a id="tocsinterestresponsedto"></a>

```json
{
  "interest_boards": [
    {
      "board_id": 1,
      "title": "새로운 게시물 제목",
      "category": "STUDY",
      "progress_way": "ONLINE",
      "end_date": "2019-08-24T14:15:22Z",
      "views": 150,
      "likes": 20,
      "author": "john_doe",
      "author_id": 42
    }
  ],
  "interest_job_posts": [
    {
      "job_post_id": 123,
      "post_id": 456,
      "job_post_name": "Backend Developer",
      "company_name": "ABC Corp",
      "company_address": "서울시 강남구 테헤란로 123",
      "company_link": "http://example.com",
      "post_date": "2019-08-24T14:15:22Z",
      "open_date": "2019-08-24T14:15:22Z",
      "end_date": "2019-08-24T14:15:22Z",
      "salary": "회사 내규에 따름",
      "job_type": "REGULAR",
      "status": "RECRUITING",
      "views": 100,
      "likes": 100,
      "tech_stacks": [
        {
          "tech_stack_id": 1,
          "name": "Java",
          "code": "200"
        }
      ]
    }
  ]
}

```

관심 게시물 전체 조회 응답 정보

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|interest_boards|[[BoardInfoResponseDto](#schemaboardinforesponsedto)]|false|none||여러 개의 단일 게시글 정보|
|interest_job_posts|[[JobPostInfoResponseDto](#schemajobpostinforesponsedto)]|false|none||여러 개의 채용공고 게시글 정보|

<h2 id="tocS_CommentResponseDto">CommentResponseDto</h2>

<a id="schemacommentresponsedto"></a>
<a id="schema_CommentResponseDto"></a>
<a id="tocScommentresponsedto"></a>
<a id="tocscommentresponsedto"></a>

```json
{
  "comment_id": 1,
  "member_id": 2,
  "author": "JohnDoe",
  "content": "이것은 댓글입니다.",
  "updated_at": "2019-08-24T14:15:22Z",
  "parent_id": 2
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|comment_id|integer(int64)|false|none||댓글 ID|
|member_id|integer(int64)|false|none||작성자 ID|
|author|string|false|none||작성자 닉네임|
|content|string|false|none||댓글 내용|
|updated_at|string(date-time)|false|none||댓글 수정 날짜|
|parent_id|integer(int64)|false|none||부모 댓글 ID (대댓글인 경우)|

<h2 id="tocS_PageCommentResponseDto">PageCommentResponseDto</h2>

<a id="schemapagecommentresponsedto"></a>
<a id="schema_PageCommentResponseDto"></a>
<a id="tocSpagecommentresponsedto"></a>
<a id="tocspagecommentresponsedto"></a>

```json
{
  "totalElements": 0,
  "totalPages": 0,
  "size": 0,
  "content": [
    {
      "comment_id": 1,
      "member_id": 2,
      "author": "JohnDoe",
      "content": "이것은 댓글입니다.",
      "updated_at": "2019-08-24T14:15:22Z",
      "parent_id": 2
    }
  ],
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": true,
    "unsorted": true
  },
  "first": true,
  "last": true,
  "numberOfElements": 0,
  "pageable": {
    "offset": 0,
    "sort": {
      "empty": true,
      "sorted": true,
      "unsorted": true
    },
    "paged": true,
    "pageNumber": 0,
    "pageSize": 0,
    "unpaged": true
  },
  "empty": true
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|totalElements|integer(int64)|false|none||none|
|totalPages|integer(int32)|false|none||none|
|size|integer(int32)|false|none||none|
|content|[[CommentResponseDto](#schemacommentresponsedto)]|false|none||none|
|number|integer(int32)|false|none||none|
|sort|[SortObject](#schemasortobject)|false|none||none|
|first|boolean|false|none||none|
|last|boolean|false|none||none|
|numberOfElements|integer(int32)|false|none||none|
|pageable|[PageableObject](#schemapageableobject)|false|none||none|
|empty|boolean|false|none||none|

<h2 id="tocS_MessageFullResponse">MessageFullResponse</h2>

<a id="schemamessagefullresponse"></a>
<a id="schema_MessageFullResponse"></a>
<a id="tocSmessagefullresponse"></a>
<a id="tocsmessagefullresponse"></a>

```json
{
  "room_id": 100,
  "message_list": {
    "message_id": 10,
    "sender_id": 2,
    "nickname": "김철수",
    "content": "안녕하세요!",
    "created_at": "2019-08-24T14:15:22Z"
  }
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|room_id|integer(int64)|false|none||채팅 방 ID|
|message_list|[MessageResponse](#schemamessageresponse)|false|none||메시지 목록|

<h2 id="tocS_MessageResponse">MessageResponse</h2>

<a id="schemamessageresponse"></a>
<a id="schema_MessageResponse"></a>
<a id="tocSmessageresponse"></a>
<a id="tocsmessageresponse"></a>

```json
{
  "message_id": 10,
  "sender_id": 2,
  "nickname": "김철수",
  "content": "안녕하세요!",
  "created_at": "2019-08-24T14:15:22Z"
}

```

메시지 목록

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|message_id|integer(int64)|false|none||메시지 ID|
|sender_id|integer(int64)|false|none||보낸 사람의 ID|
|nickname|string|false|none||보낸 사람의 닉네임|
|content|string|false|none||메시지 내용|
|created_at|string(date-time)|false|none||메시지 생성 시간|

<h2 id="tocS_ChatRoomListResponse">ChatRoomListResponse</h2>

<a id="schemachatroomlistresponse"></a>
<a id="schema_ChatRoomListResponse"></a>
<a id="tocSchatroomlistresponse"></a>
<a id="tocschatroomlistresponse"></a>

```json
{
  "member_id": 2,
  "chatpart_id": 1,
  "room_id": 100,
  "status": "ACTIVE"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|member_id|integer(int64)|false|none||멤버 ID|
|chatpart_id|integer(int64)|false|none||채팅 참여자 ID|
|room_id|integer(int64)|false|none||채팅 방 ID|
|status|string|false|none||채팅 방 상태|

#### Enum

|Name|Value|
|---|---|
|status|ACTIVE|
|status|INACTIVE|

<h2 id="tocS_BoardResponseDto">BoardResponseDto</h2>

<a id="schemaboardresponsedto"></a>
<a id="schema_BoardResponseDto"></a>
<a id="tocSboardresponsedto"></a>
<a id="tocsboardresponsedto"></a>

```json
{
  "boardId": 1,
  "authorId": 42,
  "author": "john_doe",
  "title": "새로운 프로젝트 모집",
  "content": "프로젝트 설명을 작성합니다.",
  "category": "PROJECT",
  "recruitNum": 5,
  "progressWay": "ONLINE",
  "progressPeriod": "2개월",
  "endDate": "2019-08-24T14:15:22Z",
  "likes": 150,
  "views": 500,
  "createdDate": "2019-08-24T14:15:22Z",
  "updatedDate": "2019-08-24T14:15:22Z",
  "status": "RECRUITING",
  "techStacks": [
    {
      "tech_stack_id": 1,
      "name": "Java",
      "code": "200"
    }
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|boardId|integer(int64)|false|none||게시물 ID|
|authorId|integer(int64)|false|none||작성자 ID|
|author|string|false|none||작성자 닉네임|
|title|string|false|none||게시물 제목|
|content|string|false|none||게시물 내용|
|category|string|false|none||게시물 카테고리|
|recruitNum|integer(int32)|false|none||모집 인원|
|progressWay|string|false|none||진행 방식|
|progressPeriod|string|false|none||진행 기간|
|endDate|string(date-time)|false|none||게시물 종료 날짜|
|likes|integer(int32)|false|none||좋아요 수|
|views|integer(int32)|false|none||조회수|
|createdDate|string(date-time)|false|none||생성일자|
|updatedDate|string(date-time)|false|none||수정일자|
|status|string|false|none||게시물 상태|
|techStacks|[[TechStackResponseDto](#schematechstackresponsedto)]|false|none||기술 스택 리스트|

#### Enum

|Name|Value|
|---|---|
|category|STUDY|
|category|TEAM_PROJECT|
|progressWay|ONLINE|
|progressWay|OFFLINE|
|progressWay|HYBRID|
|status|RECRUITING|
|status|CLOSED|
|status|DELETED|

<h2 id="tocS_PageBoardResponseDto">PageBoardResponseDto</h2>

<a id="schemapageboardresponsedto"></a>
<a id="schema_PageBoardResponseDto"></a>
<a id="tocSpageboardresponsedto"></a>
<a id="tocspageboardresponsedto"></a>

```json
{
  "totalElements": 0,
  "totalPages": 0,
  "size": 0,
  "content": [
    {
      "boardId": 1,
      "authorId": 42,
      "author": "john_doe",
      "title": "새로운 프로젝트 모집",
      "content": "프로젝트 설명을 작성합니다.",
      "category": "PROJECT",
      "recruitNum": 5,
      "progressWay": "ONLINE",
      "progressPeriod": "2개월",
      "endDate": "2019-08-24T14:15:22Z",
      "likes": 150,
      "views": 500,
      "createdDate": "2019-08-24T14:15:22Z",
      "updatedDate": "2019-08-24T14:15:22Z",
      "status": "RECRUITING",
      "techStacks": [
        {
          "tech_stack_id": 1,
          "name": "Java",
          "code": "200"
        }
      ]
    }
  ],
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": true,
    "unsorted": true
  },
  "first": true,
  "last": true,
  "numberOfElements": 0,
  "pageable": {
    "offset": 0,
    "sort": {
      "empty": true,
      "sorted": true,
      "unsorted": true
    },
    "paged": true,
    "pageNumber": 0,
    "pageSize": 0,
    "unpaged": true
  },
  "empty": true
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|totalElements|integer(int64)|false|none||none|
|totalPages|integer(int32)|false|none||none|
|size|integer(int32)|false|none||none|
|content|[[BoardResponseDto](#schemaboardresponsedto)]|false|none||none|
|number|integer(int32)|false|none||none|
|sort|[SortObject](#schemasortobject)|false|none||none|
|first|boolean|false|none||none|
|last|boolean|false|none||none|
|numberOfElements|integer(int32)|false|none||none|
|pageable|[PageableObject](#schemapageableobject)|false|none||none|
|empty|boolean|false|none||none|

<h2 id="tocS_AlarmGetResponse">AlarmGetResponse</h2>

<a id="schemaalarmgetresponse"></a>
<a id="schema_AlarmGetResponse"></a>
<a id="tocSalarmgetresponse"></a>
<a id="tocsalarmgetresponse"></a>

```json
{
  "sender": "홍길동",
  "created_time": "2019-08-24T14:15:22Z",
  "content": "알림 메시지가 도착했어요!",
  "is_read": true
}

```

단일 알림 응답 정보

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|sender|string|false|none||알림 수신자 닉네임|
|created_time|string(date-time)|false|none||알림 생성 시각|
|content|string|false|none||알림 메시지|
|is_read|boolean|false|none||읽음표시: true - 읽음, false - 읽지않음|

<h2 id="tocS_AlarmsGetResponse">AlarmsGetResponse</h2>

<a id="schemaalarmsgetresponse"></a>
<a id="schema_AlarmsGetResponse"></a>
<a id="tocSalarmsgetresponse"></a>
<a id="tocsalarmsgetresponse"></a>

```json
{
  "count": 123,
  "alarms": [
    {
      "sender": "홍길동",
      "created_time": "2019-08-24T14:15:22Z",
      "content": "알림 메시지가 도착했어요!",
      "is_read": true
    }
  ]
}

```

전체 알림 응답 정보

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|count|integer(int32)|false|none||전체 알림의 개수|
|alarms|[[AlarmGetResponse](#schemaalarmgetresponse)]|false|none||여러 개의 단일 알림 응답 정보|

