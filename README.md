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

* usecase
%3CmxGraphModel%3E%3Croot%3E%3CmxCell%20id%3D%220%22%2F%3E%3CmxCell%20id%3D%221%22%20parent%3D%220%22%2F%3E%3CmxCell%20id%3D%222%22%20value%3D%22%22%20style%3D%22rounded%3D0%3BwhiteSpace%3Dwrap%3Bhtml%3D1%3BlabelBackgroundColor%3Dnone%3B%22%20vertex%3D%221%22%20parent%3D%221%22%3E%3CmxGeometry%20x%3D%2220%22%20y%3D%2250%22%20width%3D%22910%22%20height%3D%22760%22%20as%3D%22geometry%22%2F%3E%3C%2FmxCell%3E%3C%2Froot%3E%3C%2FmxGraphModel%3E

***

### 2. trouble shooting
