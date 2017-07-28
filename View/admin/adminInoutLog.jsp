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
	<link rel="stylesheet" type="text/css" href="/css/admin-inout.css">
	<link rel="stylesheet" type="text/css" href="/css/modal.css">
	<link rel="stylesheet" type="text/css" href="/css/register-page.css">
	<title>Admin::InOut Log</title>
</head>

<body>
<div class="wrap">
<div class="con_title">
	<h2>근태관리 : 내역 조회 / 엑셀 다운로드</h2>
</div>

<div class="con_body">
	
	<div class="tbl-contents">
		<table class="table table-hover table-striped table-bordered">
			<thead>
				<tr>
					<th>REAL DAY</th>
					<th>직원</th>
					<th>출근</th>
					<th>지각</th>
					<th>직출</th>
					<th>직출 사유</th>
					<th>퇴근</th>
					<th>야근 사유</th>
					<th>수정</th>
					<th>외근 내역 조회</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach var="miDto" items="${list }">
				<tr>
					<td>
					<fmt:parseDate var="dateString" value="${miDto.real_day }" pattern="yyyy-MM-dd" /> 
					<fmt:formatDate value="${dateString }" pattern="yy-MM-dd" /></td>
					<td><span>${miDto.emp_id }</span><span>(</span>${miDto.name }<span>)</span></td>
					<td><fmt:parseDate var="dateString" value="${miDto.mi_in }" pattern="yyyy-MM-dd HH:mm:ss" /> 
						<fmt:formatDate value="${dateString }" pattern="yy-MM-dd HH:mm:ss" /></td>
					<td><c:if test="${miDto.late_yn == 1 }"><span>○</span></c:if>
						<c:if test="${miDto.late_yn == 0 }"><span>X</span></c:if></td>
					<td><c:if test="${miDto.jik_yn == 1 }"><span>○</span></c:if>
						<c:if test="${miDto.jik_yn == 0 }"><span>X</span></c:if></td>
					<td>${miDto.jik_comment }</td>
					<td><fmt:parseDate var="dateString" value="${miDto.mi_out }" pattern="yyyy-MM-dd HH:mm:ss" /> 
						<fmt:formatDate value="${dateString }" pattern="yy-MM-dd HH:mm:ss" /></td>
					<td>${miDto.night_comment }</td>
					<td><button onclick="miModalon(${miDto.rid} , '${miDto.real_day }' , '${miDto.emp_id }', '${miDto.name }')">수정</button></td>
					<td><button onclick="workoutModalon('${miDto.real_day}', '${miDto.emp_id }')">조회</button></td>
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
		<form:form id="searchpageForm" action="/admin/inoutLog.mi" method="post">
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
	
	<div class="btn_div">
		<button class="main-text-button" id="sheet_down" style="margin-top: 2rem;">엑셀 다운로드</button>
	</div>
	
	<!-- 외근 목록 조회 모달 -->
	<div id="workoutModal" class="modal">
		<div class="modal-content">
			<div class="modal-header">
				<span class="close" onclick="closeModal(2)">&times;</span>
				<h2 id="modal_title"></h2>
			</div>
			<div class="modal-body">
				<div>
					<div class="tbl-contents" id="workTable">
						<table class="table table-hover table-striped table-bordered">
							<thead>
								<tr>
									<th>NO</th>
									<th>OUT TIME</th>
									<th>IN TIME</th>
									<th>COMMENT</th>
								</tr>
							</thead>
							<tbody id="work_tbody">
								
							</tbody>
						</table>
					</div>
					
				</div>
			</div>
			<div class="modal-footer">

			</div>
		</div>
	</div>
	
	<form:form id="empWork_form" name="empWork_form">
		<input type="hidden" id="real_day" name="real_day" />
		<input type="hidden" id="emp_id" name="emp_id" />
	</form:form>
	
	
	<!-- 근태내역 상세보기 모달 -->
 	<div id="miModal" class="modal">
		<div class="modal-content">
			<div class="modal-header">
				<span class="close" onclick="closeModal(1)">&times;</span>
				<h2 id="iomodal_title"></h2>
			</div>
			<div class="modal-body">
				<div>
					<div class="frm_div">
						<form:form id="io_detail_form" action="/admin/updateInout.service" method="post">
							<table class="form_tbl">
								<tbody>
									<tr>
										<th>
											<label>출근시간</label>
										</th>
										<td>
											<input type="hidden" name="rid" id="rid"/>
											<input type="hidden" name="real_day" id="real_day" />
											<input type="hidden" name="emp_id" id="emp_id" />
											<input type="datetime-local" name="mi_in" id="mi_in" />
										</td>
									</tr>
									<tr>
										<th>
											<label>지각 여부</label>
										</th>
										<td>
											<input type="hidden" name="late_yn" id="late_yn"/>
											<input type="checkbox" name="late_ynck" id="late_ynck"/>
											<label for="late_yn">지각</label>
										</td>
									</tr>
									<tr>
										<th>
											<label>직출 여부</label>
										</th>
										<td>
											<input type="hidden" name="jik_yn" id="jik_yn"/>
											<input type="checkbox" name="jik_ynck" id="jik_ynck"/>
											<label for="jik_yn">직출</label>
										</td>
									</tr>
									<tr>
										<th>
											<label>직출사유</label>
										</th>
										<td>
											<input class="frm-input border fff" type="text" name="jik_comment" id="jik_comment" />
										</td>
									<tr>
										<th>
											<label>퇴근시간</label>
										</th>
										<td>
											<input type="datetime-local" name="mi_out" id="mi_out" />
										</td>
									</tr>
									<tr>
										<th>
											<label>야근 사유</label>
										</th>
										<td>
											<input class="frm-input border fff" type="text" name="night_comment" id="night_comment" />
										</td>
									</tr>
								</tbody>
							</table>
						</form:form>
					</div>
				</div>
				
				<div class="btn_div">
					<button class="main-text-button" id="updateInoutBtn">수정</button>
				</div>
			</div>
			
			<div class="modal-footer">

			</div>
		</div>
	</div>
	
	<form:form id="empinout_form" name="empinout_form">
		<input type="hidden" id="rid" name="rid" />
		<input type="hidden" id="real_day" name="real_day" />
		<input type="hidden" id="emp_id" name="emp_id" />
	</form:form>


