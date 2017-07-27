/**
 * 
 */
package com.template.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
* <pre>
* com.template.service 
*    |_ VacationService.java
* </pre>
* @date : 2017. 6. 5. 오후 1:25:58
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */

import com.template.model.CodeDto;
import com.template.model.VacationDto;
public interface VacationService {

	/**
	* @Method Name : codeList
	* @Since    : 2017. 6. 5. 오후 1:31:11
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<CodeDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 코드 리스트 :3c
	*/
	public abstract List<CodeDto> codeList(String code_type);
	
	/**
	* @Method Name : vacationList
	* @Since    : 2017. 6. 5. 오후 2:54:48
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<VacationDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 등록 휴가 리스트
	*/
	public abstract List<VacationDto> vacationList(String emp_id);
	
	/**
	* @Method Name : insertVac
	* @Since    : 2017. 6. 12. 오후 1:58:36
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 등록
	*/
	public abstract String insertVac(VacationDto dto);
	
	/**
	* @Method Name : cancelVac
	* @Since    : 2017. 6. 12. 오후 1:59:44
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 취소
	*/
	public abstract int cancelVac(int rid);
	
}
