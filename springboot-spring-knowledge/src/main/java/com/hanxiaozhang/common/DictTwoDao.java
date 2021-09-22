package com.hanxiaozhang.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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
public interface DictTwoDao {

    DictTwoDO get(Long id);

    int save(DictTwoDO dict);

    int update(DictTwoDO dict);

    int remove(Long id);

    int batchRemove(Long[] ids);


    @Update("update sys_dict set type=#{status} where id=#{id}")
    int updateStatusById(@Param("id") Long id, @Param("status") String status);

}