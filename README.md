# 파일 확장자 차단 시스템

플로우 과제 - 보안을 위한 파일 확장자 차단 관리 시스템

## 프로젝트 개요

서버에 업로드되는 파일의 보안을 위해 특정 확장자를 차단하는 시스템입니다.
exe, sh 등의 실행 파일이 서버에 업로드되어 실행되는 것을 방지하기 위한 관리 페이지를 제공합니다.

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

## 주요 기능

### 1. 고정 확장자 관리
- 7가지 확장자 제공: `bat`, `cmd`, `com`, `cpl`, `exe`, `scr`, `js`
- 체크박스로 차단 여부 설정
- 변경 사항 자동 저장 및 새로고침 시 유지

### 2. 커스텀 확장자 관리
- 사용자 정의 확장자 추가 (기본 최대 20자/ ExtensionConstants.EXTENSION_MAX_LENGTH)
- 기본 최대 200개까지 등록 가능 (ExtensionConstants.MAX_CUSTOM_EXTENSIONS)
- 개별 삭제 기능
- 추가된 확장자는 태그 형태로 표시

### 3. 커스텀 확장자 상수(갯수, 최대길이) 관리
- `ExtensionConstants` 클래스로 모든 상수를 중앙 관리
- 확장자 최대 길이(EXTENSION_MAX_LENGTH), 커스텀 확장자 최대 개수 (MAX_CUSTOM_EXTENSIONS) 등
- 변경 시 영향도를 주석으로 명시하여 안전한 유지보수 지원
- 해당 상수는 프론트엔드 UI에서도 이용중임.

### 4. 예외 처리
- 글로벌 예외 핸들러로 일관된 에러 응답
- 중복 확장자, 개수 초과, 찾기 실패 등 커스텀 예외 처리
- 사용자 친화적인 에러 메시지 제공

## 기술 스택

### Backend
- **Java**: 21
- **Spring Boot**: 3.5.7
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Hibernate Validator**

### Frontend
- **Thymeleaf**
- **HTML5/CSS3/JavaScript**
- **Fetch API**

### Build Tool
- **Gradle**: 8.14.3
- **Gradle Wrapper**: 포함



## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/flow/folwteamtest/
│   │   ├── common/          # 상수 관리
│   │   ├── controller/      # REST API 및 화면 컨트롤러
│   │   ├── service/         # 비즈니스 로직
│   │   ├── repository/      # 데이터 접근 계층
│   │   ├── entity/          # JPA 엔티티
│   │   ├── dto/             # 데이터 전송 객체
│   │   └── exception/       # 커스텀 예외
│   └── resources/
│       ├── application.properties
│       └── templates/
│           └── index.html
└── test/
```

## API 명세

자세한 API 명세는 [API.md](./API.md) 문서를 참고하세요.

## 개발 환경 설정

### IDE 설정
- IntelliJ IDEA 추천
- Lombok 플러그인 설치 필수
- Annotation Processing 활성화

### 환경 변수
별도 환경 변수 설정 불필요 (H2 인메모리 DB 사용)

## 향후 개선 방향
- [ ] 파일 업로드 기능과의 실제 연동
- [ ] 확장자별 차단 로그 이력 기록 및 조회
- [ ] 대량 확장자 일괄 등록 (CSV/Excel 업로드)
- [ ] 확장자 그룹 관리 기능 (예: 실행파일, 압축파일 등)
- [ ] 단위 테스트 및 통합 테스트 추가
- [ ] API 문서화 (Swagger/OpenAPI)

## 라이선스
This project is for assignment purposes only.

## 작성자
- dewawd@gmail.com(Dong Jae LEE)
- phone: 010-2088-6739
