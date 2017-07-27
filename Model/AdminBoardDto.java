package com.template.model;
import java.util.Date;

/**
* <pre>
* com.template.model 
*    |_ AdminBoardDto.java
* </pre>
* @date : 2017. 5. 29. 오후 3:16:26
* @version : 
* @author : OMNILAB-A_zd
*/
public class AdminBoardDto {
	private int rid;
	private String emp_id;
	private String contents;
	private Date regdate;
	private Date cdate;
	private int state;
	private int admin_state;
	
	public AdminBoardDto(){}
	public AdminBoardDto(int rid, String emp_id, String contents, Date regdate, Date cdate, int state, int admin_state) {
		super();
		this.rid = rid;
		this.emp_id = emp_id;
		this.contents = contents;
		this.regdate = regdate;
		this.cdate = cdate;
		this.state = state;
		this.admin_state = admin_state;
	}
	
	
	
	public int getAdmin_state() {
		return admin_state;
	}
	public void setAdmin_state(int admin_state) {
		this.admin_state = admin_state;
	}
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
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
} // end class AdminBoardDto
