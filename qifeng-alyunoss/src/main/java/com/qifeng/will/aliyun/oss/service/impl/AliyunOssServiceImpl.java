package com.qifeng.will.aliyun.oss.service.impl;


import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qifeng.will.aliyun.oss.service.AliyunOssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;

/**
 * 阿里云oss操作组件
 * @author huyang
 *
 */
@Component
public class AliyunOssServiceImpl implements AliyunOssService {
	
	Logger logger = LoggerFactory.getLogger(AliyunOssServiceImpl.class);

	@Value("${oss.endPoint}")
	private String OSS_ENDPOINT;
	@Value("${oss.accessKey}")
	private String OSS_ACCESSKEY;
	@Value("${oss.accessSecret}")
	private String OSS_ACCESSSECRET;
	@Value("${oss.fileUpload}")
	private String OSS_FILEUPLOAD;
	
	@Override
	public void upload2Oss(MultipartFile fileData, String fileName, String bucketName) {
		OSSClient ossClient = null;
		try{
			ossClient = new OSSClient(OSS_ENDPOINT, OSS_ACCESSKEY, OSS_ACCESSSECRET);
		} catch(Exception e){
			logger.error("oss连接异常",e);
			return ;
		}
		// 上传文件
		try{
			ossClient.putObject(bucketName, OSS_FILEUPLOAD + fileName, new ByteArrayInputStream(fileData.getBytes()));
			logger.debug("fileName: {}" , fileName);
		} catch(Exception e){
			logger.error("oss上传文件失败",e);
		}
		ossClient.shutdown();
	}
	
	@Override
	public void deleteFileByOSS(String fileName, String bucketName) {
		OSSClient ossClient = null;
		try{
			ossClient = new OSSClient(OSS_ENDPOINT, OSS_ACCESSKEY, OSS_ACCESSSECRET);
		} catch(Exception e){
			logger.error("oss连接异常",e);
		}
		List<String> keys = new ArrayList<String>();
        keys.add(OSS_FILEUPLOAD + fileName);
        DeleteObjectsResult deleteObjectsResult = ossClient
                .deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        // 关闭client
        ossClient.shutdown();
	}
	
	@Override
	public String getUrl(String bucketName, String fileName, boolean isOpen) {
		OSSClient ossClient = null;
		try{
			ossClient = new OSSClient(OSS_ENDPOINT, OSS_ACCESSKEY, OSS_ACCESSSECRET);
		} catch(Exception e){
			logger.error("oss连接异常",e);
			return null;
		}
		// 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 100);
     // 生成URL
        String key = OSS_FILEUPLOAD + fileName;
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
        	if(isOpen) {
				return (url.toString().substring(0,url.toString().indexOf("://")+3)+url.getAuthority().toString()+url.getPath().toString());
			} else {
        		return url.toString();
			}
        }
        ossClient.shutdown();
        return null;
		
	}
	
	
}
