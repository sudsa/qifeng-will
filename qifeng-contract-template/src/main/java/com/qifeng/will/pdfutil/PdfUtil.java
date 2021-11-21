package com.hanxiaozhang.pdfutil;


import com.hanxiaozhang.contracttemplate.domain.ContractTemplateDO;
import com.hanxiaozhang.utils.DateUtil;
import com.hanxiaozhang.utils.StringUtil;
import com.hanxiaozhang.watermarkutil.PDFWatermarkPrint;
import com.hanxiaozhang.watermarkutil.WatermarkPrint;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author pangxin
 * @date 2019/9/3 11:21
 */
@Slf4j
public class PdfUtil {


    /**
     *   协议模板
      */
    private ContractTemplateDO contractTemplateDO;

    private PdfHelper pdf;

    private Map<String, Object> data;

    /**
     * 生成pdf路径名
     */
    private String pdfName;

    /**
     * 添加水印的pdf路径名
     */
    private String waterPdfName;


    private File pdfFile;

    private File waterPdfFile;

    /**
     * 文件上传到fast的地址
     */
    private String fileUrl;

    /**
     * 水印文字
     */
    private String watermark;


    private static class Holder{
        private static PdfUtil INSTANCE = new PdfUtil();
    }


    private PdfUtil(){}

    public static PdfUtil getInstance() {
        return Holder.INSTANCE;
    }


    /**
     * 执行生成PDF文件
     */
    public String execute(ContractTemplateDO contractTemplateDO, Map<String, Object> templateData, String watermark, String fileName) {
        this.contractTemplateDO = contractTemplateDO;
        pdfName = CommonUtil.getDir() + fileName + "-" + DateUtil.format2() + ".pdf";
        waterPdfName = CommonUtil.getDir() + fileName + "-" + DateUtil.format2() + "-1.pdf";
        this.pdf = PdfHelper.instance(pdfName);
        this.waterPdfFile = new File(waterPdfName);
        this.data = templateData;
        this.watermark = watermark;
        prepare();
        valid();
        initDate();
        createPdf();
        watermark(watermark);
        //uploadFast();

        return fileUrl;
    }

    /**
     * 预处理
     */
    private void prepare() {
    }

    /**
     * 校验
     */
    private void valid() {
    }

    /**
     * 初始化参数
     */
    private void initDate() {
    }

    /**
     * 创建PDF
     */
    private void createPdf() {
        boolean checkFile = false;
        File pdfFile = new File(pdfName);
        try {
            if (!pdfFile.exists()) {
                pdfFile.mkdir();
            }
            String out = FreemarkerUtil.renderTemplate(contractTemplateDO.getTemplateContent(), this.data);
            ProtocolHelper.templateHtml(out, pdf);
            checkFile = true;
        } catch (IOException e) {
            log.error("解析模板出错",e);
            throw new RuntimeException("解析模板出错");
        } catch (Exception e) {
            log.error("生成pdf出错",e);
        }
        if (!checkFile) {
            throw new RuntimeException("pdf生成的路径不存在...");
        }
        pdf.exportPdf();
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
     * 打印水印
     *
     * @param watermark
     */
    private void watermark(String watermark) {
        WatermarkPrint watermarkPrint = new PDFWatermarkPrint();
        File file = new File(pdfName);
        try {
            watermarkPrint.print(file, new BufferedOutputStream(new FileOutputStream(waterPdfFile)),watermark, DateUtil.format2());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
