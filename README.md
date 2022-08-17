# 나만의 블로그


![](https://velog.velcdn.com/images/lries7897/post/3dd4e309-1b2f-405a-a67f-0f72e69ba4c3/image.png)

[배포사이트 : http://3.39.195.61/](http://3.39.195.61/)

***

### 1. api 설계

* 요구사항 
> - 전체 게시글 목록 조회 API (전체 글 목록)
    - 제목, 작성자명, 작성 날짜를 조회하기
    - 작성 날짜 기준으로 내림차순 정렬하기    
>- 게시글 작성 API (글쓰기)
    - 제목, 작성자명, 비밀번호, 작성 내용을 입력하기    
>- 게시글 조회 API (게시글 상세보기)
    - 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기     
>- 게시글 비밀번호 확인 API (게시글 수정/삭제 권한 확인)
    - 비밀번호를 입력 받아 해당 게시글의 비밀번호와 일치여부 판단하기    
>- 게시글 수정 API (수정하기)
    - 제목, 작성자명, 비밀번호, 작성 내용을 수정되게 하기   
>- 게시글 삭제 API (삭제하기)
    - 글이 삭제되게 하기

* api 명세서
>|description|method|uri|request|response|
|-|-|-|-|-|
|전체조회|GET|/api/posts|-|[ { "id" : "1", "title" : "title1", "author" : "author1", "content" : "content1" } ]
|테스트1|테스트2|테스트3|
|테스트1|테스트2|테스트3|



* usecase
![](https://velog.velcdn.com/images/lries7897/post/b9e01b13-fdd8-4496-81b4-f989555b6c46/image.png)


***

### 2. trouble shooting
