package com.qifeng.will.common.task;

import com.qifeng.will.planRemind.service.PlanRemindService;
import com.qifeng.will.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;

/**
 * 〈一句话功能简述〉<br>
 * 〈任务执行提醒定时〉
 *
 * @author howill
 * @create 2020/7/5
 * @since 1.0.0
 */
@Slf4j
public class PlanRemindJob implements Job {

    @Autowired
    private PlanRemindService planRemindService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String logId = String.valueOf(System.currentTimeMillis());
        log.info("=====>>>>>>>>>> 处理计划提醒，logId:{}, 开始时间为: {}", logId, DateUtil.format2());
        try {
            planRemindService.handlePlanRemindJob(logId);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
