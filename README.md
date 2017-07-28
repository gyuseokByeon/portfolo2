# 1.1 사내 근태시스템 구축

프로젝트 소개
=====
#### 1.1 개발기간
2017.05.24 ~ 2017.06.30 (사내 프로젝트로 단독 진행)

#### 1.2 개발환경
Spring 프레임워크 기반 사내 자체 템플릿, Spring Security 적용

DB : MariaDB

프로젝트 개요
=====
#### 2.1 DB - ERD
![ERD](/img/ERD_milvusSecurity.jpg)

#### 2.2 페이지 리스트
** (★)은 관리자만 접근 가능한 페이지
> 1.1 로그인  
> <br/>
> 2.1 근태 등록 / 조회  
> 2.2 전직원 근태 내역 조회 / 다운로드 (★)  
> 2.3 전직원 월별 근태 통계 조회 / 다운로드 (★)  
> <br/>
> 3.1 휴가 내역 조회 <br/>
> 3.2 휴가 등록 <br/>
> 3.3 전직원 휴가 내역 조회 / 다운로드 (★) <br/>
> 3.4 전직원 연차 조회 / 등록 (★) <br/>
> <br/>
> 4.1 직원 목록 <br/>
> 4.2 신규 직원 등록 (★) <br/>
> <br/>
> 5 마이페이지 <br/>
> 5.1 월별 근태내역 통계 <br/>
> 5.2 연도별 연차내역 <br/>
> 5.3 내 정보 수정 <br/>
> <br/>
> 6.1 관리자 요청 게시판 (일반직원 : 요청글 작성, 본인 등록글 조회 가능 / 관리자 : 전체 요청내역 조회 및 처리상태 변경 ) <br/>

프로젝트 상세
=====
1.1 로그인
--------
<img src="/img/01_login_Page.JPG" width="500">

> * spring security <br/>
> * 직원 테이블의 LEVEL 컬럼의 값으로 권한 구분 <br/>
> * Remember Me : Cookie 사용, 2주간 로그인이 유지되도록 구현 <br/>
>
~~~
// remember_me 체크박스 확인 : 자동로그인 쿠키 생성
String rememberYn = logDto.getIto1();
if(rememberYn.equals("rememberMe")) {
	String cookieBase = logDto.getEmp_id() + "," + empDto.getPw();
	String cookieValue = "";
	for(GrantedAuthority auth : authentication.getAuthorities()) {
		cookieBase += "," + auth.getAuthority();
	}
	try {
		cookieValue = AES256.AES_Encode(cookieBase, "milvussecurityrm");
	} catch (Exception e) {
		logger.error("쿠키값 생성 실패", e);
	}
	Cookie cookie = new Cookie("REMEMBER_ME", cookieValue);
	cookie.setMaxAge(2419200);
	rep.addCookie(cookie);
}
~~~
>
> * 비밀번호 : 암호화(sha512) 되어 DB에 저장, 통신<br/>

2.1 근태 등록 / 조회
-----
<img src="/img/02_inout_Page01.jpg" height="300" align="left">
<img src="/img/02_inout_Page03.jpg" height="300">

#### 코드 링크
`페이지 : ` [inout-log.jsp](/)
<br/>
`Controller : ` [InoutController.java](/)
<br/>
`Service : ` [InoutServiceImpl.java](/)
<br/>
`SQL : ` [InoutMapper.xml](/)

