/**
 * 
 */
package com.template.service.Impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.template.common.CommonUtils;
import com.template.model.AnnualVacationDto;
import com.template.model.EmployeeDto;
import com.template.model.InoutMiDto;
import com.template.model.MyInoutDto;
import com.template.model.PageSearchDto;
import com.template.model.VacationDto;
import com.template.repository.EmployeeDao;
import com.template.repository.InoutDao;
import com.template.repository.VacationDao;
import com.template.service.AdminService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
/**
* <pre>
* com.template.service.Impl 
*    |_ AdminServiceImpl.java
* </pre>
* @date : 2017. 6. 12. 오후 7:51:34
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
@Service("AdminServiceImpl")
public class AdminServiceImpl implements AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	@Qualifier("InoutDaoImpl")
	private InoutDao inoutDao;
	
	@Autowired
	@Qualifier("VacationDaoImpl")
	private VacationDao vacDao;
	
	@Autowired
	@Qualifier("EmployeeDaoImpl")
	private EmployeeDao empDao;
	
	
	public PaginationInfo makePage(int page, int recordCountPerPage, int pageSize) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(page);
		paginationInfo.setRecordCountPerPage(recordCountPerPage);
		paginationInfo.setPageSize(pageSize);
		return paginationInfo;
	} // end makePage
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#adm_inoutList(com.template.model.InoutSearchDto, int)
	 */
	@Override
	public Map<String, Object> adm_inoutList(PageSearchDto dto, int page) {
		PaginationInfo paginationInfo = makePage(page, 10, 10);
		
		dto.setFirstIndex(paginationInfo.getFirstRecordIndex());
		dto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(inoutDao.adm_cntInoutList(dto));
		List<InoutMiDto> inoutList = inoutDao.adm_inoutList(dto);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", inoutList);
		map.put("page", paginationInfo);
		
		return map;
	} // end adm_inoutList
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#all_inout_sheet()
	 */
	@Override
	public Map<String, Object> all_inout_sheet() {
		Map<String, Object> map = new HashMap<>();
		
		List<String> label = new ArrayList<String>();
		label.add("일자");
		label.add("직원 ID");
		label.add("이름");
		label.add("영어이름");
		label.add("출근");
		label.add("지각여부");
		label.add("직출여부");
		label.add("직출사유");
		label.add("퇴근");
		label.add("야근사유");
		label.add("재직상태");
		map.put("label", label);
		
		List<InoutMiDto> list = inoutDao.all_inout_list();
		List<List<String>> body = new ArrayList<>();
		
		for(InoutMiDto d : list) {
			List<String> li = new ArrayList<>();
			li.add(d.getReal_day());
			li.add(d.getEmp_id());
			li.add(d.getName());
			li.add(d.getSub_name());
			li.add(d.getMi_in());
			li.add(String.valueOf(d.getLate_yn()));
			li.add(String.valueOf(d.getJik_yn()));
			li.add(d.getJik_comment());
			li.add(d.getMi_out());
			li.add(d.getNight_comment());
			if(d.getEmp_state() == 1) {
				li.add("재직중");
			} else if(d.getEmp_state() == 0) {
				li.add("퇴사");
			} else {
				li.add(String.valueOf(d.getEmp_state()));
			}
			body.add(li);
		}
		map.put("body", body);
		
		return map;
	} // all_inout_sheet
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#adm_monthInout(com.template.model.PageSearchDto, int)
	 */
	@Override
	public Map<String, Object> adm_monthInout(PageSearchDto dto, int page) {
		PaginationInfo paginationInfo = makePage(page, 10, 10);

		dto.setFirstIndex(paginationInfo.getFirstRecordIndex());
		dto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(inoutDao.month_inout_cnt(dto));
		List<MyInoutDto> monthList = new ArrayList<>(); 
		monthList = inoutDao.month_inout(dto);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", monthList);
		map.put("page", paginationInfo);
		
		return map;
	} // end adm_monthInout
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#allEmployeeMonthInoutSheet(java.lang.String)
	 */
	@Override
	public Map<String, Object> allEmployeeMonthInoutSheet(String month) {
		// 월별 근태내역 통계 엑셀 다운로드
		Map<String, Object> map = new HashMap<>();
		
		List<String> label = new ArrayList<>();
		label.add("YY/MM");
		label.add("직원 ID");
		label.add("이름");
		label.add("출근 일수");
		label.add("지각 일수");
		label.add("외근 일수");
		map.put("label", label);
		
		List<List<String>> body = new ArrayList<>();
		List<MyInoutDto> monthInoutList = inoutDao.allEmployeeMonthInoutSheetDao(month);
		for(MyInoutDto dto : monthInoutList) {
			List<String> li = new ArrayList<>();
			li.add(dto.getMonth());
			li.add(dto.getEmp_id());
			li.add(dto.getName());
			li.add(String.valueOf(dto.getInout_count()));
			li.add(String.valueOf(dto.getLate_count()));
			li.add(String.valueOf(dto.getWork_count()));
			body.add(li);
		}
		map.put("body", body);
		
		return map;
	} // allEmployeeMonthInoutSheet
	
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#adm_vacList(com.template.model.PageSearchDto, int)
	 */
	@Override
	public Map<String, Object> adm_vacList(PageSearchDto dto, int page) {
		PaginationInfo paginationInfo = makePage(page, 10, 10);
		
		dto.setFirstIndex(paginationInfo.getFirstRecordIndex());
		dto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(vacDao.admVacationListCnt(dto));
		List<VacationDto> vacationList = vacDao.admVacationList(dto);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", vacationList);
		map.put("page", paginationInfo);
		
		return map;
	} // end adm_vacList
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#all_vacation_sheet()
	 */
	@Override
	public Map<String, Object> all_vacation_sheet() {
		Map<String, Object> map = new HashMap<>();
		
		List<String> label = new ArrayList<String>();
		label.add("직원 ID");
		label.add("이름");
		label.add("휴가종류");
		label.add("등록일");
		label.add("휴가 시작일");
		label.add("휴가 종료일");
		label.add("휴가 일수");
		label.add("휴가 사유");
		label.add("상태");
		map.put("label", label);
		
		List<VacationDto> list = vacDao.downVacationList();
		List<List<String>> body = new ArrayList<>();
		
		for(VacationDto d : list) {
			List<String> li = new ArrayList<>();
			li.add(d.getEmp_id());
			li.add(d.getName());
			li.add(d.getCode_name());
			li.add(CommonUtils.dateToString1(d.getRegdate()));
			li.add(d.getVac_start());
			li.add(d.getVac_end());
			li.add(String.valueOf(d.getVac_cnt()));
			li.add(d.getVac_comment());
			li.add(String.valueOf(d.getState()));
			body.add(li);
		}
		map.put("body", body);
		return map;	
	} // end all_vacation_sheet
	
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#annVacList(com.template.model.PageSearchDto, int)
	 */
	@Override
	public Map<String, Object> annVacList(PageSearchDto dto, int page) {		
		PaginationInfo paginationInfo = makePage(page, 10, 10);
		dto.setFirstIndex(paginationInfo.getFirstRecordIndex());
		dto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(vacDao.admAnnVacCnt(dto));
		List<AnnualVacationDto> annVacList = new ArrayList<>();
		annVacList = vacDao.admAnnVacList(dto);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", annVacList);
		map.put("page", paginationInfo);
		
		return map;
	} // end annVacList
	
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#insertAnnVac(com.template.model.AnnualVacationDto)
	 */
	@Override
	public String insertAnnVac(AnnualVacationDto dto) {
		if(vacDao.ckinsertann(dto)==0) {
			int result = vacDao.insertAnnVac(dto);
			if(result == 1) {
				return "insert_s";
			} else {
				return "insert_f";
			}
			
		} else {
			return "error1";
		}
	} // end insertAnnVac()
	
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#allEmpList(com.template.model.PageSearchDto, int)
	 */
	@Override
	public Map<String, Object> allEmpList(PageSearchDto dto, int page) {
		PaginationInfo paginationInfo = makePage(page, 10, 10);
		dto.setFirstIndex(paginationInfo.getFirstRecordIndex());
		dto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(empDao.selectCntAllEmp(dto));
		List<EmployeeDto> empList = new ArrayList<>();
		empList = empDao.selectAllEmp(dto);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", empList);
		map.put("page", paginationInfo);
		
		return map;
	} // end allEmpList()
	
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#allEmpList()
	 */
	@Override
	public Map<String, Object> allEmpList() {
		Map<String, Object> map = new HashMap<>();
		
		List<String> label = new ArrayList<String>();
		label.add("직원 ID");
		label.add("이름");
		label.add("영어이름");
		label.add("이메일");
		label.add("H.P");
		label.add("O.P");
		map.put("label", label);
		
		List<EmployeeDto> list = empDao.allListEmp();
		List<List<String>> body = new ArrayList<>();
		
		for(EmployeeDto dto : list) {
			List<String> l = new ArrayList<>();
			l.add(dto.getEmp_id());
			l.add(dto.getName());
			l.add(dto.getSub_name());
			l.add(dto.getEmail());
			l.add(dto.getH_phone());
			l.add(dto.getH_phone());
			l.add(dto.getO_phone());
			body.add(l);
		}
		map.put("body", body);
		return map;
	} // end allEmpList()
	
	
	/* (non-Javadoc)
	 * @see com.template.service.AdminService#insertNewEmployee(com.template.model.EmployeeDto)
	 */
	@Override
	public int insertNewEmployee(EmployeeDto dto) {
		if(dto.getPw().length() < 10) {
			return 99;
		} else {
			Pattern p = Pattern.compile("(^[a-zA-Z]+[0-9]+$)");
			Matcher m = p.matcher(dto.getPw());
			
			if(!m.find()) {
				return 99;
			} else {
				dto.setPw(CommonUtils.sha512(dto.getPw()));
			}
		}
		
		int result = empDao.insertEmp(dto);
		
		return result;
	}
	
	
} // end class AdminServiceImpl
