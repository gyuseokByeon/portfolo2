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
	<title>Admin::Annual Vacation Registration</title>
</head>

<body>
<div>
<div class="con_title">
	<h2>연차 휴가 등록 페이지</h2>
</div>

<div class="con_body">
	<div class="frm_div">
		<form:form id="form_annreg" name="form_annreg" action="/admin/reg-ann.service" method="post" >
			<table class="form_tbl">
				<tbody>
					<tr>
						<th>
							<label>YEAR</label>
						</th>
						<td>
							<select class="frm-select border" name="year" id="selectyear" size="1">
								<option value="0" disabled="disabled" selected="selected">연차를 등록할 년도 선택해주세요</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<label>직원</label>
						</th>
						<td>
							<select class="frm-select border" name="emp_id" id="select_emp" size="1">
								<option>---</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<label>COUNT</label>
						</th>
						<td>
							<input class="frm-input border fff" name="total_cnt" id="total_cnt" />
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
	
	<div class="btn_div" style="text-align: right;">
		<button class="main-text-button" id="regCancel">취소</button>
		<button class="main-text-button" id="regAnnBtn">등록</button>
	</div>
	
	<form:form name="year_form" id="year_form">
		<input type="hidden" name="year" id="year" />
	</form:form>
	
	
</div>
</div>
</body>

<content tag="script">
<script type="text/javascript" src="/js/jquery.validate.min.js"></script>
<script>
var today = new Date();
var y1 = today.getFullYear();
var y2 = y1+1;

var select_year = document.getElementById("selectyear");
var option_year1 = document.createElement("option");
var option_year2 = document.createElement("option");
option_year1.value = y1;
option_year1.text=y1;
select_year.add(option_year1);
option_year2.value = y2;
option_year2.text=y2;
select_year.add(option_year2);

$(function() {
	$('#selectyear').change(function() {
		var selyear = $('#selectyear').val();
		var year_form = $('#year_form');
		year_form.find('[name="year"]').val(selyear);
		var form_data = $('#year_form').serialize();
		var select_emp = document.getElementById("select_emp");
		
		for(var i = 0; i < select_emp.options.length; i++) {
			select_emp.options[i] = null;
		}
		select_emp.options.length = 0;
		
		$.ajax({
			name: "year_form",
			type: "post",
			url: "/admin/year_emplist.ajax",
			data: form_data, 
			success: function(response) {
				var empList = response;
				
				var select_emp = document.getElementById("select_emp");
				
				for(var i=0; i<empList.length; i++) {
					var empDto = empList[i];
					var option_emp = document.createElement("option");
					option_emp.value = empDto.emp_id;
					option_emp.text = ''+empDto.emp_id+'/'+empDto.name+'/'+empDto.sub_name;
					select_emp.add(option_emp);
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
		
	}); /* end select setting */
	
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
			year: "required"
			,emp_id: "required"
			,total_cnt: {
				required: true,
				digits: true,
				min: 1,
				max: 50
			}
		}
		,messages : {
			year: "필수 선택사항입니다"
			,emp_id: "필수 선택사항입니다"
			,total_cnt: {
				required: "필수 입력 사항입니다.",
				digits: "숫자만 입력해주세요",
				min: "입력 범위는 [1~50] 사이의 숫자입니다.",
				max: "입력 범위는 [1~50] 사이의 숫자입니다."
			}
		}
	}); // end validate
	
	$('#regAnnBtn').click(function() {
		$('#form_annreg').submit();
	});
	
	$('#regCancel').click(function() {
		location = "/admin/annualVacationLog.mi";
	});
	
});


</script>
</content>


</html>