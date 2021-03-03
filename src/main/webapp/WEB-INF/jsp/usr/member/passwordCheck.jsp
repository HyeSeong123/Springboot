<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<c:set var="title" value="본인 확인" />
<%@ include file="../part/header.jspf"%>

	<h1>${title}</h1>
	<script>
		function passwordCheckFormSubmit(form){
			let doPasswordCheckForm_submited = false;
			
			if (doPasswordCheckForm_submited) {
					alert('처리중입니다.');
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
			doPasswordCheckForm_submited = true;
		}
	</script>
	<div>
		<form action="doPasswordCheck" method="POST" onsubmit="passwordCheckFormSubmit(this); return false;">
			<input type="hidden" name="redirectUri" value="/usr/member/modify" />
			
			<div>
				비밀번호 : <input type="password" name="loginPw"
					placeholder="비밀번호를 입력해주세요" />
			</div>

			<div>
				<input type="submit" value="확인" />
			</div>

		</form>
	</div>
	
<%@ include file="../part/footer.jspf"%>