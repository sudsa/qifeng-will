package com.hanxiaozhang.importexcel;

import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈错误信息实体〉
 *
 * @author hanxinghua
 * @create 2020/2/23
 * @since 1.0.0
 */
@Data
public class ErrorInfoEntity<T> {

    /**
     * 业务上判断有错误的数据集合
     */
    private List<T> errorList;


}
