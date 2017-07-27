package com.template.model;
import java.util.Date;

/**
* <pre>
* com.template.model 
*    |_ FingerPrintDto.java
* </pre>
* @date : 2017. 5. 29. 오후 3:24:27
* @version : 
* @author : OMNILAB-A_zd
*/
public class FingerPrintDto {
	private int rid;
	private String emp_id;
	private String id;
	private String id_type;
	private Date regdate;
	
	public FingerPrintDto(){}
	public FingerPrintDto(int rid, String emp_id, String id, String id_type, Date regdate) {
		super();
		this.rid = rid;
		this.emp_id = emp_id;
		this.id = id;
		this.id_type = id_type;
		this.regdate = regdate;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId_type() {
		return id_type;
	}
	public void setId_type(String id_type) {
		this.id_type = id_type;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

} // end class FingerPrintDto