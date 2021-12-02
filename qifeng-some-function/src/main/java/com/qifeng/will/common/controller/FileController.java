package com.qifeng.will.common.controller;

import com.qifeng.will.common.dao.DictDao;
import com.qifeng.will.common.dao.FileDao;
import com.qifeng.will.common.domain.DictDO;
import com.qifeng.will.common.domain.FileDO;
import com.qifeng.will.common.dto.FileDTO;
import com.qifeng.will.common.service.FileService;
import com.qifeng.will.common.util.ZipDownloadUtil;
import com.qifeng.will.utils.JsonUtil;
import com.qifeng.will.utils.R;
import com.qifeng.will.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文件表
 * 
 * @author  howill.zou
 * @email  howill.zou@sina.com
 * @date 2020-04-30 09:43:57
 */
@Slf4j
@Controller
@RequestMapping("/common/file")
public class FileController {

	@Autowired
	private FileService fileService;
	@Resource
	private FileDao fileDao;
	@Resource
	private DictDao dictDao;

	/**
	 * 文件Demo
	 *
	 * @return
	 */
	@GetMapping()
	private  String File(){
	    return "common/file/file";
	}


	/**
	 * 查询文件列表
	 *
	 * @param belongId
	 * @param type
	 * @param fileTag
	 * @return
	 */
	@GetMapping("/listByBelongIdAndTypeAndFileTag")
	@ResponseBody
	public List<FileDTO> listByTypeAndFileTag(@RequestParam("belongId") Long belongId,
											  @RequestParam("type") String type,
											  @RequestParam("fileTag") String fileTag){
		List<FileDTO> busFileDOList=new ArrayList<>();
		try {
			busFileDOList =fileService.listByBelongIdAndTypeAndFileTag(belongId,type,fileTag);
		} catch (Exception e) {
			log.info("查询文件信息,异常信息：[{}]",e.getMessage());
		}
		return busFileDOList;
	}

	/**
	 * 上传文件页面
	 *
	 * @param type
	 * @param model
	 * @return
	 */
	@GetMapping("/uploadFilePage/{type}/{tableId}")
	public String uploadFilePage(@PathVariable("type") String type,@PathVariable("tableId") String tableId, Model model) {
		List<DictDO> busDictList = dictDao.listByType(type);
		model.addAttribute("type",type);
		model.addAttribute("tableId",tableId);
		model.addAttribute("busDictList",busDictList);
		return "common/file/uploadFile";
	}


	/**
	 * 上传文件1
	 *
	 * @param file
	 * @param type
	 * @param fileTag
	 * @return
	 */
	@PostMapping("/uploadFile1/{type}/{fileTag}")
	@ResponseBody
	public R uploadFile1(@RequestParam("file") MultipartFile file, @PathVariable("type") String type, @PathVariable("fileTag") String fileTag) {
		log.info("上传文件开始,type:[{}],fileTag:[{}]",type,fileTag);
		if (file == null || StringUtil.isBlank(file.getOriginalFilename())) {
			log.info("未选择文件");
			return R.error("请选择需要上传的文件");
		}
		Map<String, Object> map = new HashMap<>(2);
		try {
			FileDTO busFile = fileService.uploadFile(file, type, fileTag);
			map.put("busFile", busFile);
			log.info("上传文件结束,type:[{}],fileTag:[{}],id:[{}],fileName:[{}]",type,fileTag,busFile.getId(),busFile.getFileFull());
		} catch (IOException e) {
			log.info("上传文件IO流异常：异常信息:[{}]",e.getMessage());
			return R.error("上传文件IO流异常：异常信息:"+e.getMessage());
		}catch (Exception e){
			log.info("上传文件异常：异常信息:[{}]",e.getMessage());
			return R.error("上传文件异常：异常信息:"+e.getMessage());
		}
		return R.ok(map);
	}

