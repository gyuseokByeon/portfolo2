/**
 * 
 */
package com.template.service.Impl;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.template.common.CommonUtils;
import com.template.model.CodeDto;
import com.template.model.EmployeeDto;
import com.template.model.VacationDto;
import com.template.repository.EmployeeDao;
import com.template.repository.VacationDao;
import com.template.repository.Impl.EmployeeDaoImpl;
import com.template.service.SlackService;
import com.template.service.VacationService;
/**
* <pre>
* com.template.service.Impl 
*    |_ VacationServiceImpl.java
* </pre>
* @date : 2017. 6. 5. 오후 1:26:15
* @version : 
* @author : OMNILAB-A
*/
/**
 * @author OMNILAB-A
 *
 */
@Service("VacationServiceImpl")
public class VacationServiceImpl implements VacationService {

	private static final Logger logger = LoggerFactory.getLogger(VacationServiceImpl.class);
	
	@Autowired
	@Qualifier("VacationDaoImpl")
	private VacationDao dao;
	
	@Autowired
	@Qualifier("EmployeeDaoImpl")
	private EmployeeDao empDao;
	
	/* (non-Javadoc)
	 * @see com.template.service.VacationService#codeList(java.lang.String)
	 */
	@Override
	public List<CodeDto> codeList(String code_type) {
		return dao.getCodeList(code_type);
	} // end codeList


	/* (non-Javadoc)
	 * @see com.template.service.VacationService#vacationList(java.lang.String)
	 */
	@Override
	public List<VacationDto> vacationList(String emp_id) {
		return dao.selectListVacation(emp_id);
	}


	/* (non-Javadoc)
	 * @see com.template.service.VacationService#insertVac(com.template.model.VacationDto)
	 */
	@Override // 휴가 등록
	public String insertVac(VacationDto dto) {
		dto.setState(1);
		
		EmployeeDto empDto = empDao.selectOneEmp(dto.getEmp_id());
		SlackService slack = new SlackService();
		
		CodeDto codeDto = dao.getCodeOne(dto.getVac_id());
		// ATTR1 = 연차 유무 / ATTR2 = 휴가 사용일
		if(codeDto.getAttr1().equals("1")) { // 연차 사용 휴가
			float cnt = dto.getVac_cnt();
			Calendar cal = Calendar.getInstance();
			int y = cal.get(Calendar.YEAR);
			String year = String.valueOf(y);
			float restcnt = dao.selectRestVac(dto.getEmp_id(), year);
			
			if(cnt > restcnt) {
				return "error1"; // 사용 가능한 연차 회수 초과
			} else {
				int annupresult = dao.updateVacAnn(dto.getEmp_id(), year, cnt); // 연차 사용횟수 업데이트
				if(annupresult == 1) {
					 int insresult = dao.insertVacation(dto);
					 if(insresult == 1) {
						 slack.sendToSlackInOut(empDto.getName(), dto.getVac_start() + " ~ " + dto.getVac_end() + " [ "+dto.getVac_comment() + " ]", codeDto.getCode_name() + "를 등록 하셨습니다.");
						 return "vac_insert_s";
					 } else {
						 return "vac_insert_f";
					 }
				} else {
					return "error2"; // 연차 사용회수 없데이트 실패
				}
			}
			
		} else { // 연차 사용x 휴가
			int result = dao.insertVacation(dto);
			if(result == 1) {
				slack.sendToSlackInOut(empDto.getName(), dto.getVac_start() + " ~ " + dto.getVac_end() + " [ "+dto.getVac_comment() + " ]", codeDto.getCode_name() + "를 등록 하셨습니다.");
				return "vac_insert_s";
			} else {
				 return "vac_insert_f";
			}
		}

	} // end insertVac
	
	
	/* (non-Javadoc)
	 * @see com.template.service.VacationService#cancelVac(int)
	 */
	@Override
	public int cancelVac(int rid) { // 1성공 0실패
		VacationDto vacDto = dao.selectOneVac(rid);
		SlackService slack = new SlackService();
		EmployeeDto empDto = empDao.selectOneEmp(vacDto.getEmp_id());
		
		if((dao.getCodeOne(vacDto.getVac_id()).getAttr1()).equals("1")) { // 연차 사용 휴가
			Date start_date = CommonUtils.StringToDate2(vacDto.getVac_start());
			Calendar cal = Calendar.getInstance();
			cal.setTime(start_date);
			int y = cal.get(Calendar.YEAR);
			String year = String.valueOf(y);
			
			int resultUpdateAnnCnt = dao.updateVacAnn(vacDto.getEmp_id(), year, vacDto.getVac_cnt()*(-1)); // 연차 사용회수 수정
			if(resultUpdateAnnCnt == 1) {
				int resultUpdateState = dao.updateVcState(rid);
				if(resultUpdateState == 1) {
					slack.sendToSlackInOut(empDto.getName(), vacDto.getVac_start() + " ~ " + vacDto.getVac_end(), dao.getCodeOne(vacDto.getVac_id()).getCode_name() + " 등록을 취소하셨습니다.");
					return 1;
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		} else { // 연차 사용 휴가 아님
			int result = dao.updateVcState(rid);
			if(result == 1) {
				slack.sendToSlackInOut(empDto.getName(), vacDto.getVac_start() + " ~ " + vacDto.getVac_end(), dao.getCodeOne(vacDto.getVac_id()).getCode_name() + " 등록을 취소하셨습니다.");
				return 1;
			} else {
				return 0;
			}
		}
	} // end cancelVac()
	
}
