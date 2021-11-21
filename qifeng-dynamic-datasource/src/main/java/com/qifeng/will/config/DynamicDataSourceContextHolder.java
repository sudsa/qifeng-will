package com.hanxiaozhang.config;

import com.hanxiaozhang.enums.DataSourceKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据源上下文配置，用于切换数据源〉
 *
 *  Tips：ThreadLocal 解决线程安全问题，多线程程序的并发
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Slf4j
public class DynamicDataSourceContextHolder {


    private static final ThreadLocal<DataSourceKey> currentDatesource = new ThreadLocal<>();

    /**
     * 清除当前数据源
     */
    public static void clear() {
        currentDatesource.remove();
    }

    /**
     * 获取当前使用的数据源
     *
     * @return 当前使用数据源的ID
     */
    public static DataSourceKey get() {
        return currentDatesource.get();
    }

    /**
     * 设置当前使用的数据源
     *
     * @param value 需要设置的数据源ID
     */
    public static void set(DataSourceKey value) {
        currentDatesource.set(value);
    }

    /**
     * 设置从从库读取数据
     * Tips：没有配置，直接读DB_SLAVE1库 2019-08-13
     *
     */
    public static void setSlave() {
        if (RandomUtils.nextInt(0, 2) > 0) {
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE2);
            log.info("Random Use DataSource to：[{}]", "DB_Slave2");
        } else {
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE1);
            log.info("Random Use DataSource to：[{}]", "DB_Slave1");
        }
    }
}

