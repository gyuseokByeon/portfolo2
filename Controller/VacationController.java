/**
 * 
 */
package com.template.controller;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.template.model.CodeDto;
import com.template.model.EmployeeDto;
import com.template.model.VacationDto;
import com.template.repository.EmployeeDao;
import com.template.repository.VacationDao;
import com.template.service.SlackService;
import com.template.service.VacationService;
/**
* <pre>
* com.template.controller 
*    |_ VacationController.java
* </pre>
* @date : 2017. 6. 2. 오후 6:39:48
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
@Controller
@RequestMapping("/vacation")
public class VacationController {
	
	@Autowired
	@Qualifier("VacationServiceImpl")
	private VacationService service;
	
	@Autowired
	@Qualifier("VacationDaoImpl")
	private VacationDao vacDao;
	
	@Autowired
	@Qualifier("EmployeeDaoImpl")
	private EmployeeDao empDao;
	

	private static final Logger logger = LoggerFactory.getLogger(VacationController.class);
	
	/**
	* @Method Name : myVacation
	* @Since    : 2017. 6. 9. 오후 5:52:44
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 
	*/
	@RequestMapping(value="/mylist.mi", method={RequestMethod.GET, RequestMethod.POST})
	public String myVacation(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		String emp_id = (String)session.getAttribute("LOGIN_ID");
		List<VacationDto> vacList = service.vacationList(emp_id);
		model.addAttribute("vacList", vacList);

		return "/vac/my-vacation";
	} // end todayHistory
	
	
	/**
	* @Method Name : insertVacationView
	* @Since    : 2017. 6. 9. 오후 5:52:40
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 등록 페이지 이동
	*/
	@RequestMapping(value="/reg-vc.mi", method={RequestMethod.GET, RequestMethod.POST})
	public String insertVacationView(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		logger.info("휴가 등록 페이지");
		
		String emp_id = (String) session.getAttribute("LOGIN_ID");
		String ridst = req.getParameter("rid");
		
		List<CodeDto> codeList = service.codeList("VAC");
		model.addAttribute("codeList", codeList);
		
		return "/vac/myvac-reg";
	} // end todayHistory
	
	/**
	* @Method Name : insertVacation
	* @Since    : 2017. 6. 5. 오후 2:37:31
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : ModelAndView
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 등록
	*/
	@RequestMapping(value="/reg-vc.service", method=RequestMethod.POST)
	public String insertVacation(Model model, VacationDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session, RedirectAttributes attr) {
		String emp_id = (String)session.getAttribute("LOGIN_ID");
		dto.setEmp_id(emp_id);
		String result = service.insertVac(dto);
		attr.addFlashAttribute("insert_result", result);
		
		return "redirect:/vacation/mylist.mi";
	} // end insertVacation
	
	
	/**
	* @Method Name : cancelVacaion
	* @Since    : 2017. 6. 9. 오후 6:22:44
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 휴가 등록 취소 
	*/
	@RequestMapping(value="/cancel-vc.service", method=RequestMethod.POST)
	public String cancelVacaion(Model model, int rid, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		String emp_id = (String)session.getAttribute("LOGIN_ID");
		service.cancelVac(rid);
		
		
		return "redirect:/vacation//mylist.mi";
	} // end cancelVacation

	
}
