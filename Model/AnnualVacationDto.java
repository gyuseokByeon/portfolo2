package com.template.model;
import java.util.Date;

/**
* <pre>
* com.template.model 
*    |_ AnnualVacationDto.java
* </pre>
* @date : 2017. 5. 29. 오후 3:18:24
* @version : 
* @author : OMNILAB-A_zd
*/
public class AnnualVacationDto {
	private int rid;
	private String emp_id;
	private String year;
	private String vac_id;
	private float total_cnt;
	private float rest_cnt;
	private float use_cnt;
	private Date regdate;
	
	private String name;
	private String sub_name;
	
	public AnnualVacationDto(){}
	public AnnualVacationDto(int rid, String emp_id, String year, String vac_id, float total_cnt, float rest_cnt,
			float use_cnt, Date regdate) {
		super();
		this.rid = rid;
		this.emp_id = emp_id;
		this.year = year;
		this.vac_id = vac_id;
		this.total_cnt = total_cnt;
		this.rest_cnt = rest_cnt;
		this.use_cnt = use_cnt;
		this.regdate = regdate;
	}
	
	// GET/SET
	
	public int getRid() {
		return rid;
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
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getVac_id() {
		return vac_id;
	}
	public void setVac_id(String vac_id) {
		this.vac_id = vac_id;
	}
	public float getTotal_cnt() {
		return total_cnt;
	}
	public void setTotal_cnt(float total_cnt) {
		this.total_cnt = total_cnt;
	}
	public float getRest_cnt() {
		return rest_cnt;
	}
	public void setRest_cnt(float rest_cnt) {
		this.rest_cnt = rest_cnt;
	}
	public float getUse_cnt() {
		return use_cnt;
	}
	public void setUse_cnt(float use_cnt) {
		this.use_cnt = use_cnt;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

} // end class AnnualVacationDto
