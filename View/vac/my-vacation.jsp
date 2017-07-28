<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url">${pageContext.request.requestURL}</c:set>
<c:set var="baseURL" value="${fn:replace(url, pageContext.request.requestURI, pageContext.request.contextPath)}" />
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="/js/jquery-2.1.3.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/table-page.css">

	<title>My Vacation List</title>
</head>
<body>
<div class="wrap">
<div class="con_title">
<h2>Vacation</h2>
</div>

<div class="con_body">
	<div class="tbl-contents">
	<table class="table table-hover table-striped table-bordered">
		<thead>
			<tr>
				<th class="kor_th">휴가</th>
				<th hidden="hidden">RID</th>
				<th class="kor_th">등록일</th>
				<th class="kor_th">시작일</th>
				<th class="kor_th">종료일</th>
				<th class="kor_th">휴가일수</th>
				<th class="kor_th">휴가사유</th>
				<th class="kor_th">취소</th>
			</tr>
		</thead>
		
		<tbody id="vc_table">
			<c:forEach var="dto" items="${vacList }">
				<c:choose>
					<c:when test="${dto.state eq 1 }">
						<tr>
							<td class="kor_td">${dto.code_name }</td>
							<td hidden="hidden">${dto.rid }</td>
							<td class="eng_td"><fmt:formatDate value="${dto.regdate }" pattern="yy-MM-dd HH:mm:ss" /></td>
							<td class="eng_td">${dto.vac_start }</td>
							<td class="eng_td">${dto.vac_end }</td>
							<td class="eng_td">${dto.vac_cnt }</td>
							<td class="kor_td">${dto.vac_comment }</td>
							<td>
								<div>
									<%-- <form:form class="aa" action="/vacation/reg-vc.mi" method="post">
										<input type="hidden" name="rid" value="${dto.rid }" />
										<input type="submit" id="cedit" value="수정">
									</form:form> --%>
									<button onclick="cancan(${dto.rid})">취소</button>
			 					</div>
							</td>
						</tr>
					</c:when>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
	</div>
	
	<form:form id="ccvac" class="aa" action="/vacation/cancel-vc.service" method="post">
		<input type="hidden" name="rid" />
	</form:form>
	
	<div class="btn_div">
		<button class="main-text-button" id="vc-reg">휴가 등록</button>
	</div>
</div>
</div>
</body>

<content tag="script">
<script>
function cancan(rid) {
	var r = confirm('휴가 등록을 취소하시겠습니까?');
	if(r == true) {
		$('#ccvac').find('[name="rid"]').val(rid);
		$('#ccvac').submit();
	}
}

$(function() {
	$('#vc-reg').click(function() {
		location="/vacation/reg-vc.mi";
	});
	
	if('${insert_result}' == 'vac_insert_s') {
		alert('휴가 등록 성공');
	} else if('${insert_result}' == 'vac_insert_f') {
		alert('휴가 등록 실패');
	} else if('${insert_result}' == 'error1') {
		alert('사용 가능한 연차 횟수를 초과했습니다.');
	} else if('${insert_result}' == 'error1') {
		alert('휴가 등록에 실패했습니다. 관리자에게 문의부탁드립니다.');
	}


});


</script>
</content>


</html>
