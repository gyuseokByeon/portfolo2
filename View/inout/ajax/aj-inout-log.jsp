<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url">${pageContext.request.requestURL}</c:set>
<c:set var="baseURL" value="${fn:replace(url, pageContext.request.requestURI, pageContext.request.contextPath)}" />
<div id="tbl_body">
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
<div id="message">${result }</div>