#### 기능 요약
> * 반응형 웹페이지 <br/>
> * 무한스크롤 : 7개씩 근태카드가 리로딩 <br/>
~~~
var iii = 1;
$(window).scroll(function() {	
	if($(window).scrollTop() >= ($(document).height()-window.innerHeight)-5) {
		console.log('bottom');
		var orip_start = $('#scrollpage_form').find('[name="p_start"]').val();

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
~~~

> * 날짜 하루단위 이동, 지정 검색 가능 <br/>

> <img src="/img/02_inout_Page02.jpg" width="600">

> * 사용자 편의를 위해 지각 구분 자동 / 외근, 직출 사유 입력 제공 <br/>
> * 일반 사용자 : 본인의 직출,외근, 야근 사유만 수정 가능 <br/>

> <img src="/img/02_inout_Page04.jpg" width="500"> <br/>
> <img src="/img/02_inout_Page05.jpg" width="500"> <br/>
> <img src="/img/02_inout_Page06.jpg" width="500"> <br/>

2.2 전직원 근태내역 조회, 수정, 엑셀 다운로드 (관리자)
-----
<img src="/img/02_inout_Admin_Page01.jpg" width="500"><br/>
<img src="/img/02_inout_Admin_Page02.jpg" width="500"><br/>
<img src="/img/02_inout_Admin_Page03.jpg" width="500"><br/>

#### 코드 링크
`페이지 : ` [adminInoutLog.jsp](/)
<br/>
`Controller : ` [AdminController.java](/)
<br/>
`Service : ` [AdminServiceImpl.java](/)
<br/>
`SQL : ` [InoutMapper.xml](/)


#### 기능 요약
> * 관리자 권한을 부여받은 사용자만 접근 가능
> * 모든 근태 내용 수정 가능
> * 리스트 페이징 처리
> * 검색 : 날짜 검색, 기간 검색, 키워드 검색
~~~
<select id="adm_inout_list" resultType="InoutMiDto">
select			LOG.RID, LOG.REAL_DAY, LOG.EMP_ID, EMP.SUB_NAME, EMP.NAME, LOG.MI_IN, LOG.JIK_COMMENT, LOG.MI_OUT, LOG.LATE_YN, LOG.JIK_YN, LOG.NIGHT_COMMENT
from 			INOUT_MI_LOG as LOG 
left join 
				EMPLOYEE_INFO as EMP
	on			LOG.EMP_ID = EMP.EMP_ID
where 			<include refid="search_inout"></include>
order by		LOG.REAL_DAY DESC
				,EMP.NAME ASC
limit			#{firstIndex, javaType=int}, #{recordCountPerPage, javaType=int}
</select>

<sql id="search_inout">
<choose>
	<when test="searchType == 'all'">
	(
			EMP.NAME 			like CONCAT('%', #{keyword }, '%') 
	or 		EMP.SUB_NAME	 	like CONCAT('%', #{keyword }, '%') 
	or 		LOG.EMP_ID 			like CONCAT('%', #{keyword }, '%')
	or 		LOG.JIK_COMMENT 	like CONCAT('%', #{keyword }, '%')
	or 		LOG.NIGHT_COMMENT 	like CONCAT('%', #{keyword }, '%')
	)
	</when>
	
	<when test="searchType == 'one_day'">
			date(LOG.REAL_DAY) = date(#{day1 })
	and		(
			EMP.NAME 			like CONCAT('%',#{keyword},'%') 
		or	EMP.SUB_NAME 		like CONCAT('%',#{keyword},'%') 
		or	LOG.EMP_ID 			like CONCAT('%',#{keyword},'%')
		or	LOG.JIK_COMMENT 	like CONCAT('%',#{keyword},'%')
		or	LOG.NIGHT_COMMENT 	like CONCAT('%',#{keyword},'%')
			)
	</when>
	
	<when test="searchType == 'two_day'">
			date(LOG.REAL_DAY) &gt;= date(#{day1 })
	and		date(LOG.REAL_DAY) &lt;= date(#{day2 })
	and		(
			EMP.NAME			like CONCAT('%',#{keyword},'%') 
		or	EMP.SUB_NAME		like CONCAT('%',#{keyword},'%') 
		or	LOG.EMP_ID			like CONCAT('%',#{keyword},'%')
		or	LOG.JIK_COMMENT		like CONCAT('%',#{keyword},'%')
		or	LOG.NIGHT_COMMENT	like CONCAT('%',#{keyword},'%')
			)
	</when>
	
	<otherwise></otherwise>
</choose>
</sql>
~~~
> * 수정 : validate 수행 후 submit 실행
> * 전체 내역 엑셀 다운로드

2.3 전직원 월별 근태 통계 조회 / 다운로드
----
<img src="/img/02_inout_Admin_Page04.jpg" width="500"><br/>
<img src="/img/02_inout_Admin_Page05.jpg" width="500"><br/>

#### 코드 링크
`페이지 : ` [admin-inout-month.jsp](/)
<br/>
`Controller : `[AdminController.java](/)
<br/>
`Service : ` [AdminServiceImpl.java](/)
<br/>
`SQL : ` [InoutMapper.xml](/)

#### 기능 요약
> * 관리자 권한을 부여받은 사용자만 접근 가능, 메뉴 노출
> * 리스트 페이징 처리
> * 월별 직원들의 근태내역 통계 
~~~
<!-- 관리자 : 월별 근태 통계 내역 조회 -->
<select id="all_month_inout" resultType="MyInoutDto">
select 		TTL.EMP_ID, EMP.NAME, EMP.SUB_NAME, TTL.month
			,IFNULL(TTL.INOUT_COUNT, 0) as INOUT_COUNT
			,IFNULL(TTL.LATE_COUNT, 0) as LATE_COUNT
			,IFNULL(TTL.WORK_COUNT, 0) as WORK_COUNT
from		(
			select		MI.EMP_ID , MI.MONTH, MI.INOUT_COUNT, MI.LATE_COUNT, WW.WORK_COUNT 
			from		(
						select		DATE_FORMAT(REAL_DAY, '%Y-%m') as MONTH, SUM(INOUT_STATE) as INOUT_COUNT, SUM(LATE_YN) as LATE_COUNT, EMP_ID
						from		INOUT_MI_LOG
						group by	MONTH, EMP_ID
						) as MI
			left join	(
						select		DATE_FORMAT(REAL_DAY,'%Y-%m') as MONTH, SUM(WORK_STATE) as WORK_COUNT, EMP_ID
						from		INOUT_WORK_LOG
						group by	MONTH, EMP_ID
						) as WW
				on		MI.MONTH = WW.MONTH and MI.EMP_ID = WW.EMP_ID
			) as TTL
left join
			EMPLOYEE_INFO as EMP
	on		TTL.EMP_ID = EMP.EMP_ID
where		(
				EMP.NAME		like CONCAT('%',#{keyword},'%') 
			or	EMP.SUB_NAME 	like CONCAT('%',#{keyword},'%') 
			or	TTL.EMP_ID		like CONCAT('%',#{keyword},'%')
			)
			and TTL.MONTH = #{month }
order by	TTL.MONTH DESC
			,EMP.NAME ASC
limit 		#{firstIndex, javaType=int}, #{recordCountPerPage, javaType=int}
</select>
~~~

> * 엑셀 다운로드


3.1 휴가 내역 조회 
----
<img src="/img/03_vacation_Page01.jpg">

#### 코드 링크
`페이지 : ` [my-vacation.jsp](/)
<br/>
`Controller : `[VacationController.java](/)
<br/>
`Service : ` [VacationServiceImpl.java](/)
<br/>
`SQL : ` [VacationMapper.xml](/)

#### 기능 요약
> * 본인이 등록한 휴가 내역 조회
> * 휴가 취소 : 취소 시 DB에서는 지워지지 않고 상태만 취소로 변경, 리스트에 노출안됨

3.2 휴가 등록
----
<img src="/img/03_vacation_Page03.jpg">

#### 코드 링크
`페이지 : ` [myvac-reg.jsp](/)
<br/>
`Controller : `[VacationController.java](/)
<br/>
`Service : ` [VacationServiceImpl.java](/)
<br/>
`SQL : ` [VacationMapper.xml](/)

#### 기능 요약
> * 휴가 종류 : DB에서 값을 받아와서 리스트 생성
<img src="/img/03_vacation_Page02.jpg">
> * 휴가 일수 : 기간을 선택하면 주말을 제외한 일수 자동계산

~~~
// ATTR1 = 연차 유무 / ATTR2 = 휴가 사용일
		if(codeDto.getAttr1().equals("1")) { // 연차 사용 휴가
			float cnt = dto.getVac_cnt();
			Calendar cal = Calendar.getInstance();
			int y = cal.get(Calendar.YEAR);
			String year = String.valueOf(y);
			float restcnt = dao.selectRestVac(dto.getEmp_id(), year);
			
			if(cnt > restcnt) {
				return "error1"; // 사용 가능한 연차 회수 초과
			} else {
				int annupresult = dao.updateVacAnn(dto.getEmp_id(), year, cnt); // 연차 사용횟수 업데이트
				if(annupresult == 1) {
					 int insresult = dao.insertVacation(dto);
					 if(insresult == 1) {
						 slack.sendToSlackInOut(empDto.getName(), dto.getVac_start() + " ~ " + dto.getVac_end() + " [ "+dto.getVac_comment() + " ]", codeDto.getCode_name() + "를 등록 하셨습니다.");
						 return "vac_insert_s";
					 } else {
						 return "vac_insert_f";
					 }
				} else {
					return "error2"; // 연차 사용회수 없데이트 실패
				}
			}
			
		} else { // 연차 사용x 휴가
			int result = dao.insertVacation(dto);
			if(result == 1) {
				slack.sendToSlackInOut(empDto.getName(), dto.getVac_start() + " ~ " + dto.getVac_end() + " [ "+dto.getVac_comment() + " ]", codeDto.getCode_name() + "를 등록 하셨습니다.");
				return "vac_insert_s";
			} else {
				 return "vac_insert_f";
			}
		}
~~~


> * 올해에 사용할 수 있는 연차 개수가 없으면 등록 안되도록 설계 <br/>
> * 반차는 하루만 등록/ 자동으로 0.5일 계산 <br/>
> * 연차/반차 사용시 연차일수 자동차감 <br/>
> * Validate 처리(날짜, 사유 입력, 연차일수 계산 등) <br/>


~~~
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
~~~

3.3 전직원 휴가 내역 조회 / 다운로드
-----
<img src="/img/03_vacation_Admin_Page01.jpg">

#### 코드 링크
`페이지 : ` [vac-log.jsp](/)
<br/>
`Controller : `[AdminController.java](/)
<br/>
`Service : ` [AdminServiceImpl.java](/)
<br/>
`SQL : ` [VacationMapper.xml](/)
<br/>

#### 기능 요약
> * 관리자 권한을 부여받은 사용자만 접근 가능
> * 페이징 처리
> * 날짜, 기간, 키워드 검색 구현
> * 전체 내역 엑셀 다운로드


3.4 전직원 연차 조회 / 등록
----
##### 조회
<img src="/img/03_vacation_Admin_Page02.jpg">

#### 코드 링크
`View : ` [vac-ann-log.jsp](/)
<br/>
`Controller : `[AdminController.java:570line](/)
<br/>
`Service : ` [AdminServiceImpl.java:260line](/)
<br/>
`SQL : ` [VacationMapper.xml:150line](/) `

#### 기능 요약
> * 관리자 권한을 부여받은 사용자만 접근 가능 / 메뉴 노출
> * 페이징 처리
> * 상단의 select 창에서 년도 이동

##### 등록
<img src="/img/03_vacation_Admin_Page04.jpg">

#### 코드 링크
`View : ` [vac-ann-reg.jsp](/)
<br/>
`Controller : `[AdminController.java:570line](/)
<br/>
`Service : ` [AdminServiceImpl.java:281line](/)
<br/>
`SQL : ` [VacationMapper.xml:150line](/)

#### 기능 요약
> * 관리자 권한을 부여받은 사용자만 접근 가능 / 메뉴 노출
> * 년도를 선택하면 해당 년도에 아직 연차등록을 하지 않은 직원들 목록만 불러옴(ajax)

<img src="/img/03_vacation_Admin_Page03.jpg">

~~~
@ResponseBody
@RequestMapping(value="/year_emplist.ajax", method=RequestMethod.POST)
public List<EmployeeDto> annregEmplist(Model model, @ModelAttribute("year_form")AnnualVacationDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
	String year = dto.getYear();
	List<EmployeeDto> empList = vacDao.admAnnregEmpList(year);
	return empList;
} // end annregEmplist()
~~~

~~~
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
~~~


4.1 직원 목록(연락처)
----
일반 직원 로그인   
<img src="/img/04_employeeList_Page01.jpg">

관리자 로그인  
<img src="/img/04_employeeList_Page03.jpg">

#### 코드 링크
관리자 로그인  
`View : ` [employeeList.jsp](/)
<br/>
`Controller : `[AdminController.java:100line](/)
<br/>
`Service : ` [AdminServiceImpl.java:300line](/)
<br/>

일반 직원 로그인  
`View : ` [list.jsp](/)
<br/>

`SQL : ` [EmployeeMapper.xml](/)

#### 기능 요약
> * 일반 직원 로그인 : 현재 재직 상태인 직원들 목록만 노출, 수정 불가
> * 관리자 로그인 : 퇴사자 포함 전체 등록된 직원 목록 노출, 수정-퇴사처리 가능
> * 이름 검색
<img src="/img/04_employeeList_Page02.jpg">






# 1.2 내부 프로젝트 수정 및 유지보수
