# 프로젝트 구성

## 의존성

- 자바
  - Java 21
- test 환경
  - Junit5
  - h2 database
- RDB
  - JPA
  - QueryDSL
  - MySQL
- NoSQL
  - Redis
- WebSocket
  - Stomp
- Lombok

## 프로젝트 구조

- Clean Architecture 적용
- 도메인별 패키지 구조

## 개발 방식

- TDD 적용
- 외부 의존 없는 단위테스트 위주의 테스트 코드 작성
- 통합테스트는 **필요시** CI/CD 에서만 동작하도록 작성

## 형상관리 규칙

- Git Flow 적용
- squash merge 사용