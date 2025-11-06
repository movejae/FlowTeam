# 파일 확장자 차단 시스템

플로우 과제 - 보안을 위한 파일 확장자 차단 관리 시스템

## 프로젝트 개요

서버에 업로드되는 파일의 보안을 위해 특정 확장자를 차단하는 시스템입니다.
exe, sh 등의 실행 파일이 서버에 업로드되어 실행되는 것을 방지하기 위한 관리 페이지를 제공합니다.

## 주요 기능

### 1. 고정 확장자 관리
- 7가지 위험한 확장자 제공: `bat`, `cmd`, `com`, `cpl`, `exe`, `scr`, `js`
- 체크박스로 차단 여부 설정
- 변경 사항 자동 저장 및 새로고침 시 유지

### 2. 커스텀 확장자 관리
- 사용자 정의 확장자 추가 (최대 20자)
- 최대 200개까지 등록 가능
- 개별 삭제 기능
- 추가된 확장자는 태그 형태로 표시

### 3. 데이터 영속성
- 모든 설정이 DB에 저장
- 애플리케이션 재시작 후에도 설정 유지

### 4. 상수 관리
- `ExtensionConstants` 클래스로 모든 상수를 중앙 관리
- 확장자 최대 길이 (20자), 커스텀 확장자 최대 개수 (200개) 등
- 변경 시 영향도를 주석으로 명시하여 안전한 유지보수 지원

### 5. 예외 처리
- 글로벌 예외 핸들러로 일관된 에러 응답
- 중복 확장자, 개수 초과, 찾기 실패 등 커스텀 예외 처리
- 사용자 친화적인 에러 메시지 제공

## 기술 스택

### Backend
- **Java**: 21
- **Spring Boot**: 3.5.7
- **Spring Data JPA**: 데이터 영속성 관리
- **H2 Database**: 인메모리 데이터베이스
- **Lombok**: 보일러플레이트 코드 감소
- **Hibernate Validator**: 입력 데이터 검증

### Frontend
- **Thymeleaf**: 서버 사이드 템플릿 엔진
- **HTML5/CSS3/JavaScript**: UI 구현
- **Fetch API**: 비동기 REST API 통신

### Build Tool
- **Gradle**: 8.14.3
- **Gradle Wrapper**: 포함

## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/flow/folwteamtest/
│   │   ├── FolwTeamTestApplication.java
│   │   ├── common/
│   │   │   └── ExtensionConstants.java         # 상수 관리 클래스
│   │   ├── controller/
│   │   │   ├── ExtensionApiController.java     # REST API 컨트롤러
│   │   │   ├── ViewController.java             # 화면 컨트롤러
│   │   │   └── GlobalExceptionHandler.java     # 전역 예외 핸들러
│   │   ├── service/
│   │   │   └── ExtensionService.java           # 비즈니스 로직
│   │   ├── repository/
│   │   │   ├── FixedExtensionRepository.java   # 고정 확장자 Repository
│   │   │   └── CustomExtensionRepository.java  # 커스텀 확장자 Repository
│   │   ├── entity/
│   │   │   ├── FixedExtension.java             # 고정 확장자 엔티티
│   │   │   └── CustomExtension.java            # 커스텀 확장자 엔티티
│   │   ├── dto/
│   │   │   ├── FixedExtensionDto.java          # 고정 확장자 DTO
│   │   │   ├── FixedExtensionUpdateRequest.java# 고정 확장자 업데이트 요청
│   │   │   ├── CustomExtensionDto.java         # 커스텀 확장자 DTO
│   │   │   └── CustomExtensionCreateRequest.java# 커스텀 확장자 생성 요청
│   │   └── exception/
│   │       ├── DuplicateExtensionException.java    # 중복 확장자 예외
│   │       ├── ExtensionLimitExceededException.java# 개수 초과 예외
│   │       └── ExtensionNotFoundException.java     # 확장자 미발견 예외
│   └── resources/
│       ├── application.properties              # 애플리케이션 설정
│       └── templates/
│           └── index.html                      # 메인 화면 (Thymeleaf)
└── test/
    └── java/com/flow/folwteamtest/
        └── FolwTeamTestApplicationTests.java
```

## 실행 방법

### 1. 프로젝트 클론
```bash
git clone https://github.com/movejae/FlowTeam.git

```

### 2. 애플리케이션 실행
```bash
# Windows
gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

