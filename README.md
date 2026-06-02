# 🏫 삼육대학교 공지사항 AI 앱 - Frontend

## 프로젝트 소개

삼육대학교 학생들을 위해 흩어져 있는 학사/장학/행사 공지사항을 한곳에서 모아보고, AI(GPT)를 통해 복잡한 공지 내용을 빠르고 직관적으로 요약해 주는 네이티브 안드로이드 애플리케이션입니다.

## 팀원

| 이름 | 담당 |
| --- | --- |
| **이정우** | 안드로이드 UI/UX 설계, Retrofit API 연동, AI 요약 렌더링 처리, 상태별 화면(Loading/Error) 제어 |

## 기술 스택

| 분류 | 기술 |
| --- | --- |
| **Framework** | Android (Native) |
| **Language** | Java |
| **Network** | Retrofit2, Gson |
| **UI/UX** | Material Design, ConstraintLayout, Markwon |

## 프로그램 구조

```text
src/main/java/com/example/capstonedesign/
├── MainActivity.java            # 메인 화면 UI 및 API 통신 제어
├── NoticeAdapter.java           # 공지사항 리스트(RecyclerView) 어댑터
├── NoticeService.java           # 백엔드 API 엔드포인트 인터페이스
├── RetrofitClient.java          # Retrofit 네트워크 통신 클라이언트 설정
└── models/
    ├── Notice.java              # 공지사항 데이터 모델
    ├── ChatRequest.java         # AI 요약 요청 데이터 모델
    └── ChatResponse.java        # AI 요약 및 공지 목록 응답 모델

src/main/res/
├── layout/
│   ├── activity_main.xml        # 메인 화면 레이아웃 (검색창, 요약 UI, 목록)
│   └── item_notice.xml          # 공지사항 리스트 단일 항목 레이아웃
└── drawable/
    └── loading.gif              # 통신 대기 상태 로딩 애니메이션
    └── empty_image.png          # 결과 없음 상태 이미지
    └── error_image.png          # 에러 발생 상태 이미지

```

## 주요 기능

* **빠른 키워드 검색 및 칩(Chip) UI** <br>
  자주 찾는 카테고리(장학금, 학사일정, 수강신청 등)를 터치 한 번으로 빠르게 검색할 수 있는 칩 버튼 제공
<br><br>
* **AI 공지사항 요약 (Markdown 렌더링)** <br>
  서버로부터 전달받은 AI 요약 데이터를 `Markwon` 라이브러리를 활용해 높은 가독성의 마크다운 포맷(볼드, 리스트 등)으로 화면에 출력
  텍스트 길이에 따른 UI 겹침 방지를 위해 동적 스크롤(Max Height & MovementMethod) 적용
<br><br>
* **비동기 네트워크 통신 (Retrofit2)** <br>
  단일 통신(API 호출)으로 AI 요약 텍스트와 공지사항 리스트를 동시에 수신하여 데이터 렌더링 속도 최적화
<br><br>

* **화면 상태(State) 제어 시스템**
  검색 시나리오에 따라 화면을 4가지 상태(`Start`, `Loading`, `Success`, `Error / Empty`)로 나누어 사용자 경험(UX) 극대화
  통신 실패 시 예외 처리 및 이전 검색어 기반의 '다시 시도' 로직 구현
