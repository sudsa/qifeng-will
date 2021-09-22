package com.hanxiaozhang.watermarkutil;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;



/**
 * 功能描述: <br>
 * 〈PDF文件水印〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/15
 */
@Slf4j
public class PDFWatermarkPrint implements WatermarkPrint {



    /**
     * 文件水印
     * @param inputFile      需要加水印PDF文件
     * @param outputStream   加完水印后的PDF文件
     * @param waterMark      页面水印文字
     * @param footWaterMark  页脚水印文字
     * @throws IOException
     * @throws DocumentException
     */
    @Override
    public void print(File inputFile, BufferedOutputStream outputStream,String waterMark,String footWaterMark) throws IOException, DocumentException {

        // 将pdf文件先加水印然后输出
        setWatermark(outputStream, inputFile,waterMark, footWaterMark );

    }

    /**
     * 输出流水印
     * @param inputFilePath   需要加水印PDF文件路径
     * @param outputStream    加完水印后的PDF文件
     * @param waterMark       页面水印文字
     * @param footWaterMark   页脚水印文字
     * @throws IOException
     * @throws DocumentException
     */
    @Override
    public void print(String inputFilePath,BufferedOutputStream outputStream,String waterMark,String footWaterMark) throws IOException, DocumentException {

        setWatermark(outputStream, Paths.get(inputFilePath).toFile(),waterMark, footWaterMark);

    }

    /**
     *
     * @param bos            输出文件的位置
     * @param input          原PDF位置
     * @param waterMark      页面添加水印
     * @param footWaterMark  页脚添加水印
     * @throws DocumentException
     * @throws IOException
     */
    public void setWatermark(BufferedOutputStream bos, String input, String waterMark,String footWaterMark)
            throws DocumentException, IOException {

        setWatermark(bos, Paths.get(input).toFile(),waterMark,footWaterMark);

    }

    /**
     *
     * @param bos            输出文件的位置
     * @param inputFile      原PDF文件
     * @param waterMark      页面添加水印
     * @param footWaterMark  页脚添加水印
     * @throws DocumentException
     * @throws IOException
     */
    private void setWatermark(BufferedOutputStream bos, File inputFile, String waterMark,String footWaterMark)
            throws DocumentException, IOException {
        PdfReader pdfReader = new PdfReader(new FileInputStream(inputFile));
        PdfStamper pdfStamper = new PdfStamper(pdfReader, bos);
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        for(int i=1; i<= pdfReader.getNumberOfPages(); i++){
            //相关水印大小设置，大小不合适修改这里 todo
            PdfContentByte content = pdfStamper.getUnderContent(i);
            content.beginText();
            content.setColorFill(BaseColor.LIGHT_GRAY);
            content.setFontAndSize(baseFont, 16);
            //x宽，y高
            for (int x = 60; x < 1000; x+=100) {
                for (int y = 40; y < 1000; y+=100) {
                    //页面水印
                    content.showTextAligned(Element.ALIGN_CENTER, waterMark, x,y, 50);
                }
                x+=25;
            }
            content.setColorFill(BaseColor.BLACK);
            content.setFontAndSize(baseFont, 7);
            content.showTextAligned(Element.ALIGN_CENTER, footWaterMark, 300, 10, 0);
            content.endText();
        }
        pdfStamper.close();
    }




}
