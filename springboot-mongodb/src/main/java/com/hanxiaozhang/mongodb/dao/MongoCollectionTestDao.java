package com.hanxiaozhang.mongodb.dao;

import com.hanxiaozhang.mongodb.domain.MongoCollectionTestDO;


import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈MongoDB集合测试Dao〉
 *
 * @author hanxinghua
 * @create 2020/6/14
 * @since 1.0.0
 */
public interface MongoCollectionTestDao {


    List<MongoCollectionTestDO> findAll();

    MongoCollectionTestDO get(String id);

    MongoCollectionTestDO getByName(String name);

    List<MongoCollectionTestDO> listLikeByName(String name);

    List<MongoCollectionTestDO> listByAge(Integer age);

    void save(MongoCollectionTestDO mongoCollectionTest);

    void updateFirst(MongoCollectionTestDO mongoCollectionTest);

    void updateMulti(MongoCollectionTestDO mongoCollectionTest);

    void upsert(MongoCollectionTestDO mongoCollectionTest);

    void delete(MongoCollectionTestDO mongoCollectionTest);

    void deleteById(String id);
}