</div>
</div>
</body>

<content tag="script">
<script type="text/javascript" src="/js/jquery.validate.min.js"></script>
<script>
var mi_modal = document.getElementById('miModal');
var work_modal = document.getElementById('workoutModal');

function closeModal(value) {
	if(value==1) {
		mi_modal.style.display = "none";
	} else if(value==2) {
		work_modal.style.display = "none";
	}
}

window.click = function(event) {
	if (event.target == work_modal) {
		work_modal.style.display = "none";
	} else if (event.target == mi_modal) {
		mi_modal.style.display = "none";
	}
}


/* ==== 출퇴근 상세내역 수정 모달 ==== */
function miModalon(rid, real_day, emp_id, name) {
	$('#iomodal_title').html('[ ' + real_day + ' : ' + name +'(' +emp_id + ')' + ' ]');
	
	var empinout_form = $('#empinout_form');
	empinout_form.find('[name="rid"]').val(rid);
	empinout_form.find('[name="real_day"]').val(real_day);
	empinout_form.find('[name="emp_id"]').val(emp_id);
	
	var form_data = $('#empinout_form').serialize();
	
	$.ajax({
		name: "empinout_form",
		type: "post",
		url: "/admin/adm_empinout_oneday.ajax",
		data: form_data,
		success: function(response) {
			var ioDto = response;
			var io_form = $('#io_detail_form');
			
			io_form.find('[name="rid"]').val(ioDto.rid);
			io_form.find('[name="emp_id"]').val(ioDto.emp_id);
			io_form.find('[name="real_day"]').val(ioDto.real_day);
			io_form.find('[name="jik_comment"]').val(ioDto.jik_comment);
			io_form.find('[name="night_comment"]').val(ioDto.night_comment);
			
			
			if(ioDto.jik_yn == 1) {
				document.getElementById("jik_ynck").checked = true;
				io_form.find('[name="jik_yn"]').val("1");
			} else if(ioDto.jik_yn == 0) {
				document.getElementById("jik_ynck").checked = false;
				io_form.find('[name="jik_yn"]').val("0");
			}
			
			if(ioDto.late_yn == 1) {
				document.getElementById("late_ynck").checked = true;
				io_form.find('[name="late_yn"]').val("1");
			} else if(ioDto.late_yn == 0) {
				document.getElementById("late_ynck").checked = false;
				io_form.find('[name="late_yn"]').val("0");
			}
			
			console.log(ioDto.late_yn + " : " + io_form.find('[name="late_yn"]').val() + " : " + ioDto.jik_yn + " : " + io_form.find('[name="jik_yn"]').val() );
			
			/* mi_in */
			var inLocalDate = new Date(ioDto.mi_in);
			var inString = inLocalDate.format("yyyy-MM-dd") + "T" + inLocalDate.format("HH:mm:ss");
			
			var minInDate = new Date(ioDto.mi_in);
			minInDate.setHours(6);
			minInDate.setMinutes(0);
			minInDate.setSeconds(0);
			var minInDateString = minInDate.format("yyyy-MM-dd") + "T" + minInDate.format("HH:mm:ss");
			
			var maxInDate = new Date(ioDto.mi_in);
			maxInDate.setHours(23);
			maxInDate.setMinutes(0);
			maxInDate.setSeconds(0);
			var maxInDateString = maxInDate.format("yyyy-MM-dd") + "T" + maxInDate.format("HH:mm:ss");
			
			io_form.find('[name="mi_in"]').attr("min", minInDateString);
			io_form.find('[name="mi_in"]').attr("max", maxInDateString);
			io_form.find('[name="mi_in"]').val(inString);
			
			/* mi_out */
			var outLocalDate = new Date(ioDto.mi_out);
			var outString = outLocalDate.format("yyyy-MM-dd") + "T" + outLocalDate.format("HH:mm:ss");
			
			io_form.find('[name="mi_out"]').val(outString);
			
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
	
	mi_modal.style.display = "block";
} /* end detailModal() */



/* ==== 외근 내역 조회 모달 ======*/
function workoutModalon(real_day, emp_id) {
	var empWork_form = $('#empWork_form');
	empWork_form.find('[name="real_day"]').val(real_day);
	empWork_form.find('[name="emp_id"]').val(emp_id);
	
	var form_data = $('#empWork_form').serialize();
	
	$('#modal_title').html('[ ' + real_day + ' : ' + emp_id + ' ]');
	$.ajax({
		name: "empWork_form"
		,type: "post"
		,url: "/admin/adm_workinout_onelist.ajax"
		,data: form_data
		,success: function(response) {
			var workList = response;
			$('#work_tbody').html('');
			console.log(workList.length);
			if(workList.length <= 0) {
				$('#work_tbody').html('<tr><td colspan="4">'+'외근 기록이 없습니다.'+'</td></td>');
			} else {
				
				for(var i = 0; i < workList.length; i++) {
					var dto = workList[i];
					
					var work_out = new Date(dto.work_out).format("a/p hh:mm:ss");
					var work_in = new Date(dto.work_in).format("a/p hh:mm:ss");
					
					$('#work_tbody').append('<tr><td>'+(i+1)+'</td><td>'+work_out+'</td><td>'+work_in+'</td><td>'+dto.work_comment+'</td></tr>');
				}
			}
			
			work_modal.style.display = "block";

		}
		,error: function(reqest, status, error) {
			console.log(error);
		}
		,beforeSend:function(e) {
			e.setRequestHeader('${_csrf.headerName}', '${_csrf.token}'); 
		}
		,complete:function() {
			
		}
	}); /* end ajax */
	
} /* end workoutModal() */




$(function() {
	var form = $('#searchpageForm');
	
 	var s_searchType = '${search.searchType }';
 	if(s_searchType.length > 0) {
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
		form.attr('action', '/admin/inoutLog.mi');
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
		location = "/admin/inoutSheet.service";
	});
	
	
	$('#io_detail_form').validate({
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
			mi_in: {
				check_miin: true
			}
			,mi_out: {
				check_miout: true
				,check_night: true
			}
			,jik_comment: {
				maxlength: 200
			}
			,night_comment: {
				maxlength: 200
			}
		}
		,messages : {
			mi_in: {
				check_miin: "출근 시간을 확인해주세요"
			}
			,mi_out: {
				check_miout: "퇴근 시간 입력을 확인해주세요"
				,check_night: "야근 사유를 입력해주세요"
			}
			,jik_comment: {
				maxlength: "최대 200글자 까지 입력 가능합니다"
			}
			,night_comment: {
				maxlength: "최대 200글자 까지 입력 가능합니다"
			}
		}
	});

	$('#updateInoutBtn').click(function() {	
		var form = $("#io_detail_form");
		
		var late = $('#late_ynck');
		if(late[0].checked == true) {
			form.find('[name="late_yn"]').val(1);
		} else {
			form.find('[name="late_yn"]').val(0);
		}
		
		var jik = $('#jik_ynck');
		if(jik[0].checked == true) {
			form.find('[name="jik_yn"]').val(1);
		} else {
			form.find('[name="jik_yn"]').val(0);
		}
		
		var ck_late = form.find('[name="late_yn"]').val();
		var ck_jik = form.find('[name="jik_yn"]').val();
		if(ck_late == 1 && ck_jik == 1) {
			alert('직출과 지각은 동시에 선택될 수 없습니다.');
		} else if(ck_jik == 1 && ((form.find('[name="jik_comment"]').val()).length) == 0) {
			alert('직출 사유를 입력해주세요');
		} else {
			form.submit();
		}
		
	});
	
	if('${update_r }' == 's') {
		alert("정보 수정 성공");
	} else if('${update_r}' == 'f') {
		alert("정보 수정 실패");
	}
	
});


$.validator.addMethod("check_miin", function(value) {
	var ck_form = $('#io_detail_form');
	
	var realday = ck_form.find('[name="real_day"]').val();
	realday = new Date(realday);
	
	var in_date = new Date(value);
	
	if(in_date.getMonth() == realday.getMonth() && in_date.getDate() == realday.getDate() && in_date.getHours() >= 6) {
		return true;
	} else {
		return false;
	}
});


$.validator.addMethod("check_miout", function(value) {
	var ck_form = $('#io_detail_form');

	if(value.length == 0) {
		return true;
	} else {
		var realday = ck_form.find('[name="real_day"]').val();
		realday = new Date(realday);
		console.log(realday);
		
		var out_date = new Date(value);

		var tmpReal = new Date(realday.getTime() + (1 * 24 * 60 * 60 * 1000));
		
		if(out_date.getMonth() == realday.getMonth() && out_date.getDate() == realday.getDate() && out_date.getHours() >= 6) {
			return true;
		} else if(out_date.getMonth() == tmpReal.getMonth() && out_date.getDate() == tmpReal.getDate() && out_date.getHours() < 6) {
			return true;
		} else {
			return false;
		}
	}
});

$.validator.addMethod("check_night", function(value) {
	var ck_form = $('#io_detail_form');
	
	var out_date = new Date(value);
	if(out_date.getHours() >= 23 || out_date.getHours() <= 5 ) {
		var comment = ck_form.find('[name="night_comment"]').val();
		if(comment.length == 0) {
			return false;
		} else {
			return true;
		}
		
	} else {
		return true;
	}
});



function fn_page(page) {
	var form = $('#searchpageForm');
	console.log(page);
	form.find('[name="page"]').val(page);
	form.attr('action', '/admin/inoutLog.mi');
	form.attr('method', 'post');
	form.submit();
}

Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};
 
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};


</script>
</content>


</html>