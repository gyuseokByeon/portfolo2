package com.template.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.template.common.CommonUtils;
import com.template.model.PageSearchDto;
import com.template.repository.EmployeeDao;
import com.template.service.AdminService;


/**
* <pre>
* com.template.controller 
*    |_ EmployeeController.java
* </pre>
* @date : 2017. 6. 2. 오후 6:40:29
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */


@Controller
@RequestMapping("/emp")
public class EmployeeController {
	
	@Autowired
	@Qualifier("EmployeeDaoImpl")
	private EmployeeDao empDao;
	
	@Autowired
	@Qualifier("AdminServiceImpl")
	private AdminService admService;
	
	
	@RequestMapping(value="/list.mi", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView emp_emplist(Model model, Integer page, PageSearchDto search_dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		if(CommonUtils.empty(page)) {
			page = 1;
			search_dto.setSearchType("loginemp");
		}
		
		Map<String, Object> map = admService.allEmpList(search_dto, page);
		ModelAndView view = new ModelAndView("/emp/list");		
		view.addObject("search", search_dto);
		view.addObject("page", map.get("page"));
		view.addObject("list", map.get("list"));
		return view;
	} // end emp_emplist
	
	
	

} // end class EmployeeController
