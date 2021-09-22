package com.hanxiaozhang.watermarkutil;

import com.itextpdf.text.DocumentException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;


/**
 * 功能描述: <br>
 * 〈文件水印接口〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/15
 */
public interface WatermarkPrint {
    /**
     * 文件水印
     * @param inputFile      需要加水印PDF文件
     * @param outputStream   加完水印后的PDF文件
     * @param waterMark      页面水印文字
     * @param footWaterMark  页脚水印文字
     * @throws IOException
     * @throws DocumentException
     */
    void print(File inputFile, BufferedOutputStream outputStream, String waterMark, String footWaterMark) throws IOException, DocumentException;

    /**
     * 输出流水印
     * @param inputFilePath   需要加水印PDF文件路径
     * @param outputStream    加完水印后的PDF文件
     * @param waterMark       页面水印文字
     * @param footWaterMark   页脚水印文字
     * @throws IOException
     * @throws DocumentException
     */
    void print(String inputFilePath, BufferedOutputStream outputStream, String waterMark, String footWaterMark) throws IOException, DocumentException;

}
