package com.qifeng.will.es.service;


import com.alibaba.fastjson.TypeReference;
import com.qifeng.will.es.dto.EsBaseDto;
import com.qifeng.will.es.dto.EsPageDto;
import com.qifeng.will.es.dto.OperateDto;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Collection;
import java.util.List;

public interface OperateLogService {

	boolean indexExist(String index);

	//向index插入或更新一条数据
	IndexResponse insertOrUpdateOne(String index, EsBaseDto dto);

	//向index进行批量插入
	BulkResponse insertBatch(String index, List<EsBaseDto> list);

	//根据条件查询index中匹配
	<T> List<T> search(String index, SearchSourceBuilder searchSourceBuilder, Class<T> resultClass);

	//删除index
	AcknowledgedResponse deleteIndex(String index);

	//根据条件批量删除
	BulkByScrollResponse deleteByQuery(String index, QueryBuilder builder);

	//批量删除
	<T> BulkResponse deleteBatch(String index, Collection<T> idList);

	boolean createIndex(String index, EsBaseDto entity);

	<T> EsPageDto<T> pageQuery(String index, SearchSourceBuilder searchSourceBuilder, TypeReference<T> reference);

	void addLog2Es(OperateDto operateDto);

}