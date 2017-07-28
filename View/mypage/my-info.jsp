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
	<link rel="stylesheet" type="text/css" href="/css/register-page.css">
	<title>내 정보 수정</title>
</head>

<body>
<div class="wrap">
<div class="con_title">
<h2>내 정보 수정</h2>
</div>

<div class="con_body">
	<div class="frm_div">
	<form:form id="myinfo_form" action="/mypage/my-info.service" method="post">
	<table class="form_tbl">
		<tbody>
		<tr>
			<th>
				<label>Name</label>
			</th>
			
			<td>
				<div>
					<input type="hidden" id="emp_id" name="emp_id" value="${dto.emp_id }"/>
					<input class="frm-input border fff" type="text" disabled="disabled" 
						value="${dto.name }" name="name" id="name"/>
				</div>
			</td>
		</tr>
		
		<tr>
			<th>
				<label>Sub Name</label>
			</th>
			<td>
				<div>
					<input class="frm-input border fff" type="text" disabled="disabled" 
						value="${dto.sub_name }" name="sub_name" id="sub_name"/>
				</div>
			</td>
		</tr>
				
		<tr>
			<th>
				<label>E-Mail</label>
			</th>
			<td>
				<div>
					<input class="frm-input border fff" type="text" 
						value="${dto.email }" name="email" id="email"/>
				</div>
			</td>
		</tr>
		
		<tr>
			<th>
				<label>H.P</label>
			</th>
			<td>
				<div>
					<input class="frm-input border fff" type="text" 
						value="${dto.h_phone }" name="h_phone" id="h_phone"/>
				</div>
			</td>
		</tr>
		
		<tr>
			<th>
				<label>O.P</label>
			</th>
			<td>
				<div>
					<input class="frm-input border fff" type="text" disabled="disabled" 
						value="${dto.o_phone }" name="o_phone" id="o_phone"/>
				</div>
			</td>
		</tr>
		
		</tbody>
	</table>
	</form:form>
	
	<table class="form_tbl">
		<tfoot>
			<tr>
				<td colspan="2">
					<div class="btn_div">
						<button class="main-text-button" id="btn_edit">수정</button>
						<button class="main-text-button" id="btn_cancel">취소</button>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
	</div>
</div>
</div>
</body>

<content tag="script">
<script type="text/javascript" src="/js/jquery.validate.min.js"></script>
<script>

$(function() {
	$('#btn_edit').click(function() {
		$('#myinfo_form').submit();
	});
	
	$('#btn_cancel').click(function() {
		location='/mypage/myInoutMonthStats.mi';
	});
	
	$('#myinfo_form').validate({
		debug: false
		,errorPlacement: $.noop
		,invalidHandler: function(form, validator) {
	       var errors = validator.numberOfInvalids();
	       if (errors) {
	           alert(validator.errorList[0].message);
	           validator.errorList[0].element.focus();
	       }
	  	}
		,onfocusout: false
		,rules: {
			email: {
				required: true
				,email: true
			}
			,h_phone: {
				required: true
				,vali_phone: true
			}
		}
		,messages : {
			email: {
				required: "필수입력사항입니다."
				,email: "E-Mail 형식(aaaa@bbb.ccc)이 아닙니다."
			}
			,h_phone: {
				required: "필수입력사항입니다."
				,vali_phone: "올바른 형식이 아닙니다.\n핸드폰 번호는 하이픈(-)을 포함하여 입력해주세요"
			}
		}
	});
	
	if('${update_result}' == 's') {
		alert('정보수정 성공');
	} else if ('${update_result}' == 'f') {
		alert('정보수정 실패');
	}
});

$.validator.addMethod("vali_phone", function(value) {
	return /^01([0|1|6|7|8|9]?)-([0-9]{3,4})-([0-9]{4})$/.test(value);
});


</script>
</content>




</html>