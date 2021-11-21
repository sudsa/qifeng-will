package com.hanxiaozhang.testdictcode.controller;

import com.hanxiaozhang.testdictcode.domain.TestDictDO;
import com.hanxiaozhang.testdictcode.service.TestDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestDictController {


    @Autowired
    private TestDictService testDictService;



    @RequestMapping()
    @ResponseBody
    public TestDictDO test1(){
        TestDictDO dictDO = testDictService.get(1L);
        int i=0;
//        i= i/0;
        return dictDO;
    }



}
