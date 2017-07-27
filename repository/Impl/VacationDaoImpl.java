/**
 * 
 */
package com.template.repository.Impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.template.common.CommonUtils;
import com.template.model.AnnualVacationDto;
import com.template.model.CodeDto;
import com.template.model.EmployeeDto;
import com.template.model.PageSearchDto;
import com.template.model.VacationDto;
import com.template.repository.VacationDao;
/**
* <pre>
* com.template.repository.Impl 
*    |_ VacationDaoImpl.java
* </pre>
* @date : 2017. 5. 31. 오후 1:37:20
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
@Component("VacationDaoImpl")
public class VacationDaoImpl implements VacationDao {
	@Autowired
	private SqlSession sql;
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#selectListVacation(com.template.model.VacationDto)
	 */
	@Override
	public List<VacationDto> selectListVacation(String emp_id) {
		return sql.selectList("selectListVacation", emp_id);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#getCodeList(java.lang.String)
	 */
	@Override
	public List<CodeDto> getCodeList(String code_type) {
		return sql.selectList("getCodeList", code_type);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#getCodeOne(java.lang.String)
	 */
	@Override
	public CodeDto getCodeOne(String code_id) {
		return sql.selectOne("getOneCode", code_id);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#insertVacation(com.template.model.VacationDto)
	 */
	@Override
	public int insertVacation(VacationDto dto) {
		return sql.insert("insertVacation", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#selectMyAnnuVac(java.lang.String)
	 */
	@Override
	public List<AnnualVacationDto> selectMyAnnuVac(String emp_id) {
		return sql.selectList("myAnnualVac", emp_id);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#selectOneVac(int)
	 */
	@Override
	public VacationDto selectOneVac(int rid) {
		return sql.selectOne("selectOneVac",rid);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#updateVcState(int)
	 */
	@Override
	public int updateVcState(int rid) {
		return sql.update("updateVacState", rid);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#selectRestVac(java.lang.String, java.lang.String)
	 */
	@Override
	public float selectRestVac(String emp_id, String year) {
		Map<String, String> map = new HashMap<>();
		map.put("emp_id", emp_id);
		map.put("year", year);
		
		//int result = sql.selectOne("selectRestVac", map);
		if(CommonUtils.empty(sql.selectOne("selectRestVac", map))) {
			return 0;
		} else {
			return sql.selectOne("selectRestVac", map);
		} 
	} // end selectRestVac
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#updateVacAnn(java.lang.String, java.lang.String, int)
	 */
	@Override
	public int updateVacAnn(String emp_id, String year, float vac_cnt) {
		Map<String, Object> map = new HashMap<>();
		map.put("emp_id", emp_id);
		map.put("year", year);
		map.put("vac_cnt", vac_cnt);
		
		return sql.update("updateAnnVac", map);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#admVacationListCnt(com.template.model.PageSearchDto)
	 */
	@Override
	public int admVacationListCnt(PageSearchDto dto) {
		return sql.selectOne("emp_vacation_list_cnt", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#admVacationList(com.template.model.PageSearchDto)
	 */
	@Override
	public List<VacationDto> admVacationList(PageSearchDto dto) {
		return sql.selectList("emp_vacation_list", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#downVacationList()
	 */
	@Override
	public List<VacationDto> downVacationList() {
		return sql.selectList("vacationListExel");
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#admAnnVacList(com.template.model.PageSearchDto)
	 */
	@Override
	public List<AnnualVacationDto> admAnnVacList(PageSearchDto dto) {
		return sql.selectList("adm_annVac", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#admAnnVacCnt(com.template.model.PageSearchDto)
	 */
	@Override
	public int admAnnVacCnt(PageSearchDto dto) {
		return sql.selectOne("adm_annVac_cnt", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#admAnnregEmpList(java.lang.String)
	 */
	@Override
	public List<EmployeeDto> admAnnregEmpList(String year) {
		return sql.selectList("annreg_empList", year);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#ckinsertann(com.template.model.AnnualVacationDto)
	 */
	@Override
	public int ckinsertann(AnnualVacationDto dto) {
		return sql.selectOne("ck_annreg", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#insertAnnVac(com.template.model.AnnualVacationDto)
	 */
	@Override
	public int insertAnnVac(AnnualVacationDto dto) {
		return sql.insert("ann_insert", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#selectOneAnnvac(int)
	 */
	@Override
	public AnnualVacationDto selectOneAnnvac(int rid) {
		return sql.selectOne("select_annvac_one", rid);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.VacationDao#updateAnnVac(com.template.model.AnnualVacationDto)
	 */
	@Override
	public int updateAnnVac(AnnualVacationDto dto) {
		return sql.update("update_annvac", dto);
	}

}
