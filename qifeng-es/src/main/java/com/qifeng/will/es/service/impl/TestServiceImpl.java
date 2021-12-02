package com.qifeng.will.es.service.impl;

import org.apache.http.HttpHost;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl {

    @Autowired
    @Qualifier("esSafeClient")
    private RestHighLevelClient esSafeRestClient;

    private HttpHost[] esHosts = new HttpHost[]{
            new HttpHost("localhost", 9200)
    };
    private RestHighLevelClient client = null;
    private BoolQueryBuilder boolQueryBuilder = null;

    @Before
    public void init() throws Exception {
        // 2.创建BoolQueryBuilder对象
        boolQueryBuilder = new BoolQueryBuilder();
        // 3.设置boolQueryBuilder条件
        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders
                .matchPhraseQuery("key_word", "广东");
        MatchPhraseQueryBuilder matchPhraseQueryBuilder2 = QueryBuilders
                .matchPhraseQuery("key_word", "湖人");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders
                .rangeQuery("postdate")
                .from("2016-01-01 00:00:00");
        // 子boolQueryBuilder条件条件，用来表示查询条件or的关系
        BoolQueryBuilder childBoolQueryBuilder = new BoolQueryBuilder()
                .should(matchPhraseQueryBuilder)
                .should(matchPhraseQueryBuilder2);
        // 4.添加查询条件到boolQueryBuilder中
        boolQueryBuilder
                .must(childBoolQueryBuilder)
                .must(rangeQueryBuilder);
    }

    // 测试SearchSourceBuilder的搜索
    //@Test
    public void test01() throws Exception {

        // 1.创建并设置SearchSourceBuilder对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 查询条件--->生成DSL查询语句
        searchSourceBuilder.query(boolQueryBuilder);
        // 第几页
        searchSourceBuilder.from(0);
        // 每页多少条数据
        searchSourceBuilder.size(100);
        // 获取的字段（列）和不需要获取的列
        searchSourceBuilder.fetchSource(new String[]{"postdate", "key_word"}, new String[]{});
        // 设置排序规则
        searchSourceBuilder.sort("postdate", SortOrder.ASC);
        // 设置超时时间为2s
        searchSourceBuilder.timeout(new TimeValue(2000));

        // 2.创建并设置SearchRequest对象
        SearchRequest searchRequest = new SearchRequest();
        // 设置request要搜索的索引和类型
        searchRequest.indices("spnews").types("news");
        // 设置SearchSourceBuilder查询属性
        searchRequest.source(searchSourceBuilder);

        // 3.查询
        SearchResponse searchResponse = client.search(searchRequest,null);
        System.out.println(searchResponse.toString());
    }


}
