package com.bpaas.doc.framework.base.util.lbs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: ruilink
 * @description: 定位api响应对象
 * @author: ligy16
 * @create: 2018-08-14 15:47
 **/
@Getter
@Setter
@ToString
public class LbsApiResponse implements Serializable {

    private static final long serialVersionUID = 1429693044401055727L;
    private Integer status;
    private Result result;
}
