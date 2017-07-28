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
	<link rel="stylesheet" type="text/css" href="/css/inout-log.css">
	<title>근태관리</title>
</head>
<body>
	<div class="inoutHead"> <div class="wrap">
		<c:if test="${sessionScope.TODAY_RD eq real_day }">
			<div class="btn_div">
				<button class="miin_btn" id="miin_btn">
					<div class="btnImg"><img src="/img/icons/commute_main_topbt1.png"></div>
					<div class="btnText">출근</div>
				</button>
				<button class="mijik_btn" id="mijik_btn">
					<div class="btnImg"><img src="/img/icons/commute_main_topbt2.png"></div>
					<div class="btnText">직출</div>
				</button>
				<button class="miout_btn" id="miout_btn">
					<div class="btnImg"><img src="/img/icons/commute_main_topbt3.png"></div>
					<div class="btnText">퇴근</div>
				</button>
				<button class="workout_btn" id="workout_btn">
					<div class="btnImg"><img src="/img/icons/commute_main_topbt4.png"></div>
					<div class="btnText">외근</div>
				</button>
				<button class="workin_btn" id="workin_btn">
					<div class="btnImg"><img src="/img/icons/commute_main_topbt5.png"></div>
					<div class="btnText">복귀</div>
				</button>
			</div>
		</c:if>
		<c:if test="${sessionScope.TODAY_RD ne real_day }">
			<div class="btn_div">
				<button class="none_clicker miin_btn">
					<div class="btnImg"><img src="/img/icons/commute_main_topbt1.png"></div>
					<div class="btnText">출근</div>
				</button>
				<button class="none_clicker mijik_btn">
					<div class="btnImg"><img src="/img/icons/commute_main_topbt2.png"></div>
					<div class="btnText">직출</div>
				</button>
				<button class="none_clicker miout_btn">
					<div class="btnImg"><img src="/img/icons/commute_main_topbt3.png"></div>
					<div class="btnText">퇴근</div>
				</button>
				<button class="none_clicker workout_btn">
					<div class="btnImg"><img src="/img/icons/commute_main_topbt4.png"></div>
					<div class="btnText">외근</div>
				</button>
				<button class="none_clicker workin_btn">
					<div class="btnImg"><img src="/img/icons/commute_main_topbt5.png"></div>
					<div class="btnText">복귀</div>
				</button>
			</div>
		</c:if>
		<div class="date_div Gotham">
			<div class="moveDateBtn" id="btn_preday">
				<img alt="" src="/img/icons/commute_main_month_leftbt.png">
			</div>
			<div class="real_day" id="btn_today">
				<div>Go Today</div>
				<div>
					<fmt:parseDate var="dateString" value="${real_day }" pattern="yyyy-MM-dd" /> 
					<fmt:formatDate value="${dateString }" pattern="yyyy.MM.dd" />
				</div>
			</div> 
			<div class="moveDateBtn" id="btn_nextday">
				<img alt="" src="/img/icons/commute_main_month_rightbt.png">
			</div>
		</div>
		<div class="Gotham searchBox" id="searchBoxIn">
			<input type="text" class="date-pick-box" id="datepicker">
			<button id="btn_daysearch" class="main-text-button">search</button>
		</div>
		<form:form id="moveday_form" method="post" action="/inout/inout-log.mi">
			<input type="hidden" name="today" id="today" value="${real_day }" />
			<input type="hidden" name="move_day" id="move_day" value="" />
		</form:form>
	</div></div>
	
	<div class="inoutBody"> <div class="wrap">
		<div class="wrap_cards" id="card_body"> 
		<div class="oneCardline" id="card_append">
		<c:forEach var="midto" items="${mi_list }">
			<div class="oneCard"> 
				<div class="cardContents">
					<div class="nameDiv">
						<div class="centerGo">
							<div class="imageCover">
								<img src="/img/icons/commute_main_box_human.png">
							</div>
							<div class="nameTag">
								<p class="main">${midto.name }</p>
								<p class="sub">${midto.sub_name }</p>
							</div>
						</div>
					</div>
					<div class="oneday-inout">
						<div class="in-out-check-cover">
							<div class="centerGo">
								<div class="checkin-cover">
									<div class="checkin-spot">
										<b>출근</b>
										<%-- <c:if test="${midto.late_yn == 1 }"><div class="late">지각</div></c:if> --%>
										<span class="detail-time">
											<span class="<c:if test="${midto.late_yn == 1 }">late</c:if>"><fmt:parseDate var="dateString" value="${midto.mi_in }" pattern="yyyy-MM-dd HH:mm:ss" /> 
											<fmt:formatDate value="${dateString }" pattern="aa HH:mm:ss" /></span>
										</span>
									</div>
									<div class="checkin-spot comment">
										<c:if test="${midto.jik_yn == 1 }">
											<b>직출 사유</b>
											<span class="detail_comment">${midto.jik_comment }</span>
											<c:if test="${login_user == midto.emp_id }">
											<button onclick='upd_jikcom("${midto.rid}", "${midto.jik_comment }")' class="edit"></button>
											</c:if>
										</c:if>
									</div>
								</div>
								<div class="checkin-cover out">
									<div class="checkin-spot out">
										<b>퇴근</b>
										<span class="detail-time">
											<span><fmt:parseDate var="dateString" value="${midto.mi_out }" pattern="yyyy-MM-dd HH:mm:ss" /> 
											<fmt:formatDate value="${dateString }" pattern="aa HH:mm:ss" /></span>
										</span>
									</div>
									<div class="checkin-spot comment out">
										<c:if test="${!empty midto.night_comment}">
											<b>야근 사유</b>
											<span class="detail_comment">${midto.night_comment }</span> 
											<c:if test="${login_user == midto.emp_id }">
											<button onclick='upd_nightcom("${midto.rid}", "${midto.night_comment }")' class="edit"></button>
											</c:if>
										</c:if>
									</div>
								</div>
							</div>
						</div>
						<div class="workinout">
							<div class="centerGo">
								<c:forEach var="workdto" items="${work_list }">
									<c:if test="${workdto.emp_id == midto.emp_id }">
										<div class="oneWorkinout">
											<div class="row">
												<p>${workdto.work_comment}
													<c:if test="${login_user == midto.emp_id }">
													<button onclick='upd_workcom("${workdto.rid}", "${workdto.work_comment }")' class="edit"></button>
													</c:if>
												</p>
											</div>
											<div class="icon-cover">
												<div class="block out">
													<div class="icon">
														<img src="/img/icons/commute_main_box_out.png" />
													</div>
													<span class="detail-date-ss">
														<fmt:parseDate var="dateString" value="${workdto.work_out }" pattern="yyyy-MM-dd HH:mm:ss" /> 
														<fmt:formatDate value="${dateString }" pattern="aa HH:mm:ss" />
													</span>
												</div>
												<div class="block in">
													<div class="icon">
														<img src="/img/icons/commute_main_box_in.png" />
													</div>
													<span class="detail-date-ss">
														<fmt:parseDate var="dateString" value="${workdto.work_in }" pattern="yyyy-MM-dd HH:mm:ss" /> 
														<fmt:formatDate value="${dateString }" pattern="aa HH:mm:ss" />
													</span>
												</div>
											</div>
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
		</div>
		</div>
	
		<form:form id="scrollpage_form" name="scrollpage_form">
		<input type="hidden" name="p_start" id="p_start" value="${p_start }"/>
		<input type="hidden" name="real_day" id="real_day" value="${real_day }"/>
		<input type="hidden" name="ito" id="ito"/>
		<input type="hidden" name="size" id="size" value="${fn:length(mi_list)}" />
		</form:form>
		
		<form:form id="inout_form" name="inout_form">
		<input type="hidden" name="btn_type" value=""/>
		<input type="hidden" name="jik_comment" value="" />
		<input type="hidden" name="night_comment" value=""/>
		</form:form>
		
		<form:form id="inout_work_form" name="inout_work_form">
		<input type="hidden" name="btn_type" value=""/>
		<input type="hidden" name="work_comment" value="" />
		</form:form>
		
		<form:form id="upd_jik_form">
		<input type="hidden" name="jik_comment" >
		<input type="hidden" name="rid" >
		</form:form>
		
		<form:form id="upd_ni_form">
		<input type="hidden" name="night_comment" >
		<input type="hidden" name="rid" >
		</form:form>
		
		<form:form id="upd_wo_form">
		<input type="hidden" name="work_comment" >
		<input type="hidden" name="rid" >
		</form:form>
	</div></div>
