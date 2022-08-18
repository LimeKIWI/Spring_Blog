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

|description|method|uri|request|response|
|:---:|:---:|:---:|:---:|:---:|
|전체조회|GET|/api/posts|-|[ {"id": 2, "title": "제목2", "author": "작성자2", "createAt": "2022-08-16T00:10:00", "modifiedAt": "2022-08-16T00:10:00" }, {"id": 1, "title": "제목1", "author": "작성자1", "createAt": "2022-08-16T00:00:00", "modifiedAt": "2022-08-16T00:00:00" } ]
|글작성|POST|/api/posts|{ "title": "제목1", "author": "작성자1", "content": "내용1", "password": "비밀번호1"}|{"id": 1, "title": "제목1", "author": "작성자1", "content": "내용1", "createAt": "2022-08-16T00:00:00", "modifiedAt": "2022-08-16T00:00:20" }|
|글조회|GET|/api/posts/{id}|-|{"id": 1, "title": "제목1", "author": "작성자1", "content": "내용1", "createAt": "2022-08-16T00:00:00", "modifiedAt": "2022-08-16T00:00:20" }|{"id": 1, "title": "제목1", "author": "작성자1", "content": "내용1", "createAt": "2022-08-16T00:00:00", "modifiedAt": "2022-08-16T00:00:20" }|
|비밀번호확인|POST|/api/auth/{id}|{"password": "비밀번호1"}|true|
|글수정|POST|/api/posts/{id}|{ "title": "제목1", "author": "작성자1", "content": "내용1", "password": "비밀번호1"}|{"id": 1, "title": "제목1", "author": "작성자1", "content": "내용1", "createAt": "2022-08-16T00:00:00", "modifiedAt": "2022-08-16T00:00:20" }|
|글삭제|DELETE|/api/posts/{id}|-|1|



* usecase

![](https://velog.velcdn.com/images/lries7897/post/b9e01b13-fdd8-4496-81b4-f989555b6c46/image.png)


***

### 2. 질문과 답변
**1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)	**
	
    수정api는 param과 body를 이용하여 구현되었습니다. param으로 글id를 uri뒤에 붙여서 controller에 전달하였고
    body에 수정사항을 json문자열로 바꾸어 넣어서 전달하였습니다.
    삭제api는 삭제할 타깃의 id만 가지면 되므로 param에 id만 붙여서 controller에 전달하였습니다.
    이후 요청사항의 실제수행은 service에서 이루어졌습니다.
	
**2. 어떤 상황에 어떤 방식의 request를 써야하나요?**

	GET이나 DELETE등을 사용하며 특정id를 가진 객체를 찾고 싶다면 param에 id를 담아 요청을 보내거나 
    query방식으로 경로뒤에 ?id=123 등으로 적어서 변수를 전달할 때 사용할 수 있습니다. 
    POST나 PUT으로 데이터를 보내고 싶다면 body에 json형식의 데이터를 담아 요청을 전달하면 됩니다. 
    
**3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?**

	RESTful API를 따라서 CRUD를 설계하였습니다
    생성에 POST, 읽기에 GET, 수정에 PUT, 삭제에 DLELTE를 사용했습니다.
    비밀번호 확인 기능의 uri를 처음 설계당시 /api/auth/로 지정하여 그대로 구현하였지만
    전달하고자하는 데이터의 명사를 쓰는게 나으므로 /api/password/로 썼으면 더 좋았을 것 같습니다.      

**4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)**

	Controller에서 요청들을 받고 각 요청에맞는 수행은 service를 호출하여
    요청의 실제수행은 Service에서 이루어지도록 하였습니다.
    Service에서 요청을 수행하다가 db에 접근이 필요하다면 Repository를 이용해 db로 접근합니다.
    
**5. 작성한 코드에서 빈(Bean)을 모두 찾아보세요!**

	bean은 자바에서 객체를 관리해주는 것으로 
    new등의 객체생성과정을 생략할 수 있습니다.
    @Component 어노테이션이 있다면 그 클래스를 빈으로 등록할 수 있고
    컴포넌트 스캔을 통해 빈으로 등록이 가능합니다.
    @Service, @Controller, @Repository, @Configuration는 Component 어노테이션이 포함되어
    컴포넌트 스캔에 포함되게 되며
	작성코드에서는 @RestController, @Service가 빈에 포함됩니다.
    
**6. API 명세서 작성 가이드라인을 검색하여 직접 작성한 명세서와 비교해보세요!**

	여러 예시들과 가이드라인에 공통적으로 들어가는 기능이름, 메소드, URI, 요청, 응답을 작성하였습니다.
    요청은 임의의 JSON 데이터 예시를 적었고 응답으로 요청에따른 예상 반환값을 적었습니다.

### 3. trouble shooting
