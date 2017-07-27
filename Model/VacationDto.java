package com.template.model;
import java.util.Date;

/**
* <pre>
* com.template.model 
*    |_ VacationDto.java
* </pre>
* @date : 2017. 5. 29. 오후 3:12:07
* @version : 
* @author : OMNILAB-A_zd
*/
public class VacationDto {
	private int rid;
	private String emp_id;
	private String vac_id;
	private String vac_start;
	private String vac_end;
//	private int vac_cnt;
	private float vac_cnt;
	private String vac_comment;
	private Date regdate;
	private int state;
	
	private int o_rid;
	
// 	private CodeDto code;
	private String code_name;
	private String comment;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	
	private String name;
	private String sub_name;
	
	public VacationDto(){}
	public VacationDto(int rid, String emp_id, String vac_id, String vac_start, String vac_end, float vac_cnt,
			String vac_comment, Date regdate, int state, String code_name, String comment, String attr1,
			String attr2, String attr3, String attr4) {
		super();
		this.rid = rid;
		this.emp_id = emp_id;
		this.vac_id = vac_id;
		this.vac_start = vac_start;
		this.vac_end = vac_end;
		this.vac_cnt = vac_cnt;
		this.vac_comment = vac_comment;
		this.regdate = regdate;
		this.state = state;
		this.code_name = code_name;
		this.comment = comment;
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		this.attr4 = attr4;
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
	public String getVac_id() {
		return vac_id;
	}
	public void setVac_id(String vac_id) {
		this.vac_id = vac_id;
	}
	public String getVac_start() {
		return vac_start;
	}
	public void setVac_start(String vac_start) {
		this.vac_start = vac_start;
	}
	public String getVac_end() {
		return vac_end;
	}
	public void setVac_end(String vac_end) {
		this.vac_end = vac_end;
	}
	public float getVac_cnt() {
		return vac_cnt;
	}
	public void setVac_cnt(float vac_cnt) {
		this.vac_cnt = vac_cnt;
	}
	public String getVac_comment() {
		if(vac_comment == null) {
			return "";
		} else {
			return vac_comment;
		}
	}
	public void setVac_comment(String vac_comment) {
		this.vac_comment = vac_comment;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public String getAttr3() {
		return attr3;
	}
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	public String getAttr4() {
		return attr4;
	}
	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	};
	
} // end class VacationDto
