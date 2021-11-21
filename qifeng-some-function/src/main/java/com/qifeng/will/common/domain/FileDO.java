package com.hanxiaozhang.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;



/**
 * 文件表
 * 
 * @author hanxinghua
 * @email hanxinghua2015@sina.com
 * @date 2020-04-30 09:43:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FileDO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//文件名
	private String fileName;
	//文件扩展名
	private String fileExt;
	//文件全名
	private String fileFull;
	//URL地址
	private String url;
	//文件服务器地址
	private String serverPath;
	//文件服务器路径
	private String groupPath;
	//文件属于
	private Long belongId;
	//文件附属类型
	private String type;
	//文件标签
	private String fileTag;
	//
	private Long createBy;
	//创建时间
	private Date createTime;
	//
	private Long updateBy;
	//更新时间
	private Date updateTime;
	//
	private String remark;
	//0 未删除 1已删除
	private Integer delFlag;

}
