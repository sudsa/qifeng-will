/**
 * 
 */
package com.hanxiaozhang.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈GeneratorService〉
 *
 * @Author:hanxinghua
 * @Date: 2020/6/19
 */
public interface GeneratorService {

	List<Map<String, Object>> list();

	byte[] generatorCode(String[] tableNames);

}
