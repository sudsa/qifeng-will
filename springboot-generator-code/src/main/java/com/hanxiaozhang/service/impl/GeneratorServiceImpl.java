package com.hanxiaozhang.service.impl;


import com.hanxiaozhang.dao.GeneratorMapper;
import com.hanxiaozhang.service.GeneratorService;
import com.hanxiaozhang.util.GenUtils;
import org.apache.commons.io.IOUtils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 功能描述: <br>
 * 〈GeneratorServiceImpl〉
 *
 * @Author:hanxinghua
 * @Date: 2020/6/19
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

	@Resource
	private GeneratorMapper generatorMapper;

	@Override
	public List<Map<String, Object>> list() {
		List<Map<String, Object>> list = generatorMapper.list();
		return list;
	}

	@Override
	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = generatorMapper.get(tableName);
			//查询列信息
			List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
			//生成代码
			GenUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

}
