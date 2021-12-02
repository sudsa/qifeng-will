package com.qifeng.will.common.service;

import java.util.List;
import java.util.Map;

import com.qifeng.will.common.domain.TaskDO;
import org.quartz.SchedulerException;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:howill
 * @Date: 2020/9/9
 */
public interface TaskService {
	
	TaskDO get(Long id);
	
	List<TaskDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TaskDO taskScheduleJob);
	
	int update(TaskDO taskScheduleJob);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	void initSchedule() throws SchedulerException;

	void changeStatus(Long jobId, String cmd) throws SchedulerException;

	void updateCron(Long jobId) throws SchedulerException;
}