	/**
	 * 上传文件2
	 *
	 * @param file
	 * @param type
	 * @param fileTag
	 * @return
	 */
	@PostMapping("/uploadFile2")
	@ResponseBody
	public R uploadFile2(@RequestParam("file") MultipartFile file,@RequestParam("type") String type, @RequestParam("fileTag") String fileTag) {
		log.info("上传文件开始,type:[{}],fileTag:[{}]",type,fileTag);
		if (file == null  || StringUtil.isBlank(file.getOriginalFilename())) {
			log.info("未选择文件");
			return R.error("请选择需要上传的文件");
		}
		Map<String, Object> map = new HashMap<>(2);
		try {
			FileDTO busFile = fileService.uploadFile(file, type, fileTag);
			map.put("busFile", busFile);
			log.info("上传文件结束,type:[{}],fileTag:[{}],id:[{}],fileName:[{}]",type,fileTag,busFile.getId(),busFile.getFileFull());
		} catch (IOException e) {
			log.info("上传文件IO流异常：异常信息:[{}]",e.getMessage());
			return R.error("上传文件IO流异常：异常信息:"+e.getMessage());
		}catch (Exception e){
			log.info("上传文件异常：异常信息:[{}]",e.getMessage());
			return R.error("上传文件异常：异常信息:"+e.getMessage());
		}
		return R.ok(map);
	}


	/**
	 * 删除文件
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@GetMapping("/deleteFile/{id}")
	public R deleteFile(@PathVariable("id") Long id) {
		try {
			fileDao.updateDelFlagById(id);
		} catch (Exception e) {
			log.info("删除文件信息,异常信息：[{}]",e.getMessage());
			return R.error("异常信息："+e.getMessage());
		}
		return R.ok();
	}


	/**
	 * 预览文件
	 *
	 * @param url
	 * @param model
	 * @return
	 */
	@GetMapping("/previewFile")
	public String previewFile(@RequestParam("url") String url, Model model) {
		model.addAttribute("url",url);
		return "common/file/previewFile";
	}


	/**
	 * 下载
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/downloadFile/{id}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("id") Long id){
		FileDO file = fileDao.get(id);
		log.info("下载文件开始,文件id:[{}],文件名称:[{}]",file.getId(),file.getFileFull());
		byte[] content = ZipDownloadUtil.downloadUrlConvertByte(file.getUrl());
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.set("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(file.getFileFull(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.info("文件名编码集转换异常,异常信息[{}]",e.getMessage());
		}
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> entity = new ResponseEntity<>(content, headers, statusCode);
		return entity;
	}


	/**
	 * 打包下载1
	 *
	 * @param belongId
	 * @param type
	 * @param fileTag
	 */
	@GetMapping("/downloadZip1")
	@ResponseBody
	public void downloadZip1(@RequestParam("belongId") Long belongId,
							 @RequestParam("type") String type,
							 @RequestParam("fileTag") String fileTag,
							 HttpServletResponse response) {
		log.info("打包下载文件开始,文件所属id:[{}],文件总类型:[{}],文件类型:[{}]",belongId,type,fileTag);
		try {
			fileService.downloadZip(belongId,type,fileTag,"压缩包",response);
			log.info("打包下载文件结束,文件所属id:[{}],文件总类型:[{}],文件类型:[{}]",belongId,type,fileTag);
		} catch (Exception e) {
			log.error("打包下载文件异常，异常信息:[{}]", e.getMessage());
		}
	}

	/**
	 *
	 * 打包下载2
	 *
	 * @param belongIds
	 * @param zipFilename
	 */
	@GetMapping("/downloadZip2")
	@ResponseBody
	public void downloadZip2(@RequestParam("belongIds[]") List<Long> belongIds,
							 @RequestParam("zipFilename") String zipFilename,
							 HttpServletResponse response) {
		log.info("打包下载文件开始,文件所属id集合:[{}]", JsonUtil.beanToJson(belongIds));
		try {
			fileService.batchDownloadZip(belongIds,zipFilename,response);
			log.info("打包下载文件结束,文件所属id集合:[{}],压缩文件名称:[{}]", JsonUtil.beanToJson(belongIds),zipFilename);
		} catch (Exception e) {
			log.error("打包下载文件异常，异常信息:[{}]", e.getMessage());
		}

	}



}
