<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<c:set var="title" value="로그인" />
<%@ include file="../part/header.jspf"%>

	<h1>로그인</h1>
	<script>
		function loginFormSubmit(form){
			let doLoginForm_submited = false;
			
			if (doLoginForm_submited) {
					alert('처리중입니다.');
					return;
				}
				
			form.loginId.value = form.loginId.value.trim();
			
			if ( form.loginId.value.length == 0 ){
				alert('로그인 아이디를 입력해주세요');
				form.loginId.focus();
				return;
			}
			
			form.loginPw.value = form.loginPw.value.trim();
			
			if ( form.loginPw.value.length == 0 ){
				alert('로그인 패스워드를 입력해주세요');
				form.loginPw.focus();
				return;
			}
			
			form.loginPw.value = sha256(form.loginPw.value);
			
			form.submit();
			doLoginForm_submited = true;
		}
	</script>
	<div>
		<form action="doLogin" method="POST" onsubmit="loginFormSubmit(this); return false;">
			
			<input type="text" name="listUrl" value="${encodedPreUrl}" readonly />
			
			<div>
				아이디 : <input type="text" name="loginId" placeholder="아이디를 입력해주세요" />
			</div>

			<div>
				비밀번호 : <input type="password" name="loginPw"
					placeholder="비밀번호를 입력해주세요" />
			</div>

			<div>
				<input type="submit" value="로그인" />
			</div>

		</form>
	</div>
	
<%@ include file="../part/footer.jspf"%>