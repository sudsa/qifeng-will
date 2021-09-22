package com.hanxiaozhang.mongodb.service;

import com.hanxiaozhang.mongodb.domain.MongoCollectionTestDO;

import java.awt.print.Book;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/6/14
 * @since 1.0.0
 */
public interface MongoCollectionTestService {

    void save(MongoCollectionTestDO mongoCollectionTestDO);

    List<MongoCollectionTestDO> list();

    MongoCollectionTestDO get(String id);

    MongoCollectionTestDO getByName(String name);

    List<MongoCollectionTestDO> listLikeByName(String name);

    void update(MongoCollectionTestDO mongoCollectionTestDO);

    void deleteById(String id);

}
