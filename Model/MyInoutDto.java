/**
 * 
 */
package com.template.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
* <pre>
* com.template.model 
*    |_ MyInoutDto.java
* </pre>
* @date : 2017. 6. 5. 오후 3:26:32
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
public class MyInoutDto {
	private String month;
	private int inout_count; // 총 근무일
	private int late_count; // 지각일
	private int work_count; // 외근수
	
	private String emp_id;
	private String name;
	private String sub_name;
	
	public MyInoutDto() {}
	public MyInoutDto(String month, int inout_count, int late_count, int work_count) {
		super();
		this.month = month;
		this.inout_count = inout_count;
		this.late_count = late_count;
		this.work_count = work_count;
	}
	
	
	
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
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
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getInout_count() {
		return inout_count;
	}
	public void setInout_count(int inout_count) {
		this.inout_count = inout_count;
	}
	public int getLate_count() {
		return late_count;
	}
	public void setLate_count(int late_count) {
		this.late_count = late_count;
	}
	public int getWork_count() {
		return work_count;
	}
	public void setWork_count(int work_count) {
		this.work_count = work_count;
	}

}
