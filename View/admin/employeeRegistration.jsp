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
	<link rel="stylesheet" type="text/css" href="/css/admin-inout.css">
	<title>Admin::New Employee Registration</title>
</head>

<body>
<div class="wrap">
<div class="con_title">
<c:if test="${page_type eq 'new_employee' }">
	<h2>직원 관리 : 등록</h2>
</c:if>

<c:if test="${page_type eq 'update_employee' }">
	<h2>직원 관리 : 수정</h2>
</c:if>

</div>

<div class="con_body">
	<div class="frm_div">
		<form:form id="empreg_form" action="/admin/employeeRegistration.service" method="post">
			<table class="form_tbl">
				<tbody>
					<tr>
						<th colspan="1">** 은 필수 입력 사항입니다</th>
					</tr>
					<tr>
						<th>
							<label>ID</label>
						</th>
						<td>
							<c:if test="${page_type eq 'new_employee' }">
								<input class="frm-input border aaa" type="text" name="emp_id" id="emp_id" />
								<span>**</span>
								<input type="button" id="ck_idbtn" value="중복확인">
							</c:if>
							<c:if test="${page_type eq 'update_employee' }">
								<input class="frm-input border aaa" type="text" name="emp_id" id="emp_id" value="${empDto.emp_id }" readonly="readonly" />
								<span>**</span>
							</c:if>
						</td>
					</tr>
					<tr>
						<th>
							<label>PW</label>
						</th>
						<td>
							<input class="frm-input border aaa" type="password" name="pw" id="pw" placeholder="영문+숫자 10자 이상" />
							<span>**</span>
						</td>
					</tr>
					<tr>
						<th>
							<label>NAME</label>
						</th>
						<td>
							<input class="frm-input border aaa" type="text" name="name" id="name" />
							<span>**</span>
						</td>
					</tr>
					<tr>
						<th>
							<label>SUB NAME</label>
						</th>
						<td>
							<input class="frm-input border aaa" type="text" name="sub_name" id="sub_name" placeholder="영어 이름은 영문으로 입력 해주세요." />
							<span>**</span>
						</td>
					</tr>
					<tr>
						<th>
							<label>E-MAIL</label>
						</th>
						<td>
							<input class="frm-input border aaa" type="text" name="email" id="email" placeholder="전체 Email 주소를 입력해주세요." />
							<span>**</span>
						</td>
					</tr>
					<tr>
						<th>
							<label>H.P</label>
						</th>
						<td>
							<input class="frm-input border aaa" type="text" name="h_phone" id="h_phone" placeholder="하이픈(-)을 포함하여 입력해주세요." />
							<span>**</span>
						</td>
					</tr>
					<tr>
						<th>
							<label>O.P</label>
						</th>
						<td>
							<input class="frm-input border aaa" type="text" name="o_phone" id="o_phone" placeholder="하이픈(-)을 포함하여 입력해주세요." />
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
	
	<c:if test="${page_type eq 'new_employee' }">
		<div class="btn_div align-rtr">
			<button class="main-text-button" id="regCancel">취소</button>
			<button class="main-text-button" id="regAnnBtn">등록</button>
		</div>
	</c:if>
	
	<c:if test="${page_type eq 'update_employee' }">
		<div class="btn_div align-rtr">
			<button class="main-text-button" id="regCancel">취소</button>
			<button class="main-text-button" id="updateEmployee">수정</button>
		</div>
	</c:if>

	
	
</div>
</div>


</body>

