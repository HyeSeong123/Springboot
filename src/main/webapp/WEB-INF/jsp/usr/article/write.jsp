<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="title" value="게시물 작성" />

<%@ include file="../part/header.jspf"%>
	<h1>게시물 작성화면</h1>
		<a href="./list">목록</a>
		
		<form action="doWrite" method="POST">
			<input type="hidden" name="boardNum" value="${board.num}" />
			<input type="hidden" name="boardCode" value="${board.code}" />
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
		
<%@ include file="../part/footer.jspf"%>