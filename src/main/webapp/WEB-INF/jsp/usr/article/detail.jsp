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
		<a onclick="if( confirm('삭제하시겠습니까?') == false) return false;" href="./doDelete?num=${article.num}">삭제</a>
		<a href="./modify?num=${article.num}"> 수정</a>
		<div> 게시물 번호 : ${article.num}</div>
		<div> 게시물 작성일 : ${article.regDate}</div>
		<div> 게시물 수정일 : ${article.updateDate}</div>
		<div> 게시물 제목 : ${article.title}</div>
		<div> 게시물 내용 : ${article.body}</div>
	
</body>
</html>