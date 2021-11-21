package com.qifeng.will.es.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 杜志诚
 * @create 2021/8/10 19:26
 */
@Data
public class EsPageDto<T> {

    private Integer pageNum;

    private Integer pageSize;

    private Long total;

    private List<T> list;
}
