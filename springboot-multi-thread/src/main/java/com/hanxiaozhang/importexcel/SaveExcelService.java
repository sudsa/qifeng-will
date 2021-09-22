package com.hanxiaozhang.importexcel;

import java.util.List;


/**
 * 〈一句话功能简述〉<br>
 * 〈保存ExcelService〉
 *
 * @author hanxinghua
 * @create 2020/2/23
 * @since 1.0.0
 */

public interface SaveExcelService<T> {


    /**
     * 批量保存
     *
     * @param list
     * @param flag
     * @return
     * @throws Exception
     */
    ErrorInfoEntity batchSave(List<T> list, MultiThreadEndFlag flag) throws Exception;

}
