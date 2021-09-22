package com.hanxiaozhang.testcode.controller;

import com.hanxiaozhang.testcode.domain.DictDO;
import com.hanxiaozhang.testcode.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
public class TestController {


    @Autowired
    private TestService testService;



    @RequestMapping()
    public String  test1(){
        DictDO dictDO = new DictDO();
        dictDO.setName("分布式事务测试");
        dictDO.setType("分布式事务测试");
        dictDO.setDescription("分布式事务测试");
        dictDO.setCreateDate(new Date());
        testService.distributedTrans(dictDO);
        return "OK";
    }



}
