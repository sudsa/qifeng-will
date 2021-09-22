package com.hanxiaozhang.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 〈一句话功能简述〉<br>
 * 〈动态创建数据源〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {

        if(null==DynamicDataSourceContextHolder.get()){
            log.info("Current use DataSource to：[{}]","Default DataSource");
        }else {
            log.info("Current use DataSource to：[{}]",DynamicDataSourceContextHolder.get());
        }

        return DynamicDataSourceContextHolder.get();
    }

}