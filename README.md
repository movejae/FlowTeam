# 파일 확장자 차단 시스템

플로우 과제 - 보안을 위한 파일 확장자 차단 관리 시스템

## 프로젝트 개요

서버에 업로드되는 파일의 보안을 위해 특정 확장자를 차단하는 시스템입니다.
exe, sh 등의 실행 파일이 서버에 업로드되어 실행되는 것을 방지하기 위한 관리 페이지를 제공합니다.

## 기술 스택

### Backend
- **Java**: 21
- **Spring Boot**: 3.5.7
- **Spring Data JPA**: 데이터 영속성 관리
- **H2 Database**: 인메모리 데이터베이스
- **Lombok**: 보일러플레이트 코드 감소

### Frontend
- **Thymeleaf**: 서버 사이드 템플릿 엔진
- **HTML5/CSS3/JavaScript**: UI 구현
- **Fetch API**: 비동기 REST API 통신

### Build Tool
- **Gradle**: 8.x
- **Gradle Wrapper**: 포함

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

## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/flow/flowteamtest/
│   │   ├── FolwTeamTestApplication.java
│   │   ├── controller/
│   │   │   ├── ExtensionApiController.java    # REST API 컨트롤러
│   │   │   └── ViewController.java             # 화면 컨트롤러
│   │   ├── service/
│   │   │   └── ExtensionService.java           # 비즈니스 로직
│   │   ├── repository/
│   │   │   ├── FixedExtensionRepository.java
│   │   │   └── CustomExtensionRepository.java
│   │   ├── entity/
│   │   │   ├── FixedExtension.java             # 고정 확장자 엔티티
│   │   │   └── CustomExtension.java            # 커스텀 확장자 엔티티
│   │   └── dto/
│   │       ├── FixedExtensionDto.java
│   │       └── CustomExtensionDto.java
│   └── resources/
│       ├── application.properties               # 애플리케이션 설정
│       ├── templates/
│       │   └── index.html                      # 메인 화면
│       └── static/
│           ├── css/
│           └── js/
└── test/
```

## 실행 방법

### 1. 프로젝트 클론
```bash
git clone [repository-url]
cd FlowTest
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

### 고정 확장자 조회
```
GET /api/extensions/fixed
Response: List<FixedExtensionDto>
```

### 고정 확장자 차단 설정 변경
```
PUT /api/extensions/fixed/{id}
Request Body: { "blocked": true/false }
Response: FixedExtensionDto
```

### 커스텀 확장자 조회
```
GET /api/extensions/custom
Response: List<CustomExtensionDto>
```

### 커스텀 확장자 추가
```
POST /api/extensions/custom
Request Body: { "extension": "확장자명" }
Response: CustomExtensionDto
```

### 커스텀 확장자 삭제
```
DELETE /api/extensions/custom/{id}
Response: 204 No Content
```

## 추가 구현 사항

### 1. 보안 및 검증
- 확장자 입력 길이 제한 (최대 20자)
- 커스텀 확장자 중복 체크
- 최대 등록 개수 제한 (200개)
- XSS 방지를 위한 입력 검증
- 특수문자 필터링

### 2. 사용성 개선
- 대소문자 구분 없이 처리 (소문자로 통일)
- 공백 자동 제거
- 확장자 앞의 '.' 자동 제거
- 실시간 카운트 표시 (n/200)

### 3. 에러 처리
- 중복 추가 시 사용자 친화적 메시지
- 최대 개수 초과 시 경고
- 네트워크 오류 처리

## 개발 환경 설정

### IDE 설정
- IntelliJ IDEA 추천
- Lombok 플러그인 설치 필수
- Annotation Processing 활성화

### 환경 변수
별도 환경 변수 설정 불필요 (H2 인메모리 DB 사용)

## 향후 개선 방향
- [ ] 파일 업로드 기능과의 실제 연동
- [ ] 관리자 인증/권한 관리
- [ ] 확장자별 차단 로그 기록
- [ ] 대량 확장자 일괄 등록 (CSV 업로드)
- [ ] 확장자 그룹 관리 기능
- [ ] PostgreSQL/MySQL 등 실제 DB 연동

## 라이선스
This project is for assignment purposes only.

## 작성자
- dewawd@gmail.com(Dong Jae LEE)
- phone: 010-2088-6739
