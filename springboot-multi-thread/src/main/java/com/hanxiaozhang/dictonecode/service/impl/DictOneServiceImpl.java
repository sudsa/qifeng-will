package com.hanxiaozhang.dictonecode.service.impl;

import com.hanxiaozhang.dictonecode.dao.DictOneDao;
import com.hanxiaozhang.dictonecode.domain.DictOneDO;
import com.hanxiaozhang.dictonecode.service.DictOneService;
import com.hanxiaozhang.importexcel.DictSaveExcelServiceImpl;
import com.hanxiaozhang.importexcel.ImportExcelExecutor;
import com.hanxiaozhang.utils.ExcelToEntityListUtil;
import com.hanxiaozhang.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @CacheConfig(cacheNames = "dict") 类级注释，允许共享缓存名称
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Slf4j
@Service
public class DictOneServiceImpl implements DictOneService {

    @Resource
    private DictOneDao dictOneDao;

    @Autowired
    private DictSaveExcelServiceImpl dictSaveExcelServiceImpl;

    @Override
    public int save(DictOneDO dict) {
        return dictOneDao.save(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R importExcel(MultipartFile file) {

        try {
            //读取Excel中数据
            ArrayList<DictOneDO> list = ExcelToEntityListUtil.getInstance().execute(DictOneDO.class, file.getInputStream(), initTitleToAttr());
            log.info("读取Excel中数据的条数：[{}]",list.size());
            //多线程处理数据，并导出错误数据
            List<DictOneDO> errorList = ImportExcelExecutor.execute(dictSaveExcelServiceImpl, list, 14);
            //封装错误数据
            if (errorList!=null&&!errorList.isEmpty()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorData", errorList);
                map.put("title", initAttrToTitle());
                map.put("fileName", "有问题数据.xlsx");
                return R.error(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    private Map<String,String> initTitleToAttr(){
        Map<String, String> map = new LinkedHashMap<>(8);
        map.put("姓名","name");
        map.put("值","value");
        map.put("类型","type");
        map.put("描述","description");
        map.put("时间","createDate");
        return map;
    }

    private Map<String,String> initAttrToTitle(){
        Map<String, String> map = new LinkedHashMap<>(8);
        map.put("name","姓名");
        map.put("value","值");
        map.put("type","类型");
        map.put("description","描述");
        map.put("createDate","时间");
        map.put("remarks","数据问题备注");
        return map;
    }


}
