package com.hanxiaozhang.testcode.service.impl;

import com.hanxiaozhang.testcode.dao.DictDao;
import com.hanxiaozhang.testcode.domain.DictDO;
import com.hanxiaozhang.testcode.service.TestService;
import com.hanxiaozhang.testcode.service.TestService1;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private DictDao dictDao;



    @Autowired
    private TestService1 testService1;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void distributedTrans(DictDO dictDO) {

        testService1.save1(dictDO);
        int i=1/0;
        testService1.save2(dictDO);

    }



}
