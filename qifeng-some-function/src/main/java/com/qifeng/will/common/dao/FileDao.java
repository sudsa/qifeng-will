package com.qifeng.will.common.dao;


import java.util.List;
import java.util.Map;

import com.qifeng.will.common.domain.FileDO;
import com.qifeng.will.common.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 文件表
 * @author howill
 * @email howill@sina.com
 * @date 2020-04-30 09:43:57
 */
@Mapper
public interface FileDao {

	FileDO get(Long id);
	
	List<FileDTO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FileDO file1);
	
	int update(FileDO file1);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	/**
	 *
	 * @param belongId
	 * @param type
	 * @param fileTag
	 * @return
	 */
	List<FileDTO> listByBelongIdAndTypeAndFileTag(@Param("belongId") Long belongId, @Param("type") String type, @Param("fileTag")  String fileTag);


	/**
	 * 更新删除标识
	 *
	 * @param id
	 * @return
	 */
	@Update("update sys_file set `del_flag` = 1 where id =#{id}")
	int updateDelFlagById(Long id);


}
