package com.template.model;

/**
* <pre>
* com.template.model 
*    |_ InoutWorkDto.java
* </pre>
* @date : 2017. 5. 29. 오후 3:09:49
* @version : 
* @author : OMNILAB-A_zd
*/
public class InoutWorkDto {
	private int rid;
	private String emp_id;
	private String real_day;
	private String work_out;
	private String work_in;
	private String work_comment;
	private int work_state; 
	
	public InoutWorkDto(){}
	public InoutWorkDto(int rid, String emp_id, String real_day, String work_out, String work_in, String work_comment, int work_state) {
		super();
		this.rid = rid;
		this.emp_id = emp_id;
		this.real_day = real_day;
		this.work_out = work_out;
		this.work_in = work_in;
		this.work_comment = work_comment;
		this.work_state = work_state;
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
	public String getReal_day() {
		return real_day;
	}
	public void setReal_day(String real_day) {
		this.real_day = real_day;
	}
	public String getWork_out() {
		if(work_out==null) {
			return "";
		} else {
			return work_out;
		}
	}
	public void setWork_out(String work_out) {
		this.work_out = work_out;
	}
	public String getWork_in() {
		if(work_in == null) {
			return "";
		} else {
			return work_in;
		}
	}
	public void setWork_in(String work_in) {
		this.work_in = work_in;
	}
	public String getWork_comment() {
		if(work_comment == null) {
			return "";
		} else {
			return work_comment;
		}
	}
	public void setWork_comment(String work_comment) {
		this.work_comment = work_comment;
	}
	public int getWork_state() {
		return work_state;
	}
	public void setWork_state(int work_state) {
		this.work_state = work_state;
	}
	
	
	
} // end class InoutWorkDto
