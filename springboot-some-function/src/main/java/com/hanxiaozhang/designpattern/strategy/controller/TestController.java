package com.hanxiaozhang.designpattern.strategy.controller;

import com.hanxiaozhang.designpattern.strategy.constant.ProcessTypeEnum;
import com.hanxiaozhang.designpattern.strategy.domain.A;
import com.hanxiaozhang.designpattern.strategy.domain.B;
import com.hanxiaozhang.designpattern.strategy.service.ProcessStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试Controller〉
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
@Controller
@RequestMapping("/")
public class TestController {


    @Autowired
    private ProcessStrategyFactory strategyFactory;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        strategyFactory.getByName(ProcessTypeEnum.A.getKey()).startProcess(new A(),false);
        strategyFactory.getByName(ProcessTypeEnum.B.getKey()).startProcess(new B(),false);
        return "OK";
    }


}
