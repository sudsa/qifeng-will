package com.hanxiaozhang.contracttemplate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.hanxiaozhang.contracttemplate.dao.ContractTemplateDao;
import com.hanxiaozhang.contracttemplate.domain.ContractTemplateDO;
import com.hanxiaozhang.contracttemplate.service.ContractTemplateService;

import javax.annotation.Resource;


@Service
public class ContractTemplateServiceImpl implements ContractTemplateService {

	@Resource
	private ContractTemplateDao contractTemplateDao;
	
	@Override
	public ContractTemplateDO get(Long templateId){
		return contractTemplateDao.get(templateId);
	}
	
	@Override
	public List<ContractTemplateDO> list(Map<String, Object> map){
		return contractTemplateDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return contractTemplateDao.count(map);
	}
	
	@Override
	public int save(ContractTemplateDO contractTemplate){
		return contractTemplateDao.save(handleIllegalHtmlFormat(contractTemplate));
	}
	
	@Override
	public int update(ContractTemplateDO contractTemplate){
		return contractTemplateDao.update(handleIllegalHtmlFormat(contractTemplate));
	}
	
	@Override
	public int remove(Long templateId){
		return contractTemplateDao.remove(templateId);
	}
	
	@Override
	public int batchRemove(Long[] templateIds){
		return contractTemplateDao.batchRemove(templateIds);
	}


	/**
	 * 处理非法的html格式
	 * 举例：<br></br> 是完整标签，但是富本编辑器，只生成<br>表示换行，因此要把<br>-><br/>
	 *
	 *
	 * @param contractTemplate
	 * @return
	 *
	 */
	private  ContractTemplateDO  handleIllegalHtmlFormat(ContractTemplateDO contractTemplate){
		String templateContent = contractTemplate.getTemplateContent();
		contractTemplate.setTemplateContent(templateContent.replace("<br>","<br/>"));
		return contractTemplate;
	}
	
}
