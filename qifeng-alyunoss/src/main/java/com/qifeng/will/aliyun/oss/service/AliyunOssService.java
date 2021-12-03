package com.qifeng.will.aliyun.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface AliyunOssService {
	
	/**
	 * 上传文件到OSS中
	 * @param fileData 文件
	 * @param fileName 文件名
	 * @param bucketName oss存储目录
	 */
	public void upload2Oss(MultipartFile fileData, String fileName, String bucketName);
	
	/**
	 * 删除oss中的文件
	 * @param fileName 文件名
	 */
	public void deleteFileByOSS(String fileName, String bucketName);
	
	/**
	 * 获取oss中文件的url路径
	 * @param bucketName
	 * @param  fileName
	 */
	public String getUrl(String bucketName, String fileName, boolean isOpen);
}
