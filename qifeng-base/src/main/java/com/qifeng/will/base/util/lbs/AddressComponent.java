package com.bpaas.doc.framework.base.util.lbs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: ruilink
 * @description: addressComponent
 * @author: ligy16
 * @create: 2018-08-14 15:51
 **/
@Getter
@Setter
@ToString
public class AddressComponent implements Serializable {

    private static final long serialVersionUID = 3967147668113031426L;
    private String country;
    private Integer country_code;
    private String country_code_iso;
    private String country_code_iso2;
    private String province;
    private String city;
    private Integer city_level;
    private String district;
    private String town;
    private String adcode;
    private String street;
    private String street_number;
    private String direction;
    private String distance;
}
