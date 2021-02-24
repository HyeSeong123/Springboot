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
	<c:set var="detailUrl" value="/usr/article/detail?num=${article.num}&listUrl=${encodedCurrentUri}" />
	<c:set var="count" value= "0" />
	
		<span> 게시물 번호 : ${article.num}</span>
		<span> 작성자 : ${article.extra.writer}</span>
		<span> 게시물 작성일 : ${article.regDate}</span>
		<span> 게시물 수정일 : ${article.updateDate}</span>
		<a href="${detailUrl}"> 게시물 제목 : ${article.title}</a>
		
		<c:forEach items="${replies}" var="reply">
			<c:if test= "${reply.relNum == article.num}" >
				<c:set var="count" value= "${count+1}" />			
			</c:if>
		</c:forEach>
		[<c:out value="${count}" />]
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
	
	<div>
		<form>
			<input type="text"  placeholder="검색어를 입력하세요" name="searchKeyword" value="${param.searchKeyword}"/>
			<input type="submit" value="검색"/>
		</form>
	</div>
<%@ include file="../part/footer.jspf"%>
