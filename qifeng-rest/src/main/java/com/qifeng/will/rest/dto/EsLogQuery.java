package com.qifeng.will.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qifeng.will.es.dto.EsPageDto;
import com.qifeng.will.es.dto.OperateLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 杜志诚
 * @create 2021/8/10 19:19
 */
@Data
public class EsLogQuery {

    @NotNull(message = "索引名称不能为空")
    @ApiModelProperty("索引名称")
    private String indexName;
    @ApiModelProperty("业务id")
    private String businessId;

    @ApiModelProperty("业务类型")
    private String businessType;

    @ApiModelProperty("操作类型（01：添加 02：编辑）")
    private String operateType;

    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    @ApiModelProperty("分页对象")
    private EsPageDto<OperateLog> page;
}
