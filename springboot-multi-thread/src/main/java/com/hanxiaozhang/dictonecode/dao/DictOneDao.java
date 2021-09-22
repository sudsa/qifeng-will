package com.hanxiaozhang.dictonecode.dao;

import com.hanxiaozhang.dictonecode.domain.DictOneDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Mapper
public interface DictOneDao {

    int save(DictOneDO dict);


}