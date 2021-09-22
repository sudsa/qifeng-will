package com.hanxiaozhang.mongodb.controller;

import com.hanxiaozhang.mongodb.domain.MongoCollectionTestDO;
import com.hanxiaozhang.mongodb.service.MongoCollectionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/6/14
 * @since 1.0.0
 */
@RestController
@RequestMapping
public class MongoCollectionTestController {

    @Autowired
    private MongoCollectionTestService mongoCollectionTestService;

    /**
     * 通过Id获取
     *
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public MongoCollectionTestDO get(String id) {
        return mongoCollectionTestService.get(id);
    }

    /**
     * 获取列表
     *
     * @return
     */
    @GetMapping("/list")
    public List<MongoCollectionTestDO> list() {
        return mongoCollectionTestService.list();
    }


    /**
     * 通过name获取
     *
     * @param name
     * @return
     */
    @GetMapping("/getByName")
    public MongoCollectionTestDO getByName(String name) {
        return mongoCollectionTestService.getByName(name);
    }


    /**
     * 通过name获取
     *
     * @param name
     * @return
     */
    @GetMapping("/listLikeByName")
    public List<MongoCollectionTestDO>  listLikeByName(String name) {
        return mongoCollectionTestService.listLikeByName(name);
    }


    /**
     * 保存
     *
     * @param mongoCollectionTestDO
     */
    @PostMapping("/save")
    public void save(@RequestBody MongoCollectionTestDO mongoCollectionTestDO) {
        mongoCollectionTestService.save(mongoCollectionTestDO);
    }

    /**
     * 更新
     *
     * @param mongoCollectionTestDO
     */
    @PostMapping("/update")
    public void update(@RequestBody MongoCollectionTestDO mongoCollectionTestDO) {
        mongoCollectionTestService.update(mongoCollectionTestDO);
    }


    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("/deleteById")
    public void deleteById(String id) {
         mongoCollectionTestService.deleteById(id);
    }



}
