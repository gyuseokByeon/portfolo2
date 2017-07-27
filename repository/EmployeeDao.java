/**
 * 
 */
package com.template.repository;

import java.util.List;

import com.template.model.EmployeeDto;
import com.template.model.PageSearchDto;

/**
* <pre>
* com.template.repository 
*    |_ EmployeeDto.java
* </pre>
* @date : 2017. 6. 5. 오후 4:01:06
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
public interface EmployeeDao {

	/**
	* @Method Name : selectAllEmp
	* @Since    : 2017. 6. 14. 오후 4:15:42
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<EmployeeDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 전체 직원 리스트 조회_v2(페이지, 검색 추가)
	*/
	public abstract List<EmployeeDto> selectAllEmp(PageSearchDto dto);
	
	/**
	* @Method Name : selectCntAllEmp
	* @Since    : 2017. 6. 14. 오후 4:19:01
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 리스트 cnt 
	*/
	public abstract int selectCntAllEmp(PageSearchDto dto);
	
	
	/**
	* @Method Name : selectOneEmp
	* @Since    : 2017. 6. 5. 오후 4:02:45
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : EmployeeDto
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 한명 정보 조회
	*/
	public abstract EmployeeDto selectOneEmp(String emp_id);
	
	/**
	* @Method Name : updateMyInfo
	* @Since    : 2017. 6. 12. 오후 3:04:32
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 내 정보 수정
	*/
	public abstract int updateMyInfo(EmployeeDto dto);
	

	/**
	* @Method Name : insertEmp
	* @Since    : 2017. 6. 14. 오후 5:26:00
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자: 직원 등록
	*/
	public abstract int insertEmp(EmployeeDto dto);
	
	/**
	* @Method Name : ckEmpid
	* @Since    : 2017. 6. 14. 오후 5:26:26
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 : 직원 등록 - 아이디 중복 확인
	*/
	public abstract int ckEmpid(String emp_id);
	
	/**
	* @Method Name : updateEmp
	* @Since    : 2017. 6. 15. 오전 9:40:06
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 : 직원 정보 수정 (비밀번호, 이름, 영어이름, 이메일, 전화번호x2)
	*/
	public abstract int updateEmp(EmployeeDto dto);
	
	/**
	* @Method Name : deleteEmp
	* @Since    : 2017. 6. 15. 오전 10:40:33
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자: 직원정보 삭제(데이터 테이블에서 삭제) 
	*/
//	public abstract int deleteEmp(int rid);
	
	/**
	* @Method Name : updateEmployeeState
	* @Since    : 2017. 6. 21. 오전 9:39:24
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 퇴사처리
	*/
	public abstract int employeeResignation(EmployeeDto dto);
	
	/**
	* @Method Name : allListEmp
	* @Since    : 2017. 6. 15. 오전 10:54:06
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<EmployeeDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 : 전체 직원 내역 select (엑셀 다운로드) 
	*/
	public abstract List<EmployeeDto> allListEmp();
	
	
	
	
	
}
