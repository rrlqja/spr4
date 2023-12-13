<details>
<summary> <b>spr4</b> </summary>

게시글 -> 댓글, 댓글, 댓글 -> 댓글 작성자, 댓글 작성자, 댓글 작성자, ... n+1

aop 트랜잭션 전파

파일업로드 ckeditor
</details>


<details>
<summary><b>entity relationship diagram</b></summary>

![erd](/src/main/resources/content/spr4.png)
</details>

## 시나리오

### 회원
- [x] 로그인<br/>    
  ![login](/src/main/resources/content/login.gif)
- [x] 회원 가입<br/>  
  ![signup](/src/main/resources/content/signupEnd.gif)
  <details> 
  <summary><b>username 중복 검사</b></summary>
  
  ![validateUsername](/src/main/resources/content/validateUsername.gif)
  </details>
  <details>
  <summary><b>email 인증</b></summary> 
  
  ![verifyEmail](/src/main/resources/content/verifyEmail.gif)
  </details>
- [x] 회원 정보 관리
  <details>
  <summary><b>username 변경</b></summary> 

  ![verifyEmail](/src/main/resources/content/verifyEmail.gif)
  </details>
  <details>
  <summary><b>password 변경</b></summary> 

  ![verifyEmail](/src/main/resources/content/verifyEmail.gif)
  - [x] 기존 비밀번호, 새 비밀번호가 같으면 예외 발생 
  </details>
  <details>
  <summary><b>name 변경</b></summary> 

  ![verifyEmail](/src/main/resources/content/verifyEmail.gif)
  </details>
  <details>
  <summary><b>email 변경</b></summary> 

  ![verifyEmail](/src/main/resources/content/verifyEmail.gif)
  - [x] email 인증
  </details>
- [ ] sns 인증
- [x] 회원 탈퇴

### 사용자
- [x] username 찾기
- [x] 비밀번호 재설정

### 게시글
- [x] 게시글 작성
![saveBoard](/src/main/resources/content/saveBoard.gif)
  - [x] 게시글 내용에 이미지 첨부 가능


- [x] 게시글 수정

![editBoard](/src/main/resources/content/editBoard.gif)

- [x] 게시글 삭제

![deleteBoard](/src/main/resources/content/deleteBoard.gif)

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

[//]: # (![home]&#40;/src/main/resources/content/home.gif&#41;)



