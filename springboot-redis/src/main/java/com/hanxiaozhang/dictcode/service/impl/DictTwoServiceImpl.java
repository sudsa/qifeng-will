package com.hanxiaozhang.dictcode.service.impl;

import com.hanxiaozhang.dictcode.dao.DictTwoDao;
import com.hanxiaozhang.dictcode.domain.DictDO;
import com.hanxiaozhang.dictcode.service.DictTwoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @CacheConfig(cacheNames = "dict") 类级注释，允许共享缓存名称
 *
 *  cacheNames与key共同组成redis中的key值,如果key不写,会默认获取方法中的参数值
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "dict")
public class DictTwoServiceImpl implements DictTwoService {

    @Resource
    private DictTwoDao dictTwoDao;

    /**
     *
     * @Cacheable 缓存被执行方法的结果，key:键的值 unless：排除条件，缓存在什么时候不触发
     *  unless="#result == null"：方法返回值为空时触发不缓存
     *  Tips: #p0:传入的第一个参数作为key值，#p0.id：传入第一个参数中id作为key值
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(cacheNames = "dict",key = "#p0", unless="#result == null")
    public DictDO get(Long id) {
        return dictTwoDao.get(id);
    }

    @Override
    @Cacheable
    public List<DictDO> list(Map<String, Object> map) {
        return dictTwoDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return dictTwoDao.count(map);
    }

    @Override
    public int save(DictDO dict) {
        return dictTwoDao.save(dict);
    }

    @Override
    @CacheEvict(key = "#dict.id")
    public int update(DictDO dict) {
        return dictTwoDao.update(dict);
    }

    /**
     * @CacheEvict: 作用是清除缓存 key：按key值清除，allEntries：是否清除所有 true/false
     *
     * @param id
     * @return
     */
    @Override
    @CacheEvict(key = "#p0")
    public int remove(Long id) {
        return dictTwoDao.remove(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public int batchRemove(Long[] ids) {
        return dictTwoDao.batchRemove(ids);
    }



}
