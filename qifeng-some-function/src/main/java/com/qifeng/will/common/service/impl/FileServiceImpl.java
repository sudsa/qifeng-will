package com.qifeng.will.common.service.impl;

import com.qifeng.will.common.dao.DictDao;
import com.qifeng.will.common.dao.FileDao;
import com.qifeng.will.common.domain.DictDO;
import com.qifeng.will.common.dto.FileDTO;
import com.qifeng.will.common.service.FileService;
import com.qifeng.will.common.util.ZipDownloadUtil;
import com.qifeng.will.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Service
public class FileServiceImpl implements FileService {


	@Resource
	private FileDao fileDao;

	@Resource
	private DictDao dictDao;


	@Override
	public List<FileDTO> listByBelongIdAndTypeAndFileTag(Long belongId, String type, String fileTag) {
		return fileDao.listByBelongIdAndTypeAndFileTag(belongId,type,fileTag);
	}

	@Override
	public FileDTO uploadFile(MultipartFile file, String type, String fileTag) throws IOException {
		FileDTO busFile = new FileDTO();
		Date now = new Date();
		String fileName = file.getOriginalFilename();
		String preName = fileName.substring(0, fileName.lastIndexOf("."));
		String extName = fileName.substring(fileName.lastIndexOf(".") + 1);

		// 上传文件到Fast
//		StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
//		String groupPath = storePath.getFullPath();
//		String serverPath = fdfsConfig.getUrl();
		String serverPath = "https://img-blog.csdnimg.cn";
		String groupPath = "/20200404131040657.png";

		String fileUrl=null;
		// 处理fileUrl
		if (serverPath.endsWith("/")) {
			fileUrl = serverPath + groupPath;
		} else {
			fileUrl = serverPath + "/" + groupPath;
		}
		//封装数据
		busFile.setFileName(preName)
				.setFileExt(extName)
				.setFileFull(fileName)
				.setUrl(fileUrl)
				.setType(type)
				.setServerPath(serverPath)
				.setGroupPath(groupPath)
				.setFileTag(fileTag)
				.setCreateTime(now)
				.setUpdateTime(now)
				.setDelFlag(0);
		fileDao.save(busFile);
		//处理数据
		DictDO dict = dictDao.getByTypeAndValue(type, fileTag);
		busFile.setTypeName(dict.getDescription());
		busFile.setFileTagName(dict.getName());
		busFile.setUuid(UUID.randomUUID().toString());
		return busFile;
	}

	@Override
	public void downloadZip(Long belongId, String type, String fileTag, String zipFilename, HttpServletResponse response) throws IOException {

		Map<String, Object> map = new HashMap<>(4);
		map.put("belongId", belongId);
		map.put("type", type);
		map.put("fileTag", fileTag);
		List<FileDTO> list = fileDao.list(map);
		List<byte[]> contentList = new ArrayList<>();
		List<String> filenameList = new ArrayList<>();
		String typeName = "";
		if (!list.isEmpty()) {
			typeName = list.get(0).getTypeName();
			list.forEach(x -> {
				log.info("文件名称和url,name:[{}],url:[{}] ", x.getFileName(), x.getUrl());
				if (StringUtil.isNotBlank(x.getUrl())) {
					contentList.add(ZipDownloadUtil.downloadUrlConvertByte(x.getUrl()));
					filenameList.add(getFilename(x));
				}
			});
		}

		ZipDownloadUtil.downloadZip(response, zipFilename, contentList, filenameList);

	}

	@Override
	public void batchDownloadZip(List<Long> belongIdList, String zipFilename, HttpServletResponse response) throws IOException {
		//数据为空
		if (belongIdList.isEmpty()) {
			return;
		}
		List<byte[]> contentList = new ArrayList<>();
		List<String> filenameList = new ArrayList<>();
		//遍历每一个资产包
		belongIdList.forEach(belongId->{
			Map<String,Object> param = new HashMap<>(2);
			param.put("belongId", belongIdList.get(0));
			List<FileDTO> list = fileDao.list(param);
			//按文件类型分组
			Map<String, List<FileDTO>> map = list.stream().collect(Collectors.groupingBy(FileDTO::getTypeName));
			String partlPackageNo = "";
			//遍历分组
			map.forEach((key, value)->{
				String typeName=key;
				value.forEach(x->{
					log.info("文件名称和url,name:[{}],url:[{}] ", x.getUrl(), x.getFileName());
					contentList.add(ZipDownloadUtil.downloadUrlConvertByte(x.getUrl()));
					filenameList.add(partlPackageNo+"/"+typeName+"/"+getFilename(x));
				});
			});
		});
		//封装压缩包名字
		zipFilename=zipFilename+"_"+ LocalDateTime.now().toString()+".zip";
		ZipDownloadUtil.downloadZip(response, zipFilename, contentList, filenameList);

	}


	/**
	 * 获取文件名
	 *
	 * @param file
	 * @return
	 */
	private String getFilename(FileDTO file){
		return file.getFileName()+"_"+ LocalTime.now().toString()+"."+file.getFileExt();
	}


}
