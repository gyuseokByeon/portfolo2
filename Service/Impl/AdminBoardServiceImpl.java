/**
 * 
 */
package com.template.service.Impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.template.common.CommonUtils;
import com.template.model.AdminBoardDto;
import com.template.model.PageSearchDto;
import com.template.repository.AdminBoardDao;
import com.template.service.AdminBoardService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
/**
* <pre>
* com.template.service.Impl 
*    |_ AdminBoardServiceImpl.java
* </pre>
* @date : 2017. 6. 16. 오후 4:06:14
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
@Service("AdminBoardServiceImpl")
public class AdminBoardServiceImpl implements AdminBoardService {
	
	@Autowired
	@Qualifier("AdminBoardDaoImpl")
	private AdminBoardDao abDao;
	
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminBoardService#emp_ablist(com.template.model.PageSearchDto, int)
	 */
	@Override
	public Map<String, Object> emp_ablist(PageSearchDto dto, int page) {
		PaginationInfo pageinfo = CommonUtils.makePage(page, 15, 10);
		
		dto.setFirstIndex(pageinfo.getFirstRecordIndex());
		dto.setRecordCountPerPage(pageinfo.getRecordCountPerPage());
		
		pageinfo.setTotalRecordCount(abDao.empAbListCnt(dto));
		List<AdminBoardDto> abList = abDao.empAbList(dto);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", abList);
		map.put("page", pageinfo);
		
		return map;
	}
} // end class AdminBoardServiceImpl
