<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<c:set var="title" value="회원 가입" />

<%@ include file="../part/header.jspf"%>
<h1>내 정보</h1>
<script>
		function modifyFormSubmit(form){
			let doModifyForm_submited = false;
			
			if (doModifyForm_submited) {
					alert('처리중입니다.');
					return;
				}
				

			form.loginPw.value = form.loginPw.value.trim();
			
			if ( form.loginPw.value.length == 0 ){
				alert('로그인 패스워드를 입력해주세요');
				form.loginPw.focus();
				return;
			}
			
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
			
			if ( form.loginPwConfirm.value.length == 0 ){
				alert('패스워드 확인란을 입력해주세요');
				form.loginPwConfirm.focus();
				return;
			}
			
			if (form.loginPw.value != form.loginPwConfirm.value){
				alert('패스워드와 패스워드 확인란이 일치하지 않습니다.');
				form.loginPwConfirm.focus();
				return;
			}
			
			form.name.value = form.name.value.trim();
			
			if ( form.name.value.length == 0 ){
				alert('이름을 입력해주세요');
				form.name.focus();
				return;
			}
			
			form.nickname.value = form.nickname.value.trim();
			
			if ( form.nickname.value.length == 0 ){
				alert('닉네임을 입력해주세요');
				form.nickname.focus();
				return;
			}
			
			form.loginPw.value = sha256(form.loginPw.value);
			form.loginPwConfirm.value = "";
			
			form.submit();
			doModifyForm_submited = true;
		}
	</script>
<div>

	<form action="doModify" method="POST"
		onsubmit="modifyFormSubmit(this); return false;">
		
		<input type="hidden" name="loginedMemberNum" value= "${loginedMemberNum}" />
		
		<div>
			비밀번호 : <input type="password" name="loginPw"
				placeholder="비밀번호를 입력해주세요" />
		</div>

		<div>
			비밀번호 확인 : <input type="password" name="loginPwConfirm"
				placeholder="비밀번호를 다시 입력해주세요" />
		</div>

		<div>
			이름 : <input type="text" name="name" placeholder="이름을 입력해주세요"
				value="${loginedMember.name}" />
		</div>

		<div>
			닉네임 : <input type="text" name="nickname" placeholder="닉네임을 입력해주세요"
				value="${loginedMember.nickname}" />
		</div>

		<div>
			이메일 : <input type="email" name=email placeholder="이메일을 입력해주세요"
				value="${loginedMember.email}" />
		</div>

		<div>
			<input type="submit" value="변경" />
		</div>
		
		<a href="../home/main">홈으로</a>
	</form>

</div>
<%@ include file="../part/footer.jspf"%>