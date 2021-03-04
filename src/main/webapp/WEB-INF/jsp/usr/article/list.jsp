<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="게시물 리스트" />


<%@ include file="../part/header.jspf"%>
	

	<h1>게시물 리스트</h1>
	<h1>게시판 이름 : ${board.name}</h1>
	
	<h3>게시물 수 : ${totalCount}</h3>
	<a href="./write?listUrl=${encodedCurrentUri}&boardCode=${board.code}">글 작성</a>
	
	<hr></hr>
	<c:forEach items="${articles}" var="article">
	<c:set var="detailUrl" value="/usr/article-${board.code}/detail?num=${article.num}&listUrl=${encodedCurrentUri}" />
	<c:set var="count" value= "0" />
	
		<span> 게시물 번호 : ${article.num}</span>
		<span> 작성자 : ${article.extra.writer}</span>
		<span> 좋아요 : ${article.likePoint}</span>
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
			<select name="searchKeywordType" value="${param.searchKeywordType}">
				<c:if test="${param.searchKeywordType != null }" >
					<option value="${param.searchKeywordType}">${param.searchKeywordType}</option>
				</c:if>
				
				<c:if test="${param.searchKeywordType != '제목' }" >
					<option value="제목">제목</option>
				</c:if>
				
				<c:if test="${param.searchKeywordType != '내용' }" >
					<option value="내용">내용</option>
				</c:if>
				
				<c:if test="${param.searchKeywordType != '제목&내용' }" >
					<option value="제목과내용">제목&내용</option>
				</c:if>
				
				<c:if test="${param.searchKeywordType != '작성일' }" >
					<option value="작성일">작성일</option>
				</c:if>
			</select>
			${param.searchKeywordType}
			<input type="text"  placeholder="검색어를 입력하세요" name="searchKeyword" value="${param.searchKeyword}"/>
			<input type="submit" value="검색"/>
		</form>
	</div>
<%@ include file="../part/footer.jspf"%>
