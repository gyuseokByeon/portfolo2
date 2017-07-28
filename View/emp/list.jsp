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
	<title>Employee List</title>
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
						<th class="eng_th">ID</th>
						<th class="eng_th">Name</th>
						<th class="eng_th">Sub_Name</th>
						<th class="eng_th">E-Mail</th>
						<th class="eng_th">H.P</th>
						<th class="eng_th">O.P</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="empDto" items="${list }">
					<tr>
						<td class="eng_td">${empDto.emp_id }</td>
						<td class="kor_td">${empDto.name }</td>
						<td class="eng_td">${empDto.sub_name }</td>
						<td class="eng_td">${empDto.email }</td>
						<td class="eng_td emp_phone">${empDto.h_phone }</td>
						<td class="eng_td">${empDto.o_phone }</td>
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
			<form:form id="searchpageForm" action="/emp/list.mi" method="post">
				<input type="hidden" name="page" value="${pageMaker.pagination.page}" />
				<input type="hidden" name="perPage" value="${pageMaker.pagination.perPage }"/>
				<input type="hidden" name="searchType" value="loginemp" />
				<input class="only_keyword" type="text" name="keyword" id="keyword" placeholder="keyword" />
				<input class="search_button" type="submit" name="searchbtn" id="searchbtn" value="검색"/>
			</form:form>
		</div>
		
	</div>
</div>
</body>

<content tag="script">
<script>
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
		form.attr('action', '/emp/list.mi');
		form.attr('method', 'post');
		form.submit();	
	});
	

});

function fn_page(page) {
	var form = $('#searchpageForm');
	console.log(page);
	form.find('[name="page"]').val(page);
	form.attr('action', '/emp/list.mi');
	form.attr('method', 'post');
	form.submit();
}

</script>
</content>


</html>