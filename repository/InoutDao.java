package com.template.repository;
import java.util.List;
import com.template.model.InoutMiDto;
import com.template.model.PageSearchDto;
import com.template.model.InoutWorkDto;
import com.template.model.MyInoutDto;

/**
* <pre>
* com.template.repository 
*    |_ InoutDao.java
* </pre>
* @date : 2017. 5. 29. 오후 7:18:18
* @version : 
* @author : OMNILAB-A_zd
*/

public interface InoutDao {
	/**
	* @Method Name : selectListInout
	* @Since    : 2017. 5. 31. 오전 10:49:43
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<InoutMiDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.2
	* @Method Description : inoutlist_select, 하루단위 근태 내역 리스트 조회 <br/>
	* 
	*/
	public abstract List<InoutMiDto> selectListInout(InoutMiDto dto); 
	
	/**
	* @Method Name : selectCntInout
	* @Since    : 2017. 5. 31. 오전 10:50:07
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : inout_cnt, 데이터 있는지 검사(cnt)<br/>
	* select count(RID) from INOUT_MI_LOG
	where EMP_ID=#{emp_id } and REAL_DAY=#{real_day}
	*/
	public abstract int selectCntInout(InoutMiDto dto);
	
	/**
	* @Method Name : selectInout
	* @Since    : 2017. 5. 31. 오전 10:50:34
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : InoutMiDto
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : inout_select<br/>
	* select * from INOUT_MI_LOG 
	where EMP_ID=#{emp_id } and REAL_DAY=#{real_day}
	*/
	public abstract InoutMiDto selectInout(InoutMiDto dto);
	
	/**
	* @Method Name : insertMiin
	* @Since    : 2017. 5. 31. 오전 10:50:41
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : insert_miin, 출근 입력<br/>
	* insert into INOUT_MI_LOG
	(EMP_ID, REAL_DAY, MI_IN, LATE_YN, JIK_YN, JIK_COMMENT)
	value (#{emp_id }, #{real_day }, #{mi_in }, #{late_yn}, #{jik_yn }, #{jik_comment})
	*/
	public abstract int insertMiin(InoutMiDto dto);
	
	/**
	* @Method Name : updateMiout
	* @Since    : 2017. 5. 31. 오전 10:51:16
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : update_miout, 퇴근 업데이트
	* update INOUT_MI_LOG set MI_OUT = #{mi_out }, NIGHT_COMMENT = #{night_comment }
	where EMP_ID=#{emp_id } and REAL_DAY=#{real_day }
	*/
	public abstract int updateMiout(InoutMiDto dto);
	
	/**
	* @Method Name : cntWorkInout
	* @Since    : 2017. 5. 31. 오전 10:56:08
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : cnt_workinout : 외근내역회수<br/>
	* select count(RID) from INOUT_WORK_LOG
	where EMP_ID=#{emp_id } and REAL_DAY=#{real_day} and WORK_STATE=#{work_state}
	*/
	public abstract int cntWorkInout(InoutWorkDto dto);
	
	/**
	* @Method Name : selectListWorkinout
	* @Since    : 2017. 5. 31. 오전 10:56:36
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<InoutWorkDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : selectlist_workinout : 외근내역<br/>
	* select * from INOUT_WORK_LOG
	where REAL_DAY = #{real_day } and emp_id=#{emp_id }
	*/
	public abstract List<InoutWorkDto> selectListWorkinout(InoutWorkDto dto);
	
	/**
	* @Method Name : insertWorkin
	* @Since    : 2017. 5. 31. 오전 10:57:04
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : insert_workout : 외근 입력
	* insert into INOUT_WORK_LOG 
	(EMP_ID, REAL_DAY, WORK_OUT, WORK_COMMENT) 
	value (#{emp_id}, #{real_day}, #{work_out }, #{work_comment})
	*/
	public abstract int insertWorkin(InoutWorkDto dto);
	
	/**
	* @Method Name : updateWorkout
	* @Since    : 2017. 5. 31. 오전 10:57:22
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : update_workin : 복귀 입력
	* update INOUT_WORK_LOG
	set WORK_IN = #{work_in}
	where RID = #{rid}
	*/
	public abstract int updateWorkout(InoutWorkDto dto);
	
