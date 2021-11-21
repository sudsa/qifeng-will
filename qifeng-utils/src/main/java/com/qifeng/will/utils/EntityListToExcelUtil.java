package com.hanxiaozhang.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import static java.util.regex.Pattern.compile;


/**
 * 功能描述: <br>
 * 〈导出Excel〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/25
 */
public class EntityListToExcelUtil {


    private StringBuffer error = new StringBuffer(0);


    private EntityListToExcelUtil(){}

    private static class Holder{
        private static  EntityListToExcelUtil INSTANCE=new EntityListToExcelUtil();
    }

    public static EntityListToExcelUtil getInstance(){
        return Holder.INSTANCE;
    }


    /**
     * 将实体类列表entityList转换成excel
     * 2003- 版本的excel
     *
     * @param attrToTitle      包含headMapping信息，key为属性名，value为列名<br>
     * @param entityList
     * @param excel
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws IOException
     */
    @Deprecated
    private <T> boolean executeXLS(Map<String, String> attrToTitle, List<T> entityList, OutputStream excel) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        System.out.println(excel.toString());
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        int i = 0;
        List<String> proList = new ArrayList<String>();
        HSSFFont blueFont = workbook.createFont();
        blueFont.setColor(HSSFColor.BLUE.index);
        for (Map.Entry<String, String> entry : attrToTitle.entrySet()) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(entry.getValue());
            text.applyFont(blueFont);
            cell.setCellValue(text);
            proList.add(entry.getKey());
            i++;
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = entityList.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            for (i = 0; i < proList.size(); i++) {
                HSSFCell cell = row.createCell(i);
                String propertyName = proList.get(i);
                String textValue = null;
                try {
                    textValue = this.getPropertyValue(t, propertyName);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.error.append("第").append(index + 1).append("行，列名：").append(attrToTitle.get(propertyName)).append("，字段：").append(propertyName).append("，数据错误，跳过！").append("<br>");
                }
                // 利用正则表达式判断textValue是否全部由数字组成
                if (textValue != null) {
                    Pattern p = compile("^//d+(//.//d+)?$");
                    Matcher matcher = p.matcher(textValue);
                    if (matcher.matches()) {
                        // 是数字当作double处理
                        cell.setCellValue(Double.parseDouble(textValue));
                    } else {
                        HSSFRichTextString richString = new HSSFRichTextString(
                                textValue);
                        cell.setCellValue(richString);
                    }
                }
            }

        }
        workbook.write(excel);
        //关闭输出流
        excel.close();
        return true;
    }


    /**
     * 将实体类列表entityList转换成excel
     * 2007+ 版本的excel
     *
     * @param attrToTitle      包含headMapping信息，key为属性名，value为列名<br>
     * @param entityList
     * @param excel
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws IOException
     */
    public  <T> boolean executeXLSX(Map<String, String> attrToTitle, List<T> entityList, OutputStream excel) throws NoSuchMethodException,
            SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, IOException {

        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet("sheet1");
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(30);
        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);

        int i = 0;
        List<String> proList = new ArrayList<>();
        //设置单元格格式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        //设置字体
        XSSFFont blueFont = workbook.createFont();
        blueFont.setColor(IndexedColors.BLUE.getIndex());
        //设置表头
        Iterator<Map.Entry<String, String>> itr = attrToTitle.entrySet().iterator();
        while (itr.hasNext()){
            Map.Entry<String, String> entry = itr.next();
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(entry.getValue());
            text.applyFont(blueFont);
            cell.setCellValue(text);
            proList.add(entry.getKey());
            i++;
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = entityList.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，动态调用getXxx()方法得到属性值
            for (i = 0; i < proList.size(); i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                String propertyName = proList.get(i);
                String textValue = null;
                try {
                    textValue = this.getPropertyValue(t, propertyName);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.error.append("第").append(index + 1).append("行，列名：").append(attrToTitle.get(propertyName)).append("，字段：").append(propertyName).append("，数据错误，跳过！").append("<br>");
                }
                // 利用正则表达式判断textValue是否全部由数字组成
                if (textValue != null) {
                    Pattern p = compile("^//d+(//.//d+)?$");
                    Matcher matcher = p.matcher(textValue);
                    if (matcher.matches()) {
                        // 是数字当作double处理
                        cell.setCellValue(Double.parseDouble(textValue));
                    } else {
                        XSSFRichTextString richString = new XSSFRichTextString(textValue);
                        cell.setCellValue(richString);
                    }
                }
            }

        }
        workbook.write(excel);
        //关闭输出流
        excel.close();
        return true;
    }


    /**
     * 获取实体instance的propertyName属性的值
     *
     * @param instance
     * @param propertyName
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public <T> String getPropertyValue(T instance, String propertyName)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        String getMethodName = this.initGetMethod(propertyName);
        Class<?> tCls = instance.getClass();
        Method getMethod = null;
        Object value = null;

        getMethod = tCls.getMethod(getMethodName, new Class[]{});
        value = getMethod.invoke(instance, new Object[]{});

        String returnType = getMethod.getReturnType().getName();

        // 判断值的类型后进行强制类型转换
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String textValue = "";
        if(value==null){
            return textValue;
        }
        if ("java.util.Date".equals(returnType)) {
            textValue = dateFormat.format(value);
        } else {
            textValue = value.toString();
        }
        return textValue;
    }


    /**
     * 返回fiel属性的getXXX方法字符串
     *
     * @param field
     * @return
     */
    private String initGetMethod(String field) {
        return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }


    /**
     * @return true 存在错误，false 不存在错误
     */
    public boolean hasError() {
        return error.capacity() > 0;
    }


    /**
     * 获得错误信息
     *
     * @return
     */
    public StringBuffer getError() {
        return error;
    }




}
