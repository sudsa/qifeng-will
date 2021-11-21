package com.hanxiaozhang.planRemind.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.hanxiaozhang.planRemind.dao.PlanRemindDao;
import com.hanxiaozhang.planRemind.domain.PlanRemindDO;
import com.hanxiaozhang.planRemind.service.PlanRemindService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class PlanRemindServiceImpl implements PlanRemindService {

	@Resource
	private PlanRemindDao planRemindDao;

	@Autowired
	private JavaMailSender javaMailSender;

	// 发送者
	@Value("${mail.fromMail.sender}")
	private String sender;
	
	@Override
	public PlanRemindDO get(Long id){
		return planRemindDao.get(id);
	}
	
	@Override
	public List<PlanRemindDO> list(Map<String, Object> map){
		return planRemindDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return planRemindDao.count(map);
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
	public int save(PlanRemindDO planRemind){
        Long userId = 1L;
        Date now = new Date();
        planRemind.setFinishFlag(0)
				.setCreateBy(userId)
                .setCreateTime(now)
                .setUpdateBy(userId)
                .setUpdateTime(now)
                .setDelFlag("0");
		return planRemindDao.save(planRemind);
	}
	
	@Override
	public int update(PlanRemindDO planRemind){
        Long userId = 1L;
        Date now = new Date();
        planRemind.setRecentExecuteDateFlag(1)
				.setUpdateBy(userId)
                .setUpdateTime(now);
		return planRemindDao.update(planRemind);
	}
	
	@Override
	public int remove(Long id){
		return planRemindDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return planRemindDao.batchRemove(ids);
	}

	@Override
	public void changeExecuteStatus(Long id, Integer executeStatus) {
		planRemindDao.changeExecuteStatus(id,executeStatus);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public void handlePlanRemindJob(String logId) throws MessagingException {
        Map<String, Object> map=new HashMap<>();
        map.put("finishFlag",0);
        map.put("executeStatus",1);
        map.put("delFlag",0);
        List<PlanRemindDO> planRemindList = planRemindDao.list(map);
        if (planRemindList == null) {
            return;
        }
		try {
			for (PlanRemindDO planRemind : planRemindList) {
				Long id = planRemind.getId();
				Integer planType = planRemind.getPlanType();
				LocalDate startDate = planRemind.getStartDate();
				LocalDate endDate = planRemind.getEndDate();
				LocalDate executeDate = planRemind.getExecuteDate();
				LocalTime executeTime = planRemind.getExecuteTime();
				boolean sendFlag=false;
				LocalDateTime now = LocalDateTime.now();

				if (planType==0&&compareMinute(now, executeDate, executeTime)) {
					// 0：单次执行
					planRemindDao.changeFinishFlag(id,1);
					sendFlag=true;
				}else if(planType==1 && compareMinute(now, now.toLocalDate(), executeTime)){
					// 1：每日执行
					sendFlag=true;
				} else if(planType==2 && compareRangeAndUpdateFinishFlag(now, startDate,endDate,id)){
					// 2：周期执行
					sendFlag=handleCycleType(planRemind,now);
				}else if(planType==3){
					// 3：星期执行
					sendFlag=handleWeekType(planRemind,now);
				} else if(planType==4 && compareRangeAndUpdateFinishFlag(now, startDate,endDate,id)){
					// 4：周期星期执行
					sendFlag=handleWeekType(planRemind,now);
				}

                // 发邮件
				if (sendFlag && planRemind.getRemindType()==0) {
					String[] receivers = planRemind.getRemindValue().split(",");
					String topic = planRemind.getTopic();
					String content = planRemind.getContent();
					sendHtmlMail(receivers,topic,content,now);
				}


			}
		} catch (MessagingException e) {
			throw e;
		}
    }

	/**
	 * 处理周期执行
	 *
	 * @param planRemind
	 * @param now
	 * @return
	 */
	private boolean handleCycleType(PlanRemindDO planRemind,LocalDateTime now){

		LocalDateTime recentExecuteDate=planRemind.getRecentExecuteDate();
		if (recentExecuteDate == null) {
			recentExecuteDate=LocalDateTime.of(now.toLocalDate(), planRemind.getExecuteTime());
		}
		Long id = planRemind.getId();

		Integer cycleNumber = planRemind.getCycleNumber();
		Integer cycleUnit = planRemind.getCycleUnit();

		while (recentExecuteDate.isBefore(now)){
			if (cycleUnit==1) {
				// 1：分
				recentExecuteDate = recentExecuteDate.plusMinutes(cycleNumber);
			}else if(cycleUnit==2){
				// 2：时
				recentExecuteDate = recentExecuteDate.plusHours(cycleNumber);
			}else if(cycleUnit==3){
				// 3：天
				recentExecuteDate = recentExecuteDate.plusDays(cycleNumber);
			}else if(cycleUnit==4){
				// 4：月
				recentExecuteDate = recentExecuteDate.plusMonths(cycleNumber);
			}else if(cycleUnit==5){
				// 5：年
				recentExecuteDate = recentExecuteDate.plusYears(cycleNumber);
			}
		}

		LocalDate endDate = planRemind.getEndDate();

		// 周期日期大于结束日期
		if (endDate!=null && endDate.isBefore(recentExecuteDate.toLocalDate())) {
			planRemindDao.changeFinishFlag(id,1);
			return false;
		}

		LocalTime localTime = now.toLocalTime();
		LocalDate localDate = now.toLocalDate();
		LocalTime executeTime = recentExecuteDate.toLocalTime();
		LocalDate executeDate = recentExecuteDate.toLocalDate();
		if (localDate.equals(executeDate) &&
				localTime.getHour()==executeTime.getHour() &&
				localTime.getMinute()==executeTime.getMinute()) {
			planRemindDao.updateRecentExecuteDate(id,recentExecuteDate);
			return true;
		}
		return false;
	}


	/**
	 * 处理星期执行
	 *
	 * @param planRemind
	 * @param now
	 * @return
	 */
    private boolean handleWeekType(PlanRemindDO planRemind,LocalDateTime now){
		int weekIndex = now.getDayOfWeek().getValue();
		boolean flag=false;
		if (planRemind.getMondayFlag()==weekIndex) {
			flag=true;
		}
		if (planRemind.getTuesdayFlag()==weekIndex) {
			flag=true;
		}
		if (planRemind.getWednesdayFlag()==weekIndex) {
			flag=true;
		}
		if (planRemind.getThursdayFlag()==weekIndex) {
			flag=true;
		}
		if (planRemind.getFridayFlag()==weekIndex) {
			flag=true;
		}
		if (planRemind.getSaturdayFlag()==weekIndex) {
			flag=true;
		}
		if (planRemind.getSaturdayFlag()==weekIndex) {
			flag=true;
		}

		if (flag){
			LocalTime localTime = now.toLocalTime();
			LocalTime executeTime = planRemind.getExecuteTime();
			if (localTime.getHour()==executeTime.getHour()&&localTime.getMinute()==executeTime.getMinute()) {
				return true;
			}
		}

		return false;

	}

	/**
	 * 比较范围并且更新完成状态
	 *
	 * @param now
	 * @param startDate
	 * @param endDate
	 * @param id
	 * @return
	 */
	private boolean compareRangeAndUpdateFinishFlag(LocalDateTime now, LocalDate startDate, LocalDate endDate,Long id) {

		LocalDate localDate = now.toLocalDate();

		// 当前日期在开始日期之后
		if (startDate!=null && startDate.isAfter(localDate)) {
			return false;
		}

        // 当前日期在结束日期之后
		if(endDate!=null && endDate.isBefore(localDate)){
			planRemindDao.changeFinishFlag(id,1);
			return false;
		}

		return true;

	}

	/**
	 * 比较分钟
	 *
	 *
	 * @param now
	 * @param localDate1
	 * @param localTime1
	 * @return
	 */
	private boolean compareMinute(LocalDateTime now, LocalDate localDate1, LocalTime localTime1) {

		if (now.toLocalDate().equals(localDate1)) {
			LocalTime localTime = now.toLocalTime();

			if (localTime.getHour()==localTime1.getHour()&&localTime.getMinute()==localTime1.getMinute()) {
                return true;
			}
		}

		return false;
	}

	/**
	 * 发送邮件
	 *
	 * @param receivers
	 * @param topic
	 * @param content
	 * @param now
	 * @throws MessagingException
	 */
	private void sendHtmlMail(String[] receivers,String topic,String content,LocalDateTime now) throws MessagingException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("<p>您好！您有一个计划提醒，请及时处理，内容如下：</p>");
		stringBuilder.append("<p>&nbsp; &nbsp; &nbsp; &nbsp;");
		stringBuilder.append(content);
		stringBuilder.append("</p>");
		stringBuilder.append("<p>☛☛  发送时间：");
		stringBuilder.append(dtf.format(now));
		stringBuilder.append("</p>");
		String contents =stringBuilder.toString();
		log.info("发送注册邮件内容,[{}]",contents);
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(sender);
		helper.setTo(receivers);
		helper.setSubject(topic);
		helper.setText(contents, true);
		javaMailSender.send(message);
	}

}
