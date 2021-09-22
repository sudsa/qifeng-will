package com.qifeng.will.base.util.lbs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @program: ruilink
 * @description: 定位请求结果集
 * @author: ligy16
 * @create: 2018-08-14 15:47
 **/
@Getter
@Setter
@ToString
public class Result implements Serializable {

    private static final long serialVersionUID = 6979818908565724751L;
    private LbsLocation location;
    private String formatted_address;
    private String business;
    private AddressComponent addressComponent;
    private List<String> pois;
    private List<String> roads;
    private List<PoiRegions> poiRegions;
    private String sematic_description;
    private Integer cityCode;
}
