package com.qifeng.will.rest.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.TypeReference;
import com.qifeng.will.es.dto.EsPageDto;
import com.qifeng.will.es.dto.OperateLog;
import com.qifeng.will.es.service.EsOperateLogService;
import com.qifeng.will.rest.dto.EsLogQuery;
import com.qifeng.will.rest.dto.EsQueryBuilderUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@RestController
@RequestMapping("elastic")
public class ElasticController {

    @Autowired
    private EsOperateLogService esOperateLogService;

    @RequestMapping("/insert")
    public String insertOperateLog(){
        OperateLog log = new OperateLog();
        log.setId(StrUtil.uuid());
        log.setBusinessId("businessId:"+log.getId());
        log.setOperateType("009");
        IndexResponse index = esOperateLogService.insertLog("operateLog", log);
        return index.toString();
    }
    public String insetBatch(){
        List<OperateLog> list = new ArrayList<>();
        IntStream.range(1,20).forEach(i->{
            OperateLog log = new OperateLog();
            log.setId(StrUtil.uuid());
            log.setBusinessId("businessId:"+"i "+log.getId());
            log.setOperateType(i+"009");
            log.setBusinessType(i+"");
            log.setOperId("howill.zou");
            log.setOperTime(DateUtil.today());
            list.add(log);
        });

        BulkResponse bulkResponse = esOperateLogService.insertBatch("operateLog", list);
        return bulkResponse.status().toString();
    }

    public String search(EsLogQuery esLogQuery){
        SearchSourceBuilder builder = new SearchSourceBuilder();
        EsPageDto<OperateLog> page = setPage(esLogQuery);
        builder.from(page.getPageNum());
        builder.size(page.getPageSize());
        BoolQueryBuilder must = QueryBuilders.boolQuery();
        EsQueryBuilderUtil.matchPhraseQuery("businessType", esLogQuery.getBusinessType(), must);
        EsQueryBuilderUtil.matchPhraseQuery("businessId", esLogQuery.getBusinessId(), must);
        EsQueryBuilderUtil.matchPhraseQuery("operateType", esLogQuery.getOperateType(), must);
        EsQueryBuilderUtil.rangeQuery("operTime", esLogQuery.getStartTime(), esLogQuery.getEndTime(), must);
        builder.sort(new FieldSortBuilder("operTime").order(SortOrder.DESC));
        builder.trackTotalHits(true);
        builder.query(must);
        EsPageDto<OperateLog> operateDtoEsPageDto = esOperateLogService.getLogs(esLogQuery.getIndexName(), builder,
                new TypeReference<OperateLog>() {});
        // 构建统返回的前端数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", operateDtoEsPageDto.getList());
        map.put("total", operateDtoEsPageDto.getTotal());
        map.put("pageIndex", page.getPageNum());
        map.put("pageSize", page.getPageSize());
        //return ResponseResult.genSuccessResp(map);
        return map.toString();
    }

    private EsPageDto<OperateLog> setPage(EsLogQuery esLogQuery) {
        if (esLogQuery.getPage() == null || esLogQuery.getPage().getPageNum() == null || esLogQuery.getPage().getPageSize() == null) {
            EsPageDto<OperateLog> page = new EsPageDto<>();
            page.setPageNum(0);
            page.setPageSize(10);
            return page;
        }
        return esLogQuery.getPage();
    }
}
