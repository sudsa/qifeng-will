package com.hanxiaozhang.mongodb.service.impl;

import com.hanxiaozhang.mongodb.dao.MongoCollectionTestDao;
import com.hanxiaozhang.mongodb.domain.MongoCollectionTestDO;
import com.hanxiaozhang.mongodb.service.MongoCollectionTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/6/14
 * @since 1.0.0
 */
@Slf4j
@Service
public class MongoCollectionTestServiceImpl implements MongoCollectionTestService {


    @Autowired
    private MongoCollectionTestDao mongoCollectionTestDao;

    @Override
    public void save(MongoCollectionTestDO mongoCollectionTestDO) {
        mongoCollectionTestDao.save(mongoCollectionTestDO);
    }

    @Override
    public List<MongoCollectionTestDO> list() {
        return mongoCollectionTestDao.findAll();
    }

    @Override
    public MongoCollectionTestDO get(String id) {
        return mongoCollectionTestDao.get(id);
    }

    @Override
    public MongoCollectionTestDO getByName(String name) {
        return mongoCollectionTestDao.getByName(name);
    }

    @Override
    public List<MongoCollectionTestDO> listLikeByName(String name) {
        return mongoCollectionTestDao.listLikeByName(name);
    }

    @Override
    public void update(MongoCollectionTestDO mongoCollectionTestDO) {
        mongoCollectionTestDao.updateMulti(mongoCollectionTestDO);
    }

    @Override
    public void deleteById(String id) {
        mongoCollectionTestDao.deleteById(id);
    }
}
