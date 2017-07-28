<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://egovframework.gov/ctl/ui" prefix="ui" %>
<c:set var="url">${pageContext.request.requestURL}</c:set>
<c:set var="baseURL" value="${fn:replace(url, pageContext.request.requestURI, pageContext.request.contextPath)}" />
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="/js/jquery-2.1.3.min.js"></script>
	<title>Admin::Admin Contact Board</title>
	<link rel="stylesheet" type="text/css" href="/css/board-page.css">
	<link rel="stylesheet" type="text/css" href="/css/table-page.css">
</head>

<body>
<div class="wrap">
<div class="con_title">
	<h2>관리자 요청 게시판(관리모드)</h2>
</div>

<div class="con_body">
	<div class="tbl-contents">
		<table class="table table-hover table-striped">
			<thead>
				<tr7>
					<th>NO</th>
					<th>EMP ID</th>
					<th>요청 내용</th>
					<th>등록일</th>
					<th>처리일</th>
					<th>처리 상태</th>
					<th>a</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="abDto" items="${list }">
					<c:if test="${abDto.admin_state == 0}">
						<tr>
							<td>${abDto.rid }</td>
							<td>${abDto.emp_id }</td>
							<td>${abDto.contents }</td>
							<td>
								<fmt:formatDate value="${abDto.regdate }" pattern="yy-MM-dd HH:mm"/>
							</td>
							<td>
								<fmt:formatDate value="${abDto.cdate }" pattern="yy-MM-dd HH:mm"/>
							</td>
							<td>
								<c:if test="${abDto.state == 0}">대기중</c:if>
								<c:if test="${abDto.state == 1}">승인</c:if>
								<c:if test="${abDto.state == 2}">반려</c:if>
							</td>
							<td>
								<form:form name="state_form" action="/contact/adm_updatestate.service" method="post">
									<input type="hidden" id="rid" name="rid" value="${abDto.rid }" />
									<input type="hidden" name="admin_state" value="1" />
									<select name="state" id="state" size="1">
									<option value="1">승인</option>
									<option value="2">반려</option>
									</select>
									<input type="submit" name="stateBtn" value="확인"/>
								</form:form>
							</td>
						</tr>
					</c:if>
				

					<c:if test="${abDto.admin_state == 1}">
						<tr class="darkblue">
							<td>${abDto.rid }</td>
							<td>${abDto.emp_id }</td>
							<td>${abDto.contents }</td>
							<td>
								<fmt:formatDate value="${abDto.regdate }" pattern="yy-MM-dd HH:mm"/>
							</td>
							<td>
								<fmt:formatDate value="${abDto.cdate }" pattern="yy-MM-dd HH:mm"/>
							</td>
							<td>
								<c:if test="${abDto.state == 0}">대기중</c:if>
								<c:if test="${abDto.state == 1}">승인</c:if>
								<c:if test="${abDto.state == 2}">반려</c:if>
							</td>
							<td>
								<form:form action="/contact/adm_updatestate.service" method="post">
									<input type="hidden" name="state" value="0"/>
									<input type="hidden" name="rid" value="${abDto.rid }"/>
									<input type="hidden" name="admin_state" value="0" />
									<input type="submit" name="stateBtn" value="취소"/>
								</form:form>
							</td>
						<tr>
					</c:if>
				</c:forEach>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
	</div>
	
	<div class="page_div">
		<ul class="pageLinks">
			<ui:pagination paginationInfo = "${page}" type="image" jsFunction="fn_page"/>
		</ul>
	</div>
		
	<form:form id="searchpageForm" action="/admin/employeeList.mi" method="post">
		<input type="hidden" name="page" value="${pageMaker.pagination.page}" />
		<input type="hidden" name="perPage" value="${pageMaker.pagination.perPage }"/>
	</form:form>
</div>
</div>


</body>
<content tag="script">
<script>


var trlist = document.querySelector('tbody');
trlist.addEventListener('click', function(ev) {
	if (ev.target.tagName === 'tr') {
	 ev.target.classList.toggle('checked');
	}
	}, false);
	
$(function() {
	$('.pageLinks li a').click(function() {
		event.preventDefault(); // 기본 이벤트 처리 방식 중단
		$(this).css('font-weight', 'bold');
		
		var targetPage = $(this).attr('href');
		form.find('[name="page"]').val(targetPage);
		form.attr('action', '/admin/employeeList.mi');
		form.attr('method', 'post');
		form.submit();	
	});
});

function fn_page(page) {
	var form = $('#searchpageForm');
	console.log(page);
	form.find('[name="page"]').val(page);
	form.attr('action', '/contact/list.mi');
	form.attr('method', 'post');
	form.submit();
}

</script>
</content>




</html>