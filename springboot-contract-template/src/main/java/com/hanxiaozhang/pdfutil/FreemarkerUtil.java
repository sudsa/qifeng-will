package com.hanxiaozhang.pdfutil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;


/**
 * 功能描述: <br>
 * 〈freemarker的模板处理工具类〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/17
 */
@Slf4j
public class FreemarkerUtil {


	public static Configuration CONFIG;

	public static String renderTemplate(String s, Map<String, Object> data) throws IOException, TemplateException {
		Template t = new Template(null, new StringReader(s), CONFIG);
		// 执行插值，并输出到指定的输出流中
		StringWriter w = new StringWriter();
		t.getConfiguration();
		try {
			t.process(data, w);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("填充模板内容出现异常，模板："+s);
			log.debug("noticeTime",data.get("noticeTime"));
		}

		return w.getBuffer().toString();
	}

	public static String renderFileTemplate(String file, Map<String, Object> data) throws IOException,
		TemplateException {
		Configuration cfg = CONFIG;
		cfg.setDefaultEncoding("UTF-8");
		// 取得模板文件
		Template t = cfg.getTemplate(file);
		// 执行插值，并输出到指定的输出流中
		StringWriter w = new StringWriter();
		t.getConfiguration();
		t.process(data, w);
		return w.getBuffer().toString();
	}

}
