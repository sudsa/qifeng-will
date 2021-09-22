package com.hanxiaozhang.testcode.service.impl;

import com.hanxiaozhang.annotation.TargetDataSource;
import com.hanxiaozhang.testcode.dao.DictDao;
import com.hanxiaozhang.testcode.domain.DictDO;
import com.hanxiaozhang.testcode.service.TestService1;
import com.hanxiaozhang.enums.DataSourceKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2019/12/26
 * @since 1.0.0
 */
@Slf4j
@Service
public class TestServiceImpl1 implements TestService1 {

    @Resource
    private DictDao dictDao;




    @Override
    @TargetDataSource(dataSourceKey = DataSourceKey.DB_SLAVE1)
    public  void save1(DictDO dictDO){
        dictDao.save(dictDO);
    }

    @Override
    @TargetDataSource(dataSourceKey = DataSourceKey.DB_MASTER)
    public  void save2(DictDO dictDO){
        dictDao.save(dictDO);
    }


}