<content tag="script">
<script type="text/javascript" src="/js/jquery.validate.min.js"></script>
<script>
$(function() {
	var ckid = '';
	var form = $('empreg_form');
	
	$('#ck_idbtn').click(function() {
		var emp_id = $('#emp_id').val();
		if(emp_id.length <= 0) {
			alert('ID를 입력해주세요');
		} else {
			var ck_id = $('#emp_id').serialize();
			
			$.ajax({
				name: "emp_id",
				type: "post",
				url: "/admin/checkEmpId.ajax",
				data: ck_id, 
				success: function(response) {
					var ck = response;
					if(ck == 0) {
						alert('사용 가능한 아이디입니다.');
						ckid = emp_id;
					} else if(ck == 99) {
						alert('사용 불가능한 아이디 형식입니다.\n아이디는 영어or숫자로 이루어진 3-20글자 문자열만 가능합니다[특문/공백 사용 불가]');
						$('#emp_id').val("");
						ckid = '';
					} else if(ck == 1) {
						alert('이미 사용중인 아이디입니다.');
						ckid='';
					}
				}, 
				error: function(reqest, status, error) {
					console.log(error);
				},
				beforeSend:function(e) {
					e.setRequestHeader('${_csrf.headerName}', '${_csrf.token}'); 
				}, 
				complete:function() {
					
				}
			}); /* end ajax */
		}
	}); /* end ck emp_id */	
	
	
	$('#regAnnBtn').click(function() {
		var f_id = $('#emp_id').val();
		
		if(f_id == ckid) {
			$('#empreg_form').submit();
		} else {
			alert('아이디 중복확인을 해주세요');
		}
	});
	
	$('#updateEmployee').click(function() {
		$('#empreg_form').attr("action", "/admin/employeeUpdate.service");
		$('#empreg_form').attr("method", "post");
		$('#empreg_form').submit();
	});
	
	$('#empreg_form').validate({
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
			emp_id: {
				required: true
				,vali_id: true
				,minlength: 3
				,maxlength: 20
			}
			,pw: {
				required: true
				,vali_pw: true
				,minlength: 10
			}
			,name: {
				required: true
				,vali_kr: true
				,minlength: 1
				,maxlength: 20
			}
			,sub_name: {
				required: true
				,vali_eng: true
				,minlength: 2
				,maxlength: 20
			}
			,email: {
				required: true
				,email: true
			}
			,h_phone: {
				required: true
				,vali_phone: true
			}
			,o_phone: {
				vali_ophone: true
			}
		}
		,messages: {
			emp_id: {
				required: "필수입력 사항입니다."
				,vali_id: "사용 불가능한 아이디 형식입니다.\n아이디는 영어or숫자로 이루어진 3-20글자 문자열만 가능합니다[특문/공백 사용 불가]"
				,minlength: "ID는 3-20글자 사이로 입력해주세요."
				,maxlength: "ID는 3-20글자 사이로 입력해주세요."
			}
			,pw: {
				required: "필수입력 사항입니다."
				,vali_pw: "사용 불가능한 비밀번호 형식입니다.\n비밀번호는 영어or숫자로 이루어진 10글자 이상 문자열만 가능합니다"
				,minlength: "비밀번호는 최소 10글자 이상 입력해주세요"
			}
			,name: {
				required: "필수입력 사항입니다."
				,vali_kr: "사용 불가능한 이름 형식입니다.\n이름은 한글로만 입력해주세요[공백불가]"
				,minlength: "이름은 1글자 이상 입력해주세요"
				,maxlength: "입력 가능 길이를  초과하였습니다."
			}
			,sub_name: {
				required: "필수입력 사항입니다."
				,vali_eng: "사용 불가능한 영어이름입니다.\n영어이름은 영문으로만 입력해주세요[공백사용불가]"
				,minlength: "영어이름은 2글자 이상 입력해주세요"
				,maxlength: "입력 가능 길이를  초과하였습니다."
			}
			,email: {
				required: "필수입력 사항입니다."
				,email: "올바른 이메일 형식이 아닙니다."
			}
			,h_phone: {
				required: "필수입력 사항입니다."
				,vali_phone: "올바른 형식이 아닙니다.\n핸드폰 번호는 하이픈(-)을 포함하여 입력해주세요"
			}
			,o_phone: {
				vali_ophone: "올바른 형식이 아닙니다.\n사내번호는 하이픈(-)을 포함하여 입력해주세요"
			}
		}
	});
	
	
		
	if('${insertEmp}' == 'f') {
		alert('직원 등록 실패');
	} 
	
	$('#regCancel').click(function() {
		location = "/admin/employeeList.mi";
	});
	
	
	
	if("${page_type}" == "update_employee") {
		var form = $("#empreg_form");
		form.find('[name = "name"]').val('${empDto.name}');
		form.find('[name = "sub_name"]').val('${empDto.sub_name}');
		form.find('[name = "email"]').val('${empDto.email}');
		form.find('[name = "h_phone"]').val('${empDto.h_phone}');
		form.find('[name = "o_phone"]').val('${empDto.o_phone}');
	}
	
});

$.validator.addMethod("vali_id", function(value) {
	return /^[a-zA-Z0-9]+$/.test(value);
});

$.validator.addMethod("vali_pw", function(value) {
//	return /^.*(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/.test(value);
//	return /^[a-zA-Z0-9!@#$%^*+=-]+$/.test(value);
	return /^[a-zA-Z]+[0-9]+$/.test(value);
});

$.validator.addMethod("vali_kr", function(value) {
	return /^[가-힣]+$/.test(value);
});

$.validator.addMethod("vali_eng", function(value) {
	return /^[a-zA-Z]+$/.test(value);
});

$.validator.addMethod("vali_phone", function(value) {
	return /^01([0|1|6|7|8|9]?)-([0-9]{3,4})-([0-9]{4})$/.test(value);
});

$.validator.addMethod("vali_ophone", function(value) {
	return  /^(\d{2,3}-\d{3,4}-\d{4}|)$/.test(value);
});


</script>
</content>


</html>