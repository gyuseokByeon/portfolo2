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
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/register-page.css">
	<title>Vacation Registration</title>
</head>
<body>
<div class="wrap">
<div class="con_title">
	<h2>Vacation Register</h2>
</div>

<div class="con_body">
	<div class="frm_div">
	<form:form id="vcreg_form" action="/vacation/reg-vc.service" method="post">
		<table class="form_tbl">
			<tbody>
			<tr>
				<th>
					<label>휴가 종류</label>
				</th>
				<td>
					<div>
						<select class="frm-select border" name="vac_id" id="vac_id" size="1" >
							<option value="" disabled="disabled" selected="selected">사용할 휴가를 선택해주세요</option>
							<c:forEach var="code" items="${codeList }">
							<option value="${code.code_id }" id="${code.attr2 }">${code.code_name }</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			
			<tr> 
				<th>
					<label>휴가 기간</label>
				</th>
				<td>
					<div class="dd">
						<input class="frm-input border aaa date-pick-box" type="text" name="vac_start" id="vac_start" />
						<span>~</span>
						<input class="frm-input border aaa date-pick-box" type="text" name="vac_end" id="vac_end" />
					</div>
				</td>
			</tr>
			
			<tr>
				<th>
					<label>휴가 일수</label>
				</th>
				<td>
					<div>
						<input class="frm-input border fff" type="text" name="vac_cnt" id="vac_cnt" readonly="readonly"/>
						<span id="restvac" hidden="hidden">${restvac }</span>
					</div>
				</td>
			</tr>
			
			<tr>
				<th>
					<label>휴가 사유</label>
				</th>
				<td>
					<div>
						<input class="frm-input border" type="text" name="vac_comment" id="vac_comment">
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
						<button class="main-text-button" id="btn_vcreg">등록</button>
						<button class="main-text-button" id="btn_back">취소</button>
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
	$('#vac_id').change(function() {
		$('#vac_end').val('');
		$('#vac_cnt').val('');
	})
	
	
	$('#vac_start').datepicker({
		dateFormat: 'yy-mm-dd',
		onSelect: function(value) {
			var cd = new Date(value);
			if(cd.getDay()==6 || cd.getDay()==0) {
				alert('토/일요일은 선택하실수 없습니다.');
				$('#vac_start').val("");
			}
			if($("#vac_id option:selected").val() == "" || $("#vac_id option:selected").val() == null || $("#vac_id option:selected").val() == "0") {
				alert('사용하실 휴가의 종류를 먼저 선택해주세요');
				$('#vac_start').val("");
			}
		}
	});
	
	
	$('#vac_end').datepicker({
		dateFormat: 'yy-mm-dd',
		onSelect: function(value) {
			var cd = new Date(value);
			var addnum = $("#vac_id option:selected").attr('id');
			if($("#vac_id option:selected").val() == "" || $("#vac_id option:selected").val() == null || $("#vac_id option:selected").val() == "0") {
				alert('사용하실 휴가의 종류를 먼저 선택해주세요');
				$('#vac_start').val("");
			}
			if(addnum == 0.5 && $('#vac_start').val() < value ) {
				alert('반차는 하루만 선택할 수 있습니다');
				$('#vac_end').val("");
			}
			if($('#vac_start').val() == null || $('#vac_start').val() == "") {
				alert('시작일을 먼저 선택해주세요');
				$('#vac_end').val("");
			} else if(cd.getDay()==6 || cd.getDay()==0) {
				alert('토/일요일은 선택하실수 없습니다.');
				$('#vac_end').val("");
			} else {
				if($('#vac_start').val() > value) {
					alert('시작일 이후의 날을 선택해주세요' + value);
					$('#vac_end').val('');
				}
			}
		},
		onClose: function(value) {
			if($('#vac_start').val() != null || $('#vac_start').val() != "" || $('#vac_end').val() != null || $('#vac_end').val() != "") {
				var stst = new Date($('#vac_start').val());
				var eded = new Date(value);
				var cntcnt = 0;
				var ck = eded;
				
				var addnum = $("#vac_id option:selected").attr('id');
				
				while( ck >= stst) {
					if(ck.getDay() == 6 || ck.getDay() == 0) {
						
					} else {
						cntcnt = cntcnt+(1*addnum);
					}
					ck = new Date(ck.getTime() - (1 * 24 * 60 * 60 * 1000));
				}
				
				$('#vac_cnt').val(cntcnt);
				$('#vcreg_form').find('[name="vac_cnt"]').val(cntcnt);
				
			}
		}
	});
	
	$('#btn_back').click(function() {
		location="/vacation/mylist.mi";
	});
	
	
	function validateDate(dateString) {
		var regExp = /^\d{4}\-\d{1,2}\-\d{1,2}$/;
		if(dateString.lenght == 0) {
			return false;
		}
		if(!dateString.match(regExp)) {
			return false;
		}
		return true;
	}
	
	$('#btn_vcreg').click(function() {
		if(validateDate($('#vac_start').val()) && validateDate($('#vac_end').val())) {
			$('#vcreg_form').submit();
		} else {
			alert('기간 입력을 확인해주세요');
			$('#vac_end').val('');
			$('#vac_start').val('');
			$('#vac_cnt').val('');
			$('#vac_comment').val('');
		}
	});
	
	$('#vcreg_form').validate({
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
			vac_end: "required"
			,vac_start: "required"
			,vac_comment: "required"
			,vac_id: "required"
		}
		,messages : {
			  vac_end : "필수입력사항입니다."
			 ,vac_start: "필수입력사항입니다"
			 ,vac_comment: "필수입력사항입니다"
			 ,vac_id: "필수입력사항입니다"
		}
	});
});

</script>
</content>


</html>
