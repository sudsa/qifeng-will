package com.hanxiaozhang.importexcel;

import com.hanxiaozhang.dictonecode.dao.DictOneDao;
import com.hanxiaozhang.dictonecode.domain.DictOneDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据字典数据导入〉
 *
 * @author hanxinghua
 * @create 2020/2/23
 * @since 1.0.0
 */
@Slf4j
@Service
public class DictSaveExcelServiceImpl implements SaveExcelService<DictOneDO> {


    @Resource
    private DictOneDao dictOneDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ErrorInfoEntity batchSave(List<DictOneDO> list, MultiThreadEndFlag flag) throws Exception {
        int resultFlag = 0;
        log.info("batchSave(),当前线程名称:[{}]",Thread.currentThread().getName());
        try {
            //创建返回错误信息实体
            ErrorInfoEntity errorInfoEntity = new ErrorInfoEntity();
            //业务操作
            List<DictOneDO> errorList = handleDict(list);
            //赋值错误数据
            errorInfoEntity.setErrorList(errorList);
            //操作成功
            resultFlag = 1;
            //等待其他线程完成操作
            flag.waitForEnd(resultFlag);
            //其他线程异常手工回滚
            if (resultFlag == 1 && !flag.allSuccessFlag()) {
                String message = "子线程未全部执行成功，对线程[" + Thread.currentThread().getName() + "]进行回滚";
                log.info(message);
                throw new Exception(message);
            }
            return errorInfoEntity;
        } catch (Exception e) {
            log.error(e.toString());
            //本身线程异常抛出异常，并且没有调用flag.waitForEnd()时触发
            if (resultFlag == 0) {
                flag.waitForEnd(resultFlag);
            }
            throw e;
        }
    }


    /**
     * 处理相关数据
     *
     * @param list
     * @return
     */
    private List<DictOneDO> handleDict(List<DictOneDO> list) {
        List<DictOneDO> errorList=new ArrayList<>();
        list.forEach(x->{
            dictOneDao.save(x);
//            boolean flag=true;
//            List<String> errorMsg=new ArrayList<>();
//            //模拟一个业务数据错误，姓名不能为空
//            if (StringUtil.isBlank(x.getName())) {
//                errorMsg.add("姓名不能为空!");
////                flag=false;
//            }
//            //模拟一个业务数据错误，类型不能为空
//            if (StringUtil.isBlank(x.getType())){
//                errorMsg.add("类型不能为空!");
////                flag=false;
//            }
//            if (flag){
//                dictOneDao.save(x);
//            }else {
//                x.setRemarks(String.join("\n",errorMsg));
//                errorList.add(x);
//            }
        });

        return errorList;
    }


}
