## Open Market Project
- Commerce 가 제공하는 기능들을 직접 구현함으로써 Commerce 도메인을 이해하자
- 대규모 트래픽에도 견고한 어플리케이션을 구현하자 


## 기능
#### 고객
- 상품조회, 구매
- 장바구니
- 회원정보 수정, 회원 탈퇴
#### 판매자
- 상품 등록,수정,삭제
#### 관리자
- 회원관리
- 광고비에 따른 상품노출 
#### 자주보여주는 데이터 캐싱
- 메인화에 보여주는 데이터
- 핫딜 상품 데이터  
#### 이벤트
- 선착순 이벤트
- 핫딜

 
## 기술적인 문제 해결 과정
#### 회원 데이터에 대한 접근 분리 (회원/관리자)
- 회원 정보를 같은 컨트롤러에서 처리 시 회원이 엔드포인트에 접근하는 경우 발생
- 복잡해지는 인가 로직 -> 컨트롤러 분리
- 회원은 회원 id(PK)값으로 접근하는 것이 아닌 Session 에서 현재 사용자를 불러와 접근
- Adapter 패턴을 이용하여 Account 객체를 스프링 시큐리티가 알고있는 UserDetails로 구현
- User(UserDetails 구현체)를 통해 DB에서 Account를 불러오는 과정을 생략 할 수있음 -> DB에 대한 부하를 줄일 수 있었다 
- 관련 포스팅 : [@AuthenticationPrincipal - 현재 사용자 조회하기](https://jjeda.tistory.com/7) 
#### 개인정보 보호
- 비밀번호와 같이 중요한 개인정보가 Response Message 에 포함되어 주고받는 문제
- TODO

## USECASE
- 고객
  - 고객은 회원가입, 회원수정, 회원탈퇴를 할 수 있다.
  - 고객은 (랭킹, 가격, 등록) 등의 옵션으로 상품목록를 카테고리별로 볼 수 있다.
  - 고객은 카테고리별로 브랜드 목록을 볼 수있고(랭킹순), 해당 브랜드를 클릭하면 판매중인 상품목록을 볼 수 있다.
    - 신발 카테고리 : 나이키, 반스, 아디다스  
  - 고객은 원하는 물건을 검색하여 물건을 판매하는 판매자의 목록을 불러올 수 있다.
  - 고객은 장바구니를 통해 원하는 상품을 구매 전에 확인 할 수 있다.
- 판매자
  - 판매자는 판매하고 싶은 물건을 등록, 수정, 삭제 할 수 있다.
  - 판매자는 판매하고 싶은 물건에 대해 광고 및 이벤트를 진행 할 수 있다.
- 관리자
  - 관리자는 고객/판매자의 상태를 관리 할 수있다.
    - BAN, NORMAL
