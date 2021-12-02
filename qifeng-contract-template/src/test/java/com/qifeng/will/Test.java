package com.qifeng.will;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试类〉
 *
 * @author hanxinghua
 * @create 2020/2/15
 * @since 1.0.0
 */
public class Test {

    public static void main(String[] args) {

        //路径测试：
        //Tips:
        // 1.相对路径;
        // 2.路径用"\"时，必须用转义写法"\\"，或者用"/";
        // 3.相对路径不能是"/"开头,否则抛系统找不到指定的路径异常（java.io.FileNotFoundException）
        String path ="/springboot-contract-template/templatedoc/PathTestDoc.txt";

        try {
            FileInputStream fis = new FileInputStream(path);
            byte b[] = new byte[1024];
            int read = 0;
            while ((read = fis.read(b)) != -1) {
                System.out.println(new String(b, 0, read));
            }
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
