/**
 * 
 */
package com.template.repository.Impl;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.template.model.AdminBoardDto;
import com.template.model.PageSearchDto;
import com.template.repository.AdminBoardDao;
/**
* <pre>
* com.template.repository.Impl 
*    |_ AdminBoardDaoImpl.java
* </pre>
* @date : 2017. 6. 16. 오후 3:58:58
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
@Component("AdminBoardDaoImpl")
public class AdminBoardDaoImpl implements AdminBoardDao {

	@Autowired
	private SqlSession sql;

	/* (non-Javadoc)
	 * @see com.template.repository.AdminBoardDao#empAbList(com.template.model.PageSearchDto)
	 */
	@Override
	public List<AdminBoardDto> empAbList(PageSearchDto dto) {
		return sql.selectList("emp_boardlist", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.AdminBoardDao#empAbListCnt(com.template.model.PageSearchDto)
	 */
	@Override
	public int empAbListCnt(PageSearchDto dto) {
		return sql.selectOne("emp_boardlist_cnt", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.AdminBoardDao#empAbInsert(com.template.model.AdminBoardDto)
	 */
	@Override
	public int empAbInsert(AdminBoardDto dto) {
		return sql.insert("emp_inst_ab", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.AdminBoardDao#admAbupdate(com.template.model.AdminBoardDto)
	 */
	@Override
	public int admAbupdate(AdminBoardDto dto) {
		return sql.update("adm_update_abstate", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.AdminBoardDao#empDeleteAb(int)
	 */
	@Override
	public int empDeleteAb(int rid) {
		return sql.delete("emp_del_ab", rid);
	}
	
	
} // end class AdminBoardDaoImpl
