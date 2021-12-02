package com.qifeng.will.common.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 功能描述: <br>
 * 〈下载文件工具类〉
 *
 * @Author:hanxinghua
 * @Date: 2020/4/26
 */
@Slf4j
public class ZipDownloadUtil {

    /**
     * @param response
     * @param zipFilename  zip的文件名
     * @param contentList  每个文件的字节流
     * @param filenameList 每个文件的文件名（如果想包含文件夹可命名为   文件夹名称/文件名.png 例:  高一(1)班/小陈.png）
     * @throws IOException
     */
    public static void downloadZip(HttpServletResponse response,
                                   String zipFilename,
                                   List<byte[]> contentList,
                                   List<String> filenameList) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String((zipFilename).getBytes(), "iso-8859-1"));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CheckedOutputStream cos = new CheckedOutputStream(output, new CRC32());
        ZipOutputStream zos = new ZipOutputStream(cos);


        for (int i = 0; i < contentList.size(); i++) {
            byte[] content = contentList.get(i);
            String filename = filenameList.get(i);

            //构建输入流
            BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(content));

            //创建文件（zip里面的文件）
            ZipEntry entry = new ZipEntry(filename);
            //放入文件
            zos.putNextEntry(entry);
            //写入文件
            IOUtils.copy(bis, zos);
            //关闭流
            bis.close();
        }

        zos.closeEntry();
        zos.close();
        //设置返回信息
        response.setHeader("Content-Length", String.valueOf(output.size()));
        IOUtils.copy(new ByteArrayInputStream(output.toByteArray()), response.getOutputStream());

        //创建完压缩文件后关闭流
        cos.close();
        output.close();
    }

    /**
     * 下载远程文件转byte
     *
     * @param dUrl
     * @return
     * @throws Exception
     */
    public static byte[] downloadUrlConvertByte(String dUrl) {
        synchronized (ZipDownloadUtil.class) {
            InputStream is = null;
            HttpURLConnection conn = null;
            byte[] bs = new byte[0];
            try {
                URL url = new URL(dUrl);
                conn = (HttpURLConnection) url.openConnection();
                //设置超时间为3秒  
                conn.setConnectTimeout(6 * 1000);
                //防止屏蔽程序抓取而返回403错误  
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                //得到输入流  
                is = conn.getInputStream();
                //获取自己数组  
                bs = readInputStream(is);
            } catch (IOException e) {
                log.info("读取Url的IO流异常,异常信息:[{}]",e.getMessage());
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    log.error("转成字节码失败,异常信息:[{}]", e.getMessage());
                }

                if (conn != null) {
                    conn.disconnect();
                }
            }
            return bs;
        }
    }

    /**
     *      * 从输入流中获取字节数组
     *      * @param inputStream
     *      * @return
     *      * @throws IOException
     *     
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }

        bos.flush();
        bos.close();

        return bos.toByteArray();
    }
}
