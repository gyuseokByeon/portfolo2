/**
 * 
 */
package com.template.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
* <pre>
* com.template.model 
*    |_ InoutSearchDto.java
* </pre>
* @date : 2017. 6. 12. 오후 6:09:05
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
public class PageSearchDto {
	private String searchType;
	private String day1;
	private String day2;
	private String keyword;
	
	private String month;
	
	private int firstIndex;
	private int recordCountPerPage;
	
	public PageSearchDto() {}
	public PageSearchDto(String searchType, String day1, String day2, String keyword, int start, int end, String month) {
		super();
		this.searchType = searchType;
		this.day1 = day1;
		this.day2 = day2;
		this.keyword = keyword;
		this.month = month;
	}
	
	public String getMonth() {
		if(month == null) {
			return "";
		} else {
			return month;
		}
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getSearchType() {
		if(searchType==null) {
			return "";
		} else {
			return searchType;
		}
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getDay1() {
		if(day1==null) {
			return "";
		} else {
			return day1;
		}
	}
	public void setDay1(String day1) {
		this.day1 = day1;
	}
	public String getDay2() {
		if(day2==null) {
			return "";
		} else {
			return day2;
		}
	}
	public void setDay2(String day2) {
		this.day2 = day2;
	}
	public String getKeyword() {
		if(keyword==null) {
			return "";
		} else {
			return keyword;
		}
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	
	

}
