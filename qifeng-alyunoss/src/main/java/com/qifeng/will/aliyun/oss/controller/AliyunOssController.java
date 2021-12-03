package com.qifeng.will.aliyun.oss.controller;

import com.qifeng.will.aliyun.oss.service.AliyunOssService;
import com.qifeng.will.web.view.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 阿里云oss请求处理
 * 该接口对外提供接口服务
 * @author huyang
 *
 */
@Controller
@RequestMapping("aliyun/oss")
public class AliyunOssController {
	@Autowired
	private AliyunOssService aliyunOssService;

	/**
	 * 获取文件的url路径
	 */
	@RequestMapping(value = "getUrl", method = RequestMethod.GET)
	public ResponseResult<String> getUrl(String bucketName, String fileName, boolean isOpen){
		String url = aliyunOssService.getUrl(bucketName, fileName, isOpen);
		if (CheckEmptyUtil.isEmpty(url)){
			return new ResponseResult<String>(false,"阿里源OSS连接异常,请稍后重试");
		} else{
			ResponseResult<String> responseResult = new ResponseResult<String>(true);
			responseResult.setData(url);
			return responseResult;
		}
	}
	
}
