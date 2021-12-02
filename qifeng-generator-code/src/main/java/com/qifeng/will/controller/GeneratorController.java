package com.qifeng.will.controller;

import com.alibaba.fastjson.JSON;


import com.hanxiaozhang.service.GeneratorService;
import com.hanxiaozhang.util.GenUtils;
import com.hanxiaozhang.utils.R;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈GeneratorController〉
 *
 * @Author:howill.zou
 * @Date: 2020/6/19
 */
@RequestMapping("/generator")
@Controller
public class GeneratorController {


	@Autowired
	private GeneratorService generatorService;

	@GetMapping()
	public String generator() {
		return "generator/generator";
	}

	@ResponseBody
	@GetMapping("/list")
	public List<Map<String, Object>> list() {
		List<Map<String, Object>> list = generatorService.list();
		return list;
	};

	@RequestMapping("/code/{tableName}")
	public void code(HttpServletRequest request, HttpServletResponse response,
                     @PathVariable("tableName") String tableName) throws IOException {
		String[] tableNames = new String[] { tableName };
		commonCode(response, tableNames);
	}


	@RequestMapping("/batchCode")
	public void batchCode(HttpServletRequest request, HttpServletResponse response, String tables) throws IOException {
		String[] tableNames = new String[] {};
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		commonCode(response, tableNames);
	}

	@GetMapping("/edit")
	public String edit(Model model) {
		Configuration conf = GenUtils.getConfig();
		Map<String, Object> property = new HashMap<>(16);
		property.put("author", conf.getProperty("author"));
		property.put("email", conf.getProperty("email"));
		property.put("package", conf.getProperty("package"));
		property.put("autoRemovePre", conf.getProperty("autoRemovePre"));
		property.put("tablePrefix", conf.getProperty("tablePrefix"));
		model.addAttribute("property", property);
		return "generator/edit";
	}

	@ResponseBody
	@PostMapping("/update")
	public R update(@RequestParam Map<String, Object> map) {
		try {
			PropertiesConfiguration conf = new PropertiesConfiguration("generator.properties");
			conf.setProperty("author", map.get("author"));
			conf.setProperty("email", map.get("email"));
			conf.setProperty("package", map.get("package"));
			conf.setProperty("autoRemovePre", map.get("autoRemovePre"));
			conf.setProperty("tablePrefix", map.get("tablePrefix"));
			conf.save();
		} catch (ConfigurationException e) {
			return R.error("保存配置文件出错");
		}
		return R.ok();
	}


	/**
	 * 生产代码公共
	 *
	 * @param response
	 * @param tableNames
	 * @throws IOException
	 */
	private void commonCode(HttpServletResponse response, String[] tableNames) throws IOException {
		byte[] data = generatorService.generatorCode(tableNames);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"hanxiaozhang.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}
}
