<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="title" value="회원 가입" />

<%@ include file="../part/header.jspf"%>
<h1>내 정보</h1>

<div>
	<div>아이디 : ${loginedMember.loginId}</div>
	<div>이름 : ${loginedMember.name}</div>
	<div>생성일자 : ${loginedMember.regDate}</div>
	<div>별명 : ${loginedMember.nickname}</div>
	<div>이메일 : ${loginedMember.email}</div>
	<a href="modify">내 정보 변경</a>

</div>
<%@ include file="../part/footer.jspf"%>