<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="게시물 ${article.num}" />

<%@ include file="../part/header.jspf"%>

	<h1>게시물 상세화면</h1>
		
			<a href="${listUrl}">목록</a>
			
		<c:if test="${article.extra.actorCanDelete}">
			<a onclick="if( confirm('삭제하시겠습니까?') == false) return false;" href="./doDelete?num=${article.num}">삭제</a>
		</c:if>
		
		<c:if test="${article.extra.actorCanModify}">
			<a href="./modify?num=${article.num}"> 수정</a>
		</c:if>
		
		<div> 게시물 번호 : ${article.num}</div>
		<div> 게시물 작성자 : ${article.extra.writer}</div>
		<div> 게시물 작성일 : ${article.regDate}</div>
		<div> 게시물 수정일 : ${article.updateDate}</div>
		<div> 댓글 : ${article.replyNum} </div>
		<div> 
			게시물 제목 : ${article.title}
			<a onclick="if ( confirm('추천하시겠습니까?') == false ) { return false; }" href="../like?num=${article.num}">좋아요</a>
			<a onclick="if ( confirm('추천하시겠습니까?') == false ) { return false; }" href="../disLike?num=${article.num}">싫어요</a>
		</div>
		
		<div> 
			<h3>게시물 내용</h3>
			${article.body}
		</div>

		<h2>댓글</h2>
		<script>
			function writeReplyFormSubmit(form){
			let doWriteReplyForm_submited = false;
			
			if (doWriteReplyForm_submited) {
					alert('처리중입니다.');
					return;
				}
			
			form.body.value = form.body.value.trim();
			
			if ( form.body.value.length == 0 ){
				alert('댓글의 내용을 입력해주세요');
				form.body.focus();
				return;
			}
			
			form.submit();
			doWriteReplyForm_submited = true;
		}
		</script>
		
		<form action="/usr/reply/doWrite" method= "POST" onsubmit="writeReplyFormSubmit(this); return false;">
			<input type="hidden" name="replaceUrl" value="${currentUri}" />
			<input type="hidden" name="memberNum" value="${loginedMemberNum}"/>
			<input type="hidden" name="relNum" value="${article.num}"/>
			<input type="hidden" name="relTypeCode" value="article"/>
			<input type="hidden" name="num" value="${article.num}"/>
			<div>
				<textarea name="body" rows="3"></textarea>
			</div>
			
			<div>
				<input type="submit" value="작성"/>
			</div>
		</form>
		
		<h2>댓글 (${replies.size()})</h2>
		
		<div>
			<c:forEach items="${replies}" var="reply">
				<span> 번호: ${reply.num}</span>
				<span> 작성자 : ${reply.extra.writer}</span>
				<span> 댓글 작성일 : ${reply.regDate}</span>
				<span> 내용 : ${reply.body}</span>
				<style>
					.modify{
						cursor: pointer;
					}
					.modify_toggle{
					  display : none;
					}
					.active_modify{
					  display : block;
					}
					
				</style>
				
				<c:if test="${reply.extra.actorCanDelete}">
				<span class="modify modify${reply.num}">수정</span>
				<span class="modify_toggle modify_toggle${reply.num}">
					<form action="/usr/reply/doModify">
						<input type="hidden" name="replaceUrl" value="${currentUri}" />
						<input type="hidden" name="memberNum" value="${loginedMemberNum}"/>
						<input type="hidden" name="relNum" value="${article.num}"/>
						<input type="hidden" name="relTypeCode" value="article"/>
						<input type="hidden" name="num" value="${article.num}"/>
						<input type="hidden" name="replyNum" value="${reply.num}"/>
						
						<textarea name="body" rows="5"></textarea>
						<input type="submit" value="수정"/>
					</form>
					
				</c:if>
				
				<c:if test="${reply.extra.actorCanModify}">
				</span>
				<a onclick="if( confirm('댓글을 삭제하시겠습니까?') == false) return false;" 
							href="../reply/doDelete?num=${reply.num}&replaceUrl=${encodedCurrentUri}">삭제</a>
				</c:if>
				
				<hr />
			</c:forEach>
		</div>
				
		<script type="text/javascript">
			$('.modify').click(function(){
			  $(this).next().toggleClass('active_modify');
			})
		</script>
		
<%@ include file="../part/footer.jspf"%>