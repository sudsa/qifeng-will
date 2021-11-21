package com.qifeng.will.rest.dto;

import lombok.experimental.UtilityClass;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author 杜志诚
 * @create 2021/8/11 15:48
 */
@UtilityClass
public class EsQueryBuilderUtil {

    public void rangeQuery(String name, String startTime, String endTime, BoolQueryBuilder must) {
        must.must(QueryBuilders.rangeQuery(name).from(startTime).to(endTime));
    }

    public void matchPhraseQuery(String name, Object value, BoolQueryBuilder must) {
        if (value != null) {
            must.must(QueryBuilders.matchPhraseQuery(name, value));
        }
    }
}
