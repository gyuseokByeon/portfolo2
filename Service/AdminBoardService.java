/**
 * 
 */
package com.template.service;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.template.model.PageSearchDto;
/**
* <pre>
* com.template.service 
*    |_ AdminBoardService.java
* </pre>
* @date : 2017. 6. 16. 오후 4:03:58
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
public interface AdminBoardService {
	/**
	* @Method Name : emp_ablist
	* @Since    : 2017. 6. 16. 오후 4:05:30
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원-관리자요청게시판 리스트(페이징)
	*/
	public abstract Map<String, Object> emp_ablist(PageSearchDto dto, int page);

	
	
	
} // end class AdminBoardService
