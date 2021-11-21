package com.hanxiaozhang.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 功能描述: <br>
 * 〈Excel导入成实体集合〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/24
 */
@Slf4j
public class ExcelToEntityListUtil {


    private BeanStorage storage = new BeanStorage();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private ExcelToEntityListUtil(){}

    private static class Holder{
        private static  ExcelToEntityListUtil INSTANCE=new ExcelToEntityListUtil();
    }

    public static ExcelToEntityListUtil getInstance(){
        return Holder.INSTANCE;
    }

    /**
     * 执行Excel转EntityList
     *
     * @param entity
     * @param excel  excel输入流
     * @param titleToAttr  key为excel的中文title，value为该中文title对于的entity属性名
     * @param <T>
     * @return
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvalidFormatException
     */
    public <T> ArrayList<T> execute(Class<?> entity, InputStream excel, Map<String, String> titleToAttr) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException,InvalidFormatException {
        ArrayList<T> result = new ArrayList<T>();

        Workbook book = create(excel);
        Sheet sheet = book.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        if (rowCount < 1) {
            return result;
        }
        //加载标题栏数据,以此和headMapping对应
        Map<Integer, String> headTitle = loadHeadTitle(sheet);
        //循环行
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            //空行跳过
            if (row == null) {
                continue;
            }
            int cellCount = row.getLastCellNum();
            @SuppressWarnings("unchecked")
            T instance = (T) entity.newInstance();
            int col = 0;
            try {
                //循环每行单元格
                for (; col < cellCount; col++) {
                    String cellValue = getCellValue(row.getCell(col));
                    if (null != cellValue) {
                        this.setEntity(entity, instance, titleToAttr.get(headTitle.get(col)), cellValue);
                    }
                }
                result.add(instance);
            } catch (Exception e) {
                String message="第" + (i + 1) + "行，" + headTitle.get(col) + "字段，数据错误!";
                log.info(message);
                throw new IllegalArgumentException(message);
            }
        }
        excel.close();
        return result;
    }

    /**
     * 加载Excel的标题栏
     *
     * @param sheet
     * @return 返回列序号和对于的标题名称Map
     */
    private Map<Integer, String> loadHeadTitle(Sheet sheet) {
        Map<Integer, String> map = new HashMap<Integer, String>();
        Row row = sheet.getRow(0);
        int cellCount = row.getLastCellNum();
        for (int i = 0; i < cellCount; i++) {
            String value = row.getCell(i).getStringCellValue();
            if (null == value) {
                throw new RuntimeException("Excel导入：标题栏不能为空！");
            }
            map.put(i, value);
        }
        return map;
    }

    /**
     * 获取表格列的值
     *
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell) {
        if (null == cell||"".equals(cell)) {
            return "";
        }
        String value = "";
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_BLANK:
                //空值
                value = null;
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                // 判断当前的cell是否为Date
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = dateFormat.format(cell.getDateCellValue());
                } else {
                    value = String.valueOf((long) cell.getNumericCellValue());
                }
                break;
            case XSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                log.info("不支持带有函数的单元格!");
                throw new IllegalArgumentException("不支持带有函数格式的单元格!");
            default:
                log.info("单元格格式有误!");
                throw new IllegalArgumentException("单元格格式有误!");
        }

        return value;
    }


    /**
     * 封装实体值
     *
     * @param clazz
     * @param instance
     * @param pro
     * @param value
     * @param <T>
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws Exception
     */
    private <T> void setEntity(Class<?> clazz, T instance, String pro, String value) throws SecurityException, NoSuchMethodException, Exception {
        String innerPro = null;
        String outterPro = null;
        if (pro.contains(".")) {
            String[] pros = pro.split("\\.");
            outterPro = pros[0];
            innerPro = pros[1];
            // 将成员变量的类型存储到仓库中
            storage.storeClass(instance.hashCode() + outterPro, clazz.getDeclaredMethod(this.initGetMethod(outterPro), null).getReturnType());
        }
        String getMethod = this.initGetMethod(outterPro != null ? outterPro : pro);
        Class<?> type = clazz.getDeclaredMethod(getMethod, null).getReturnType();
        Method method = clazz.getMethod(this.initSetMethod(outterPro != null ? outterPro : pro), type);
        if (type == String.class) {
            method.invoke(instance, value);
        } else if (type == int.class || type == Integer.class) {
            method.invoke(instance, Integer.parseInt("".equals(value) ? "0" : value));
        } else if (type == long.class || type == Long.class) {
            method.invoke(instance, Long.parseLong("".equals(value) ? "0" : value));
        } else if (type == float.class || type == Float.class) {
            method.invoke(instance, Float.parseFloat("".equals(value) ? "0" : value));
        } else if (type == double.class || type == Double.class) {
            method.invoke(instance, Double.parseDouble("".equals(value) ? "0" : value));
        } else if (type == Date.class) {
            method.invoke(instance, dateFormat.parse(value));
        } else if (type == boolean.class || type == Boolean.class) {
            method.invoke(instance, Boolean.parseBoolean("".equals(value) ? "false" : value));
        } else if (type == byte.class || type == Byte.class) {
            method.invoke(instance, Byte.parseByte(value));
        } else {
            // 引用类型数据
            Object ins = storage.getInstance(instance.hashCode() + outterPro);
            this.setEntity(ins.getClass(), ins, innerPro, value);
            method.invoke(instance, ins);
        }
    }

    /**
     * 初始化set方法
     *
     * @param field
     * @return
     */
    private String initSetMethod(String field) {
        return "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /**
     * 初始化get方法
     *
     * @param field
     * @return
     */
    private String initGetMethod(String field) {
        return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }


    /**
     * 处理2003、2007兼容问题
     *
     * @param inp
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    private Workbook create(InputStream inp) throws IOException, InvalidFormatException {
        if (!inp.markSupported()) {
            inp = new PushbackInputStream(inp, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(inp)) {
            return new HSSFWorkbook(inp);
        }
        if (POIXMLDocument.hasOOXMLHeader(inp)) {
            return new XSSFWorkbook(OPCPackage.open(inp));
        }
        throw new IllegalArgumentException("当前Excel版本poi不能解析!");
    }


    /**
     * 存储bean中的bean成员变量内部类
     */
    class BeanStorage {

        private Map<String, Object> instances = new HashMap<String, Object>();

        public void storeClass(String key, Class<?> clazz) throws Exception {
            if (!instances.containsKey(key)) {
                instances.put(key, clazz.newInstance());
            }
        }

        public Object getInstance(String key) {
            return instances.get(key);
        }
    }

}

