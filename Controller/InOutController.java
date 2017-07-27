package com.template.controller;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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

import com.template.common.CommonUtils;
import com.template.model.InoutMiDto;
import com.template.model.InoutWorkDto;
import com.template.repository.InoutDao;
import com.template.service.InoutService;

/**
* <pre>
* com.template.controller 
*    |_ InOutController.java
* </pre>
* @date : 2017. 6. 2. 오후 6:38:49
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
@Controller
@RequestMapping("/inout")
public class InOutController {

	private static final Logger logger = LoggerFactory.getLogger(InOutController.class);
	
	@Autowired
	@Qualifier("InoutServiceImpl")
	private InoutService service;
	
	@Autowired
	@Qualifier("InoutDaoImpl")
	private InoutDao dao;
	
	
	/**
	* @Method Name : calcRealDay
	* @Since    : 2017. 5. 30. 오후 2:51:22
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      : 
	* @version  : 0.1
	* @Method Description : 받아온 date 값에서 시간, 날짜 계산해서 기준(06:00)으로 REAL_DAY를 계산
	*/
	public String calcRealDay(Date date) { 
		String realday = "";
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int now_hour = cal.get(Calendar.HOUR_OF_DAY); // 시
		int now_mi = cal.get(Calendar.MINUTE); // 분
		int now_se = cal.get(Calendar.SECOND); // 초
		
		if(now_hour >= 0 && now_hour <= 5 ) {
			// 밤 0시~5시59분 사이 : 하루 전날< Real_day
			cal.add(Calendar.DATE, -1);
			Date rdate = new Date(cal.getTimeInMillis()); // 전일 날짜
			realday = CommonUtils.dateToString2(rdate);
		} else if(now_hour >= 6 && now_mi >=0 && now_se >= 0) {
			// 06:00:00 이후 : 부터 현재 날짜 < REAL_DAY
			realday = CommonUtils.dateToString2(date);
		}
		return realday;
	} // end calcRealTime
	
	
	/**
	* @Method Name : todayHistory
	* @Since    : 2017. 6. 5. 오전 10:44:31
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : String
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 근태내역 리스트 페이지
	*/
	@RequestMapping(value="/inout-log.mi", method={RequestMethod.GET, RequestMethod.POST})
	public String todayHistory(Model model, String move_day, HttpServletRequest req, HttpServletResponse rep, HttpSession session){
		Cookie[] cookies = req.getCookies();
		String aa = "";
		if(!CommonUtils.empty(cookies)){
			if(cookies.length > 0) {
				for(int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					if(c.getName().equals("remember-me")) {
						logger.error(aa);
						aa = c.getValue();
					}
				}
				
			}
		}
		
		
		
		String today = "";
		String emp_id = (String) session.getAttribute("LOGIN_ID");
		
		Date todayrdrd = new Date();
		String todayRD = calcRealDay(todayrdrd);
		session.setAttribute("TODAY_RD", todayRD);
		
		if(CommonUtils.empty(move_day)) {
			Date date = new Date();
			today = calcRealDay(date);
		} else {
			today = move_day;
		}

		InoutMiDto dto = new InoutMiDto();
		dto.setEmp_id(emp_id);
		dto.setReal_day(today);
		dto.setP_start(0);
		
		List<InoutMiDto> mi_list = service.selectInout(dto);
		List<InoutWorkDto> work_list = service.selectListWork(today);
		
		model.addAttribute("mi_list", mi_list);
		model.addAttribute("work_list", work_list);
		model.addAttribute("real_day", today);
		model.addAttribute("login_user", emp_id);
		model.addAttribute("p_start", 0);
		
		return "/inout/inout-log";
	} // end todayHistory
	
	
	/**
	* @Method Name : logaddscroll
	* @Since    : 2017. 6. 9. 오전 11:29:49
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : ModelAndView
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 무한 스크롤 > 스크롤시 추가 페이지 로딩
	*/
	@ResponseBody
	@RequestMapping(value="/inout_addlog.ajax", method=RequestMethod.POST)
	public ModelAndView logaddscroll(Model model, @ModelAttribute("scrollpage_form") InoutMiDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {

		dto.setEmp_id((String)session.getAttribute("LOGIN_ID"));
		List<InoutMiDto> list = service.selectInout(dto);
		
		int nestart = dto.getP_start()+7;
		
		ModelAndView view = new ModelAndView("inout/ajax/aj-inout-log");
		
		view.addObject("mi_list", list);
		view.addObject("work_list", service.selectListWork(dto.getReal_day()));
		view.addObject("real_day", dto.getReal_day());
		view.addObject("login_user", dto.getEmp_id());
		view.addObject("p_start", nestart);
		return view;
	} // end logaddscroll
	
	
	/**
	* @Method Name : inoutInsert
	* @Since    : 2017. 5. 31. 오전 11:05:18
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 근태 입력 controller
	*/
	@ResponseBody
	@RequestMapping(value="/mi-inout.ajax", method=RequestMethod.POST)
	public ModelAndView inoutInsert(Model model, @ModelAttribute("inout_form") InoutMiDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		ModelAndView view = new ModelAndView("inout/ajax/aj-inout-log");
		
		String emp_id = (String)session.getAttribute("LOGIN_ID"); // 로그인 한 유저 id

		Date date = new Date(); // 버튼 눌렸을때 date 값
		String real_day = calcRealDay(date); // real_day
		String result = null;
		
		String accessIp = CommonUtils.clientip(req);
		if(CommonUtils.checkAccessIp(accessIp)) {
			// 출퇴근 입력
			String btn_ty = req.getParameter("btn_type"); // 출근: miin | 퇴근: miout | 직출: mijik
			String inout_date = CommonUtils.dateToString1(date); // String
			
			dto.setEmp_id(emp_id);
			dto.setReal_day(real_day);
			
			if(btn_ty.equals("miin") || btn_ty.equals("mijik")) { // 출근,직출
				// 출근 입력 -> service
				dto.setMi_in(inout_date);
				result = service.insertInout(dto, btn_ty);			
			} else if(btn_ty.equals("miout")) {
				dto.setMi_out(inout_date);
				result = service.insertInout(dto, btn_ty);				
			} 
			view.addObject("result", result);
			
		} else {
			view.addObject("result", "회사 내부망이 아닙니다");
		}
		
		dto.setP_start(0);
		List<InoutMiDto> list = service.selectInout(dto);
		List<InoutWorkDto> dtoList = service.selectListWork(real_day);
		
		view.addObject("mi_list", list);
		view.addObject("work_list", dtoList);
		view.addObject("real_day", real_day);
		view.addObject("login_user", emp_id);
		return view;
	} // end inoutInsert
	
	
	/**
	* @Method Name : workinsertInout
	* @Since    : 2017. 5. 31. 오전 11:07:55
	* @author   : OMNILAB-A
	* @param    : 
	* @return   : Map<String,Object>
	* @exception : 
	* @serial   :
	* @see      :
	* @version  : 0.1
	* @Method Description : 외근, 복귀 입력
	*/
	@ResponseBody
	@RequestMapping(value="/work-inout.ajax", method=RequestMethod.POST)
	public ModelAndView workinsertInout(Model model, @ModelAttribute("inout_work_form") InoutWorkDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		String btn_ty = req.getParameter("btn_type"); // workout_외근 / workin_복귀
		Date date = new Date();
		String inout_date = CommonUtils.dateToString1(date);
		String emp_id = (String)session.getAttribute("LOGIN_ID");
		String result = "";
		String realday = calcRealDay(date);
		dto.setEmp_id(emp_id);
		dto.setReal_day(calcRealDay(date));
		
		ModelAndView view = new ModelAndView("inout/ajax/aj-inout-log");

		String tryIp = CommonUtils.clientip(req);
		if(CommonUtils.checkAccessIp(tryIp)) {
			switch(btn_ty) {
			case "workout":
				dto.setWork_out(inout_date);
				result = service.workinsertInout(dto, btn_ty);
				view.addObject("result", result);
				break;
			case "workin":
				dto.setWork_in(inout_date);
				result = service.workinsertInout(dto, btn_ty);
				view.addObject("result", result);
				break;
			} // end switch
		} else {
			view.addObject("result", "회사 내부망이 아닙니다");
		}
		
		InoutMiDto dtotmp = new InoutMiDto();
		dtotmp.setReal_day(realday);
		dtotmp.setEmp_id(emp_id);
		dtotmp.setP_start(0);
		List<InoutMiDto> list = service.selectInout(dtotmp);
		List<InoutWorkDto> dtoList = service.selectListWork(realday);
		
		view.addObject("mi_list", list);
		view.addObject("work_list", dtoList);
		view.addObject("real_day", realday);
		view.addObject("login_user", emp_id);
		
		return view;
	} // end workinsertInout
	
	
	@RequestMapping(value="/jikupdate.service", method=RequestMethod.POST)
	public String jikupdate(Model model, InoutMiDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		dao.jikcommentUpdate(dto);
		
		return "redirect:/inout/inout-log.mi";
	} // end jikupdate
	
	@RequestMapping(value="/niupdate.service", method=RequestMethod.POST)
	public String nightupdate(Model model, InoutMiDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		dao.nightcommentUpdate(dto);
		
		return "redirect:/inout/inout-log.mi";
	} // end nightupdate
	
	@RequestMapping(value="/woupdate.service", method=RequestMethod.POST)
	public String workupdate(Model model, InoutWorkDto dto, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		dao.workcommentUpdate(dto);
		
		return "redirect:/inout/inout-log.mi";
	} // end nightupdate

	
}
