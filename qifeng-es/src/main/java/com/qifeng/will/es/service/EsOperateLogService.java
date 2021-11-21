package com.qifeng.will.es.service;

import com.qifeng.will.es.dto.OperateLog;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;

public interface EsOperateLogService {

    List<OperateLog> getLogs(String index, SearchSourceBuilder searchSourceBuilder, Class<T> resultClass);

    IndexResponse insertLog(String index, OperateLog operateLog);

    BulkResponse insertBatch(String index, List<OperateLog> list);
}
