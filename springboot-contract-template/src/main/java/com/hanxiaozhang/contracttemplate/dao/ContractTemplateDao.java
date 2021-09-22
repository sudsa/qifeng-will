package com.hanxiaozhang.contracttemplate.dao;

import com.hanxiaozhang.contracttemplate.domain.ContractTemplateDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 合同模板表
 * @author hanxinghua
 * @email hanxinghua2015@sina.com
 * @date 2020-02-18 18:34:09
 */
@Mapper
public interface ContractTemplateDao {

	ContractTemplateDO get(Long templateId);
	
	List<ContractTemplateDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ContractTemplateDO contractTemplate);
	
	int update(ContractTemplateDO contractTemplate);
	
	int remove(Long template_id);
	
	int batchRemove(Long[] templateIds);
}
