package com.bpaas.doc.framework.base.util;

import com.bpaas.doc.framework.base.common.BaseConstant;
import com.bpaas.doc.framework.base.util.lbs.LbsApiResponse;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @program: bpaas baseframe
 * @description: LBS位置服务工具类
 * @author: huyang
 * @create: 2018-12-13 15:19
 **/
public class LBSUtil {

    private static final Logger logger = LoggerFactory.getLogger(LBSUtil.class);

    /**
     * 通过经纬度查询省市区
     * http://lbsyun.baidu.com/index.php?title=webapi/guide/webservice-geocoding-abroad
     * http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=35.658651,139.745415&output=json&pois=1&ak=您的ak //GET请求
     * @param lat
     * @param lng
     * @return
     */
    public static Map<String, String> getLocationInfoByLatLng(double lat, double lng) {
        Map<String, String> locationInfo = Maps.newHashMap();
        try {
            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
            messageConverters.add(converter);
            restTemplate.setMessageConverters(messageConverters);
            String url = "http://api.map.baidu.com/geocoder/v2/?location="+lat+","+lng+"&output=json&ak=" + BaseConstant.LBS_AK;
            LbsApiResponse lbsApiResponse = restTemplate.getForObject(url, LbsApiResponse.class);
            logger.info("getLocationInfoByLatLng lbsApiResponse:{}", lbsApiResponse);
            if (lbsApiResponse.getStatus() == 0) {
                locationInfo.put("province", lbsApiResponse.getResult().getAddressComponent().getProvince());
                locationInfo.put("city", lbsApiResponse.getResult().getAddressComponent().getCity());
                locationInfo.put("area", lbsApiResponse.getResult().getAddressComponent().getDistrict());
                locationInfo.put("formatted_address",lbsApiResponse.getResult().getFormatted_address());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return locationInfo;
    }
}
