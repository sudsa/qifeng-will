package com.qifeng.will;


import com.hanxiaozhang.asyncnode.AsyncAnnotationCode;

import com.hanxiaozhang.dictonecode.dao.DictOneDao;
import com.hanxiaozhang.dictonecode.domain.DictOneDO;
import com.hanxiaozhang.dictonecode.service.DictOneService;
import com.hanxiaozhang.importexcel.DictSaveExcelServiceImpl;
import com.hanxiaozhang.importexcel.ImportExcelExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public  class SpringbootMultiThreadApplicationTests {

    @Autowired
    private AsyncAnnotationCode asyncAnnotationCode;

    @Resource
    private DictOneDao dictOneDao;

    @Autowired
    private DictOneService dictOneService;

    @Autowired
    private DictSaveExcelServiceImpl dictSaveExcelServiceImpl;

    @Test
    public  void asyncTest() {

        try {
            long startTime = System.currentTimeMillis();
            System.out.println("执行方法 当前线程名称:"+Thread.currentThread().getName());
            asyncAnnotationCode.step1();
            asyncAnnotationCode.step2();
            asyncAnnotationCode.step3();
            long endTime = System.currentTimeMillis();
            System.out.println("流程耗时："+(endTime-startTime));

            while (true){

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void importExcelTest(){

        try {
            List<DictOneDO> execute = ImportExcelExecutor.execute(dictSaveExcelServiceImpl, initData(), 100);
            System.out.println(execute.size());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test(){
        DictOneDO dictDO = new DictOneDO();
        String s = "1";
        dictDO.setName(s);
        dictDO.setValue(s);
        dictDO.setType(s);
        dictOneService.save(dictDO);

    }

    private List<DictOneDO> initData(){

        ArrayList<DictOneDO> dictDOS = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            DictOneDO dictDO = new DictOneDO();
            String s = String.valueOf(i);
            dictDO.setName(s);
            dictDO.setValue(s);
            if((i%4)==0){
                dictDO.setType(s);
            }
            dictDOS.add(dictDO);
        }

        return dictDOS;
    }

}
