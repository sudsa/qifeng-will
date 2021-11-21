package com.hanxiaozhang.contracttemplate.service;

import com.hanxiaozhang.contracttemplate.domain.ContractTemplateDO;

import java.util.List;
import java.util.Map;

/**
 * 合同模板表
 * 
 * @author hanxinghua
 * @email hanxinghua2015@sina.com
 * @date 2020-02-18 18:34:09
 */
public interface ContractTemplateService {
	
	ContractTemplateDO get(Long templateId);
	
	List<ContractTemplateDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ContractTemplateDO contractTemplate);
	
	int update(ContractTemplateDO contractTemplate);
	
	int remove(Long templateId);
	
	int batchRemove(Long[] templateIds);


}
