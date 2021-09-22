package com.hanxiaozhang;

import com.hanxiaozhang.contracttemplate.domain.ContractTemplateDO;
import com.hanxiaozhang.contracttemplate.service.ContractTemplateService;
import com.hanxiaozhang.pdfutil.PdfUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/2/18
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfUtilTest {

    @Autowired
    private ContractTemplateService contractTemplateService;

    @Test
    public  void test() {
        ContractTemplateDO contractTemplate = contractTemplateService.get(1L);
        PdfUtil.getInstance().execute(contractTemplate,initData(),"hanxiaozhang","test");
    }

    /**
     * 初始化文件中的变量数据
     * @return
     */
    private static Map initData() {
        Map<String, String> data = new HashMap<>(4);
        data.put("url", "标题内容");
        data.put("username", "张三");
        return data;
    }
}
