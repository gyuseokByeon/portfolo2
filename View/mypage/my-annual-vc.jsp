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
	<title>My Annual Vacation State</title>
</head>

<body>
<div class="wrap">
<div class="con_title">
<h2>연도별 연차 내역</h2>
</div>

<div class="con_body">
	<div class="tbl-contents">
	<table class="table table-hover table-striped table-bordered">
		<thead>
			<tr>
				<th>Years</th>
				<th>Total</th>
				<th>Rest</th>
				<th>Use</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="dto" items="${list }">
			<tr>
				<td>${dto.year }</td>
				<td>${dto.total_cnt }</td>
				<td>${dto.rest_cnt }</td>
				<td>${dto.use_cnt }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</div>
</body>
</html>