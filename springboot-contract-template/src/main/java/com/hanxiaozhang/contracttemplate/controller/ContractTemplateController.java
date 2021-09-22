package com.hanxiaozhang.contracttemplate.controller;

import java.util.List;
import java.util.Map;


import com.hanxiaozhang.utils.PageUtil;
import com.hanxiaozhang.utils.Query;
import com.hanxiaozhang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanxiaozhang.contracttemplate.domain.ContractTemplateDO;
import com.hanxiaozhang.contracttemplate.service.ContractTemplateService;


/**
 * 合同模板表
 * 
 * @author hanxinghua
 * @email hanxinghua2015@sina.com
 * @date 2020-02-18 18:34:09
 */
 
@Controller
@RequestMapping("/contracttemplate/contractTemplate")
public class ContractTemplateController {

	@Autowired
	private ContractTemplateService contractTemplateService;
	
	@GetMapping()
	String ContractTemplate(){
	    return "contractTemplate/contractTemplate";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtil list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ContractTemplateDO> contractTemplateList = contractTemplateService.list(query);
		int total = contractTemplateService.count(query);
		PageUtil pageUtil = new PageUtil(contractTemplateList, total);
		return pageUtil;
	}
	
	@GetMapping("/add")
	String add(){
	    return "contractTemplate/add";
	}

	@GetMapping("/edit/{templateId}")
	String edit(@PathVariable("templateId") Long templateId,Model model){
		ContractTemplateDO contractTemplate = contractTemplateService.get(templateId);
		model.addAttribute("contractTemplate", contractTemplate);
	    return "contractTemplate/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( ContractTemplateDO contractTemplate){
		if(contractTemplateService.save(contractTemplate)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( ContractTemplateDO contractTemplate){
		contractTemplateService.update(contractTemplate);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( Long templateId){
		if(contractTemplateService.remove(templateId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") Long[] templateIds){
		contractTemplateService.batchRemove(templateIds);
		return R.ok();
	}
	
}
