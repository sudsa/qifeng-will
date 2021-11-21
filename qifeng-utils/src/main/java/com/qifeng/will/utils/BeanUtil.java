package com.hanxiaozhang.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 〈一句话功能简述〉<br>
 * 〈Bean工具类〉
 *
 * @author hanxinghua
 * @create 2019/10/14
 * @since 1.0.0
 */
public class BeanUtil {


    /**
     * bean拷贝，空值不用复制
     *
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNoValuePropertyNames(src));
    }

    /**
     * 获取值为空的属性
     *
     * @param source
     * @return
     */
    private static String[] getNoValuePropertyNames (Object source) {

        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();

        Set<String> noValuePropertySet = new HashSet<>();
        Arrays.stream(pds).forEach(pd -> {
            Object propertyValue = beanWrapper.getPropertyValue(pd.getName());
            if (null==propertyValue) {
                noValuePropertySet.add(pd.getName());
            } else {
                //属性是否为集合类型（List，Set，Query）
                if (Iterable.class.isAssignableFrom(propertyValue.getClass())) {
                    Iterable iterable = (Iterable) propertyValue;
                    Iterator iterator = iterable.iterator();
                    if (!iterator.hasNext()){
                        noValuePropertySet.add(pd.getName());
                    }
                }
                //属性是否为Map类型
                if (Map.class.isAssignableFrom(propertyValue.getClass())) {
                    Map map = (Map) propertyValue;
                    if (map.isEmpty()){
                        noValuePropertySet.add(pd.getName());
                    }
                }
            }
        });
        String[] result = new String[noValuePropertySet.size()];
        return noValuePropertySet.toArray(result);
    }
}
