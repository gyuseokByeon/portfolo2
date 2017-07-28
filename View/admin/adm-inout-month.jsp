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
	<link rel="stylesheet" type="text/css" href="/css/admin-inout.css">
	<title>InOut Monthly statistical data</title>
</head>

<body>
<div class="wrap">
<div class="con_title">
	<h2>근태관리 : 월별 통계</h2>
</div>

<div class="con_body">
	<div class="search_div">
		<form:form id="searchForm" name="searchForm">
			<input type="hidden" name="page" value="${pageMaker.pagination.page}" />
			<input type="hidden" name="perPage" value="${pageMaker.pagination.perPage }"/>
			
			<select class="" name="year" id="year"></select>
			<select class="" name="months" id="months" size="1">
				<option value="01">01</option>
				<option value="02">02</option>
				<option value="03">03</option>
				<option value="04">04</option>
				<option value="05">05</option>
				<option value="06">06</option>
				<option value="07">07</option>
				<option value="08">08</option>
				<option value="09">09</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
			</select>
			<input type="hidden" name="month" id="month" />
			<input type="text" name="keyword" id="keyword" placeholder="검색어 입력" />
			<input type="button" name="search_btn" id="search_btn" value="검색"/>
		</form:form>
	</div>
	
	<div class="tbl-contents">
		<table class="table table-hover table-striped table-bordered">
			<thead>
				<tr>
					<td>YY/MM</td>
					<td>회원 ID</td>
					<td>이름</td>
					<td>출근</td>
					<td>지각</td>
					<td>외근</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dto" items="${list }">
				<tr>
					<td>${dto.month }</td>
					<td>${dto.emp_id }</td>
					<td>${dto.name }</td>
					<td>${dto.inout_count }</td>
					<td>${dto.late_count }</td>
					<td>${dto.work_count }</td>
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

	
	<form:form action="/admin/inoutMonthSheet.service" method="get">
		<input type="hidden" name="month" id="month" value="${month }" />
		<input type="submit" class="main-text-button" id="sheet_down" value="내역 엑셀 다운로드"/>
	</form:form>
	
</div>
</div>
</body>

<content tag="script">
<script>
var today = new Date();
var yyyy = today.getFullYear();
var basic_yyyy = 2016;
var mon = today.getMonth()+1;
while(basic_yyyy <= yyyy) {
	var select_y = document.getElementById("year");
    var option = document.createElement("option");
    option.value=basic_yyyy;
    option.text=basic_yyyy;
    select_y.add(option);
	
	basic_yyyy = basic_yyyy+1; 
}

function getMonth() {
	var search = '${search.month }';
	if(search != null) {
		return search;
	} else {
		return 0;
	}
}

function fn_page(page) {
	var form = $('#searchForm');
	console.log(page);
	form.find('[name="page"]').val(page);
	form.find('[name="month"]').val(getMonth());
	form.attr('action', '/admin/inoutMonth.mi');
	form.attr('method', 'post');
	form.submit();
}

$(function() {
	var search = '${search.month }';
	if(search != null) {
		var monthArray = search.split('-');
		$('#year').val(monthArray[0]);
		$('#months').val(monthArray[1]);
	}
	var searchKeyword = '${search.keyword}';
	if(searchKeyword!=null) {
		$('#keyword').val(searchKeyword);
	}

	
	var form = $('#searchForm');
	
	$('.pageLinks li a').click(function() {
		event.preventDefault(); // 기본 이벤트 처리 방식 중단
		$(this).css('font-weight', 'bold');
		
		var targetPage = $(this).attr('href');
		form.find('[name="page"]').val(targetPage);
		form.find('[name="month"]').val(getMonth());
		form.attr('action', '/admin/inoutMonth.mi');
		form.attr('method', 'post');
		form.submit();	
	});
	
	$('#search_btn').click(function() {
		var year = $('#year').val();
		var month = $('#months').val();
		
		var abc = year + '-'+month;
		var keyword	= $('#keyword').val();
		
		form.find('[name="month"]').val(abc);
		form.find('[name="keyword"]').val(keyword);
		form.attr('action', '/admin/inoutMonth.mi');
		form.attr('method', 'post');
		form.submit(); 
	});
	
	
	
});

</script>
</content>


</html>