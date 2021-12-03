package com.bpaas.doc.framework.base.util;

import java.util.Date;
import java.util.Random;

import com.bpaas.doc.framework.base.security.Caesar;

/**
 * Copyright (C) huyangsoft Co., Ltd.
 * 随机数
 * @author huyang
 * @date 2015年11月3日 
 *
 */
public class RandomUtil {
    
    /**
     * 产生序列号
     * @param prefix 前缀
     * @param length 序列号长度
     * @return
     */
    public static String genSn(String prefix, int length){
        int prefixLength = CheckEmptyUtil.isEmpty(prefix)?0:prefix.length();
        int snLength = length - prefixLength;
        int key=0;
        int seed = 0;
        Random random  = new Random();  
        Date now = new Date();
        String seedStr = DateUtil.formatDate(now, "HHmmSSS");
        String keyFormat = null;
        String seedFormat = null;
        switch(snLength){
        case 9:
           key = random.nextInt(10);
            seed = random.nextInt(100);  
            keyFormat = String.format("%01d", key);
            seedFormat = String.format("%02d", seed);
            break;
        case 11:
            key = random.nextInt(100);
            seed = random.nextInt(100); 
            keyFormat = String.format("%02d", key);
            seedFormat = String.format("%02d", seed);
            break;
        case 12:
            key = random.nextInt(100); 
            seed = random.nextInt(1000); 
            keyFormat = String.format("%02d", key);
            seedFormat = String.format("%03d", seed);
            break;
        default:
            key = random.nextInt(100);
            seed = random.nextInt(100);  
            keyFormat = String.format("%02d", key);
            seedFormat = String.format("%02d", seed);
            break;
        }
        String rand = getRandomId(Long.parseLong(seedStr), key, seed);

        // 解决部分情况下生成数据不足位数的问题
        int sub = length - prefix.length() -  rand.length() - keyFormat.length() - seedFormat.length();
        if (sub > 0) {
            rand += String.format("%0" + sub + "d", random.nextInt(10 * sub));
        }
        StringBuffer sb = new StringBuffer(prefix);
        sb.append(rand).append(keyFormat).append(seedFormat);
        return sb.toString();
    }
    
    /**
     * 根据日期产生随机序列号
     * 格式为yyMMdd+12位唯一无序数
     * @return
     */
    public static String genSnByDate(){
        Date now = new Date();
        String prefix = DateUtil.formatDate(now, "yyMMdd");
        return genSn(prefix, 18);
    }
    
    /**
     * 生成9位随机数
     * @param target
     * @return
     */
    public static String getRandomId(long target, int key, int seed){
       
        String  table = "0123456789";  
        String ret = null,  
                num = String.format("%05d", target);  
            Caesar caesar = new Caesar(table, seed);
            num = caesar.encode(key, num);  
            ret = num ;
              
            return ret;  
    }
    
    /**
     * 产生定长的随机数
     * @param @param length
     * @param @return   
     * @return String    
     * @throws
     */
    public static String genRandom(int length){
        Random random = new Random();
        int rInt = 0;
        switch(length){
        case 4:
            rInt = random.nextInt(10000);
            break;
        default:
            rInt = random.nextInt(1000000);
            break;
        }
        return genRandom(length, rInt);
    }

    public static String genRandom(int length, int rInt){
        StringBuffer sb = new StringBuffer();
        //生成六位随机数
        for(int i = 0; i < length - String.valueOf(rInt).length(); i++){
            sb.append("0");
        }       
        return sb.append(rInt).toString();     
    }
}
