/**
 * 
 */
package com.template.model;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
* <pre>
* com.template.model 
*    |_ LoginLogDto.java
* </pre>
* @date : 2017. 6. 9. 오전 10:18:30
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
public class LoginLogDto {
	private int rid;
	private String emp_id;
	private Date login_dt;
	private int login_result;
	private String ip;
	private String ito1;
	private String ito2;
	private String ito3;
	
	private int level;
	
	public LoginLogDto() {}
	public LoginLogDto(int rid, String emp_id, Date login_dt, int login_result, String ip, String ito1, String ito2,
			String ito3, int level) {
		super();
		this.rid = rid;
		this.emp_id = emp_id;
		this.login_dt = login_dt;
		this.login_result = login_result;
		this.ip = ip;
		this.ito1 = ito1;
		this.ito2 = ito2;
		this.ito3 = ito3;
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
	public Date getLogin_dt() {
		return login_dt;
	}
	public void setLogin_dt(Date login_dt) {
		this.login_dt = login_dt;
	}
	public int getLogin_result() {
		return login_result;
	}
	public void setLogin_result(int login_result) {
		this.login_result = login_result;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIto1() {
		return ito1;
	}
	public void setIto1(String ito1) {
		this.ito1 = ito1;
	}
	public String getIto2() {
		return ito2;
	}
	public void setIto2(String ito2) {
		this.ito2 = ito2;
	}
	public String getIto3() {
		return ito3;
	}
	public void setIto3(String ito3) {
		this.ito3 = ito3;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLeve(int level) {
		this.level = level;
	}
}
