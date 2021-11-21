package com.qifeng.will.es.dto;

import lombok.Data;

@Data
public class OperateLog {

    private String id;

    //业务id
    private String businessId;

    //业务类型
    private String businessType;

    //操作类型
    private String operateType;

    //操作人id
    private String operId;

    //操作时间
    private String operTime;
}
