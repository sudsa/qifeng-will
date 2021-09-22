package com.hanxiaozhang.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping("req")
public class ReqGetInpstmController {

    @RequestMapping("file")
    public String getInputStream(@RequestParam("files")MultipartFile files, HttpServletRequest request){
        try {
            //获取消息体得总长度
            int totalBytes = request.getContentLength();
            String conextpath = request.getContextPath();
            String contentType = request.getContentType();
            StringBuffer sb = new StringBuffer();
            InputStream inputStream = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while((s=br.readLine())!=null){
                sb.append(s);
            }

            String strr = sb.toString();
            if(StringUtils.isEmpty(strr)){
                if(StringUtils.isNotEmpty(request.getQueryString())){
                    strr = request.getRequestURI()+"?"+request.getQueryString();
                }else{
                    strr = request.getRequestURI();
                }
            }
            System.out.println(strr);
            return strr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "here back";
    }


}
