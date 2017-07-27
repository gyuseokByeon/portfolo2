package com.template.model;
/**
* <pre>
* com.template.model 
*    |_ CodeDto.java
* </pre>
* @date : 2017. 5. 29. 오후 3:20:56
* @version : 
* @author : OMNILAB-A_zd
* 
* CODE_LIST , VAR_LIST
*/
public class CodeDto {
	private int rid;
	private String code_id; // 시스템 코드
	private String code_type;
	private String code_name;
	private String comment;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	private String var_id; // 시스템 변수
	private String var_name;
	private String var_value;
	private String var_type;
	
	public CodeDto(){}
	public CodeDto(int rid, String code_id, String code_type, String code_name, String comment, String attr1,
			String attr2, String attr3, String attr4, String var_id, String var_name, String var_value,
			String var_type) {
		super();
		this.rid = rid;
		this.code_id = code_id;
		this.code_type = code_type;
		this.code_name = code_name;
		this.comment = comment;
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		this.attr4 = attr4;
		this.var_id = var_id;
		this.var_name = var_name;
		this.var_value = var_value;
		this.var_type = var_type;
	}
	
	// GET/SET
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	public String getCode_type() {
		return code_type;
	}
	public void setCode_type(String code_type) {
		this.code_type = code_type;
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
	}
	public String getVar_id() {
		return var_id;
	}
	public void setVar_id(String var_id) {
		this.var_id = var_id;
	}
	public String getVar_name() {
		return var_name;
	}
	public void setVar_name(String var_name) {
		this.var_name = var_name;
	}
	public String getVar_value() {
		return var_value;
	}
	public void setVar_value(String var_value) {
		this.var_value = var_value;
	}
	public String getVar_type() {
		return var_type;
	}
	public void setVar_type(String var_type) {
		this.var_type = var_type;
	}
	
} // end CodeDto
