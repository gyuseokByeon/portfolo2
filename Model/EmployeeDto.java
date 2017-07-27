package com.template.model;

import java.util.Date;

/**
* <pre>
* com.template.model 
*    |_ EmployeeDto.java
* </pre>
* @date : 2017. 5. 29. 오후 2:45:40
* @version : 
* @author : OMNILAB-A_zd
*/
public class EmployeeDto {
	
	private int rid;
	private String emp_id;
	private String pw;
	private String name;
	private String sub_name;
	private String email;
	private String h_phone;
	private String o_phone;
	private String dept_id;
	private String position;
	private int level;
	private int state;
	private Date join_date;
	private Date resignation_date;
	
	public EmployeeDto(){}
	public EmployeeDto(int rid, String emp_id, String pw, String name, String sub_name, String email, String h_phone,
			String o_phone, String dept_id, String position, int level, int state) {
		super();
		this.rid = rid;
		this.emp_id = emp_id;
		this.pw = pw;
		this.name = name;
		this.sub_name = sub_name;
		this.email = email;
		this.h_phone = h_phone;
		this.o_phone = o_phone;
		this.dept_id = dept_id;
		this.position = position;
		this.level = level;
		this.state = state;
	}
	
	// GET/SET
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getH_phone() {
		return h_phone;
	}
	public void setH_phone(String h_phone) {
		this.h_phone = h_phone;
	}
	public String getO_phone() {
		return o_phone;
	}
	public void setO_phone(String o_phone) {
		this.o_phone = o_phone;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getJoin_date() {
		return join_date;
	}
	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}
	public Date getResignation_date() {
		return resignation_date;
	}
	public void setResignation_date(Date resignation_date) {
		this.resignation_date = resignation_date;
	}
	
	

}
