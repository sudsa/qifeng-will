package com.qifeng.will.strategy.simple3;

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
    private StrategyFactory strategyFactory;

    @RequestMapping("")
    @ResponseBody
    public String test(){
        strategyFactory.getByName("longSimple").handleInstanceAgree(new Object());
        return "OK";
    }


}
