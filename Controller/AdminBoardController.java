/**
 * 
 */
package com.template.controller;
import java.util.Date;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.template.common.CommonUtils;
import com.template.model.AdminBoardDto;
import com.template.model.EmployeeDto;
import com.template.model.PageSearchDto;
import com.template.repository.AdminBoardDao;
import com.template.repository.EmployeeDao;
import com.template.service.AdminBoardService;
import com.template.service.SlackService;
/**
* <pre>
* com.template.controller 
*    |_ AdminBoardController.java
* </pre>
* @date : 2017. 6. 5. 오전 9:30:27
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */

@Controller
@RequestMapping("/contact")
public class AdminBoardController {

	private static final Logger logger = LoggerFactory.getLogger(AdminBoardController.class);
	
	@Autowired
	@Qualifier("AdminBoardServiceImpl")
	private AdminBoardService abService;
	
	@Autowired
	@Qualifier("AdminBoardDaoImpl")
	private AdminBoardDao abDao;
	
	@Autowired
	@Qualifier("EmployeeDaoImpl")
	private EmployeeDao empDao;
	
	
	/**
	* @Method Name : boardListView
	* @Since    : 2017. 6. 12. 오후 4:30:54
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 
	*/
	@RequestMapping(value="/list.mi", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView boardListView(Model model, Integer page, PageSearchDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		String emp_id = (String)session.getAttribute("LOGIN_ID");
		EmployeeDto empDto = empDao.selectOneEmp(emp_id);
		ModelAndView view = null;

		if(empDto.getLevel() == 1) {
			dto.setKeyword("");
			view = new ModelAndView("/admin-board/admin-list");
		} else {
			dto.setKeyword(emp_id);
			view = new ModelAndView("/admin-board/emp-list");
		}
		
		if(CommonUtils.empty(page)) {
			page=1;
		}
		Map<String, Object> map = abService.emp_ablist(dto, page);
		view.addObject("page", map.get("page"));
		view.addObject("list", map.get("list"));
		
		return view;
	} // end todayHistory
	
	/**
	* @Method Name : empInsertAb
	* @Since    : 2017. 6. 16. 오후 4:32:15
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : void
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 일반직원 : 관리자 요청 글 등록
	*/
	@RequestMapping(value="/emp_insert.service", method=RequestMethod.POST)
	public String empInsertAb(Model model, AdminBoardDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		String emp_id = (String)session.getAttribute("LOGIN_ID");
		dto.setEmp_id(emp_id);
		
		int insertResult = abDao.empAbInsert(dto);
		if(insertResult == 1) {
			SlackService slack = new SlackService();
			EmployeeDto empDto = empDao.selectOneEmp(emp_id);
//			slack.sendToSlackInOut(empDto.getName(), "", "요청게시판에 글을 등록하셨습니다.");
		}
		
		return "redirect:/contact/list.mi";
	} //end empInsertAb
	
	
	/**
	* @Method Name : admUpdateState
	* @Since    : 2017. 6. 16. 오후 5:40:23
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 관리자 : 요청 처리 
	*/
	@RequestMapping(value="/adm_updatestate.service", method=RequestMethod.POST)
	public String admUpdateState(Model model, AdminBoardDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		
		if(dto.getAdmin_state()==0) {
			dto.setCdate(null);
		} else if(dto.getAdmin_state()==1) {
			Date date = new Date();
			dto.setCdate(date);
		}
		
		abDao.admAbupdate(dto);
		
		return "redirect:/contact/list.mi";
	}
	
	
	/**
	* @Method Name : empDeleteAb
	* @Since    : 2017. 6. 16. 오후 5:59:08
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 직원 - 요청글 삭제
	*/
	@RequestMapping(value="/emp_del.service", method=RequestMethod.POST)
	public String empDeleteAb(Model model, int rid, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		abDao.empDeleteAb(rid);
		
		return "redirect:/contact/list.mi";
	}

	
} // end class AdminBoardController
