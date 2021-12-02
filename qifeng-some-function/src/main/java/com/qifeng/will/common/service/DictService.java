package com.qifeng.will.common.service;




import com.qifeng.will.common.domain.DictDO;

import java.util.List;
import java.util.Map;


/**
 * 字典表
 *
 * @author howill.zou
 * @email howill.zou@sina.com
 * @date 2020-04-30 09:43:57
 */
public interface DictService {
	
	DictDO get(Long id);
	
	List<DictDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(DictDO dict);

	int update(DictDO dict);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<DictDO> listType();



}
