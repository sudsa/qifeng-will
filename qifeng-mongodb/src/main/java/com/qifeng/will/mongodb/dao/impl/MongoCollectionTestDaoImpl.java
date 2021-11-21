package com.hanxiaozhang.mongodb.dao.impl;

import com.hanxiaozhang.mongodb.dao.MongoCollectionTestDao;
import com.hanxiaozhang.mongodb.domain.MongoCollectionTestDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 〈一句话功能简述〉<br>
 * 〈MongoDB集合测试Dao实现类〉
 *
 * @author hanxinghua
 * @create 2020/6/14
 * @since 1.0.0
 */
@Component
public class MongoCollectionTestDaoImpl  implements MongoCollectionTestDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存对象
     *
     * @return
     */
    @Override
    public void save(MongoCollectionTestDO mongoCollectionTest) {
        mongoTemplate.save(mongoCollectionTest);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<MongoCollectionTestDO> findAll() {
        return mongoTemplate.findAll(MongoCollectionTestDO.class);
    }

    /***
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public MongoCollectionTestDO  get(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, MongoCollectionTestDO.class);
    }


    /**
     * 通过名字查询
     * （假设没有重复）
     *
     * @param name
     * @return
     */
    @Override
    public MongoCollectionTestDO getByName(String name) {

        Query query = new Query(Criteria.where("name").is(name));
        MongoCollectionTestDO mgt = mongoTemplate.findOne(query, MongoCollectionTestDO.class);
        return mgt;

    }

    /**
     * 通过名字模糊查询
     *
     * @param name
     * @return
     */
    @Override
    public List<MongoCollectionTestDO> listLikeByName(String name) {
        Pattern pattern = Pattern.compile("^" + name + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<MongoCollectionTestDO> lists = mongoTemplate.find(query, MongoCollectionTestDO.class);
        return lists;
    }

    /**
     * 通过年龄查询
     *
     * @param age
     * @return
     */
    @Override
    public List<MongoCollectionTestDO> listByAge(Integer age) {

        Query query = new Query(Criteria.where("age").is(age));
        List<MongoCollectionTestDO> mgts = mongoTemplate.find(query, MongoCollectionTestDO.class);
        return mgts;
    }


    /**
     * 更新结果集第一条
     *
     * @param mongoCollectionTest
     */
    @Override
    public void updateFirst(MongoCollectionTestDO mongoCollectionTest) {
        Query query = new Query(Criteria.where("_id").is(mongoCollectionTest.getId()));
        Update update = new Update()
                .set("name", mongoCollectionTest.getName())
                .set("age", mongoCollectionTest.getAge())
                .set("info", mongoCollectionTest.getInfo())
                .set("create_time", mongoCollectionTest.getCreateTime());
        // updateFirst 更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, MongoCollectionTestDO.class);
    }


    /**
     * 更新结果集所有
     *
     * @param mongoCollectionTest
     */
    @Override
    public void updateMulti(MongoCollectionTestDO mongoCollectionTest) {
        Query query = new Query(Criteria.where("_id").is(mongoCollectionTest.getId()));
        Update update = new Update()
                .set("name", mongoCollectionTest.getName())
                .set("age", mongoCollectionTest.getAge())
                .set("info", mongoCollectionTest.getInfo())
                .set("create_time", mongoCollectionTest.getCreateTime());
        // updateMulti 更新查询返回结果集的全部
        mongoTemplate.updateMulti(query, update, MongoCollectionTestDO.class);
    }


    /**
     * 更新对象不存在则去添加
     *
     * @param mongoCollectionTest
     */
    @Override
    public void upsert(MongoCollectionTestDO mongoCollectionTest) {
        Query query = new Query(Criteria.where("_id").is(mongoCollectionTest.getId()));
        Update update = new Update()
                .set("name", mongoCollectionTest.getName())
                .set("age", mongoCollectionTest.getAge())
                .set("info", mongoCollectionTest.getInfo())
                .set("create_time", mongoCollectionTest.getCreateTime());
        // upsert更新对象不存在则去添加
        mongoTemplate.upsert(query, update, MongoCollectionTestDO.class);
    }


    /***
     * 删除对象
     * @param mongoCollectionTest
     * @return
     */
    @Override
    public void delete(MongoCollectionTestDO mongoCollectionTest) {
        mongoTemplate.remove(mongoCollectionTest);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void deleteById(String id) {
        MongoCollectionTestDO mongoCollectionTest = get(id);
        // delete
        delete(mongoCollectionTest);
    }

}
