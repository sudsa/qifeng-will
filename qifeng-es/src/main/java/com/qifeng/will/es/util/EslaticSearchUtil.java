package com.qifeng.will.es.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.ScriptQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EslaticSearchUtil {


    //range query
    //范围查询
    //QueryBuilders.rangeQuery("hotelNo") .gt("10143262306")//大于 10143262306
    // .lt("101432623062055348221") //小于 101432623062055348221
    // .includeLower(true) //包括下界
    // .includeUpper(false); //包括上界
    public void rangeQuery(String name, Long startTime, Long endTime, BoolQueryBuilder must) {
        must.must(QueryBuilders.rangeQuery(name).from(startTime).to(endTime));
    }

    // exist query 查询字段不为null的文档 如查询字段name 不为null的数据
    public void existQuery(String name, BoolQueryBuilder must) {
        must.must(QueryBuilders.existsQuery(name));
    }

    // mustNot 查询没有字段或值为null或没有值的文档
    public void notExistQuery(String name, BoolQueryBuilder must) {
        must.mustNot(QueryBuilders.existsQuery(name));
    }

    //prefix query 匹配分词前缀 如果字段没分词，就匹配整个字段前缀
    public void prefixQuery(String name, String value,  BoolQueryBuilder must) {
        must.must(QueryBuilders.prefixQuery(name, value));
    }

    //matchAllQuery匹配所有
    //matchPhraseQuery对中文精确匹配
    //matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
    public void matchPhraseQuery(String name, Object value, BoolQueryBuilder must) {
        if (!Objects.isNull(value)) {
            must.must(QueryBuilders.matchPhraseQuery(name, value));
        }
    }

    //multiMatchQuery("text", "field1", "field2"..); 一个值匹配多个字段, field有通配符忒行
    public void multiMatchQuery(String field1, String field2,String field3,Object value, BoolQueryBuilder must) {
        if (!Objects.isNull(value)) {
            must.must(QueryBuilders.multiMatchQuery(value, field1, field2, field3));
        }
    }

    //matchPhraseQuery对中文精确匹配前缀
    public void matchPhrasePrefixQuery(String name, Object value, BoolQueryBuilder must) {
        if (!Objects.isNull(value)) {
            must.must(QueryBuilders.matchPhrasePrefixQuery(name, value));
        }
    }

    //分词精确查询
    public void termQuery(String name, Object value, BoolQueryBuilder must) {
        if (!Objects.isNull(value)) {
            must.must(QueryBuilders.termQuery(name, value));
        }
    }

    //terms Query 多term查询,匹配多个值
    public void termsQuery(String name, List<String> value, BoolQueryBuilder must) {
        if (CollectionUtil.isNotEmpty(value)) {
            must.must(QueryBuilders.termsQuery(name, value));
        }
    }

    //组合查询
    //* must:   AND
    //* mustNot: NOT
    //* should:: OR
    public void shouldQuery(String name, Object value, BoolQueryBuilder must) {
        if (!Objects.isNull(value)) {
            must.should(QueryBuilders.termsQuery(name, value));
        }
    }

    // 模糊查询
    //wildcard query 通配符查询，支持* 任意字符串；？任意一个字符
    public void wildcardQuery(String name, Object value, BoolQueryBuilder must) {
        if (!Objects.isNull(value)) {
            must.must(QueryBuilders.wildcardQuery(name+".keyword", "*" + value+"*"));
        }
    }

    //regexp query 正则表达式匹配分词
    public void regexpQuery(String name, Object value, BoolQueryBuilder must) {
        if (!Objects.isNull(value)) {
            must.must(QueryBuilders.regexpQuery(name, "../"));
        }
    }

    //fuzzy query 分词模糊查询，通过增加fuzziness 模糊属性，来查询term
    // 如下 能够匹配 name 为 te el tel前或后加一个字母的term的
    // 文档 fuzziness 的含义是检索的term 前后增加或减少n个单词的匹配查询
    public void fuzzyQuery(String name, Object value, BoolQueryBuilder must) {
        if (!Objects.isNull(value)) {
            must.must(QueryBuilders.fuzzyQuery(name, value).fuzziness(Fuzziness.ONE));
        }
    }


    /*
     *功能描述 _update_by_query
     * @author zouhw02
     * @date 2021/11/30
     * @param
     * @return
     */
    public String generateUpdateByQuery() {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.termQuery("user", "kimchy"));
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("ctx._source.%s = %s;", "extra", String.format("\"%s\"","test")));


        // if no params, keep it empty, it required in Script
        Map<String, Object> params = new HashMap<>(16);
        Script script = new Script(Script.DEFAULT_SCRIPT_TYPE, "painless", sb.toString(), params);
        ScriptQueryBuilder scriptQueryBuilder = QueryBuilders.scriptQuery(script);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);

        // deal sql
        JSONObject scriptOuter = JSON.parseObject(scriptQueryBuilder.toString());
        JSONObject scriptJson = (JSONObject)scriptOuter.get("script");
        JSONObject queryJson = JSON.parseObject(searchSourceBuilder.toString());
        scriptJson.putAll(queryJson);
        scriptJson.remove("boost");
        // query:
        return scriptJson.toJSONString();
    }


}