</body>

<content tag="script">
<script>
$(function() {
	var iii = 1;
	$(window).scroll(function() {	
		if($(window).scrollTop() >= ($(document).height()-window.innerHeight)-5) {
			console.log('bottom');
			var orip_start = $('#scrollpage_form').find('[name="p_start"]').val();
			
		/* 	if($('#scrollpage_form').find('[name="p_start"]').val() == 0) {
				
			}  */
			var aa = iii * 7;
			$('#scrollpage_form').find('[name="p_start"]').val(aa);
			iii = iii+1;
			
			var form_data = $('#scrollpage_form').serialize();
			$.ajax({
				name: "scrollpage_form",
				type: "post",
				url: "/inout/inout_addlog.ajax",
				data: form_data, 
				success: function(response) {
					$("#card_append").append(response);
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
	});
	
	
	var inout_form = $('#inout_form');
	var inout_work_form = $('#inout_work_form');
	
	$('#miin_btn').click(function() { /* 출근 */
		var nowTime = new Date();
		if(nowTime.getHours()<6) {
			alert('아직 출근 가능 시간이 아닙니다. AM 06:00 부터 출근이 가능합니다.');
		} else {
			inout_form.find('[name="btn_type"]').val('miin');
			var form_data = $('#inout_form').serialize();
			$.ajax({
				name: "inout_form",
				type: "post",
				url: "/inout/mi-inout.ajax",
				data: form_data, 
				success: function(response) {
					var $doc = new DOMParser().parseFromString(response, "text/html");
					$('#card_body').html($("#tbl_body", $doc).html());
					alert($("#message", $doc).html());
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
	}); /* end 출근 */
	
	
	$('#miout_btn').click(function () { /* 퇴근 버튼 */
		inout_form.find('[name="btn_type"]').val('miout');
		var nowDate = new Date(); /* var nowDate = new Date(2017,05,30, 6,34,12,123); */
		console.log(nowDate);
		
		if(nowDate.getHours() == 23 && nowDate.getMinutes() >= 30 || nowDate.getHours() == 24 || nowDate.getHours() < 6 && nowDate.getHours() > 0) {
			var night_prom = prompt("야근 사유를 입력해주세요");
			
			if(night_prom.length == 0) {
				alert('내용을 입력해주세요');
			} else if(night_prom.lenght > 150) {
				alert('입력 가능 범위를 초과했습니다\n[최대 150글자 까지 입력 가능]');
			} else {
				console.log("야근사유 받아옴 : " + night_co);
				var night_co = night_prom;
				$('#inout_form').find('[name="night_comment"]').val(night_co);
				
				var form_data = $('#inout_form').serialize();
				$.ajax({
					name: "inout_form",
					type: "post",
					url: "/inout/mi-inout.ajax",
					data: form_data, 
					success: function(response) {
						var $doc = new DOMParser().parseFromString(response, "text/html");
						alert($("#message", $doc).html());
						$('#card_body').html($("#tbl_body", $doc).html());
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
		} else {
			var form_data = $('#inout_form').serialize();
			$.ajax({
				name: "inout_form",
				type: "post",
				url: "/inout/mi-inout.ajax",
				data: form_data, 
				success: function(response) {
					var $doc = new DOMParser().parseFromString(response, "text/html");
					alert($("#message", $doc).html());
					$('#card_body').html($("#tbl_body", $doc).html());
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
		
		
	});
	
	$('#mijik_btn').click(function() { /* 직출 */
		console.log('직출등록');
		var jik_prom = prompt("직출 사유를 입력해주세요 ");
		
		if(jik_prom == null || jik_prom == "" ) {
			alert('내용을 입력해주세요');
		} else if(jik_prom.length > 150) {
			alert('입력 가능 범위를 초과했습니다\n[최대 150글자 까지 입력 가능]');
		} else {
			var jik_co = jik_prom;
			console.log(jik_co);
			inout_form.find('[name="jik_comment"]').val(jik_co);
			inout_form.find('[name="btn_type"]').val('mijik');
			var form_data = $('#inout_form').serialize();
			
			$.ajax({
				name: "inout_form",
				type: "post",
				url: "/inout/mi-inout.ajax",
				data: form_data, 
				success: function(response) {
					var $doc = new DOMParser().parseFromString(response, "text/html");
					alert($("#message", $doc).html());
					$('#card_body').html($("#tbl_body", $doc).html());
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
	});
	
	$('#workout_btn').click(function() { /* 외근 */
		console.log('외근등록'); 
		var workout_prom = prompt("외근 사유를 입력해주세요 ");
		
		if(workout_prom == null || workout_prom == "" ) {
			alert('내용을 입력해주세요');
		} else if(workout_prom.lenght > 150) {
			alert('입력 가능 범위를 초과했습니다\n[최대 150글자 까지 입력 가능]');
		} else {
			var work_co = workout_prom;
			console.log(work_co);
			inout_work_form.find('[name="work_comment"]').val(work_co);
			inout_work_form.find('[name="btn_type"]').val('workout');
			var form_data = $('#inout_work_form').serialize();
			$.ajax({
				name: "inout_work_form",
				type: "post",
				url: "/inout/work-inout.ajax",
				data: form_data, 
				success: function(response) {
					var $doc = new DOMParser().parseFromString(response, "text/html");
					alert($("#message", $doc).html());
					$('#card_body').html($("#tbl_body", $doc).html());
				}, 
				error: function(reqest, status, error) {
					console.log(error);
				},
				beforeSend:function(e) {
					e.setRequestHeader('${_csrf.headerName}', '${_csrf.token}'); 
				}, 
				complete:function() {
					
				}
			}); /* end ajax */		}
	});
	
	$('#workin_btn').click(function() { /* 복귀 */
		console.log('복귀등록');
		inout_work_form.find('[name="btn_type"]').val('workin');
		var form_data = $('#inout_work_form').serialize();
		$.ajax({
			name: "inout_work_form",
			type: "post",
			url: "/inout/work-inout.ajax",
			data: form_data, 
			success: function(response) {
				var $doc = new DOMParser().parseFromString(response, "text/html");
				alert($("#message", $doc).html());
				$('#card_body').html($("#tbl_body", $doc).html());
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
	});
	
	
	$('#btn_preday').click(function() {
		var form = $('#moveday_form');
		var today = new Date(form.find('[name="today"]').val());
		
		var moveday = new Date(today.getTime() - (1 * 24 * 60 * 60 * 1000));
		var moveday_str = formrealday(moveday);
		movedate_fnc(moveday_str);
	});
	
	$('#btn_nextday').click(function() {
		var form = $('#moveday_form');
		var today = new Date(form.find('[name="today"]').val());
		
		var realtoday = new Date();
		var moveday = new Date(today.getTime() + (1 * 24 * 60 * 60 * 1000));
		
		if(moveday > realtoday) {
			alert('오늘 이후의 날짜로는 이동이 불가능 합니다');
		} else {
			var moveday_str = formrealday(moveday);
			movedate_fnc(moveday_str);
		}
	});
	
	function movedate_fnc(movedate) {
		var form = $('#moveday_form');
		
		var regEx = /^\d{4}\-\d{1,2}\-\d{1,2}$/;
		
		var submitQ = true;
		if(movedate.lenght == 0) {
			alert('날짜를 선택해주세요');
			$('#datepicker').val('');
			submitQ = false;
		}
		if(!movedate.match(regEx)) {
			alert('잘못된 날짜형식입니다.');
			$('#datepicker').val('');
			submitQ = false;
		}
		if(submitQ) {
			form.find('[name="move_day"]').val(movedate);
			form.submit();
		}		
	}
	
	$('#datepicker').datepicker({
		dateFormat: 'yy-mm-dd'
	});
	
	$('#btn_daysearch').click(function() {
		var movedate = $('#datepicker').val();
		
		var chemove = new Date(movedate);
		var realtoday = new Date();
		if(chemove > realtoday) {
			alert('오늘 이후의 날짜로는 이동이 불가능 합니다');
			$('#datepicker').val('');
		} else {
			movedate_fnc(movedate);
		}
	});
	
	$('#btn_today').click(function() {
		var today = new Date();
		var today_str = formrealday(today);
		movedate_fnc(today_str);
	});
	
	$('.none_clicker').click(function() {
		alert("오늘이 아닌 날짜 내역에서는 등록이 불가합니다");
	});
	
});


function upd_jikcom(rid, jik_comment) {
	var jik_upd_prom = prompt("직출 사유 수정", jik_comment);
	
	if(jik_upd_prom.length == 0) {
		alert('내용을 입력해주세요');
	} else {
		var form = $('#upd_jik_form');
		form.find('[name="jik_comment"]').val(jik_upd_prom);
		form.find('[name="rid"]').val(rid);
		form.attr("action", "/inout/jikupdate.service");
		form.attr("method", "post");
		form.submit();
	}
}

function upd_nightcom(rid, night_comment) {
	var night_upd_prom = prompt("야근 사유 수정", night_comment);
	if(night_upd_prom == null || night_upd_prom == "" ) {
		alert('내용을 입력해주세요');
	} else {
		var form = $('#upd_ni_form');
		form.find('[name="night_comment"]').val(night_upd_prom);
		form.find('[name="rid"]').val(rid);
		form.attr("action", "/inout/niupdate.service");
		form.attr("method", "post");
		form.submit();
	}
}

function upd_workcom(rid, work_comment) {
	var work_upd_prom = prompt("외근 사유 수정", work_comment);
	if(work_upd_prom == null || work_upd_prom == "" ) {
		alert('내용을 입력해주세요');
	} else {
		var form = $('#upd_wo_form');
		form.find('[name="work_comment"]').val(work_upd_prom);
		form.find('[name="rid"]').val(rid);
		form.attr("action", "/inout/woupdate.service");
		form.attr("method", "post");
		form.submit();
	}
}


function formrealday(value) {
	var m = "";	
	if((value.getMonth()+1) < 10 ) {
		m = "0" + (value.getMonth()+1); 
	} else {
		m = value.getMonth()+1;
	}
	
	var d = "";
	if(value.getDate() < 10) {
		d = "0" + value.getDate();
	} else {
		d = value.getDate();
	}
	var daystr = value.getFullYear()+"-"+m+"-"+d;
	return daystr;
}


</script>
</content>


</html>