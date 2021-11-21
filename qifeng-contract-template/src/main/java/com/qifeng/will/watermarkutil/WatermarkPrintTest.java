package com.hanxiaozhang.watermarkutil;


import com.itextpdf.text.DocumentException;

import java.io.*;
import java.nio.file.Paths;


/**
 * 功能描述: <br>
 * 〈文件水印单元测试〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/15
 */
public class WatermarkPrintTest {

    public static void main(String[] args) throws IOException, DocumentException {
        WatermarkPrint watermarkPrint = new PDFWatermarkPrint();
        //原始文件
        File originalFile = Paths.get("springboot-contract-template/templatedoc/OriginalWatermarkTestDoc.pdf").toFile();
        //目标文件
        File targetFile = Paths.get("springboot-contract-template/templatedoc/WatermarkTestDoc.pdf").toFile();
        //添加水印
        watermarkPrint.print(originalFile, new BufferedOutputStream(new FileOutputStream(targetFile)), "made in hanxiaozhang", "2020-02-15 21:09:45");

    }


}
