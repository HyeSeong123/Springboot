<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="title" value="아이디 찾기" />
<%@ include file="../part/header.jspf"%>

	<h1>로그인</h1>
	<script>
		function findLoginFormSubmit(form){
			let doFindLoginForm_submited = false;
			
			if (doFindLoginForm_submited) {
					alert('처리중입니다.');
					return;
				}
				
			form.name.value = form.name.value.trim();
			
			if ( form.name.value.length == 0 ){
				alert('이름을 입력해주세요');
				form.name.focus();
				return;
			}
			
			form.email.value = form.email.value.trim();
			
			if ( form.email.value.length == 0 ){
				alert('이메일을 입력해주세요');
				form.email.focus();
				return;
			}
			
			form.submit();
			doFindLoginForm_submited = true;
		}
	</script>
	<div>

		<form action="findLogin" method="POST" onsubmit="findLoginFormSubmit(this); return false;">
			
			<input type="text" name="listUrl" value="${encodedPreUrl}" readonly />
			
			<div>
				아이디 : <input type="text" name="name" placeholder="아이디를 입력해주세요" />
			</div>

			<div>
				비밀번호 : <input type="password" name="email" placeholder="비밀번호를 입력해주세요" />
			</div>

			<div>
				<input type="submit" value="아이디 찾기" />
			</div>

		</form>
	</div>
	
<%@ include file="../part/footer.jspf"%>