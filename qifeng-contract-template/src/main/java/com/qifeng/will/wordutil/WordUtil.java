package com.hanxiaozhang.wordutil;


import com.hanxiaozhang.constant.ContractConstant;
import com.hanxiaozhang.pdfutil.CommonUtil;
import com.hanxiaozhang.utils.DateUtil;
import com.hanxiaozhang.utils.FileUtil;
import com.hanxiaozhang.utils.StringUtil;
import com.hanxiaozhang.watermarkutil.PDFWatermarkPrint;
import com.hanxiaozhang.watermarkutil.WatermarkPrint;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.*;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


/**
 * 功能描述: <br>
 * 〈word工具 word转pdf, pdf转word〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/17
 */
@Slf4j
public class WordUtil {

    /**
     * 代表应用程序和URL之间的通信链接
     */
    HttpURLConnection httpURLConnection = null;

    /**
     * 生成pdf路径名
     */
    private String pdfName;

    /**
     * 添加水印的pdf路径名
     */
    private String waterPdfName;

    /**
     * 文件上传到fast的地址
     */
    private String fileUrl;

    private File pdfFile;

    private File waterPdfFile;

    private static class Holder{
        private final static WordUtil INSTANCE = new WordUtil();
    }

    private WordUtil() {
    }

    public static WordUtil getInstance() {
        return Holder.INSTANCE;
    }


