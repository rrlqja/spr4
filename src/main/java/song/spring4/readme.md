## 엔티티

### User
- 속성
  - id(Long): 식별자
  - username(String): 로그인 아이디
  - password(String): 비밀번호
  - name(String): 회원 이름
  - email(String): 회원 이메일
  - isAccountNonExpired(boolean): 계정이 만료 여부
  - isAccountNonLocked(boolean): 계정이 잠김 여부
  - isCredentialsNonExpired(boolean): 인증 정보 만료 여부
  - isEnabled(boolean): 계정 활성화 여부
- 연관관계
  - UserRoleList(List<`UserRole`>): 회원 역할 리스트 `@OneToMany`

### UserRole
- 속성
  - id(Long): 식별자
  - Role(Enum): 역할
- 연관관계
  - User(`User`): 역할이 부여된 회원 `@ManyToOne`

### Board
- 속성
  - id(Long): 식별자
  - title(String): 제목
  - content(String): 내용
  - view(Integer): 조회수
- 연관관계
  - user(`User`): 작성자 `@ManyToOne`
  - commentList(List<`Comment`>): 댓글 리스트 `@OneToMany`

### Comment
- 속성
  - id(Long): 식별자
  - content(String): 내용
- 연관관계
  - user(`User`): 작성자 `@ManyToOne`
  - board(`Board`): 댓글이 달린 게시글 `@ManyToOne`
  - replyList(List<`Reply`>): 대댓글 리스트 `@OneToMany`

### Reply
- 속성
  - id(Long): 식별자
  - content(String): 내용
- 연관관계
  - user(`User`): 작성자 `@ManyToOne`
  - comment(`Comment`): 대댓글이 달린 댓글 `@ManyToOne`

### EmailVerificationToken
- 속성
  - id(Long): 식별자
  - email(String): 이메일
  - token(String): 인증 토큰
  - isVerified(boolean): 인증 여부
  - createTime(LocalDateTime): 생성 시간
  - expiryTime(LocalDateTime): 만료 시간

### ResetPasswordToken
- 속성
  - id(Long): 식별자
  - email(String): 이메일
  - token(String): 인증 토큰
  - createTime(LocalDateTime): 생성 시간
  - expiryTime(LocalDateTime): 만료 시간

### FileEntity
- 속성
  - id(Long): 식별자
  - uploadFileName(String): 업 로드시 파일 이름
  - saveFileName(String): 저장된 파일 이름
- 연관관계
  - board(`Board`): 파일이 사용된 게시글

## 기능

### 회원
- [ ] 회원 가입
  - [ ] username 중복 검사 (view)
  - [ ] email 인증 (view)
- [ ] 회원 정보 관리
  - [ ] username 변경
    - [ ] username 중복 검사
  - [ ] password 변경
    - [ ] 기존 비밀번호, 새 비밀번호가 같으면 예외 발생
  - [ ] name 변경
  - [ ] email 변경
    - [ ] email 인증 (view)
- [ ] sns 인증
- [ ] 회원 탈퇴

### 사용자
- [ ] username 찾기
- [ ] 비밀번호 재설정

### 게시글
- [ ] 게시글 작성
  - [ ] 게시글 내용에 이미지 첨부 가능
- [ ] 게시글 수정
  - [ ] 게시글 제목, 본문 수정
- [ ] 게시글 삭제

### 댓글
- [ ] 댓글 작성
- [ ] 댓글 수정
- [ ] 댓글 삭제

### 대댓글
- [ ] 대댓글 작성
- [ ] 대댓글 수정
- [ ] 대댓글 삭제

### 관리자
- [ ] 회원 계정 관리
  - [ ] 회원 계정 활성화
  - [ ] 회원 계정 비활성화
- [ ] 게시글 삭제
- [ ] 댓글 삭제







