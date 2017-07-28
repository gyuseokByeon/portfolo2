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
	<title>My Inout Month Stats</title>
</head>

<body>
<div class="wrap">
<div class="con_title">
<h2>월별 근태 내역 통계</h2>
</div>

<div class="con_body">
	<div class="tbl-contents">
	<table class="table table-hover table-striped table-bordered">
		<thead>
			<tr>
				<th>YY/MM</th>
				<th>출근</th>
				<th>지각</th>
				<th>외근</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="dto" items="${list }">
			<tr>
				<td>${dto.month }</td>
				<td>${dto.inout_count }</td>
				<td>${dto.late_count }</td>
				<td>${dto.work_count }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>

</div>

</body>
</html>