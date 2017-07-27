package com.template.model;

/**
* <pre>
* com.template.model 
*    |_ InoutMiDto.java
* </pre>
* @date : 2017. 5. 29. 오후 3:01:51
* @version : 
* @author : OMNILAB-A_zd
*/
public class InoutMiDto {
	private int rid;
	private String emp_id;
	private String real_day;
	private String mi_in;
	private String mi_out;
	private int late_yn;
	private int jik_yn;
	private String jik_comment;
	private String night_comment;
	private int inout_state;
	
	private String name;
	private String btn_type;
	private String sub_name;
	private int emp_state;
	
	private int p_start;
	private int p_cnt;
	
	public InoutMiDto(){}
	public InoutMiDto(int rid, String emp_id, String real_day, String mi_in, String mi_out, int late_yn, int jik_yn,
			String jik_comment, String night_comment, String name, String btn_type, int inout_state, String sub_name,
			int p_start, int p_cnt) {
		super();
		this.rid = rid;
		this.emp_id = emp_id;
		this.real_day = real_day;
		this.mi_in = mi_in;
		this.mi_out = mi_out;
		this.late_yn = late_yn;
		this.jik_yn = jik_yn;
		this.jik_comment = jik_comment;
		this.night_comment = night_comment;
		this.name = name;
		this.btn_type = btn_type;
		this.inout_state = inout_state;
		this.sub_name = sub_name;
		this.p_start = p_start;
		this.p_cnt = p_cnt;
	}
	
	// GET/SET
	
	
	public int getRid() {
		return rid;
	}
	public int getEmp_state() {
		return emp_state;
	}
	public void setEmp_state(int emp_state) {
		this.emp_state = emp_state;
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
	
	public String getMi_in() {
		if (mi_in == null) {
			return "";
		} else {
			return mi_in;
		}
	}
	public void setMi_in(String mi_in) {
		this.mi_in = mi_in;
	}
	
	public String getMi_out() {
		if(mi_out==null) {
			return "";
		} else {
			return mi_out;
		}
	}
	public void setMi_out(String mi_out) {
		this.mi_out = mi_out;
	}
	
	public int getLate_yn() {
		return late_yn;
	}
	public void setLate_yn(int late_yn) {
		this.late_yn = late_yn;
	}
	public int getJik_yn() {
		return jik_yn;
	}
	public void setJik_yn(int jik_yn) {
		this.jik_yn = jik_yn;
	}
	
	public String getJik_comment() {
		if(jik_comment == null) {
			return "";
		} else {
			return jik_comment;
		}
	}
	public void setJik_comment(String jik_comment) {
		this.jik_comment = jik_comment;
	}
	
	public String getNight_comment() {
		if(night_comment==null) {
			return "";
		} else {
			return night_comment;
		}
	}
	public void setNight_comment(String night_comment) {
		this.night_comment = night_comment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBtn_type() {
		return btn_type;
	}
	public void setBtn_type(String btn_type) {
		this.btn_type = btn_type;
	}
	public int getInout_state() {
		return inout_state;
	}
	public void setInout_state(int inout_state) {
		this.inout_state = inout_state;
	}
	public String getSub_name() {
		if(sub_name == null) {
			return "";
		} else {
		return sub_name;
		}
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public int getP_start() {
		return p_start;
	}
	public void setP_start(int p_start) {
		this.p_start = p_start;
	}
	public int getP_cnt() {
		return p_cnt;
	}
	public void setP_cnt(int p_cnt) {
		this.p_cnt = p_cnt;
	}
	
	
	
} // end InoutMitDto
