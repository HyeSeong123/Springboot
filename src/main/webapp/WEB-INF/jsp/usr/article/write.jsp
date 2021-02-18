<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 리스트</title>
</head>
<body>
	<h1>게시물 작성화면</h1>
		<a href="./list">목록</a>
		
		<form action="doWrite" method="POST">
		
			<div>
				제목 : <input type="text" maxlength="30" name="title" />
			</div>
			
			<div>
				내용 : <input type="text" maxlength="30" name="body" />
			</div>
			
			<div>
				제출 <input type="submit" value="작성"/>
			</div>
		</form>
		<div> 게시물 번호 : ${article.num}</div>
		
	
</body>
</html>