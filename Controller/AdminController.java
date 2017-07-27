/**
 * 
 */
package com.template.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.template.common.CommonUtils;
import com.template.model.AnnualVacationDto;
import com.template.model.EmployeeDto;
import com.template.model.InoutMiDto;
import com.template.model.InoutWorkDto;
import com.template.model.PageSearchDto;
import com.template.repository.EmployeeDao;
import com.template.repository.InoutDao;
import com.template.repository.VacationDao;
import com.template.service.AdminService;
import com.template.service.InoutService;
import com.template.service.VacationService;

/**
* <pre>
* com.template.controller 
*    |_ AdminController.java
* </pre>
* @date : 2017. 6. 2. 오후 6:40:45
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	@Qualifier("InoutServiceImpl")
	private InoutService inoutService;
	
	@Autowired
	@Qualifier("AdminServiceImpl")
	private AdminService adminService;
	
	@Autowired
	@Qualifier("VacationServiceImpl")
	private VacationService vacService;
	
	
	@Autowired
	@Qualifier("InoutDaoImpl")
	private InoutDao inoutDao;

	@Autowired
	@Qualifier("EmployeeDaoImpl")
	private EmployeeDao empDao;
	
	@Autowired
	@Qualifier("VacationDaoImpl")
	private VacationDao vacDao;
	
	/**
	* @Method Name : admin_emplist
	* @Since    : 2017. 6. 5. 오후 4:51:52
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 : 직원 조회(수정, 삭제) 페이지 이동 
	*/
	@RequestMapping(value="/employeeList.mi", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView admin_emplist(Model model, Integer page, PageSearchDto search_dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		if(CommonUtils.empty(page)) {
			page = 1;
			search_dto.setSearchType("all");
		}
		
		Map<String, Object> map = adminService.allEmpList(search_dto, page);
		ModelAndView view = new ModelAndView("/admin/employeeList");
		view.addObject("search", search_dto);
		view.addObject("page", map.get("page"));
		view.addObject("list", map.get("list"));
		
		return view;
	} // end admin_emplist()
	
	/**
	* @Method Name : reg_emp
	* @Since    : 2017. 6. 5. 오후 4:51:55
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 : 직원 등록 페이지 이동 
	*/
	@RequestMapping(value="/empRegistration.mi", method={RequestMethod.GET, RequestMethod.POST})
	public String reg_emp(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		
		model.addAttribute("page_type", "new_employee");
		return "/admin/employeeRegistration";
	} // end reg_emp
	
	
	/**
	* @Method Name : ckEmpid
	* @Since    : 2017. 6. 14. 오후 5:37:22
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : int
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 아이디 중복 확인
	*/
	@ResponseBody
	@RequestMapping(value="/checkEmpId.ajax", method=RequestMethod.POST)
	public int ckEmpid(Model model, @ModelAttribute("emp_id")String emp_id, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		int result;
		
		if (emp_id.length() < 3 && emp_id.length() > 20) {
			result = 99;
		} else {
			Pattern p = Pattern.compile("(^[a-zA-Z0-9]+$)");
			Matcher m = p.matcher(emp_id);
			
			if(!m.find()) {
				result = 99;
			} else {
				result = empDao.ckEmpid(emp_id);
			}
		}
		return result;
	} // end ckEmpid()
	
	
	/**
	* @Method Name : insertEmp
	* @Since    : 2017. 6. 15. 오전 9:48:35
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 회원 등록 서비스
	*/
	@RequestMapping(value="/employeeRegistration.service", method=RequestMethod.POST)
	public String insertEmp(Model model, EmployeeDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session, RedirectAttributes attr) {
		int result = adminService.insertNewEmployee(dto);
		if(result == 1) {
			attr.addFlashAttribute("insertEmp", "s");
			return "redirect:/admin/employeeList.mi";
		} else {
			attr.addFlashAttribute("insertEmp", "f");
			return "redirect:/admin/empRegistration.mi";
		}
		
	} // end insertEmp
	
	
	/**
	* @Method Name : updateEmpPage
	* @Since    : 2017. 6. 15. 오전 9:48:38
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 정보 수정 페이지 이동  
	*/
	@RequestMapping(value="/employeeUpdate.mi", method=RequestMethod.POST)
	public String updateEmpPage(Model model, String emp_id, HttpServletRequest req, HttpServletResponse rep, HttpSession session, RedirectAttributes attr) {
		
		EmployeeDto empDto = empDao.selectOneEmp(emp_id);
		if(!(CommonUtils.empty(empDto))) {
			model.addAttribute("empDto", empDto);
			model.addAttribute("page_type", "update_employee");
			
			return "/admin/employeeRegistration";
		} else {
			attr.addFlashAttribute("error_upd", "f");
			return "redirect:/admin/employeeList.mi";
		}
	} // end updateEmpPage
	
	
	/**
	* @Method Name : updateEmp
	* @Since    : 2017. 6. 15. 오전 9:48:40
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 회원 정보 수정
	*/
	@RequestMapping(value="/employeeUpdate.service", method=RequestMethod.POST)
	public String updateEmp(Model model, EmployeeDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session, RedirectAttributes attr) {
		
		dto.setPw(CommonUtils.sha512(dto.getPw()));
		
		int result = empDao.updateEmp(dto);
		
		if(result == 1) {
			attr.addFlashAttribute("update_result", "s");
		} else {
			attr.addFlashAttribute("update_result", "f");
		}
		
		return "redirect:/admin/employeeList.mi";
	} // end updateEmp
	
	/**
	* @Method Name : resignationEmp
	* @Since    : 2017. 6. 21. 오전 10:07:33
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 퇴사처리 (EMPLOYEE_INFO : STATE_0)
	*/
	@RequestMapping(value="/resignationEmployee.service", method=RequestMethod.POST)
	public String resignationEmp(Model model, String emp_id, HttpServletRequest req, HttpServletResponse rep, HttpSession session, RedirectAttributes attr) {
		EmployeeDto dto = empDao.selectOneEmp(emp_id);
		if(dto.getLevel() == 1) {
			attr.addFlashAttribute("deleteResult", "f_admin");
		} else {
			dto.setState(0);
			
			int result = empDao.employeeResignation(dto);
			if(result == 1) {
				attr.addFlashAttribute("deleteResult", "s");
			} else {
				attr.addFlashAttribute("deleteResult", "f");
			}
		}
		return "redirect:/admin/employeeList.mi";
	} // end resignationEmp
	
	
	/**
	* @Method Name : allEmplistDownload
	* @Since    : 2017. 6. 15. 오전 11:04:27
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 리스트 엑셀 다운로드  
	*/
	@RequestMapping(value="/empSheet.service", method=RequestMethod.GET)
	public String allEmplistDownload(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		Map<String, Object> map = adminService.allEmpList();
		model.addAttribute("label", map.get("label"));
		model.addAttribute("body", map.get("body"));
		return "exceldView.service";
	} // end allEmplistDownload()
	
	
	/**
	* @Method Name : inout_log
	* @Since    : 2017. 6. 5. 오후 4:51:57
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 근태 내역 조회 페이지 이동 
	*/
	@RequestMapping(value="/inoutLog.mi", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView inout_log(Model model, Integer page, PageSearchDto search_dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		if(CommonUtils.empty(page)) {
			page = 1;
			if(CommonUtils.empty(search_dto.getSearchType())) {
				search_dto.setSearchType("all");
			}
		}

		Map<String, Object> map = adminService.adm_inoutList(search_dto, page);
		//PaginationInfo info = (PaginationInfo) map.get("page");
		ModelAndView view = new ModelAndView("/admin/adminInoutLog");
		logger.info(search_dto.getSearchType() + "11111");
		view.addObject("search", search_dto);
		view.addObject("page", map.get("page"));
		view.addObject("list", map.get("list"));

		return view;
	} // end inout_log
	
	/**
	* @Method Name : oneempWorkinout
	* @Since    : 2017. 6. 15. 오후 1:54:44
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 근태관리-내역 페이지에서 직원 한명의 하루 외근 기록 조회
	*/
	@ResponseBody
	@RequestMapping(value="/adm_workinout_onelist.ajax", method=RequestMethod.POST)
	public List<InoutWorkDto> oneempWorkinout(Model model, @ModelAttribute("empWork_form")InoutWorkDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		dto.setWork_state(1);
		List<InoutWorkDto> workList = inoutDao.selectListWorkinout(dto);
		logger.info(workList.size()+"");
		return workList;
	} // end oneempWorkinout()
	
	/**
	* @Method Name : oneempMiinout
	* @Since    : 2017. 6. 15. 오후 3:40:55
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : InoutMiDto
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 근태내역페이지-한 항목 근태내역 조회 
	*/
	@ResponseBody
	@RequestMapping(value="/adm_empinout_oneday.ajax", method=RequestMethod.POST)
	public InoutMiDto oneempMiinout(Model model, @ModelAttribute("empinout_form") InoutMiDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		InoutMiDto oneDto = inoutDao.selectInout(dto);
		
		return oneDto;
	} // end oneempMiinout()

	/**
	* @Method Name : inoutMiUpdate
	* @Since    : 2017. 6. 16. 오후 1:07:06
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : void
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 출퇴 내역 수정
	*/
	@RequestMapping(value="/updateInout.service", method=RequestMethod.POST)
	public String inoutMiUpdate(Model model, InoutMiDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session, RedirectAttributes attr) {
		if(CommonUtils.empty(dto.getMi_out())) {
			dto.setMi_out(null);
			dto.setInout_state(0);
		} else {
			dto.setInout_state(1);
			
			String out_date = dto.getMi_out();
			out_date = out_date.replaceAll("T", " ");
			dto.setMi_out(out_date);
		}
		
		if(!(CommonUtils.empty(dto.getMi_in()))) {
			String in_date = dto.getMi_in();
			in_date = in_date.replaceAll("T", " ");
			dto.setMi_in(in_date);
		}
		
		
		int result = inoutDao.adm_update_inout(dto);
		if(result==1) {
			attr.addFlashAttribute("update_r", "s");
		} else {
			attr.addFlashAttribute("update_r", "f");
		}
		
		return "redirect:/admin/inoutLog.mi";
	} // end inoutMiUpdate()
	
	
	
	/**
	* @Method Name : allinoutDownload
	* @Since    : 2017. 6. 13. 오전 11:41:12
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 전체 근태내역 다운로드
	*/
	@RequestMapping(value="/inoutSheet.service", method=RequestMethod.GET)
	public String allinoutDownload(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		Map<String, Object> map = adminService.all_inout_sheet();
		model.addAttribute("label", map.get("label"));
		model.addAttribute("body", map.get("body"));
		return "exceldView.service";
	} // end allinoutDownload
	
	/**
	* @Method Name : month_inout
	* @Since    : 2017. 6. 5. 오후 4:53:22
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 월별 근태 내역 조회
	*/
	@RequestMapping(value="/inoutMonth.mi", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView month_inout(Model model, Integer page, PageSearchDto searchDto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		if(CommonUtils.empty(page) && CommonUtils.empty(searchDto.getMonth()) ) {
			page = 1;
			int t_month = Calendar.getInstance().get(Calendar.MONTH)+1;
			int year = Calendar.getInstance().get(Calendar.YEAR);
			StringBuffer month = new StringBuffer();
			month.append(year);
			month.append("-");
			if(t_month < 10) {
				month.append("0"+t_month);
			} else if(t_month >= 10 || t_month <13) {
				month.append(t_month);
			}
			searchDto.setMonth(month.toString());
		} else if(CommonUtils.empty(page)) {
			page = 1;
		}
		
		Map<String, Object> map = adminService.adm_monthInout(searchDto, page);
		ModelAndView view = new ModelAndView("/admin/adm-inout-month");
		view.addObject("search", searchDto);
		view.addObject("page", map.get("page"));
		view.addObject("list", map.get("list"));
		view.addObject("month",searchDto.getMonth());
		
		return view;
	} // end month_inout
	

	// inoutMonthSheet.service
	/**
	* @Method Name : allInoutMonthStatsSheet
	* @Since    : 2017. 6. 22. 오후 1:25:16
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 월별 직원 근태통계 다운로드 
	*/
	@RequestMapping(value="/inoutMonthSheet.service", method=RequestMethod.GET)
	public String allInoutMonthStatsSheet(Model model, String month, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		logger.debug("month : "+ month);
		Map<String, Object> map = adminService.allEmployeeMonthInoutSheet(month);
		model.addAttribute("label", map.get("label"));
		model.addAttribute("body", map.get("body"));
		return "exceldView.service";
	} // end allInoutMonthStatsSheet
	
	/**
	* @Method Name : vac_list
	* @Since    : 2017. 6. 5. 오후 4:53:57
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 사용 내역  
	*/
	@RequestMapping(value="/vacationLog.mi", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView vac_list(Model model, Integer page, PageSearchDto searchDto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		if(CommonUtils.empty(page)) {
			page = 1;
			
			if(CommonUtils.empty(searchDto.getSearchType())) {
				searchDto.setSearchType("all");
			}
		}

		Map<String, Object> map = adminService.adm_vacList(searchDto, page);
		ModelAndView view = new ModelAndView("/admin/vac-log");
		view.addObject("search", searchDto);
		view.addObject("page", map.get("page"));
		view.addObject("list", map.get("list"));

		return view;
	} // end vac_list
	
	
	/**
	* @Method Name : vacListDownload
	* @Since    : 2017. 6. 13. 오후 6:34:34
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 사용 리스트 다운로드
	*/
	@RequestMapping(value="/vacSheet.service", method=RequestMethod.GET)
	public String vacListDownload(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		Map<String, Object> map = adminService.all_vacation_sheet();
		model.addAttribute("label", map.get("label"));
		model.addAttribute("body", map.get("body"));
		
		return "exceldView.service";
	} // end vacListDownload
	
	
	/**
	* @Method Name : vac_annual_list
	* @Since    : 2017. 6. 5. 오후 4:55:03
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 연차 휴가 등록 내역 
	*/
	@RequestMapping(value="/annualVacationLog.mi", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView vac_annual_list(Model model, Integer page, PageSearchDto searchDto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		if(CommonUtils.empty(page) && CommonUtils.empty(searchDto.getDay1())) {
			page = 1;
			int day1 = Calendar.getInstance().get(Calendar.YEAR);
			searchDto.setDay1(String.valueOf(day1));
		} else if(CommonUtils.empty(page)) {
			page = 1;
		}
		
		Map<String, Object> map = adminService.annVacList(searchDto, page);
		ModelAndView view = new ModelAndView("/admin/vac-ann-log");
		view.addObject("search", searchDto);
		view.addObject("page", map.get("page"));
		view.addObject("list", map.get("list"));
		
		return view;
	} // end vac_list
	
	
	
	/**
	* @Method Name : annRegPage
	* @Since    : 2017. 6. 14. 오전 11:01:27
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : ModelAndView
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 페이지 : 연차 등록 페이지
	*/
	@RequestMapping(value="/ann-reg.mi", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView annRegPage(Model model, Integer page, PageSearchDto searchDto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		ModelAndView view = new ModelAndView("/admin/vac-ann-reg");
		
		return view;
	} // end annRegPage()
	
	/**
	* @Method Name : annregEmplist
	* @Since    : 2017. 6. 14. 오후 1:33:44
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : List<EmployeeDto>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 연차 등록 페이지에서 선택된 년도에 연차등록 이력이 없는 회원 정보 ajax로 전달
	*/
	@ResponseBody
	@RequestMapping(value="/year_emplist.ajax", method=RequestMethod.POST)
	public List<EmployeeDto> annregEmplist(Model model, @ModelAttribute("year_form")AnnualVacationDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		String year = dto.getYear();
		List<EmployeeDto> empList = vacDao.admAnnregEmpList(year);
		return empList;
	} // end annregEmplist()
	
	// /admin/reg-ann.service
	
	/**
	* @Method Name : regAnnvac
	* @Since    : 2017. 6. 14. 오후 1:35:17
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : void
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 연차등록 실행 메소드
	*/
	@RequestMapping(value="/reg-ann.service", method=RequestMethod.POST)
	public String regAnnvac(Model model, AnnualVacationDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session, RedirectAttributes attr) {
		String result = adminService.insertAnnVac(dto);
		attr.addFlashAttribute("insert_result", result);
		return "redirect:/admin/annualVacationLog.mi";
	} // end regAnnvac
	
	// update-annvc.service

	/**
	* @Method Name : updateAnnvac
	* @Since    : 2017. 6. 14. 오후 3:25:06
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : ModelAndView
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 연차 수정 페이지 
	*/
	@RequestMapping(value="/update-annvc.mi", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView updateAnnvac(Model model, int rid, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		ModelAndView view = new ModelAndView("/admin/vac-ann-update");
		AnnualVacationDto dto = vacDao.selectOneAnnvac(rid);
		view.addObject("annDto", dto);
		return view;
	}
	
	// update-ann.service
	/**
	* @Method Name : updateAnnvacS
	* @Since    : 2017. 6. 14. 오후 3:26:14
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 연차 내용 수정 실행
	*/
	@RequestMapping(value="/update-ann.service", method=RequestMethod.POST)
	public String updateAnnvacS(Model model, AnnualVacationDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session, RedirectAttributes attr) {
		int result = vacDao.updateAnnVac(dto);
		if(result==1) {
			attr.addFlashAttribute("update_result", "update_s");
		} else {
			attr.addFlashAttribute("update_result", "update_f");
		}
		return "redirect:/admin/annualVacationLog.mi";
	}
	
	/**
	* @Method Name : adminBoard
	* @Since    : 2017. 6. 13. 오전 11:22:10
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 게시판
	*/
	@RequestMapping(value="/admin_board.mi", method={RequestMethod.GET, RequestMethod.POST})
	public String adminBoard(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		
		
		return "/admin-board/list";
	} // end vac_list
	
	
}
