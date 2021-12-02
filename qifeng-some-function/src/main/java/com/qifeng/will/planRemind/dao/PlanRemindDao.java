package com.qifeng.will.planRemind.dao;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.qifeng.will.planRemind.domain.PlanRemindDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 计划提醒表
 * @author howill
 * @email howill@sina.com
 * @date 2020-07-05 10:18:16
 */
@Mapper
public interface PlanRemindDao {

	PlanRemindDO get(Long id);
	
	List<PlanRemindDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PlanRemindDO planRemind);
	
	int update(PlanRemindDO planRemind);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	@Update("update sys_plan_remind set `execute_status` = #{executeStatus} where id = #{id}")
    void changeExecuteStatus(@Param("id") Long id, @Param("executeStatus") Integer executeStatus);

	@Update("update sys_plan_remind set `finish_flag` = #{finishFlag} where id = #{id}")
	void changeFinishFlag(@Param("id") Long id, @Param("finishFlag") Integer finishFlag);

	@Update("update sys_plan_remind set `recent_execute_date` = #{recentExecuteDate} where id = #{id}")
	void updateRecentExecuteDate(@Param("id") Long id, @Param("recentExecuteDate") LocalDateTime recentExecuteDate);

}
