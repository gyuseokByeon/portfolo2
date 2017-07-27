/**
 * 
 */
package com.template.repository;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
* <pre>
* com.template.repository 
*    |_ VacationDao.java
* </pre>
* @date : 2017. 5. 31. 오후 1:36:47
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */

import com.template.model.AnnualVacationDto;
import com.template.model.CodeDto;
import com.template.model.EmployeeDto;
import com.template.model.PageSearchDto;
import com.template.model.VacationDto;
public interface VacationDao {
	/**
	* @Method Name : selectListVacation
	* @Since    : 2017. 5. 31. 오후 2:05:32
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<VacationDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가리스트 select (로그인한 사용자 기준 + 휴가 상태가 1인것만 표시) <br/>
	* select vac.*, vac.VAC_ID, c.CODE_ID, c.CODE_NAME, c.ATTR1, c.ATTR2, c.ATTR3
	from VACATION as vac LEFT JOIN CODE_LIST as c
	on vac.VAC_ID = c.CODE_ID
	where vac.emp_id = #{emp_id	} and STAET = 1
	*/
	public abstract List<VacationDto> selectListVacation(String emp_id);
	
	/**
	* @Method Name : getCodeList
	* @Since    : 2017. 5. 31. 오후 3:28:17
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<CodeDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 코드 타입별 리스트
	*/
	public abstract List<CodeDto> getCodeList(String code_type);
	
	/**
	* @Method Name : getCodeOne
	* @Since    : 2017. 6. 12. 오후 1:32:41
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : CodeDto
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 
	*/
	public abstract CodeDto getCodeOne(String code_id);
	
	/**
	* @Method Name : insertVacation
	* @Since    : 2017. 5. 31. 오후 5:23:10
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 등록
	*/
	public abstract int insertVacation(VacationDto dto);
	
	/**
	* @Method Name : selectMyAnnuVac
	* @Since    : 2017. 6. 5. 오후 3:51:31
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<AnnualVacationDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 개인 연차 리스트
	*/
	public abstract List<AnnualVacationDto> selectMyAnnuVac(String emp_id);

	/**
	* @Method Name : selectOneVac
	* @Since    : 2017. 6. 5. 오후 5:45:08
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : VacationDto
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 
	*/
	public abstract VacationDto selectOneVac(int rid);
	
	/**
	* @Method Name : updateVcState
	* @Since    : 2017. 6. 8. 오후 4:47:21
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 등록 상태변경 (취소만)
	*/
	public abstract int updateVcState(int rid);
	
	/**
	* @Method Name : selectRestVac
	* @Since    : 2017. 6. 9. 오후 5:57:31
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 올해 남은 연차값 데이터에서 가져옴
	*/
	public abstract float selectRestVac(String emp_id, String year);
	
	
	/**
	* @Method Name : updateVacAnn
	* @Since    : 2017. 6. 9. 오후 6:33:59
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 연차 사용회수 업데이트 
	*/
	public abstract int updateVacAnn(String emp_id, String year, float vac_cnt);
	
	/**
	* @Method Name : admVacationListCnt
	* @Since    : 2017. 6. 13. 오후 5:35:54
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자페이지: 전체 휴가 목록 카운트
	*/
	public abstract int admVacationListCnt(PageSearchDto dto);
	
	/**
	* @Method Name : admVacationList
	* @Since    : 2017. 6. 13. 오후 5:36:33
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<VacationDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자페이지: 전체 휴가 목록
	*/
	public abstract List<VacationDto> admVacationList(PageSearchDto dto);
	
	
	/**
	* @Method Name : downVacationList
	* @Since    : 2017. 6. 13. 오후 6:25:48
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<VacationDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자페이지: 휴가 목록 다운로드 
	*/
	public abstract List<VacationDto> downVacationList();
	
	/**
	* @Method Name : admAnnVacList
	* @Since    : 2017. 6. 14. 오전 10:05:28
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<AnnualVacationDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 페이지: 연차 내역 조회 (년도별/페이지)
	*/
	public abstract List<AnnualVacationDto> admAnnVacList(PageSearchDto dto);
	
	/**
	* @Method Name : admAnnVacCnt
	* @Since    : 2017. 6. 14. 오전 10:12:25
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 페이지: 연차 내역 조회 CNT
	*/
	public abstract int admAnnVacCnt(PageSearchDto dto);
	
	/**
	* @Method Name : admAnnregEmpList
	* @Since    : 2017. 6. 14. 오전 11:29:04
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<EmployeeDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자: 연차등록 페이지 : 해당 년도에 연차 등록 내역이 없는 회원 리스트 
	*/
	public abstract List<EmployeeDto> admAnnregEmpList(String year);
	
	/**
	* @Method Name : ckinsertann
	* @Since    : 2017. 6. 14. 오후 2:06:34
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 연차 등록_동일한 조건의 데이터 있는지 확인
	*/
	public abstract int ckinsertann(AnnualVacationDto dto);
	
	/**
	* @Method Name : insertAnnVac
	* @Since    : 2017. 6. 14. 오후 2:07:16
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 연차 등록
	*/
	public abstract int insertAnnVac(AnnualVacationDto dto);
	
	/**
	* @Method Name : selectOneAnnvac
	* @Since    : 2017. 6. 14. 오후 2:44:13
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : AnnualVacationDto
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : (RID) 연차 내역
	*/
	public abstract AnnualVacationDto selectOneAnnvac(int rid);
	
	/**
	* @Method Name : updateAnnVac
	* @Since    : 2017. 6. 14. 오후 3:28:38
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 연차 수정 count 만 수정 가능
	*/
	public abstract int updateAnnVac(AnnualVacationDto dto);
	

}
