package com.qifeng.will.es.service.impl;


import com.alibaba.fastjson.JSON;
import com.qifeng.will.es.config.ElasticSearchConfig;
import com.qifeng.will.es.dto.OperateLog;
import com.qifeng.will.es.service.EsOperateLogService;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsOperateLogServiceImpl implements EsOperateLogService {

    @Autowired
    @Qualifier("esSafeClient")
    private RestHighLevelClient esSafeRestClient;



    public IndexResponse insertLog(String index, OperateLog entity) {
        IndexRequest request = new IndexRequest(index);
        request.id(entity.getId());
        request.source(JSON.toJSONString(entity), XContentType.JSON);

        try {
            return this.esSafeRestClient.index(request, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    @Override
    public BulkResponse insertBatch(String index, List<OperateLog> list) {
        BulkRequest request = new BulkRequest();
        for (OperateLog item : list) {
            String _json = JSON.toJSONString(item);
            String _id = item.getId();
            IndexRequest indexRequest = new IndexRequest(index).id(_id).source(_json, XContentType.JSON);
            request.add(indexRequest);
        }
        try {
            return esSafeRestClient.bulk(request, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OperateLog> getLogs(String index, SearchSourceBuilder searchSourceBuilder, Class<T> resultClass) {
        SearchRequest request = new SearchRequest(index);
        request.source(searchSourceBuilder);
        try {
            SearchResponse response = esSafeRestClient.search(request, ElasticSearchConfig.COMMON_OPTIONS);

            SearchHits hits1 = response.getHits();
            SearchHit[] hits2 = hits1.getHits();
            List<OperateLog> retList = new ArrayList<>(hits2.length);
            for (SearchHit hit : hits2) {
                String strJson = hit.getSourceAsString();
                retList.add(JSON.parseObject(strJson, OperateLog.class));
            }
            return retList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
