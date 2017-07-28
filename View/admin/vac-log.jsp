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
	<title>Admin::Vacation Log</title>
</head>

<body>
<div class="wrap">
<div class="con_title">
	<h2>휴가관리 : 전체 등록 내역 / 엑셀 다운로드</h2>
</div>

<div class="con_body">
	<div class="table-contents">
		<table class="table table-hover table-striped table-bordered">
			<thead>
				<tr>
					<th>직원 ID</th>
					<th>휴가 종류</th>
					<th>등록일</th>
					<th>시작일</th>
					<th>종료일</th>
					<th>일수</th>
					<th>사유</th>
					<th>상태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dto" items="${list }">
				<tr>
					<td>${dto.emp_id }</td>
					<td>${dto.code_name }</td>
					<td>
					<fmt:formatDate value="${dto.regdate }" pattern="yy-MM-dd HH:mm:ss" />
					</td>
					<td>${dto.vac_start }</td>
					<td>${dto.vac_end }</td>
					<td>${dto.vac_cnt }</td>
					<td>${dto.vac_comment }</td>
					<td><c:if test="${dto.state == 0 }"><span>취소</span></c:if>
						<c:if test="${dto.state == 1 }"><span>등록</span></c:if>
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
	
	<div class="search_div">
		<form:form id="searchpageForm" action="/admin/vacationLog.mi" method="post">
			<input type="hidden" name="page" value="${pageMaker.pagination.page}" />
			<input type="hidden" name="perPage" value="${pageMaker.pagination.perPage }"/>
			<select class="" name="searchType" id="searchType" size="1">
				<option value="all">---</option>
				<option value="one_day">일자</option>
				<option value="two_day">기간</option>
			</select>
			<input type="text" name="day1" id="day1" placeholder="day1" disabled="disabled"/>
			<input type="text" name="day2" id="day2" placeholder="day2" disabled="disabled" />
			<input type="text" name="keyword" id="keyword" placeholder="keyword" />
			<input type="submit" name="searchbtn" id="searchbtn" value="검색"/>
		</form:form>
	</div>
	
	<div class="btn_div" style="padding-top: 2rem;">
		<button class="main-text-button" id="sheet_down">엑셀 다운로드</button>
	</div>
	
	
</div>
</div>


</body>

<content tag="script">
<script>
$(function() {
	var form = $('#searchpageForm');
	
 	var s_searchType = '${search.searchType}';
	if(s_searchType != null && s_searchType != '') {
		$('#searchType').val(s_searchType);
	} else {
		$('#searchType').val('all');
	}
	var s_day1 = '${search.day1}';
	if(s_day1 !=null && s_day1 != '') {
		$('#day1').val(s_day1);
	}
	var s_day2 = '${search.day2}';
	if(s_day2 !=null && s_day2 != '') {
		$('#day2').val(s_day2);
	}
	var s_keyword = '${search.keyword}';
	if(s_keyword !=null && s_keyword != '') {
		$('#keyword').val(s_keyword);
	}
	
	
	$('.pageLinks li a').click(function() {
		event.preventDefault(); // 기본 이벤트 처리 방식 중단
		$(this).css('font-weight', 'bold');
		
		var targetPage = $(this).attr('href');
		form.find('[name="page"]').val(targetPage);
		form.attr('action', '/admin/vacationLog.mi');
		form.attr('method', 'post');
		form.submit();	
	});

	if($('#searchType').val() == 'one_day') {
		document.getElementById("day1").disabled = false;
		document.getElementById("day2").disabled = true;
		$('#day2').val('');
	} else if ($('#searchType').val() == 'two_day') {
		document.getElementById("day1").disabled = false;
		document.getElementById("day2").disabled = false;
	} else if ($('#searchType').val() == 'all') {
		document.getElementById("day1").disabled = true;
		document.getElementById("day2").disabled = true;
		$('#day1').val('');
		$('#day2').val('');
	}
	
 	$('#searchType').change(function() {
		if($('#searchType').val() == 'one_day') {
			document.getElementById("day1").disabled = false;
			document.getElementById("day2").disabled = true;
			$('#day2').val('');
		} else if ($('#searchType').val() == 'two_day') {
			document.getElementById("day1").disabled = false;
			document.getElementById("day2").disabled = false;
		} else if ($('#searchType').val() == 'all') {
			document.getElementById("day1").disabled = true;
			document.getElementById("day2").disabled = true;
			$('#day1').val('');
			$('#day2').val('');
		}
	}); 
	
	$('#day1').datepicker({
		dateFormat: 'yy-mm-dd'
	});
	
	$('#day2').datepicker({
		dateFormat: 'yy-mm-dd',
		onSelect: function(value) {
			if($('#day1').val() == null || $('#day1').val() == "") {
				alert('검색 시작일을 먼저 선택해주세요');
				$('#day2').val("");
			} else if($('#day1').val() > value) {
				alert('시작일 이후의 날을 선택해주세요' + value);
				$('#day2').val('');
			}
		}
	});
	
	$('#sheet_down').click(function() {
		location = "/admin/vacSheet.service";
	});
	
});

function fn_page(page) {
	var form = $('#searchpageForm');
	console.log(page);
	form.find('[name="page"]').val(page);
	form.attr('action', '/admin/vacationLog.mi');
	form.attr('method', 'post');
	form.submit();
}


</script>
</content>


</html>