package com.template.repository.Impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.template.model.InoutMiDto;
import com.template.model.PageSearchDto;
import com.template.model.InoutWorkDto;
import com.template.model.MyInoutDto;
import com.template.repository.InoutDao;
/**
* <pre>
* com.template.repository.Impl 
*    |_ InoutDaoImpl.java
* </pre>
* @date : 2017. 5. 29. 오후 7:18:41
* @version : 
* @author : OMNILAB-A
*/
@Component("InoutDaoImpl")
public class InoutDaoImpl implements InoutDao {
	
	@Autowired
	private SqlSession sql;
	
	@Override
	public List<InoutMiDto> selectListInout(InoutMiDto dto) {
		return sql.selectList("inoutlist_v2", dto);
	} // end selectInout : real_day(기준일)로 그 날 근태 내역 조회
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#selectCntInout(com.template.model.InoutMiDto)
	 */
	@Override
	public int selectCntInout(InoutMiDto dto) {
		return sql.selectOne("inout_cnt", dto);
	} // end selectCntInout : emp_id, real_day : 데이터 있는지 검사(cnt)
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#selectInout(com.template.model.InoutMiDto)
	 */
	@Override
	public InoutMiDto selectInout(InoutMiDto dto) {
		return sql.selectOne("inout_select", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#insertMiin(com.template.model.InoutMiDto)
	 */
	@Override
	public int insertMiin(InoutMiDto dto) {
		return sql.insert("insert_miin", dto);
	} // end insertMiin : 출근 입력
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#updateMiout(com.template.model.InoutMiDto)
	 */
	@Override
	public int updateMiout(InoutMiDto dto) {
		return sql.update("update_miout", dto);
	} // end updateMiout : 퇴근 업데이트

	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#cntWorkInout(com.template.model.InoutWorkDto)
	 */
	@Override
	public int cntWorkInout(InoutWorkDto dto) {
		return sql.selectOne("cnt_workinout", dto);
	}

	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#selectListWorkinout(com.template.model.InoutWorkDto)
	 */
	@Override
	public List<InoutWorkDto> selectListWorkinout(InoutWorkDto dto) {
		return sql.selectList("selectlist_workinout", dto);
	}

	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#insertWorkin(com.template.model.InoutWorkDto)
	 */
	@Override
	public int insertWorkin(InoutWorkDto dto) {
		return sql.insert("insert_workout", dto);
	}

	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#updateWorkout(com.template.model.InoutWorkDto)
	 */
	@Override
	public int updateWorkout(InoutWorkDto dto) {
		return sql.update("update_workin", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#worklist(java.lang.String)
	 */
	@Override
	public List<InoutWorkDto> worklist(String real_day) {
		return sql.selectList("workList", real_day);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#jikcommentUpdate(com.template.model.InoutMiDto)
	 */
	@Override
	public int jikcommentUpdate(InoutMiDto dto) {
		return sql.update("jikcommupdate", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#nightcommentUpdate(com.template.model.InoutMiDto)
	 */
	@Override
	public int nightcommentUpdate(InoutMiDto dto) {
		return sql.update("nightcommupdate", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#workcommentUpdate(com.template.model.InoutWorkDto)
	 */
	@Override
	public int workcommentUpdate(InoutWorkDto dto) {
		return sql.update("workoutcommupdate", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#monthMyInout(java.lang.String)
	 */
	@Override
	public List<MyInoutDto> monthMyInout(String emp_id) {
		return sql.selectList("my-inout-info", emp_id);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#adm_cntInoutList(com.template.model.InoutSearchDto)
	 */
	@Override
	public int adm_cntInoutList(PageSearchDto dto) {
		return sql.selectOne("adm_inout_cnt", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#adm_inoutList(com.template.model.InoutSearchDto)
	 */
	@Override
	public List<InoutMiDto> adm_inoutList(PageSearchDto dto) {
		return sql.selectList("adm_inout_list", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#all_inout_list()
	 */
	@Override
	public List<InoutMiDto> all_inout_list() {
		return sql.selectList("all_inout_sheeet");
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#month_inout()
	 */
	@Override
	public List<MyInoutDto> month_inout(PageSearchDto dto) {
		return sql.selectList("all_month_inout", dto);
	}

	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#month_inout_cnt(com.template.model.PageSearchDto)
	 */
	@Override
	public int month_inout_cnt(PageSearchDto dto) {
		return sql.selectOne("all_month_inout_cnt", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#allEmployeeMonthInoutSheetDao(java.lang.String)
	 */
	@Override
	public List<MyInoutDto> allEmployeeMonthInoutSheetDao(String month) {
		return sql.selectList("monthEmployeeInOutSheet", month);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#adm_update_inout(com.template.model.InoutMiDto)
	 */
	@Override
	public int adm_update_inout(InoutMiDto dto) {
		return sql.update("adm-inout-update", dto);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#noneRegMiInout(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Integer> noneRegMiInout(String real_day, String emp_id) {
		Map<String, String> map = new HashMap<>();
		map.put("real_day", real_day);
		map.put("emp_id", emp_id);
		return sql.selectList("selectNoneRegMiInout", map);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#noneRegWorkInout(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Integer> noneRegWorkInout(String real_day, String emp_id) {
		Map<String, String> map = new HashMap<>();
		map.put("real_day", real_day);
		map.put("emp_id", emp_id);
		return sql.selectList("selectNoneRegWorkInout", map);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#selectRidMiInout(int)
	 */
	@Override
	public InoutMiDto selectRidMiInout(int rid) {
		return sql.selectOne("selectRidInout", rid);
	}
	
	/* (non-Javadoc)
	 * @see com.template.repository.InoutDao#selectRidWorkInout(int)
	 */
	@Override
	public InoutWorkDto selectRidWorkInout(int rid) {
		return sql.selectOne("selectRidWorkInout", rid);
	}
	
	
	
} // end inout dao imple
