/**
 * 
 */
package com.template.repository;
/**
* <pre>
* com.template.repository 
*    |_ AdminBoardDao.java
* </pre>
* @date : 2017. 6. 16. 오후 3:53:59
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */

import java.util.List;

import com.template.model.AdminBoardDto;
import com.template.model.PageSearchDto;

public interface AdminBoardDao {
	/**
	* @Method Name : empAbList
	* @Since    : 2017. 6. 16. 오후 4:00:10
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<AdminBoardDao>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 일반직원-관리자 요청 게시판에서 목록 조회
	*/
	public abstract List<AdminBoardDto> empAbList(PageSearchDto dto);
	
	/**
	* @Method Name : empAbListCnt
	* @Since    : 2017. 6. 16. 오후 4:00:42
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 일반직원-관리자 요청 게시판에서 목록 cnt
	*/
	public abstract int empAbListCnt(PageSearchDto dto);
	
	/**
	* @Method Name : empAbInsert
	* @Since    : 2017. 6. 16. 오후 4:29:55
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 일반직원 : 관리자 요청글 등록 
	*/
	public abstract int empAbInsert(AdminBoardDto dto);
	
	/**
	* @Method Name : admAbupdate
	* @Since    : 2017. 6. 16. 오후 5:36:09
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 : 등록된 요청 처리
	*/
	public abstract int admAbupdate(AdminBoardDto dto);
	
	/**
	* @Method Name : empDeleteAb
	* @Since    : 2017. 6. 16. 오후 5:55:20
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 : 요청 삭제
	*/
	public abstract int empDeleteAb(int rid);
	

} // end class AdminBoardDao
