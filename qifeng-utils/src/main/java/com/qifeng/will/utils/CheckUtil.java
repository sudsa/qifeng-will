package com.qifeng.will.utils;

import java.math.BigDecimal;

/**
 * 〈一句话功能简述〉<br>
 * 〈校验工具类〉
 *
 * @author hanxinghua
 * @create 2020/6/28
 * @since 1.0.0
 */
public class CheckUtil {

    /**
     * 判断对象是否不为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjectIsNotNull(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof String) {
            return !"".equals(object);
        } else if (object instanceof BigDecimal) {
            return BigDecimal.ZERO.compareTo((BigDecimal) object) < 0;
        } else if (object instanceof Integer) {
            return (Integer) object >= 0;
        } else if (object instanceof Long) {
            return (Long) object > 0;
        } else {
            return true;
        }
    }

}
