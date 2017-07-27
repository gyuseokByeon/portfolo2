package com.template.service.Impl;
import static org.mockito.Matchers.contains;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.template.common.CommonUtils;
import com.template.model.EmployeeDto;
import com.template.model.InoutMiDto;
import com.template.model.InoutWorkDto;
import com.template.repository.EmployeeDao;
import com.template.repository.InoutDao;
import com.template.repository.Impl.EmployeeDaoImpl;
import com.template.service.InoutService;
import com.template.service.SlackService;
/**
* <pre>
* com.template.service.Impl 
*    |_ InoutServiceImpl.java
* </pre>
* @date : 2017. 5. 29. 오후 7:21:10
* @version : 
* @author : OMNILAB-A
*/
@Service("InoutServiceImpl")
public class InoutServiceImpl implements InoutService {

	private static final Logger logger = LoggerFactory.getLogger(InoutServiceImpl.class);
	
	@Autowired
	@Qualifier("InoutDaoImpl")
	private InoutDao dao;
	
	@Autowired
	@Qualifier("EmployeeDaoImpl")
	private EmployeeDao empDao;
	
	
	/* (non-Javadoc)
	 * @see com.template.service.InoutService#insertInout(com.template.model.InoutMiDto, java.lang.String)
	 */
	@Override // 근태 등록 Service
	public String insertInout(InoutMiDto dto, String btn_ty) {
		String result = "";
		EmployeeDto empDto = empDao.selectOneEmp(dto.getEmp_id());
		SlackService slack = new SlackService();

		switch(btn_ty) {
		case "miin":
			// 중복 출근 여부 검사
			if (dao.selectCntInout(dto) > 0) { // 중복 출근
				result = "이미 출근 기록이 있습니다";
			} else { // 정상 출근
				String real_day = dto.getReal_day();
				Date date_realday = CommonUtils.StringToDate2(real_day);
				Date date_miin = CommonUtils.StringToDate1(dto.getMi_in());
				
				Calendar calRealday = Calendar.getInstance();
				calRealday.setTime(date_realday);
				Calendar yst_realday = calRealday;
				yst_realday.add(Calendar.DATE, -1);

				InoutMiDto preDayInoutMiDto = new InoutMiDto();
				preDayInoutMiDto.setReal_day(CommonUtils.dateToString2(yst_realday.getTime()));
				preDayInoutMiDto.setEmp_id(dto.getEmp_id());
				
				// 전일 퇴근/복귀 등록 안한 내역 조회 후 등록
				List<Integer> ridList = dao.noneRegMiInout(real_day, dto.getEmp_id());
				if(ridList.size() > 0) {
					for(int i = 0; i<ridList.size(); i++) {
						InoutMiDto tempDto = dao.selectRidMiInout(ridList.get(i));
						
						String tempRealday = tempDto.getReal_day();
						Date tempDate = CommonUtils.StringToDate2(tempRealday);
						Calendar tempCal = Calendar.getInstance();
						tempCal.setTime(tempDate);
						
						tempCal.set(Calendar.HOUR_OF_DAY, 18);
						tempCal.set(Calendar.MINUTE, 0);
						tempCal.set(Calendar.SECOND, 0);
						
						String tempCalStr = CommonUtils.dateToString1(tempCal.getTime());
						tempDto.setMi_out(tempCalStr);
						dao.updateMiout(tempDto);
					}
				}
				
				List<Integer> ridList2 = dao.noneRegWorkInout(real_day, dto.getEmp_id());
				if(ridList2.size() > 0) {
					for(int i = 0; i<ridList2.size(); i++) {
						InoutWorkDto tempWorkDto = dao.selectRidWorkInout(ridList2.get(i));
						String tempRealday = tempWorkDto.getReal_day();
						Date tempDate = CommonUtils.StringToDate2(tempRealday);
						Calendar tempCal = Calendar.getInstance();
						tempCal.setTime(tempDate);
						
						tempCal.set(Calendar.HOUR_OF_DAY, 18);
						tempCal.set(Calendar.MINUTE, 0);
						tempCal.set(Calendar.SECOND, 0);
						
						String tempCalStr = CommonUtils.dateToString1(tempCal.getTime());
						tempWorkDto.setWork_in(tempCalStr);
						tempWorkDto.setWork_state(1);
						dao.updateWorkout(tempWorkDto);
					}
				}
				

				if (dao.selectCntInout(preDayInoutMiDto) > 0) { // 전일 출근일 존재
					InoutMiDto tmpDto1 = dao.selectInout(preDayInoutMiDto); // 전일 출근내역
					
					Calendar bf_outcal = Calendar.getInstance();
					bf_outcal.setTime(CommonUtils.StringToDate1(tmpDto1.getMi_out()));

					if (bf_outcal.get(Calendar.HOUR_OF_DAY) >= 0 && bf_outcal.get(Calendar.HOUR_OF_DAY) <= 5) {
						Calendar lateBase = Calendar.getInstance();
						lateBase.setTime(date_realday);
						lateBase.set(Calendar.HOUR_OF_DAY, 9 + bf_outcal.get(Calendar.HOUR_OF_DAY));
						lateBase.set(Calendar.MINUTE, 0 + bf_outcal.get(Calendar.MINUTE));
						lateBase.set(Calendar.SECOND, 0 + bf_outcal.get(Calendar.SECOND));

						if (date_miin.after(lateBase.getTime())) {
							dto.setLate_yn(1);
						} else {
							dto.setLate_yn(0);
						}
					} else {
						Calendar lateBase = Calendar.getInstance();
						lateBase.setTime(date_realday);
						lateBase.set(Calendar.HOUR_OF_DAY, 9);
						lateBase.set(Calendar.MINUTE, 0);
						lateBase.set(Calendar.SECOND, 0);

						if (date_miin.after(lateBase.getTime())) {
							dto.setLate_yn(1);
						} else {
							dto.setLate_yn(0);
						}
					}
				} else { // 전일 출근일 X
					Calendar lateBase = Calendar.getInstance();
					lateBase.setTime(date_realday);
					lateBase.set(Calendar.HOUR_OF_DAY, 9);
					lateBase.set(Calendar.MINUTE, 0);
					lateBase.set(Calendar.SECOND, 0);

					if (date_miin.after(lateBase.getTime())) { // 지각
						dto.setLate_yn(1);
					} else {
						dto.setLate_yn(0);
					}
				}

				int insertre = dao.insertMiin(dto);
				if (insertre == 1) {
					slack.sendToSlackInOut(empDto.getName(), dto.getMi_in(), "출근 하셨습니다.");
					
					result = "출근 등록 성공";
				} else {
					result = "출근 등록에 실패했습니다. 다시 한번 시도해주세요 [관리자에게 문의 부탁드립니다.]";
				}
			}
			
			break;
		case "mijik":
			if(dao.selectCntInout(dto) == 0) {
				
				// 전일 퇴근/복귀 등록 안한 내역 조회 후 등록
				List<Integer> ridList = dao.noneRegMiInout(dto.getReal_day(), dto.getEmp_id());
				if(ridList.size() > 0) {
					for(int i = 0; i<ridList.size(); i++) {
						InoutMiDto tempDto = dao.selectRidMiInout(ridList.get(i));
						
						String tempRealday = tempDto.getReal_day();
						Date tempDate = CommonUtils.StringToDate2(tempRealday);
						Calendar tempCal = Calendar.getInstance();
						tempCal.setTime(tempDate);
						
						tempCal.set(Calendar.HOUR_OF_DAY, 18);
						tempCal.set(Calendar.MINUTE, 0);
						tempCal.set(Calendar.SECOND, 0);
						
						String tempCalStr = CommonUtils.dateToString1(tempCal.getTime());
						tempDto.setMi_out(tempCalStr);
						dao.updateMiout(tempDto);
					}
				}
				
				List<Integer> ridList2 = dao.noneRegWorkInout(dto.getReal_day(), dto.getEmp_id());
				if(ridList2.size() > 0) {
					for(int i = 0; i<ridList2.size(); i++) {
						InoutWorkDto tempWorkDto = dao.selectRidWorkInout(ridList2.get(i));
						String tempRealday = tempWorkDto.getReal_day();
						Date tempDate = CommonUtils.StringToDate2(tempRealday);
						Calendar tempCal = Calendar.getInstance();
						tempCal.setTime(tempDate);
						
						tempCal.set(Calendar.HOUR_OF_DAY, 18);
						tempCal.set(Calendar.MINUTE, 0);
						tempCal.set(Calendar.SECOND, 0);
						
						String tempCalStr = CommonUtils.dateToString1(tempCal.getTime());
						tempWorkDto.setWork_in(tempCalStr);
						tempWorkDto.setWork_state(1);
						dao.updateWorkout(tempWorkDto);
					}
				}
				
				dto.setJik_yn(1);
				int daors = dao.insertMiin(dto);
				if(daors == 1) {
					slack.sendToSlackInOut(empDto.getName(), dto.getMi_in()+" [ "+dto.getJik_comment()+" ]" , "직출 하셨습니다.");
					result = "출근 등록 성공";
				} else {
					result="출근 등록에 실패했습니다. 다시 한번 시도해주세요 [관리자에게 문의 부탁드립니다.]";
				}
				
			} else {
				result = "이미 출근 기록이 있습니다.";
			}
			
			break;
		case "miout":
			if(dao.selectCntInout(dto)==1){
				if(CommonUtils.empty(dao.selectInout(dto).getMi_out())) {
					InoutWorkDto wDto = new InoutWorkDto();
					wDto.setEmp_id(dto.getEmp_id());
					wDto.setReal_day(dto.getReal_day());
					wDto.setWork_state(0);
					int wCnt = dao.cntWorkInout(wDto);
					
					if(wCnt > 0) {
						List<InoutWorkDto> tmpWDtoList = dao.selectListWorkinout(wDto);
						if(tmpWDtoList.size() == 1) {
							int tmpRid = tmpWDtoList.get(0).getRid();
							wDto.setRid(tmpRid);
							wDto.setWork_state(1);
							wDto.setWork_in(dto.getMi_out());
							dao.updateWorkout(wDto);
						}
					}
					
					int updaters = dao.updateMiout(dto);
					if(updaters == 1) {
						if(CommonUtils.empty(dto.getNight_comment())) {
							slack.sendToSlackInOut(empDto.getName(), dto.getMi_out(), "퇴근 하셨습니다.");
						} else {
							slack.sendToSlackInOut(empDto.getName(), dto.getMi_out() + " [ "+dto.getNight_comment() + " ]", "퇴근 하셨습니다.");
						}
						
						result = "퇴근 등록 성공";
					} else {
						result = "퇴근 등록에 실패했습니다. 다시 한번 시도해주세요 [관리자에게 문의 부탁드립니다.]";
					}
				} else {
					result = "이미 퇴근 기록이 있습니다.";
				}
				
			} else {
				result = "오늘 출근한 기록이 없습니다. 출근 등록을 먼저 해주세요.";
			}
			break;
		} // end swtich
		
		
		return result;
	} // end insertInout
	
	
	/* (non-Javadoc)
	 * @see com.template.service.InoutService#workinsertInout(com.template.model.InoutWorkDto, java.lang.String)
	 */
	@Override
	public String workinsertInout(InoutWorkDto dto, String btn_ty) {
		String result = "";
		int tmp_cnt;
		
		SlackService slack = new SlackService();
		EmployeeDto empDto = empDao.selectOneEmp(dto.getEmp_id());
		
		InoutMiDto sdto = new InoutMiDto();
		sdto.setEmp_id(dto.getEmp_id());
		sdto.setReal_day(dto.getReal_day());

		switch(btn_ty) {
		case "workout":
			logger.info("#service: 외근");
			dto.setWork_state(0);
			tmp_cnt = dao.cntWorkInout(dto);

			if(dao.selectCntInout(sdto) < 1 || dao.selectInout(sdto).getInout_state() !=0) {
				result = "근태 내역을 먼저 확인해주세요 [출근 기록이 없거나 이미 퇴근하신 경우 외근 등록이 불가능 합니다]"; 
			} else if(tmp_cnt==0) { 
				dto.setWork_state(0);
				int wOutReslut = dao.insertWorkin(dto);
				
				if(wOutReslut == 1) {
					slack.sendToSlackInOut(empDto.getName(), dto.getWork_out() + " [ "+dto.getWork_comment() + " ]", "외근 하셨습니다");
					result = "외근 등록 성공";
				} else {
					result  = "외근 등록에 실패했습니다. 다시 한번 시도해주세요 [관리자에게 문의 부탁드립니다.]";
				}
			} else {
				result = "복귀 등록을 하지 않은 외근 내역이 존재합니다. 복귀 먼저 해주세요";
			}
			break;
			
		case "workin":
			logger.info("#service: 복귀");
			
			dto.setWork_state(0);
			List<InoutWorkDto> tmpDto = dao.selectListWorkinout(dto);
			if(tmpDto.size() == 1) {
				int rid = tmpDto.get(0).getRid();
				dto.setWork_state(1);
				dto.setRid(rid);
				
				int wInResult = dao.updateWorkout(dto);
				if(wInResult == 1) {
					slack.sendToSlackInOut(empDto.getName(), dto.getWork_in(), "복귀 하셨습니다.");
					result  = "복귀 등록 성공";
				} else {
					result = "복귀 등록에 실패했습니다. 다시 한번 시도해주세요 [관리자에게 문의 부탁드립니다.]";
				}
			} else {
				result = "등록할 외근 내역이 없습니다."; // 복귀 등록할 외근내역이 없음
			}
		
			break;
		} //end switch
		
		
		return result;
	} // end workinsertInout
	
	@Override // real_day 기준 근태 내역 조회 
	public List<InoutMiDto> selectInout(InoutMiDto dto) {
		List<InoutMiDto> dto_list = dao.selectListInout(dto);
		return dto_list;
	} // end selectInout 
	
	/* (non-Javadoc)
	 * @see com.template.service.InoutService#selectListWork(java.lang.String)
	 */
	@Override
	public List<InoutWorkDto> selectListWork(String real_day) {
		List<InoutWorkDto> dto = dao.worklist(real_day);
		return dto;
	} // end selectListWork
	
	

}
