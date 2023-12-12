<details>
<summary> <b>spr4</b> </summary>

게시글 -> 댓글, 댓글, 댓글 -> 댓글 작성자, 댓글 작성자, 댓글 작성자, ... n+1

aop 트랜잭션 전파

파일업로드 ckeditor
</details>

<details>
<summary><b>엔티티</b></summary>

<details>
<summary><b>User</b></summary>

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
</details>
<details>
<summary><b>UserRole</b></summary>

- 속성
    - id(Long): 식별자
    - Role(Enum): 역할
- 연관관계
    - User(`User`): 역할이 부여된 회원 `@ManyToOne`
</details>
<details>
<summary><b>Board</b></summary>

- 속성
    - id(Long): 식별자
    - title(String): 제목
    - content(String): 내용
    - view(Integer): 조회수
- 연관관계
    - user(`User`): 작성자 `@ManyToOne`
    - commentList(List<`Comment`>): 댓글 리스트 `@OneToMany`
</details>
<details>
<summary><b>Comment</b></summary>

- 속성
    - id(Long): 식별자
    - content(String): 내용
- 연관관계
    - user(`User`): 작성자 `@ManyToOne`
    - board(`Board`): 댓글이 달린 게시글 `@ManyToOne`
    - parent(`Comment`): 부모 댓글 `@ManyToOne`
    - replyList(List<`Comment`>): 대댓글 리스트 `@OneToMany`
</details>
<details>
<summary><b>EmailVerificationToken</b></summary>

- 속성
    - id(Long): 식별자
    - email(String): 이메일
    - token(String): 인증 토큰
    - createTime(LocalDateTime): 생성 시간
    - expiryTime(LocalDateTime): 만료 시간
- 역할
    - 회원가입, 이메일 변경시 이메일 인증에 사용되는 엔티티
    - 입력받은 이메일로 토큰이 전송되고 토큰인증을 거쳐야 한다.
</details>
<details>
<summary><b>ResetPasswordToken</b></summary>

- 속성
    - id(Long): 식별자
    - email(String): 이메일
    - token(String): 인증 토큰
    - createTime(LocalDateTime): 생성 시간
    - expiryTime(LocalDateTime): 만료 시간
- 역할
    - 비밀번호를 초기시 사용되는 엔티티
    - 입력받은 이메일로 새로운 비밀번호 생성 주소가 전송된다.
</details>
<details>
<summary><b>FileEntity</b></summary>

- 속성
    - id(Long): 식별자
    - uploadFileName(String): 업 로드시 파일 이름
    - saveFileName(String): 저장된 파일 이름
- 연관관계
    - board(`Board`): 파일이 사용된 게시글
- 역할
    - 서버에 업 로드된 파일과 게시글을 연결하여 관리하기위한 엔티티
</details>

</details>

## 기능

### 회원
- [x] 회원 가입
    - [x] username 중복 검사 (view)
    - [x] email 인증 (view)
- [x] 회원 정보 관리
    - [x] username 변경
        - [x] username 중복 검사
    - [x] password 변경
        - [x] 기존 비밀번호, 새 비밀번호가 같으면 예외 발생
    - [x] name 변경
    - [x] email 변경
        - [x] email 인증 (view)
- [ ] sns 인증
- [x] 회원 탈퇴

### 사용자
- [x] username 찾기
- [x] 비밀번호 재설정

### 게시글
- [x] 게시글 작성
    - [x] 게시글 내용에 이미지 첨부 가능
- [x] 게시글 수정
    - [x] 게시글 제목, 본문 수정
- [x] 게시글 삭제

### 댓글
- [x] 댓글 작성
- [x] 댓글 수정
- [x] 댓글 삭제

### 대댓글
- [x] 대댓글 작성
- [x] 대댓글 수정
- [x] 대댓글 삭제

### 관리자
- [ ] 회원 계정 관리
    - [ ] 회원 계정 활성화
    - [ ] 회원 계정 비활성화
- [ ] 게시글 삭제
- [ ] 댓글 삭제

![home](/src/main/resources/content/home.gif)



