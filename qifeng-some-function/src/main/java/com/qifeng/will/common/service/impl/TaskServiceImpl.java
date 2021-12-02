package com.qifeng.will.common.service.impl;

import com.qifeng.will.common.dao.TaskDao;
import com.qifeng.will.common.domain.ScheduleJob;
import com.qifeng.will.common.domain.TaskDO;
import com.qifeng.will.common.quartz.utils.QuartzManager;
import com.qifeng.will.common.service.TaskService;
import com.qifeng.will.utils.constant.Constant;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:howill
 * @Date: 2020/9/9 
 */
@Service
public class TaskServiceImpl implements TaskService {

	@Resource
	private TaskDao taskDao;

	@Autowired
	QuartzManager quartzManager;

	@Override
	public TaskDO get(Long id) {
		return taskDao.get(id);
	}

	@Override
	public List<TaskDO> list(Map<String, Object> map) {
		return taskDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return taskDao.count(map);
	}

	@Override
	public int save(TaskDO taskScheduleJob) {
		return taskDao.save(taskScheduleJob);
	}

	@Override
	public int update(TaskDO taskScheduleJob) {
		return taskDao.update(taskScheduleJob);
	}

	@Override
	public int remove(Long id) {
		try {
			TaskDO scheduleJob = get(id);
			quartzManager.deleteJob(entityToData(scheduleJob));
			return taskDao.remove(id);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public int batchRemove(Long[] ids) {
		for (Long id : ids) {
			try {
				TaskDO scheduleJob = get(id);
				quartzManager.deleteJob(entityToData(scheduleJob));
			} catch (SchedulerException e) {
				e.printStackTrace();
				return 0;
			}
		}
		return taskDao.batchRemove(ids);
	}

	@Override
	public void initSchedule() throws SchedulerException {
		// 这里获取任务信息数据
		List<TaskDO> jobList = taskDao.list(new HashMap<String, Object>(16));
		for (TaskDO scheduleJob : jobList) {
			if ("1".equals(scheduleJob.getJobStatus())) {
				ScheduleJob job = entityToData(scheduleJob);
				quartzManager.addJob(job);
			}

		}
	}

	@Override
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		TaskDO scheduleJob = get(jobId);
		if (scheduleJob == null) {
			return;
		}
		if (Constant.STATUS_RUNNING_STOP.equals(cmd)) {
			quartzManager.deleteJob(entityToData(scheduleJob));
			scheduleJob.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		} else {
			if (!Constant.STATUS_RUNNING_START.equals(cmd)) {
			} else {
                scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
                quartzManager.addJob(entityToData(scheduleJob));
            }
		}
		update(scheduleJob);
	}

	@Override
	public void updateCron(Long jobId) throws SchedulerException {
		TaskDO scheduleJob = get(jobId);
		if (scheduleJob == null) {
			return;
		}
		if (ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
			quartzManager.updateJobCron(entityToData(scheduleJob));
		}
		update(scheduleJob);
	}


	private ScheduleJob entityToData(TaskDO scheduleJobEntity) {
		ScheduleJob scheduleJob = new ScheduleJob();
		scheduleJob.setBeanClass(scheduleJobEntity.getBeanClass());
		scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
		scheduleJob.setDescription(scheduleJobEntity.getDescription());
		scheduleJob.setIsConcurrent(scheduleJobEntity.getIsConcurrent());
		scheduleJob.setJobName(scheduleJobEntity.getJobName());
		scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
		scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus());
		scheduleJob.setMethodName(scheduleJobEntity.getMethodName());
		scheduleJob.setSpringBean(scheduleJobEntity.getSpringBean());
		return scheduleJob;
	}
}
