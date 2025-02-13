## 명언 게시판 요구사항

### 기본 사항
1. 작가명(author)와 명언 내용(content)에는 특수문자를 입력하지 않는다.
2. 최대한 라이브러리를 사용하지 않고 구현한다.
    - 단, 자바에 기본적으로 내장된 기능은 최대한 활용 가능.
3. AssertJ를 테스트 의존성으로 사용:
    - `testImplementation("org.assertj:assertj-core:3.26.3")`

---

### 단계별 구현

#### 1단계: 종료 명령
- 프로그램은 “종료” 명령을 처리한다.

#### 2단계: 등록
- 명언과 작가명을 입력받아 명언을 등록한다.

#### 3단계: 등록된 명언 번호 노출
- 등록된 명언의 고유 번호를 사용자에게 출력한다.

#### 4단계: 번호 증가
- 등록할 때마다 고유 번호가 1씩 증가하여 부여된다.

#### 5단계: 목록 보기
- 등록된 모든 명언을 최신 순으로 출력한다.
    - 출력 형식: `번호 / 작가 / 명언`

#### 6단계: 명언 삭제
- 특정 번호의 명언을 삭제할 수 있다.
- 삭제 후에도 삭제된 번호는 재사용하지 않는다.

#### 7단계: 삭제 예외 처리
- 존재하지 않는 번호를 삭제하려 할 경우 적절한 메시지를 출력한다.
- 삭제된 번호는 재사용하지 않는다.

#### 8단계: 명언 수정
- 특정 번호의 명언과 작가를 수정할 수 있다.
- 수정 시 기존 데이터도 함께 보여준다.

#### 9단계: 파일을 통한 영속성
- 명언 데이터를 파일로 저장하고 관리한다.
    - 각 명언은 `db/wiseSaying/{번호}.json`에 저장.
    - 마지막 생성된 명언 번호는 `db/wiseSaying/lastId.txt`에 저장.
- 명언 추가/수정 시 파일이 갱신된다.

#### 10단계: 데이터 빌드
- 모든 명언을 `data.json` 파일에 통합 저장.
- 빌드 명령 실행 시 `data.json` 갱신.

#### 11단계: MVC 구조 도입
- `Controller`, `Service`, `Repository` 구조를 도입.
    - **Main**: 프로그램 실행.
    - **App**: 사용자 입력 처리 및 컨트롤러 호출.
    - **Controller**: 명령 처리.
    - **Service**: 비즈니스 로직 처리.
    - **Repository**: 데이터 조회/수정/삭제/생성 처리.
    - **WiseSaying**: 명언 객체.

#### 12단계: 테스트 주도 개발 (TDD)
- JUnit을 사용해 통합 테스트 진행.
- 명령어별 테스트 작성.

#### 13단계: 검색 기능 추가
- 특정 키워드로 명언 검색 가능:
    - 검색 타입: `content` 또는 `author`.
    - 검색어를 포함한 데이터만 출력.

#### 14단계: 페이징 기능 추가
- 목록 출력 시 페이징 지원:
    - 한 페이지에 최대 5개의 명언 표시.
    - 페이지 번호 생략 시 1페이지로 간주.
    - 최신 명언이 우선 출력.
- 검색 결과도 페이징 지원.

---

### 파일 구조
- `db/wiseSaying/`
    - `{번호}.json`: 명언 데이터 파일.
    - `lastId.txt`: 마지막 생성된 명언 번호.
- `data.json`: 모든 명언을 통합 저장.

#### 파일 예시
- `1.json`:
```json
{
  "id": 1,
  "content": "현재를 사랑하라.",
  "author": "작자미상"
}
```
- `lastId.txt`:
```
2
```
- `data.json`:
```json
[
  {
    "id": 1,
    "content": "현재를 사랑하라.",
    "author": "작자미상"
  },
  {
    "id": 2,
    "content": "과거에 집착하지 마라.",
    "author": "작자미상"
  }
]
```

