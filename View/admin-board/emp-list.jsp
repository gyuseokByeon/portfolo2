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
	<title>Admin Contact Board</title>
	<link rel="stylesheet" type="text/css" href="/css/board-page.css">
	<link rel="stylesheet" type="text/css" href="/css/table-page.css">
</head>

<body>
<div class="wrap">
	<div class="con_title">
		<h2>관리자 요청 게시판</h2>
	</div>

	<div class="con_body">
		<div class="contact_header">
			<form:form action="/contact/emp_insert.service" method="post">
			<input class="con_input" type="text" name="contents" id="contents" required="required"/>
			<input class="addBtn" type="submit" name="insertAb" value="요청등록" />
			</form:form>
		</div>
	
		<div class="tbl-contents">
			<table class="table table-bordered table-hover adminBordTable">
				<thead>
					<tr>
						<th>NO</th>
						<th>EMP ID</th>
						<th>요청 내용</th>
						<th>등록일</th>
						<th>처리일</th>
						<th>처리 상태</th>
						<th class="cancel_th"><img src="/img/icons/commute_administrator_x.png"></th>
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
						<td class="cancel_td">
							<span onclick="cancelBtn(${abDto.rid})">
								<img src="/img/icons/commute_administrator_x.png"/>
							</span>
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
						<td></td>
					</tr>
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
		
		<form:form id="del_form" action="/contact/emp_del.service" method="post">
			<input type="hidden" name="rid" />
		</form:form>
	</div>
</div>

</body>
<content tag="script">
<script>

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

function cancelBtn(rid) {
	var can = confirm('해당 요청글을 삭제하시겠습니까?');
	
	if(can == true) {
		var form = $('#del_form');
		form.find('[name="rid"]').val(rid);
		form.submit();
	}
}

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