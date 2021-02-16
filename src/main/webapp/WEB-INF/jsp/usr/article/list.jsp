<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트 화면</title>
</head>
<body>
	<h1>리스트 화면</h1>

	<c:forEach items="${articles}" var="article">
		<div>
			<div>번호 : ${article.num}</div>
			<div>작성일 : ${article.regDate}</div>
			<div>수정일 : ${article.updateDate}</div>
			<div>제목 : ${article.title}</div>
			<div>내용 : ${article.body}</div>
			<hr />
		</div>
	</c:forEach>
</body>
</html>