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
	<h1>게시물 리스트</h1>
		<a href="./write">글 작성</a>
		<hr></hr>
	<c:forEach items="${articles}" var="article">
		<span> 게시물 번호 : ${article.num}</span>
		<span> 게시물 작성일 : ${article.regDate}</span>
		<span> 게시물 수정일 : ${article.updateDate}</span>
		<a href="./detail?num=${article.num}"> 게시물 제목 : ${article.title}</a>
		<hr />
	</c:forEach>
	
</body>
</html>