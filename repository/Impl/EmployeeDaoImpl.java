/**
 * 
 */
package com.template.repository.Impl;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.template.model.EmployeeDto;
import com.template.model.PageSearchDto;
import com.template.repository.EmployeeDao;
/**
* <pre>
* com.template.repository.Impl 
*    |_ EmployeeDaoImpl.java
* </pre>
* @date : 2017. 6. 5. 오후 4:03:21
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
@Component("EmployeeDaoImpl")
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private SqlSession sql;
	
	/* (non-Javadoc)
	 * @see com.template.repository.EmployeeDao#selectAllEmp()
	 */
	@Override
	public List<EmployeeDto> selectAllEmp(PageSearchDto dto) {
		return sql.selectList("select_all_emp", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.EmployeeDao#selectCntAllEmp(com.template.model.PageSearchDto)
	 */
	@Override
	public int selectCntAllEmp(PageSearchDto dto) {
		return sql.selectOne("select_all_emp_cnt", dto);
	}

	/* (non-Javadoc)
	 * @see com.template.repository.EmployeeDao#selectOneEmp(java.lang.String)
	 */
	@Override
	public EmployeeDto selectOneEmp(String emp_id) {
		return sql.selectOne("select_one_emp", emp_id);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.EmployeeDao#updateMyInfo(com.template.model.EmployeeDto)
	 */
	@Override
	public int updateMyInfo(EmployeeDto dto) {
		return sql.update("emp_update_myinfo", dto);
	}

	/* (non-Javadoc)
	 * @see com.template.repository.EmployeeDao#insertEmp(com.template.repository.EmployeeDao)
	 */
	@Override
	public int insertEmp(EmployeeDto dto) {
		return sql.insert("insert_emp", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.EmployeeDao#ckEmpid(java.lang.String)
	 */
	@Override
	public int ckEmpid(String emp_id) {
		return sql.selectOne("ck_empid", emp_id);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.EmployeeDao#updateEmp(com.template.model.EmployeeDto)
	 */
	@Override
	public int updateEmp(EmployeeDto dto) {
		return sql.update("emp_update_admin", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.EmployeeDao#deleteEmp(int)
	 */
//	@Override
//	public int deleteEmp(int rid) {
//		return sql.delete("emp_delete_admin",rid);
//	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.EmployeeDao#updateEmployeeState(com.template.model.EmployeeDto)
	 */
	@Override
	public int employeeResignation(EmployeeDto dto) {
		return sql.update("emp_resignation_update", dto);
	}
	
	
	/* (non-Javadoc)
	 * @see com.template.repository.EmployeeDao#allListEmp()
	 */
	@Override
	public List<EmployeeDto> allListEmp() {
		return sql.selectList("all_emp_sheet");
	}
	
}
