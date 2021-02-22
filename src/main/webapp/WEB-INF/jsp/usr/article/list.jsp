<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="게시물 리스트" />


<%@ include file="../part/header.jspf"%>
	

	<h1>게시물 리스트</h1>
	<h3>게시물 수 : ${totalCount}</h3>
	<a href="./write">글 작성</a>
	
	<hr></hr>
	<c:forEach items="${articles}" var="article">
		<span> 게시물 번호 : ${article.num}</span>
		<span> 작성자 : ${article.extra.writer}</span>
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
			<a class="${className}" href="list?page=${i}&searchKeyword=${param.searchKeyword}">${i}</a>

		</c:forEach>

		<c:if test="${totalPage > 1 && page+10 < totalPage}">
			<c:if test="${(page+10) > totalPage}">
				<a href="list?page=${totalPage}&searchKeyword=${param.searchKeyword}">▶▶</a>
			</c:if>

			<c:if test="${(page+10) < totalPage}">
				<a href="list?page=${page+10}&searchKeyword=${param.searchKeyword}">▶▶</a>
			</c:if>
		</c:if>

	</div>
<%@ include file="../part/footer.jspf"%>
