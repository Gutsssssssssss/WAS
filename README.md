# WAS 직접 구현하기

## 프로젝트 목적

1. HTTP 프로토콜 학습: HTTP 요청/응답 구조와 동작 원리를 이해한다.
2. 웹 서버 동작 원리 학습
3. 클린 코드와 리팩토링: SOLID 원칙을 따르고, TDD를 통한 유지보수 가능한 코드 작성한다.

## 기술 스택

* 언어: Java
* 빌드 도구: Gradle
* 테스트: JUnit5
* IDE: IntelliJ IDEA
* 버전 관리: Git

## 프로그래밍 요구 사항

1. 유닛 테스트를 구현한다.
2. 코드 컨벤션을 지킨다.


## 단계별 요구사항

### 1단계: HTTP 프로토콜 이해 및 요청 파싱 구현

* 요청에서 RequestLine 파싱
    * HTTP 메서드, 경로, 프로토콜 버전 파싱(get, post)
* Query String 파싱 
    * name1=value1&name2=value2 같은 형식
* 예외 처리
    * 올바르지 않은 HTTP 메서드 또는 프로토콜 형식에 대한 예외 발생


### 2단계: HTTP 웹 서버 기본 기능 구현

* 정적 파일 서빙
    * URL에 해당하는 파일 src/main/resources/static 디렉토리에서 찾아 응답
    * 파일이 없을 때 적절한 응답 코드 반환
* 동적 라우팅
    * URL과 매핑된 Controller 실행
    * GET/POST 요청에 따라 처리 로직 분리

### 3단계: 동적 HTML

* 회원 가입 기능
    * 완료시 index.html 리다이렉트
* 사용자 인증 기능
    * 성공시 index.html로 이동
    * 실패시 실패 화면으로 이동
* 사용자 목록 조회
    * 로그인하지 않은 상태면 login.html로 이동
    * 동적 html 엔진 (handlebars.java.template engine) 활용
    * stylesheet 파일을 지원하도록 구현

### 4단계: 웹 서버 리팩토링

* 향후 결정

### 5단계: Thread Pool 적용

* Thread Pool을 적용해서 WAS가 동시 접속자를 안정적으로 처리하도록 구현
    * 최대 크기 250
    * 모든 Thread가 Busy 상태면 100까지 대기 상태가 되도록 구현
* RestTemplate을 활용하여 최대 Thread Pool 수 보다 많은 동시 요청