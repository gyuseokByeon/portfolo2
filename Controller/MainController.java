package com.template.controller;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.template.common.AES256;
import com.template.common.CommonUtils;
import com.template.model.EmployeeDto;
import com.template.model.LoginLogDto;
import com.template.repository.EmployeeDao;

/**
* <pre>
* com.template.controller 
*    |_ MainController.java
* </pre>
* @date : 2017. 5. 29. 오후 3:39:17
* @version : 
* @author : OMNILAB-A_zd
*/

@Controller
public class MainController {
private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	@Qualifier("EmployeeDaoImpl")
	private EmployeeDao empDao;
	
	
	@RequestMapping(value={"/", "/index.service"}, method={RequestMethod.GET, RequestMethod.POST})
	public String checkCokie(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		Cookie[] cookies = req.getCookies();
		if(!CommonUtils.empty(cookies)) {
			for(Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				
				if(cookieName.equals("REMEMBER_ME")) {
					// 쿠키에서 값 받아와서 강제 로그인
					try {
						String value = AES256.AES_Decode(cookie.getValue(), "milvussecurityrm");
						String[] values = value.split(",");
						
						List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
						if(values[2].equals("ROLE_USER")) {
							roles.add(new SimpleGrantedAuthority("ROLE_USER"));
							session.setAttribute("LOGIN_LV", "0");
						} else if(values[2].equals("ROLE_ADMIN")) {
							roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
							session.setAttribute("LOGIN_LV", "1");
						}
						
						UsernamePasswordAuthenticationToken rememberUser = new UsernamePasswordAuthenticationToken(values[0], values[1], roles);
						SecurityContext securityContext = SecurityContextHolder.getContext();
						securityContext.setAuthentication(rememberUser);
						
						session.setAttribute("LOGIN_ID", values[0]);
						
						return "redirect:/inout/inout-log.mi";
						
					} catch (Exception e) {
						logger.error("복호화 실패", e);
					}
					
					return "index";
				}
			}
			return "index";
		} else {
			return "index";
		}
	}
	
	@RequestMapping(value="login", method={RequestMethod.GET, RequestMethod.POST})
	public String login(Model model, HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		SecurityContext value=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authentication = value.getAuthentication();
		
		LoginLogDto logDto = (LoginLogDto) authentication.getDetails();
		session.setAttribute("LOGIN_ID", logDto.getEmp_id());
		session.setAttribute("LOGIN_IP", logDto.getIp());
		
		EmployeeDto empDto = empDao.selectOneEmp(logDto.getEmp_id());
		session.setAttribute("LOGIN_LV", empDto.getLevel());
		
		// remember_me 체크박스 확인 : 자동로그인 쿠키 생성
		String rememberYn = logDto.getIto1();
		if(rememberYn.equals("rememberMe")) {
			String cookieBase = logDto.getEmp_id() + "," + empDto.getPw();
			String cookieValue = "";
			for(GrantedAuthority auth : authentication.getAuthorities()) {
				cookieBase += "," + auth.getAuthority();
			}
			
			try {
				cookieValue = AES256.AES_Encode(cookieBase, "milvussecurityrm");
			} catch (Exception e) {
				logger.error("쿠키값 생성 실패", e);
			}
			Cookie cookie = new Cookie("REMEMBER_ME", cookieValue);
			cookie.setMaxAge(2419200);
			rep.addCookie(cookie);
			
		}
		return "redirect:/inout/inout-log.mi";
	} // end login
	
	
	@RequestMapping(value="/logout.service")
	public String logout(HttpServletRequest req, HttpServletResponse rep, HttpSession session) {
		session.invalidate();

		Cookie[] cookies = req.getCookies();
		if(!CommonUtils.empty(cookies)) {
			for(Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if(cookieName.equals("REMEMBER_ME")) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					rep.addCookie(cookie);
				}
			}
		}
		return "redirect:/";
	} // end logout

	
} // end MainController