package com.qifeng.will.planRemind.service;

import com.qifeng.will.planRemind.domain.PlanRemindDO;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

/**
 * 计划提醒表
 * 
 * @author hanxinghua
 * @email hanxinghua2015@sina.com
 * @date 2020-07-05 10:18:16
 */
public interface PlanRemindService {
	
	PlanRemindDO get(Long id);
	
	List<PlanRemindDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PlanRemindDO planRemind);
	
	int update(PlanRemindDO planRemind);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    void changeExecuteStatus(Long id, Integer executeStatus);

	void handlePlanRemindJob(String logId) throws MessagingException;

}
