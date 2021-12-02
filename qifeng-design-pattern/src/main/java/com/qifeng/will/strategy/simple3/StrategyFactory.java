package com.qifeng.will.strategy.simple3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈策略工厂〉
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
@Component
public class StrategyFactory {

    /**
     * Spring会自动将Strategy接口的实现类注入到这个Map中，
     * key为bean id，value值则为对应的策略实现类
     *
     */
    @Autowired
    private Map<String, SimpleStrategy> strategyMap;


    /**
     * 通过名字获取策略实现类
     *
     * @param strategyName
     * @return
     */
    public SimpleStrategy getByName(String strategyName) {
        return strategyMap.get(strategyName);
    }
}
