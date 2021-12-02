package com.qifeng.will.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.qifeng.will.es.config.ElasticSearchConfig;
import com.qifeng.will.es.dto.EsBaseDto;
import com.qifeng.will.es.dto.EsPageDto;
import com.qifeng.will.es.dto.OperateDto;
import com.qifeng.will.es.service.OperateLogService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Service
public class OperateLogServiceImpl implements OperateLogService {
	
	private static Logger logger = LoggerFactory.getLogger(OperateLogServiceImpl.class);

	@Autowired
	@Qualifier("esSafeClient")
    RestHighLevelClient esSafeRestClient;

	public void addLog2Es(OperateDto operateDto)  {
		try {
			EsBaseDto<OperateDto> esBaseDto = new EsBaseDto<>();
			esBaseDto.setId(UUID.randomUUID().toString());
			esBaseDto.setData(operateDto);
			insertOrUpdateOne("operatelog_1", esBaseDto);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public boolean indexExist(String index) {
		GetIndexRequest request = new GetIndexRequest(index);
		request.local(false);
		request.humanReadable(true);
		request.includeDefaults(false);
		try {
			//用ElasticSearchConfig.COMMON_OPTIONS替换了RequestOptions.DEFAULT
			return esSafeRestClient.indices().exists(request, ElasticSearchConfig.COMMON_OPTIONS);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public boolean createIndex(String index, EsBaseDto entity) {
		//新建一个索引
		IndexRequest indexRequest = new IndexRequest(index);
		//指定id
		indexRequest.id(entity.getId());
		// 转为json字符
		String u = JSON.toJSONString(entity.getData());
		// 保存json
		indexRequest.source(u, XContentType.JSON);

		// 执行操作
		try {
			IndexResponse idxResp = esSafeRestClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
			return true;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	public <T> EsPageDto<T> pageQuery(String index, SearchSourceBuilder searchSourceBuilder,
									  TypeReference<T> reference) {
		SearchRequest request = new SearchRequest(index);
		CountRequest countRequest = new CountRequest(index);
		countRequest.source(new SearchSourceBuilder());
		request.source(searchSourceBuilder);
		try {
			SearchResponse response = esSafeRestClient.search(request, ElasticSearchConfig.COMMON_OPTIONS);
			CountResponse countResponse = esSafeRestClient.count(countRequest, RequestOptions.DEFAULT);
			long length = countResponse.getCount();
			SearchHits hits1 = response.getHits();
			SearchHit[] hits2 = hits1.getHits();
			List<T> retList = new ArrayList<>(hits2.length);
			for (SearchHit hit : hits2) {
				String strJson = hit.getSourceAsString();
				retList.add(JSON.parseObject(strJson, reference));
			}
			EsPageDto<T> pageDto = new EsPageDto<>();
			pageDto.setTotal(length);
			pageDto.setList(retList);
			return pageDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public IndexResponse insertOrUpdateOne(String index, EsBaseDto entity) {
		IndexRequest request = new IndexRequest(index);
		request.id(entity.getId());
		request.source(JSON.toJSONString(entity.getData()), XContentType.JSON);
		logger.info(JSON.toJSONString(request));
		try {
			return esSafeRestClient.index(request, ElasticSearchConfig.COMMON_OPTIONS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public BulkResponse insertBatch(String index, List<EsBaseDto> list) {
		BulkRequest request = new BulkRequest();
		for (EsBaseDto item : list) {
			String _json = JSON.toJSONString(item.getData());
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

	public <T> List<T> search(String index, SearchSourceBuilder searchSourceBuilder, Class<T> resultClass) {
		SearchRequest request = new SearchRequest(index);
		request.source(searchSourceBuilder);
		try {
			SearchResponse response = esSafeRestClient.search(request, ElasticSearchConfig.COMMON_OPTIONS);

			SearchHits hits1 = response.getHits();
			SearchHit[] hits2 = hits1.getHits();
			List<T> retList = new ArrayList<>(hits2.length);
			for (SearchHit hit : hits2) {
				String strJson = hit.getSourceAsString();
				retList.add(JSON.parseObject(strJson, resultClass));
			}
			return retList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public AcknowledgedResponse deleteIndex(String index) {
		try {
			IndicesClient indicesClient = esSafeRestClient.indices();
			DeleteIndexRequest request = new DeleteIndexRequest(index);
			AcknowledgedResponse response = indicesClient.delete(request, ElasticSearchConfig.COMMON_OPTIONS);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public BulkByScrollResponse deleteByQuery(String index, QueryBuilder builder) {
		DeleteByQueryRequest request = new DeleteByQueryRequest(index);
		request.setQuery(builder);
		request.setBatchSize(10000);
		request.setConflicts("proceed");
		try {
			BulkByScrollResponse response = esSafeRestClient.deleteByQuery(request, ElasticSearchConfig.COMMON_OPTIONS);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> BulkResponse deleteBatch(String index, Collection<T> idList) {
		BulkRequest request = new BulkRequest();
		for (T t : idList) {
			request.add(new DeleteRequest(index, t.toString()));
		}
		try {
			BulkResponse response = esSafeRestClient.bulk(request, ElasticSearchConfig.COMMON_OPTIONS);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