	/**
	* @Method Name : worklist
	* @Since    : 2017. 5. 31. 오후 2:41:39
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<InoutWorkDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 외근 전체 리스트 조회(하루단위)
	*/
	public abstract List<InoutWorkDto> worklist(String real_day);
	
	/**
	* @Method Name : jikcommentUpdate
	* @Since    : 2017. 6. 16. 오후 2:04:10
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직출 사유 수정
	*/
	public abstract int jikcommentUpdate(InoutMiDto dto);
	
	/**
	* @Method Name : nightcommentUpdate
	* @Since    : 2017. 6. 16. 오후 2:04:12
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 야근 사유 수정 
	*/
	public abstract int nightcommentUpdate(InoutMiDto dto);
	
	
	
	/**
	* @Method Name : noneRegMiInout
	* @Since    : 2017. 6. 26. 오전 11:17:14
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<Integer>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 퇴근 등록하지 않은 과거 출퇴기록 조회
	*/
	public abstract List<Integer> noneRegMiInout(String real_day, String emp_id);
	
	
	
	/**
	* @Method Name : noneRegWorkInout
	* @Since    : 2017. 6. 26. 오전 11:18:06
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<Integer>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 복귀 등록하지 않은 과거 출퇴기록 조회 
	*/
	public abstract List<Integer> noneRegWorkInout(String real_day, String emp_id);
	
	/**
	* @Method Name : selectRidMiInout
	* @Since    : 2017. 6. 26. 오전 11:28:22
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : InoutMiDto
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : MI_INOUT RID 로 조회
	*/
	public abstract InoutMiDto selectRidMiInout(int rid);
	
	/**
	* @Method Name : selectRidWorkInout
	* @Since    : 2017. 6. 26. 오전 11:43:02
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : InoutWorkDto
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : WORK_INOUT RID 로 조회
	*/
	public abstract InoutWorkDto selectRidWorkInout(int rid);	
	
	
	/**
	* @Method Name : workcommentUpdate
	* @Since    : 2017. 6. 16. 오후 2:04:14
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 
	*/
	public abstract int workcommentUpdate(InoutWorkDto dto);
	
	/**
	* @Method Name : monthMyInout
	* @Since    : 2017. 6. 5. 오후 3:30:57
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<MyInoutDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 월별 근태내역 조회
	*/
	public abstract List<MyInoutDto> monthMyInout(String emp_id);
	
	/**
	* @Method Name : adm_inoutList
	* @Since    : 2017. 6. 12. 오후 7:46:04
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<InoutMiDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자페이지:근태내역 리스트
	*/
	public abstract List<InoutMiDto> adm_inoutList(PageSearchDto dto); 
	
	/**
	* @Method Name : adm_cntInoutList
	* @Since    : 2017. 6. 12. 오후 7:46:07
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자페이지:근태내역 리스트 개수
	*/
	public abstract int adm_cntInoutList(PageSearchDto dto);
	
	
	/**
	* @Method Name : all_inout_list
	* @Since    : 2017. 6. 13. 오전 11:18:05
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<InoutMiDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자페이지_전체 근태내역 조회(엑셀 다운로드 지원)
	*/
	public abstract List<InoutMiDto> all_inout_list();
	
	/**
	* @Method Name : month_inout
	* @Since    : 2017. 6. 13. 오후 1:31:45
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<MyInoutDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자페이지_전체 직원 월별 근태 통계
	*/
	public abstract List<MyInoutDto> month_inout(PageSearchDto dto);
	
	/**
	* @Method Name : month_inout_cnt
	* @Since    : 2017. 6. 13. 오후 3:04:13
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 월별 근태 통계 카운트
	*/
	public abstract int month_inout_cnt(PageSearchDto dto);
	
	/**
	* @Method Name : allEmployeeMonthInoutSheetDao
	* @Since    : 2017. 6. 22. 오후 1:06:09
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<MyInoutDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 전체 직원 월별 근태 통계 엑셀 다운로드
	*/
	public abstract List<MyInoutDto> allEmployeeMonthInoutSheetDao(String month);
	
	
	/**
	* @Method Name : adm_update_inout
	* @Since    : 2017. 6. 16. 오후 1:05:15
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 : 직원 출퇴 내역 수정
	*/
	public abstract int adm_update_inout(InoutMiDto dto);
	
	
} // end inout dao
