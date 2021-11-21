package com.hanxiaozhang.testdictcode.dao;

import com.hanxiaozhang.testdictcode.domain.TestDictDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Mapper
public interface TestDictDao {

    TestDictDO get(Long id);

    List<TestDictDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(TestDictDO dict);

    int update(TestDictDO dict);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<TestDictDO> listType();
}