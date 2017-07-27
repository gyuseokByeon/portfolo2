package com.template.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.template.model.InoutMiDto;
import com.template.model.InoutWorkDto;


/**
* <pre>
* com.template.service 
*    |_ InoutService.java
* </pre>
* @date : 2017. 5. 29. 오후 7:20:38
* @version : 
* @author : OMNILAB-A
*/

public interface InoutService {

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
	* @Method Description : 근태 입력(출근,퇴근,직출)
	*/
	public abstract String insertInout(InoutMiDto dto, String btn_ty);
	
	
	/**
	* @Method Name : workinsertInout
	* @Since    : 2017. 5. 31. 오전 11:40:26
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String : 
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 외근,복귀 입력
	*/
	public abstract String workinsertInout(InoutWorkDto dto, String btn_ty);

	/**
	* @Method Name : selectInout
	* @Since    : 2017. 5. 31. 오전 9:44:27
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<InoutMiDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : real_day 기준 그날의 직원 근태내역 조회
	*/
	public abstract List<InoutMiDto> selectInout(InoutMiDto dto);
	
	/**
	* @Method Name : selectListWork
	* @Since    : 2017. 5. 31. 오후 2:28:23
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<InoutWorkDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 외근전체리스트
	*/
	public abstract List<InoutWorkDto> selectListWork(String real_day);
	

} // end inout service
