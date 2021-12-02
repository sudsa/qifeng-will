package com.qifeng.will.common.dao;

import com.qifeng.will.common.domain.DictDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author howill
 * @email howill@sina.com
 * @date 2020-04-30 09:43:57
 */
@Mapper
public interface DictDao {

	DictDO get(Long id);

	List<DictDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(DictDO dict);

	int update(DictDO dict);

	int remove(Long id);

	int batchRemove(Long[] ids);

	List<DictDO> listType();

	@Select("select * from sys_dict  where type = #{type}")
	List<DictDO> listByType(@Param("type")String type);

	DictDO getByTypeAndValue(@Param("type")String type, @Param("value")String value);

}
