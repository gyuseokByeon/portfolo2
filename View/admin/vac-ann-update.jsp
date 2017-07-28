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
	<link rel="stylesheet" type="text/css" href="/css/register-page.css">
	<link rel="stylesheet" type="text/css" href="/css/admin-inout.css">
	<title>Admin::Anuual Vacation Update</title>
</head>

<body>
<div class="wrap">
<div class="con_title">
	<h2>연차 휴가 수정 페이지</h2>
</div>

<div class="con_body">
	<div class="frm_div">
		<form:form id="form_annreg" name="form_annreg" action="/admin/update-ann.service" method="post" >
			<table class="form_tbl">
				<tbody>
					<tr>
						<th>
							<label>YEAR</label>
						</th>
						<td>
							<input type="hidden" name="rid" id="rid" value="${annDto.rid }" />
							<input type="hidden" id="ori_total_cnt" value="${annDto.total_cnt }" />
							<input type="hidden" id="ori_rest_cnt" value="${annDto.rest_cnt}" />
							<input type="hidden" id="ori_use_cnt" value="${annDto.use_cnt}" />
							<input class="frm-input border fff" name="year" id="year" value="${annDto.year }" disabled="disabled" />
						</td>
					</tr>
					<tr>
						<th>
							<label>직원</label>
						</th>
						<td>
							<input class="frm-input border fff" name="emp_id" id="emp_id" value="${annDto.emp_id }" disabled="disabled" />
						</td>
					</tr>
					<tr>
						<th>
							<label>COUNT</label>
						</th>
						<td>
							<input class="frm-input border fff" name="total_cnt" id="total_cnt" value="${annDto.total_cnt }"/>
						</td>
					</tr>
					<tr>
						<th>
							<label>REST COUNT</label>
						</th>
						<td>
							<input class="frm-input border fff" name="rest_cnt" id="rest_cnt" value="${annDto.rest_cnt }" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<th>
							<label>USE COUNT</label>
						</th>
						<td>
							<input class="frm-input border fff" name="use_cnt" id="use_cnt" value="${annDto.use_cnt }" readonly="readonly" />
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
	
	<div class="btn_div" style="text-align: right;">
		<button class="main-text-button" id="regCancel">취소</button>
		<button class="main-text-button" id="updateAnnBtn">수정</button>
	</div>
	
</div>
</div>
</body>

<content tag="script">
<script type="text/javascript" src="/js/jquery.validate.min.js"></script>
<script>
$(function() {
	$('#total_cnt').change(function() {
		var ori_total = $('#ori_total_cnt').val() * 1;
		var ori_rest = $('#ori_rest_cnt').val() * 1;
		var ori_use = $('#ori_use_cnt').val() * 1;
		var totalcnt = $('#total_cnt').val() * 1;
		
		var cc = totalcnt - ori_total;
		$('#rest_cnt').val(ori_rest + cc);
	});
	
	$('#updateAnnBtn').click(function() {
		var c_rest = $('#rest_cnt').val();
		if(c_rest < 0) {
			alert('수정이 불가능합니다.');
		} else {
			$('#form_annreg').submit();
		}
		
	});
	
	$('#form_annreg').validate({
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
			total_cnt: {
				required: true,
				digits: true,
				min: 1,
				max: 50
			}
		}
		,messages : {
			total_cnt: {
				required: "필수 입력 사항입니다.",
				digits: "숫자만 입력해주세요",
				min: "입력 범위는 [1~50] 사이의 숫자입니다.",
				max: "입력 범위는 [1~50] 사이의 숫자입니다."
			}
		}
	}); // end validate
	
	
	

	$('#regCancel').click(function() {
		location = "/admin/annualVacationLog.mi";
	});
});


</script>
</content>


</html>