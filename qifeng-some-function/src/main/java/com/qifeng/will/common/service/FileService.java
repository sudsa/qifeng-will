package com.qifeng.will.common.service;

import com.qifeng.will.common.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件表
 * 
 * @author hanxinghua
 * @email hanxinghua2015@sina.com
 * @date 2020-04-30 09:43:57
 */
public interface FileService {


	/**
	 * 数据字典形式通过type和fileTag查找文件
	 *
	 * @param belongId
	 * @param type
	 * @param fileTag
	 * @return
	 */
	List<FileDTO> listByBelongIdAndTypeAndFileTag(Long belongId, String type, String fileTag);

	/**
	 * 上传文件
	 *
	 * @param file
	 * @param type
	 * @param fileTag
	 * @return
	 * @throws IOException
	 */
	FileDTO uploadFile(MultipartFile file, String type, String fileTag) throws IOException;


	/**
	 * 下载压缩包文件
	 *
	 * @param belongId
	 * @param type
	 * @param fileTag
	 * @param zipFilename
	 * @param response
	 * @throws IOException
	 */
	void downloadZip(Long belongId, String type,String fileTag,String zipFilename, HttpServletResponse response) throws IOException;


	/**
	 * 批量下载压缩包文件
	 *
	 * @param belongIdList
	 * @param zipFilename
	 * @param response
	 * @throws IOException
	 */
	void batchDownloadZip(List<Long> belongIdList, String zipFilename, HttpServletResponse response) throws IOException;

}
