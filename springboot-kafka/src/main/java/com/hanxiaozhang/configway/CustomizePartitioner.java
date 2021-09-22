package com.hanxiaozhang.configway;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/*
 *功能描述 kafka分区器
 * @author zouhw02
 * @date 2021/8/24
 * @param
 * @return
 */
public class CustomizePartitioner implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        // 自定义分区规则(这里假设全部发到0号分区)
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
