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
	<title>Admin::Annual Vacation Log</title>
</head>

<body>
<div class="wrap">
	<div class="con_title">
		<h2>휴가관리 : 연차 등록 내역 조회 / 등록</h2>
	</div>
	
	<div class="con_body">
		<div class="search_div">
			<form:form id="searchForm" name="searchForm">
				<input type="hidden" name="page" value="${pageMaker.pagination.page}" />
				<input type="hidden" name="perPage" value="${pageMaker.pagination.perPage }"/>
				<select class="" name="day1" id="day1"></select>
				
			</form:form>
		</div>
	
		<div class="tbl-contents">
			<table class="table table-hover table-striped table-bordred">
				<thead>
					<tr>
						<th>Year</th>
						<th>EMP ID</th>
						<th>Name</th>
						<th>Total</th>
						<th>Use</th>
						<th>Rest</th>
						<th>regDate</th>
						<th>button</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="ann" items="${list }">
					<tr>
						<td>${ann.year }</td>
						<td>${ann.emp_id }</td>
						<td>${ann.name }</td>
						<td>
							<c:if test="${ann.regdate == null }">
							-
							</c:if>
							<c:if test="${ann.regdate != null }">
							${ann.total_cnt }
							</c:if>
						</td>
						<td>
							<c:if test="${ann.regdate == null }">
							-
							</c:if>
							<c:if test="${ann.regdate != null }">
							${ann.use_cnt }
							</c:if>
						</td>
						<td>
							<c:if test="${ann.regdate == null }">
							-
							</c:if>
							<c:if test="${ann.regdate != null }">
							${ann.rest_cnt }
							</c:if>
						</td>
						<td>
							<c:if test="${ann.regdate == null }">
							-
							</c:if>
							<c:if test="${ann.regdate != null }">
							<fmt:formatDate value="${ann.regdate }" pattern="yy-MM-dd HH:mm:ss" />
							</c:if>
						</td>
						<td>
							<c:if test="${ann.regdate != null }">
							<form:form id="update_annvac" action="/admin/update-annvc.mi" method="post">
								<input type="hidden" value="${ann.rid}" id="rid" name="rid" />
								<input type="submit" value="수정" id="update_btn" name="update_btn"/>
							</form:form>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
	 	<div class="page_div">
			<ul class="pageLinks">
				<ui:pagination paginationInfo = "${page}" type="image" jsFunction="fn_page"/>
			</ul>
		</div>
		
		<div class="btn_div">
			<button class="main-text-button" id="regAnn">연차 등록</button>
		</div>
		
	</div>
</div>
</body>

<content tag="script">
<script>
var today = new Date();
var yyyy = today.getFullYear();
var basic_yyyy = 2016;
while(basic_yyyy <= yyyy+1) {
	var select_y = document.getElementById("day1");
    var option = document.createElement("option");
    option.value=basic_yyyy;
    option.text=basic_yyyy;
    select_y.add(option);
	
	basic_yyyy = basic_yyyy+1; 
}

function fn_page(page) {
	var form = $('#searchForm');
	var day1 = $('#day1').val();
	
	form.find('[name="page"]').val(page);
	form.find('[name="day1"]').val(day1);
	form.attr('action', '/admin/annualVacationLog.mi');
	form.attr('method', 'post');
	form.submit();
}

$(function() {
	var form = $('#searchForm');
	var day1 = '${search.day1 }';
	
	if(day1 != null) {
		$('#day1').val(day1);
	}
	
	$('.pageLinks li a').click(function() {
		event.preventDefault(); // 기본 이벤트 처리 방식 중단
		$(this).css('font-weight', 'bold');
		
		var day1 = $('#day1').val();
		
		var targetPage = $(this).attr('href');
		form.find('[name="page"]').val(targetPage);
		form.find('[name="day1"]').val(day1);
		form.attr('action', '/admin/annualVacationLog.mi');
		form.attr('method', 'post');
		form.submit();	
	});
	
	
	$('#day1').change(function() {
		form.attr('action', '/admin/annualVacationLog.mi');
		form.attr('method', 'post');
		form.submit();
	});
	
	$('#regAnn').click(function() {
		location = "/admin/ann-reg.mi";
	})
	
	if('${insert_result}' == 'insert_s') {
		alert('연차 등록 성공');
	} else if('${insert_result}' == 'insert_f') {
		alert('연차 등록 실패');
	} else if('${insert_result}' == 'error1') {
		alert('이미 등록된 내역이 존재합니다.');
	}
	
	if('${update_result}' == 'update_s') {
		alert('연차 수정 성공');
	} else if('${update_result}' == 'update_f') {
		alert('연차 등록 실패');
	}
	
	
	
	
	
});


</script>
</content>


</html>