### 3. 접속
- URL: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:flowtest`
  - Username: `sa`
  - Password: (없음)

### 4. 빌드 (JAR 파일 생성)
```bash
# Windows
gradlew.bat clean build

# Linux/Mac
./gradlew clean build
```

## API 명세

### 고정 확장자

#### 고정 확장자 전체 조회
```http
GET /api/extensions/fixed
Response: List<FixedExtensionDto>
```

#### 고정 확장자 차단 설정 변경 (ID 기반)
```http
PUT /api/extensions/fixed/{id}
Request Body: { "blocked": true/false }
Response: FixedExtensionDto
```

#### 고정 확장자 차단 설정 변경 (이름 기반)
```http
PATCH /api/extensions/fixed/{name}
Request Body: { "blocked": true/false }
Response: FixedExtensionDto
```

### 커스텀 확장자

#### 커스텀 확장자 전체 조회
```http
GET /api/extensions/custom
Response: List<CustomExtensionDto>
```

#### 커스텀 확장자 추가
```http
POST /api/extensions/custom
Request Body: { "extension": "확장자명" }
Response: CustomExtensionDto (201 Created)
```

#### 커스텀 확장자 삭제 (ID 기반)
```http
DELETE /api/extensions/custom/id/{id}
Response: 204 No Content
```

#### 커스텀 확장자 삭제 (이름 기반)
```http
DELETE /api/extensions/custom/{extension}
Response: 204 No Content
```

#### 커스텀 확장자 개수 조회
```http
GET /api/extensions/custom/count
Response: Long (개수)
```

## 추가 구현 사항

### 1. 아키텍처 및 설계
- **계층형 아키텍처**: Controller → Service → Repository 구조
- **중앙 집중식 상수 관리**: ExtensionConstants 클래스로 모든 상수 관리
- **영향도 분석**: 상수 변경 시 영향 범위를 주석으로 명시
- **RESTful API 설계**: HTTP 메서드 적절히 활용 (GET, POST, PUT, PATCH, DELETE)

### 2. 보안 및 검증
- **입력 검증**: Bean Validation (@Valid, @NotBlank, @Size, @Pattern)
- 확장자 입력 길이 제한 (최대 20자)
- 커스텀 확장자 중복 체크
- 최대 등록 개수 제한 (200개)
- 영문자와 숫자만 허용하는 정규식 검증
- XSS 방지를 위한 입력 처리

### 3. 예외 처리
- **글로벌 예외 핸들러**: @RestControllerAdvice로 일관된 에러 응답
- **커스텀 예외**: DuplicateExtensionException, ExtensionLimitExceededException, ExtensionNotFoundException
- HTTP 상태 코드 적절히 활용 (409 Conflict, 404 Not Found 등)
- 사용자 친화적인 에러 메시지

### 4. 사용성 개선
- 대소문자 구분 없이 처리 (소문자로 통일)
- 공백 자동 제거
- 확장자 앞의 '.' 자동 제거
- 실시간 카운트 표시 (n/200)
- Enter 키로 확장자 추가 가능
- 삭제 시 확인 다이얼로그

### 5. 코드 품질
- **Lombok 활용**: 보일러플레이트 코드 최소화
- **로그 관리**: SLF4J를 통한 체계적인 로깅 (모든 로그 영문으로 작성)
- **불변성 보장**: final 키워드 적극 활용
- **명확한 네이밍**: 의미있는 변수명과 메서드명 사용

## 개발 환경 설정

### IDE 설정
- IntelliJ IDEA 추천
- Lombok 플러그인 설치 필수
- Annotation Processing 활성화

### 환경 변수
별도 환경 변수 설정 불필요 (H2 인메모리 DB 사용)

## 향후 개선 방향
- [ ] 파일 업로드 기능과의 실제 연동
- [ ] Spring Security를 통한 인증/권한 관리
- [ ] 확장자별 차단 로그 이력 기록 및 조회
- [ ] 대량 확장자 일괄 등록 (CSV/Excel 업로드)
- [ ] 확장자 그룹 관리 기능 (예: 실행파일, 압축파일 등)
- [ ] PostgreSQL/MySQL 등 프로덕션 DB 연동
- [ ] 단위 테스트 및 통합 테스트 추가
- [ ] API 문서화 (Swagger/OpenAPI)
- [ ] 페이지네이션 및 검색 기능

## 라이선스
This project is for assignment purposes only.

## 작성자
- dewawd@gmail.com(Dong Jae LEE)
- phone: 010-2088-6739
