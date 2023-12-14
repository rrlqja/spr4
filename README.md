<b>spr4</b>     

게시글 -> 댓글, 댓글, 댓글 -> 댓글 작성자, 댓글 작성자, 댓글 작성자, ... n+1

aop 트랜잭션 전파

파일업로드 ckeditor


## entity relationship diagram

![erd](/src/main/resources/content/spr4.png)

## 시나리오

### 홈
![home](/src/main/resources/content/home.gif)


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

  ![updateUsername](/src/main/resources/content/updateUsername.gif)
  </details>
  <details>
  <summary><b>password 변경</b></summary> 

  ![updatePassword](/src/main/resources/content/updatePassword.gif)
  - [x] 기존 비밀번호, 새 비밀번호가 같으면 예외 발생 
  </details>
  <details>
  <summary><b>name 변경</b></summary> 

  ![updateName](/src/main/resources/content/updateName.gif)
  </details>
  <details>
  <summary><b>email 변경</b></summary> 

  ![updateEmail](/src/main/resources/content/updateEmail.gif)
  - [x] email 인증
  </details>
- [ ] sns 인증
- [x] 회원 탈퇴<br/>    
  ![deleteUser](/src/main/resources/content/deleteUser.gif)

### 사용자
- [x] username 찾기<br/>    
  ![findUsername](/src/main/resources/content/findUsername.gif)
- [x] 비밀번호 재설정<br/>    
  ![findPassword](/src/main/resources/content/findPassword.gif)

### 게시글
- [x] 게시글 작성<br/>    
  ![saveBoard](/src/main/resources/content/saveBoard.gif)
  - [x] 게시글 내용에 이미지 첨부 가능
- [x] 게시글 수정<br/>    
  ![editBoard](/src/main/resources/content/editBoard.gif)
- [x] 게시글 삭제<br/>    
  ![deleteBoard](/src/main/resources/content/deleteBoard.gif)
- [x] 게시글 검색<br/>    
  <details>
  <summary><b>제목 검색</b></summary> 

  ![findBoardByTitle](/src/main/resources/content/findBoardByTitle.gif)
  </details>
  <details>
  <summary><b>내용 검색</b></summary> 

  ![findBoardByContent](/src/main/resources/content/findBoardByContent.gif)
  </details>
  <details>
  <summary><b>작성자 검색</b></summary> 

  ![findBoardByWriter](/src/main/resources/content/findBoardByWriter.gif)
  </details>

### 댓글
- [x] 댓글 작성<br/>    
  ![saveComment](/src/main/resources/content/saveComment.gif)
- [x] 댓글 수정<br/>    
  ![editComment](/src/main/resources/content/editComment.gif)
- [x] 댓글 삭제<br/>    
  ![deleteComment](/src/main/resources/content/deleteComment.gif)

### 대댓글
- [x] 대댓글 작성<br/>    
  ![saveReply](/src/main/resources/content/saveReply.gif)
- [x] 대댓글 수정<br/>    
  ![editReply](/src/main/resources/content/editReply.gif)
- [x] 대댓글 삭제<br/>    
  ![deleteReply](/src/main/resources/content/deleteReply.gif)

### 관리자
- [ ] 회원 계정 관리
    - [ ] 회원 계정 활성화
    - [ ] 회원 계정 비활성화
- [ ] 게시글 삭제
- [ ] 댓글 삭제




