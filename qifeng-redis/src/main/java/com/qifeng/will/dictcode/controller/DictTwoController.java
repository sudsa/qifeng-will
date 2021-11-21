package com.hanxiaozhang.dictcode.controller;

import com.hanxiaozhang.dictcode.domain.DictDO;
import com.hanxiaozhang.dictcode.service.DictTwoService;
import com.hanxiaozhang.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
public class DictTwoController {


    @Autowired
    private DictTwoService dictTwoService;



    @RequestMapping("/list")
    public String  list(){
        List<DictDO> list = dictTwoService.list(new HashMap<>(4));
        return JsonUtil.beanToJson(list);
    }

    @RequestMapping("/get/{id}")
    public String  get(@PathVariable("id") Long id){
        DictDO dictDO = dictTwoService.get(id);
        return JsonUtil.beanToJson(dictDO);
    }

    @GetMapping("/save")
    public String save(){
        DictDO dictDO = new DictDO();
        dictDO.setName("save").setType("save").setValue("save").
                setCreateDate(new Date()).setDescription("save");
        dictTwoService.save(dictDO);
        return "OK";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id){
        DictDO dictDO = dictTwoService.get(id);
        dictDO.setName("update").setValue("update").setType("update");
        dictTwoService.update(dictDO);
        return "OK";
    }

    @RequestMapping("/remove/{id}")
    public String  remove(@PathVariable("id") Long id){
        dictTwoService.remove(id);
        return "OK";
    }





}
