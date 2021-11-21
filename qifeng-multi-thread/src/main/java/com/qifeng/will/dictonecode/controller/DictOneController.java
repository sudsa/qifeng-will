package com.hanxiaozhang.dictonecode.controller;

import com.hanxiaozhang.dictonecode.domain.DictOneDO;
import com.hanxiaozhang.dictonecode.service.DictOneService;
import com.hanxiaozhang.utils.EntityListToExcelUtil;
import com.hanxiaozhang.utils.JsonUtil;
import com.hanxiaozhang.utils.R;
import com.hanxiaozhang.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/2/24
 * @since 1.0.0
 */
@Slf4j
@Controller
@RequestMapping()
public class DictOneController {

    @Autowired
    private DictOneService dictOneService;

    @GetMapping
    public String excelTest(){

        return "importExcel";

    }

    @ResponseBody
    @PostMapping("/importExcel")
    public R importExcel(@RequestParam(value = "file") MultipartFile file) {

        if (file == null) {
            return R.error(1, "文件不能为空");
        }

        if (StringUtil.isBlank(file.getOriginalFilename()) || file.getSize() == 0) {
            return R.error(1, "文件不能为空");
        }

        long startTime = System.currentTimeMillis();
        log.info("Excel开始导入,logId:[{}]", startTime);
        //数据导入处理
        R r = dictOneService.importExcel(file);

        if ("1".equals(r.get("code").toString())) {
            Map<String, Object> map = (Map) r.get("map");
            map.put("logId",startTime);
            log.info("Excel导入出错，logId:[{}]", startTime);
            return R.error(1, map, "导入时有错误信息");
        }
        long endTime = System.currentTimeMillis();
        log.info("Excel导入成功,logId:[{}],导入Excel耗时(ms):[{}]", startTime,endTime-startTime);
        return r;
    }



    @ResponseBody
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestParam("data") String data, HttpServletResponse response) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        //将Json字符串转Map
        Map<String, Object> params = JsonUtil.jsonToMapSO(data);
        log.info("Excel导出错误信息，logId:[{}]", params.get("logId").toString());
        //response设置返回类型
        setDownloadExcelResponse(response, params.get("fileName").toString());
        //数据导出为excel
        EntityListToExcelUtil.getInstance().
                executeXLSX(JsonUtil.jsonToLinkedHashMapSS(params.get("title").toString()),
                        JsonUtil.jsonToList(params.get("errorData").toString(), DictOneDO.class),
                        response.getOutputStream());



    }

    /**
     * 设置下载文件响应信息
     *
     * @param response
     * @param fileName
     */
    private void setDownloadExcelResponse(HttpServletResponse response, String fileName) {

        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            log.error("该文件[{}]不支持此编码转换,异常消息:[{}]",fileName,e.getMessage());
        }
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        //使用Content-Disposition,一定要确保没有禁止浏览器缓存的操作
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
    }


}
