package com.qifeng.will.base.util.lbs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: ruilink
 * @description: PoiRegions
 * @author: ligy16
 * @create: 2018-08-14 15:49
 **/

@Getter
@Setter
@ToString
public class PoiRegions implements Serializable {

    private static final long serialVersionUID = -4663425008002600245L;
    private String direction_desc;
    private String name;
    private String tag;
    private String uid;
}
