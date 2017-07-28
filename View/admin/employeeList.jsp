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
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/table-page.css">
	<title>Admin::Employee List</title>
</head>

<body>
<div class="wrap">
<div class="con_title">
	<h2>직원 리스트</h2>
</div>

<div class="con_body">
	<div class="tbl-contents">
		<table class="table table-hover table-striped table-bordered">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Sub_Name</th>
					<th>E-Mail</th>
					<th>H.P</th>
					<th>O.P</th>
					<th>상태</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach var="empDto" items="${list }">
				<tr>
					<td>${empDto.emp_id }</td>
					<td>${empDto.name }</td>
					<td>${empDto.sub_name }</td>
					<td>${empDto.email }</td>
					<td>${empDto.h_phone }</td>
					<td>${empDto.o_phone }</td>
					<td>
						<c:if test="${empDto.state == 0 }">
							<span>퇴사</span>
						</c:if>
						
						<c:if test="${empDto.state == 1 }">
							<span>재직중</span>
						</c:if>
					</td>
					<td>
						<c:if test="${empDto.state == 1 }">
							<form:form id="emp_edit_form" action="/admin/employeeUpdate.mi" method="post">
								<input type="hidden" name="emp_id" id="emp_id" value="${empDto.emp_id }" />
								<input type="submit" name="updateemp" id="updateemp" value="수정" />
							</form:form>
						</c:if>
					</td>
					<td>
						<c:if test="${empDto.state == 1 }">
							<button onclick="delemp('${empDto.emp_id}')">삭제</button>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<form:form id="emp_del_form" action="/admin/resignationEmployee.service" method="post">
		<input type="hidden" name="emp_id" id="emp_id" />
	</form:form>
	
	<div class="page_div">
		<ul class="pageLinks">
			<ui:pagination paginationInfo = "${page}" type="image" jsFunction="fn_page"/>
		</ul>
	</div>
	
	<div class="search_div">
		<form:form id="searchpageForm" action="/admin/employeeList.mi" method="post">
			<input type="hidden" name="page" value="${pageMaker.pagination.page}" />
			<input type="hidden" name="perPage" value="${pageMaker.pagination.perPage }"/>
			<input type="hidden" name="searchType" value="all" />
			<input type="text" name="keyword" id="keyword" placeholder="keyword" />
			<input type="submit" name="searchbtn" id="searchbtn" value="검색"/>
		</form:form>
	</div>
	
	<div class="btn_div align-rtr">
		<button class="main-text-button " id="regEmpBtn">직원 추가</button>
		<button class="main-text-button" id="sheet_down">엑셀 다운로드</button>
	</div>
	
</div>
</div>
</body>

<content tag="script">
<script>
function delemp(emp_id) {
	var r = confirm('해당 직원을 퇴사처리 하시겠습니까?');
	
	if(r == true) {
		$('#emp_del_form').find('[name="emp_id"]').val(emp_id);
		$('#emp_del_form').submit();
	}
}


$(function() {
	var form = $('#searchpageForm');
	
	var keyword = '${search.keyword }';
	if(keyword != null && keyword != '') {
		$('#keyword').val(keyword);
	}
	
	$('.pageLinks li a').click(function() {
		event.preventDefault(); // 기본 이벤트 처리 방식 중단
		$(this).css('font-weight', 'bold');
		
		var targetPage = $(this).attr('href');
		form.find('[name="page"]').val(targetPage);
		form.attr('action', '/admin/employeeList.mi');
		form.attr('method', 'post');
		form.submit();	
	});
	
	$('#sheet_down').click(function() {
		location = "/admin/empSheet.service";
	});
	
	$('#regEmpBtn').click(function() {
		location = "/admin/empRegistration.mi";
	});

	if('${deleteResult}' == 's') {
		alert('직원 퇴사처리 성공');
	} else if('${deleteResult}' == 'f') {
		alert('직원 퇴사처리 실패')
	} else if('${deleteResult}' == 'f_admin') {
		alert('관리자는 퇴사처리가 불가합니다 /n시스템 관리자에게 문의해주세요')
	}
	
	
	if('${insertEmp}' == 's') {
		alert('직원 등록 성공');
	} 
	
	if('${error_upd}' == 'f') {
		alert('직원정보를 불러오는 중에 문제가 발생했습니다.');
	}
	
	if('${update_result}' == 's') {
		alert('회원정보 수정 성공');
	} else if('${update_result}' == 'f') {
		alert('회원정보 수정 실패');
	}

});

function fn_page(page) {
	var form = $('#searchpageForm');
	console.log(page);
	form.find('[name="page"]').val(page);
	form.attr('action', '/admin/employeeList.mi');
	form.attr('method', 'post');
	form.submit();
}





</script>
</content>


</html>