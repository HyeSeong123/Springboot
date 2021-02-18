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
	<h1>게시물 상세화면</h1>
		<a href="./list">목록</a>
		
		<div> 게시물 번호 : ${article.num}</div>
		
		<h1>수정 목록</h1>
		

		<form action="doModify?num=${article.num}&title=${article.title}&body=${article.body}" method="POST">
			<input type="hidden" name="num" value="${article.num}"/>
			
			<input type="text" name="title" value="${article.title}"/>
			<br />
			<input type="text" name="body" value="${article.body}"/>
			
			<input type="submit" value="제출" />
		</form>
	
</body>
</html>