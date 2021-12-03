package com.bpaas.doc.framework.base.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 校验图片流
 */
public class ImageCheck {

    private static final Logger logger = LoggerFactory
            .getLogger(ImageCheck.class);
    
	/**
	 * 图片文件
	 */
	public interface Image {

		public static final String PNG_0 = "89504E47";

		public static final String JPG_0 = "FFD8FFE0";
		
		public static final String JPG_1 = "FFD8FFE1";
		
		public static final String PNG_1 = "5B0CED35";
		
		public static final String PNG_2 = "8BBE4A50";
	}

    public static int imageCheck(byte[] src, double fileSize) throws IOException {
    	return imageCheck(src, fileSize, -1, -1);	
    }

    /**
     * 检查文件流是否为图片
     * @Description: TODO
     * @param @param src
     * @param @param fileSize 文件大小
     * @param @param width -1时不检查
     * @param @param height -1时不检查
     * @param @return
     * @param @throws IOException   
     * @return int    
     * @throws
     */
    public static int imageCheck(byte[] src, double fileSize, int width, int height) throws IOException {
        // logger.info("length = " + src.length);

        if (src.length > fileSize) {
            return 1;
        }
        // 校验数据是否有效
        if (src == null || src.length <= 0) {
            return 1;
        }
        // 读取文件数据
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            // byte转int
            int v = src[i] & 0xFF;
            // int装hex
            String h = Integer.toHexString(v);
            // 低于两位高位补0
            while (h.length() < 2) {
                h = "0" + h;
            }
            // 拼装数据
            sb.append(h);
        }
        String str = sb.toString().toUpperCase();
        // 获取文件魔术数字
        String mn = str.substring(0, 8);
        // 校验魔术数字
        logger.debug("图片格式校验mn: {} " + mn);
        if (!ImageCheck.Image.JPG_0.equalsIgnoreCase(mn)
                && !ImageCheck.Image.JPG_1.equalsIgnoreCase(mn)
                && !ImageCheck.Image.PNG_0.equalsIgnoreCase(mn)
                && !ImageCheck.Image.PNG_1.equalsIgnoreCase(mn)
                && !ImageCheck.Image.PNG_2.equalsIgnoreCase(mn)) {
            return 2;
        }
        logger.info("检查图片长宽属性");
        if(height > 0 && width > 0){
	        // byte[]转Image
	        InputStream is = new ByteArrayInputStream(src, 0, src.length);
	        BufferedImage img = ImageIO.read(is);
	        // 校验图片长宽属性
	        if (img.getHeight(null) <= 0 || img.getWidth(null) <= 0) {
	            return 2;
	        }
	        int imgHeight = img.getHeight();
	        int imgWidth = img.getWidth();
	        logger.debug("图片格式校验height: {} " + height);
	        logger.debug("图片格式校验width: {} " + width);
	        if (imgHeight != height && imgWidth != width) {
	            return 3;
	        }
        }
        return 0;
    }
}
