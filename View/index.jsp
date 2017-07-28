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
	<link rel="stylesheet" type="text/css" href="/css/index.css">
	<link rel="stylesheet" type="text/css" href="/css/milse-common-ui_zd.css">
</head>
<body>
<div class="wrap">
	<div id="mainFrm" class="login_form">
		<form:form action="/loginProcess.service" method="post" name="form">
		<div class="login" id="login">
			<img src="/img/commute_login_logo.png"/>
			<div class="form_div">
				<input class="login_input id" type="text" name="id" maxlength="30"  placeholder="ID" />
			</div>
			<div class="form_div">
				<input class="login_input pw" type="password" name="password" maxlength="25" placeholder="Password" autocomplete="off"/>
			</div>
			<div class="form_div">
				<input id="rm_ck" name ="rm_ck" type="checkbox"/>
				<label for="rm_ck">Remember me</label>
				<input type="hidden" name="remember_me" id="remember_me"/>
			</div>
			<div class="form_div button_div">
				<input class="signButton" type="button" name="move" value=" Sign-In" />
			</div>
			
			<sec:authorize access="permitAll">
			퍼밋 올
			</sec:authorize>
			<sec:authorize access="isAnonymous()">
			어노미
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
			인증
			</sec:authorize>
			<sec:authorize access="isRememberMe()">
			기억
			</sec:authorize>
			<sec:authorize access="isFullyAuthenticated()">
			올인증
			</sec:authorize>
			
		</div>

		</form:form>
	</div>
</div>
</body>

<content tag="script">
<script>
$(function(){
	$('input[name=id]').keydown(function(e) {
		if(e.keyCode==13) {
			$('input[name=move]').click();
		} else{
			return true;
		}
	});
	
	$('input[name=password]').keydown(function(e){
		if(e.keyCode==13){
			$('input[name=move]').click();
		} else {
			return true;
		}
	});
	
	$('input[name=move]').click(function(){
		if($('input[name=id]').val()==''){
			alert("ID를 입력해주세요.");
			$('input[name=id]').focus();
		}else if($('input[name=pw]').val() == ''){
			alert("비밀번호를 입력해주세요.");
			$('input[name=password]').focus();
		}else{
			var remmCbox = $('#rm_ck');
			var cc = remmCbox[0].checked;
			if(cc) {
				$('#remember_me').val("remember_me");
			} else {
				$('#remember_me').val("forget_me");
			}
			
 			$('form[name=form]').submit();
		}
	});
});
</script>
</content>
</html>