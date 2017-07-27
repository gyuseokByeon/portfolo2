package com.template.service;
import java.util.Map;

import com.template.model.AnnualVacationDto;
import com.template.model.EmployeeDto;
import com.template.model.PageSearchDto;


/**
* <pre>
* com.template.service 
*    |_ InoutService.java
* </pre>
* @date : 2017. 5. 29. 오후 7:20:38
* @version : 
* @author : OMNILAB-A
*/

public interface AdminService {

	/**
	* @Method Name : insertInout
	* @Since    : 2017. 5. 31. 오전 9:44:53
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String : already_in / miin_success / miin_fail / miout_success / miout_fail / alreay_out / no_miin / not_in
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 전체 근태내역 조회
	*/
	public abstract Map<String, Object> adm_inoutList(PageSearchDto dto, int page);
	
	/**
	* @Method Name : all_inout_sheet
	* @Since    : 2017. 6. 13. 오전 11:28:08
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 전체 근태내역 엑셀 다운로드 
	*/
	public abstract Map<String, Object> all_inout_sheet();
	
	/**
	* @Method Name : adm_monthInout
	* @Since    : 2017. 6. 13. 오후 2:55:35
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 월별 근태 통계 
	*/
	public abstract Map<String, Object> adm_monthInout(PageSearchDto dto, int page);
	
	
	/**
	* @Method Name : allEmployeeMonthInoutSheet
	* @Since    : 2017. 6. 22. 오후 1:04:02
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 전직원 월별 근태 통계 엑셀 다운로드
	*/
	public abstract Map<String, Object> allEmployeeMonthInoutSheet(String month);
	
	/**
	* @Method Name : adm_vacList
	* @Since    : 2017. 6. 13. 오후 5:38:18
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 등록 내역 조회 리스트
	*/
	public abstract Map<String, Object> adm_vacList(PageSearchDto dto, int page);
	
	
	/**
	* @Method Name : all_vacation_sheet
	* @Since    : 2017. 6. 13. 오후 6:27:21
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 등록 내역 엑셀 다운로드
	*/
	public abstract Map<String, Object> all_vacation_sheet();
	
	/**
	* @Method Name : annVacList
	* @Since    : 2017. 6. 14. 오전 10:18:38
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 년도별 직원 연차 등록 내역 조회
	*/
	public abstract Map<String, Object> annVacList(PageSearchDto dto, int page);
	
	/**
	* @Method Name : insertAnnVac
	* @Since    : 2017. 6. 14. 오후 2:10:48
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 연차 등록 실행
	*/
	public abstract String insertAnnVac(AnnualVacationDto dto);
	
	/**
	* @Method Name : allEmpList
	* @Since    : 2017. 6. 14. 오후 4:21:28
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 전체 직원 리스트 
	*/
	public abstract Map<String, Object> allEmpList(PageSearchDto dto, int page);
	
	/**
	* @Method Name : allEmpList
	* @Since    : 2017. 6. 15. 오전 10:57:33
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 전체 직원 리스트 엑셀 다운로드
	*/
	public abstract Map<String, Object> allEmpList();
	
	
	/**
	* @Method Name : insertNewEmployee
	* @Since    : 2017. 6. 21. 오후 4:40:41
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 신규 직원 등록
	*/
	public abstract int insertNewEmployee(EmployeeDto dto);
	
	

} // end inout service
