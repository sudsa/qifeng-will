package com.qifeng.will.common.dao;

import java.util.List;
import java.util.Map;

import com.qifeng.will.common.domain.TaskDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:hanxinghua
 * @Date: 2020/9/9
 */
@Mapper
public interface TaskDao {

	TaskDO get(Long id);
	
	List<TaskDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TaskDO task);
	
	int update(TaskDO task);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
