package com.hanxiaozhang.importexcel;



import java.util.List;
import java.util.concurrent.Callable;

/**
 * 〈功能描述〉<br>
 * 〈导入Excel任务〉
 *
 * @author hanxinghua
 * @create 2020/2/23
 * @since 1.0.0
 */
public class ImportExcelTask<T> implements Callable<ErrorInfoEntity> {

    /**
     * 保存Excel服务
     */
    private SaveExcelService excelService;

    /**
     * 数据集合
     */
    private List<T> list;

    /**
     * 多线程数据结束标志
     */
    private MultiThreadEndFlag flag;

    /**
     * 构造函数
     *
     * @param excelService
     * @param list
     * @param flag
     */
    public ImportExcelTask(SaveExcelService<T> excelService,List<T> list,MultiThreadEndFlag flag){
        this.excelService=excelService;
        this.list=list;
        this.flag=flag;
    }


    @Override
    public ErrorInfoEntity call() throws Exception {
        return excelService.batchSave(list,flag);
    }


}
