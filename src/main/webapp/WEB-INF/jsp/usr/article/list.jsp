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
	<h3>게시물 수 : ${totalCount}</h3>
	<a href="./write">글 작성</a>
	<a href="../member/join">회원 가입</a>
	<hr></hr>
	<c:forEach items="${articles}" var="article">
		<span> 게시물 번호 : ${article.num}</span>
		<span> 게시물 작성일 : ${article.regDate}</span>
		<span> 게시물 수정일 : ${article.updateDate}</span>
		<a href="./detail?num=${article.num}"> 게시물 제목 : ${article.title}</a>
		<hr />
	</c:forEach>

	<style>
.selected {
	color: red;
}
</style>

	<div>

		<c:if test="${page > 10}">
			<c:if test="${(page-10) < 1}">
				<a href="list?page=${page = 1}">◀◀</a>
			</c:if>
			<c:if test="${(page-10) > 1}">
				<a href="list?page=${page-10}">◀◀</a>
			</c:if>
		</c:if>

		<c:forEach var="i" begin="${pageMenuStart}" end="${pageMenuEnd}">

			<c:set var="className" value="${i==page ? 'selected' : ''}" />
			<a class="${className}" href="list?page=${i}">${i}</a>

		</c:forEach>

		<c:if test="${totalPage > 1 && page+10 < totalPage}">
			<c:if test="${(page+10) > totalPage}">
				<a href="list?page=${totalPage}">▶▶</a>
			</c:if>

			<c:if test="${(page+10) < totalPage}">
				<a href="list?page=${page+10}">▶▶</a>
			</c:if>
		</c:if>

	</div>
</body>
</html>