    /**
     * 执行生成PDF文件
     */
    public String execute(String fastUrl, String waterMark, Map<String, String> initPdfData, String fileName) {
        this.fileUrl=null;
        this.pdfName = CommonUtil.getDir() + fileName + "-" + DateUtil.format2() + ".pdf";
        log.info("WordUtil--execute-pdfName: {}",pdfName);
        this.waterPdfName = CommonUtil.getDir() + fileName + "-" + DateUtil.format2() + "-1.pdf";
        log.info("WordUtil--execute-waterPdfName: {}",waterPdfName);
        this.pdfFile = new File(pdfName);
        this.waterPdfFile = new File(waterPdfName);
        InputStream is = null;
        try {

            is = getInputStreamByPath(fastUrl);

            // word 转 pdf
            WordUtil.wordToPDF(is, initPdfData, pdfFile, ContractConstant.DOCX);

            watermark(waterMark);

            FileUtil.uploadFile(fileToByte(waterPdfFile), CommonUtil.getDir(), fileName +".pdf");

        } catch (Exception e) {
            log.error("wordToPDF『"+pdfName+"』", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }

            } catch (Exception e) {
                log.error("关闭流异常", e);
            }
        }
        log.info("返回生成pdf--execute-fileUrl{}",fileUrl);
        return fileUrl;
    }


    /**
     * word转pdf
     *
     * @param srcInput  doc输入流
     * @param data      需要替换的doc文档变量数据
     * @param targetPdf pdf文件
     * @param fileType  doc或者docx
     * @throws Exception
     */
    public static void wordToPDF(InputStream srcInput, Map<String, String> data, File targetPdf, String fileType) throws Exception {
        if (fileType.endsWith(ContractConstant.DOCX)) {
            byte[] pdfData = bindDocxDataAndToPdf(srcInput, data);
            FileUtils.writeByteArrayToFile(targetPdf, pdfData);
        } else if (fileType.endsWith(ContractConstant.DOC)) {
            byte[] pdfData = bindDocDataAndToPdf(srcInput, data);
            FileUtils.writeByteArrayToFile(targetPdf, pdfData);
        } else {
            throw new RuntimeException("文件格式不正确");
        }
    }


    /**
     * 替换docx文件内容,并转换成PDF
     *
     * @param input docx文件流
     * @param data  替换内容
     * @return pdf文件流
     * @throws Exception
     */
    private static byte[] bindDocxDataAndToPdf(InputStream input, Map<String, String> data) throws Exception {
        byte[] replacedContent = replaceDocxContent(input, data);
        byte[] pdfData = docxToPdf(new ByteArrayInputStream(replacedContent));
        return pdfData;
    }


    /**
     * 替换doc文件内容,并转换成PDF
     *
     * @param input docx文件流
     * @param data  替换内容
     * @return pdf文件流
     * @throws Exception
     */
    private static byte[] bindDocDataAndToPdf(InputStream input, Map<String, String> data) throws Exception {
        byte[] pdfData = new byte[0];
        return pdfData;
    }


    /**
     * docx转成pdf
     *
     * @param docxStream docx文件流
     * @return 返回pdf数据
     * @throws Exception
     */
    private static byte[] docxToPdf(InputStream docxStream) throws Exception {
        ByteArrayOutputStream targetStream = null;
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(docxStream);
            PdfOptions options = PdfOptions.create();
            // 中文字体处理
            options.fontProvider(new IFontProvider() {
                @Override
                public Font getFont(String familyName, String encoding, float size, int style, Color color) {
                    try {
                        BaseFont bfChinese = createFont();
                        Font fontChinese = new Font(bfChinese, size, style, color);
                        if (familyName != null) {
                            fontChinese.setFamily(familyName);
                        }
                        return fontChinese;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

            });

            targetStream = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(doc, targetStream, options);
            return targetStream.toByteArray();
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            IOUtils.closeQuietly(targetStream);
        }
    }


    /**
     * doc转成pdf
     *
     * @param docStream doc文件流
     * @return 返回pdf数据
     * @throws Exception
     */
    private static byte[] docToPdf(InputStream docStream) {
        ByteArrayOutputStream targetStream = new ByteArrayOutputStream();
        // todo 待完善doc转成pdf
        return targetStream.toByteArray();
    }


    /**
     * 替换docx内容
     *
     * @param in  docx输入流
     * @param map 替换键值对
     * @return 返回替换后的文件流
     * @throws Exception
     */
    private static byte[] replaceDocxContent(InputStream in, Map<String, String> map) throws Exception {
        // 读取word模板
        XWPFDocument hdt = null;
        ByteArrayOutputStream out = null;
        try {
            hdt = new XWPFDocument(in);
            // 替换段落内容
            List<XWPFParagraph> paragraphs = hdt.getParagraphs();
            if (map != null && !map.isEmpty()) {
                replaceParagraphsContent(paragraphs, map);
            }

            // 替换表格内容
            List<XWPFTable> tables = hdt.getTables();
            // 读取表格
            for (XWPFTable table : tables) {
                int rcount = table.getNumberOfRows();
                // 遍历表格中的行
                for (int i = 0; i < rcount; i++) {
                    XWPFTableRow row = table.getRow(i);
                    // 遍历行中的单元格
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFParagraph> cellParagraphs = cell.getParagraphs();
                        if (map != null && !map.isEmpty()) {
                            replaceParagraphsContent(cellParagraphs, map);
                        }
                    }
                }
            }

            out = new ByteArrayOutputStream();
            hdt.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        } finally {
            IOUtils.closeQuietly(out);
        }
    }


    /**
     * 替换段落内容
     *
     * @param paragraphs
     * @param map
     */
    private static void replaceParagraphsContent(List<XWPFParagraph> paragraphs, Map<String, String> map) {
        for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String text = run.getText(0);
                if (text != null) {
                    boolean isSetText = false;
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String key = entry.getKey();
                        // 在配置文件中有这个关键字对应的键
                        if (text.indexOf(key) != -1) {
                            String value = entry.getValue();
                            if (value == null) {
                                throw new RuntimeException(key + "对应的值不能为null");
                            }
                            // 文本替换
                            text = text.replace(key, value);
                            isSetText = true;
                        }
                    }
                    if (isSetText) {
                        run.setText(text, 0);
                    }
                }
            }
        }
    }


    /**
     * 创建字体
     *
     * @return
     */
    private static BaseFont createFont() {
        //仿宋体
        String font_cn = getChineseFont();
        BaseFont chinese = null;
        try {
            chinese = BaseFont.createFont(font_cn, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chinese;
    }


    /**
     * 获取中文字体位置
     *
     * @return
     */
    private static String getChineseFont() {
        // 仿宋体
        String font = "C:/windows/fonts/simfang.ttf";
        // 判断系统类型，加载字体文件
        java.util.Properties prop = System.getProperties();
        String osName = prop.getProperty("os.name").toLowerCase();
        if (osName.indexOf("linux") > -1) {
            font = "/usr/share/fonts/simsun/simfang.ttf";
        }else if(osName.indexOf("mac os x") > -1){
            font="/Users/Downloads/simfang.ttf";
        }
        if (!new File(font).exists()) {
            throw new RuntimeException("字体文件不存在,影响导出pdf中文显示！" + font);
        }
        return font;
    }


    /**
     * 将文件转换成byte数组
     *
     * @param tradeFile
     * @return
     */
    public static byte[] fileToByte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * 添加水印
     *
     * @param waterMark
     */
    private void watermark(String waterMark) {
        WatermarkPrint watermarkPrint = new PDFWatermarkPrint();
        try {
            watermarkPrint.print(pdfFile, new BufferedOutputStream(new FileOutputStream(waterPdfFile)),waterMark, DateUtil.format1());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传到fast
     */
    private void uploadFast() {

        // 上传到文件服务器
        String remoteServerPath = "https://www.xxxx.com/";

        String remoteFilePath = null;

        try {
//            remoteFilePath = FastDfsUtil.upload(fileToByte(waterPdfFile), "pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.isBlank(remoteFilePath)) {
            throw new RuntimeException("文件上传失败，请重新上传!");
        }

        String fileUrl = "";
        // 保存到数据库
        if (remoteServerPath.endsWith("/")) {
            fileUrl = remoteServerPath + remoteFilePath;
        } else {
            fileUrl = remoteServerPath + "/" + remoteFilePath;
        }

        this.fileUrl = fileUrl;
    }


    /**
     * 获取输入流通过Url
     *
     * @param fileUrl
     * @return
     */
    private InputStream getInputStreamByUrl(String fileUrl) {
        InputStream is = null;
        for (int i = 0; i < 3; i++) {
            try {
                URL url = new URL(fileUrl);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(30000);
                is = httpURLConnection.getInputStream();
                break;
            } catch(Exception e) {
                log.error("打开文件链接异常, 第【{}】次", i+1 , e);
            }
        }

        return is;
    }

    /**
     * 获取输入流通过path
     *
     * @param path
     * @return
     */
    private InputStream getInputStreamByPath(String path){
        InputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }





}
