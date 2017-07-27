/**
 * 
 */
package com.template.controller;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.template.model.AnnualVacationDto;
import com.template.model.EmployeeDto;
import com.template.model.MyInoutDto;
import com.template.repository.EmployeeDao;
import com.template.repository.InoutDao;
import com.template.repository.VacationDao;
/**
* <pre>
* com.template.controller 
*    |_ MypageController.java
* </pre>
* @date : 2017. 6. 2. 오후 6:40:38
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
@Controller
@RequestMapping("/mypage")
public class MypageController {

	private static final Logger logger = LoggerFactory.getLogger(MypageController.class);
	
	@Autowired
	@Qualifier("InoutDaoImpl")
	private InoutDao inoutDao;
	
	@Autowired
	@Qualifier("VacationDaoImpl")
	private VacationDao vacDao;
	
	@Autowired
	@Qualifier("EmployeeDaoImpl")
	private EmployeeDao empDao;
	
	
	/**
	* @Method Name : getMyinout
	* @Since    : 2017. 6. 12. 오후 3:06:37
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 
	*/
	@RequestMapping(value="/myInoutMonthStats.mi", method={RequestMethod.GET, RequestMethod.POST})
	public String getMyinout(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		String emp_id = (String)session.getAttribute("LOGIN_ID");
		List<MyInoutDto> list = inoutDao.monthMyInout(emp_id);
		
		model.addAttribute("list", list);
		
		return "/mypage/my-inout";
	} // end todayHistory
	
	/**
	* @Method Name : getMyAnnualvc
	* @Since    : 2017. 6. 12. 오후 3:06:34
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 
	*/
	@RequestMapping(value="/myAnnualVacationList.mi", method={RequestMethod.GET, RequestMethod.POST})
	public String getMyAnnualvc(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		String emp_id = (String) session.getAttribute("LOGIN_ID");
		List<AnnualVacationDto> list = vacDao.selectMyAnnuVac(emp_id);
		model.addAttribute("list", list);

		return "/mypage/my-annual-vc";
	} // end todayHistory
	
	/**
	* @Method Name : getMyinfo
	* @Since    : 2017. 6. 12. 오후 3:06:31
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 
	*/
	@RequestMapping(value="/myInfo.mi", method={RequestMethod.GET, RequestMethod.POST})
	public String getMyinfo(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		String emp_id = (String) session.getAttribute("LOGIN_ID");
		EmployeeDto dto = empDao.selectOneEmp(emp_id);
		model.addAttribute("dto", dto);
		return "/mypage/my-info";
	} // end todayHistory
	
	
	/**
	* @Method Name : updateMyInfo
	* @Since    : 2017. 6. 12. 오후 3:07:29
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : void
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 내 정보 수정 (이메일, 핸드폰 번호만 수정 가능)
	*/
	@RequestMapping(value="/my-info.service", method=RequestMethod.POST)
	public String updateMyInfo(Model model, EmployeeDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session, RedirectAttributes attr) {
		int result = empDao.updateMyInfo(dto);
		if(result == 1) {
			attr.addFlashAttribute("update_result", "s");
		} else {
			attr.addFlashAttribute("update_result", "f");
		}
		
		return "redirect:/mypage/myInfo.mi";
	} // end updateMyInfo
	
	
}
