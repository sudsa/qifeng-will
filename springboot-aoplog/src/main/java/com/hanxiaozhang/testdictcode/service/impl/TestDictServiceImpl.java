package com.hanxiaozhang.testdictcode.service.impl;

import com.hanxiaozhang.testdictcode.dao.TestDictDao;
import com.hanxiaozhang.testdictcode.domain.TestDictDO;
import com.hanxiaozhang.testdictcode.service.TestDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class TestDictServiceImpl implements TestDictService {

    @Resource
    private TestDictDao testDictDao;


    @Override
    public TestDictDO get(Long id) {
        return testDictDao.get(id);
    }
}
