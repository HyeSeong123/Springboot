<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="메인화면" />


<%@ include file="../part/header.jspf"%>
	<h1>${title}</h1>
	
	<a href="../article/list">게시물 리스트</a>
<%@ include file="../part/footer.jspf"%>
