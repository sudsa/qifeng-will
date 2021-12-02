package com.qifeng.will.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 功能描述: <br>
 * 〈文件工具类〉
 *
 * @Author:howill.zou
 * @Date: 2020/2/16
 */
@Slf4j
public class FileUtil {

    /**
     * 重命名为UUID
     *
     * @param fileName
     * @return
     */
    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    /**
     * 上传文件
     *
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {

        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }


    /**
     * 创建临时文件
     *
     * @param inputStream 输入文件流
     * @param name        文件名
     * @param ext         扩展名
     * @param tmpDirFile  临时文件夹目录
     * @return
     * @throws IOException
     */
	public static File createTempFile(InputStream inputStream, String name, String ext, File tmpDirFile) throws IOException {

	    File resultFile = File.createTempFile(name, '.' + ext, tmpDirFile);
	    resultFile.deleteOnExit();
	    return resultFile;

	}


    /**
     * 删除指定路径下的文件
     *
     * @param fileName 文件路径名称
     * @return
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * 删除文件夹里面的所有文件
     *
     * @param path 文件夹路径 如 c:/fqf
     */
    public static void deleteAllFile(String path) {

        File file = new File(path);

        if (!file.exists()) {
            return;
        }

        if (!file.isDirectory()) {
            return;
        }

        String[] tempList = file.list();

        File temp = null;

        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                deleteAllFile(path + "/" + tempList[i]);
                // 再删除空文件夹
                deleteFolder(path + "/" + tempList[i]);
            }
        }
    }


    /**
     * 删除文件夹及里面的文件
     *
     * @param folderPath 文件夹路径及名称 如c:/fqf
     */
    public static void deleteFolder(String folderPath) {
        try {
            // 删除完里面所有内容
            deleteAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            // 删除空文件夹
            myFilePath.delete();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    /**
     * 复制单个文件
     *
     * @param oldPath 源文件路径
     * @param newPath 复制后路径
     * @return 文件大小
     */
    public static int copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                //读入原文件
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    //字节数 文件大小
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
            return bytesum;
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 复制文件流到新的文件
     *
     * @param inStream 文件流
     * @param file     新文件
     * @return 是否复制成功
     */
    public static boolean copyInputStreamToFile(final InputStream inStream, File file) throws IOException {
        int bytesum = 0;
        int byteread = 0;
        byte[] buffer = new byte[1024];
        FileOutputStream fs = new FileOutputStream(file);
        while ((byteread = inStream.read(buffer)) != -1) {
            //字节数 文件大小
            bytesum += byteread;
            fs.write(buffer, 0, byteread);
        }
        inStream.close();
        fs.close();
        return true;
    }


    /**
     * 判断文件是否是图像文件
     *
     * @param fileName
     * @return
     */
    public static boolean isImage(String fileName) {
        boolean valid = true;
        try {
            Image image = ImageIO.read(new File(fileName));
            if (image == null) {
                valid = false;
                log.info("The file {} could not be opened , it is not an image.", fileName );
            }
        } catch (IOException ex) {
            valid = false;
            log.info("The file {} could not be opened , an error occurred.", fileName );
        }
        return valid;
    }


    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos > -1) {
        	String temp=fileName.substring(pos + 1).toLowerCase();
        	pos=temp.indexOf("?");
        	if(pos>-1){
        		return temp.substring(0,pos);
        	}
            return temp.trim().replaceAll("\"","");
        }
        return "";
    }


    /**
     * 判断文件夹是否存在，不存在创建文件夹
     *
     * @param filePath
     */
    public static void isFileExist(String filePath) {
    	
    	File file = new File(filePath);
    	 if(!file.exists())    
    	 {    
    	     try {    
    	         file.mkdir();    
    	     } catch (Exception e) {
    	         e.printStackTrace();    
    	     }    
    	 } 
    }


}
