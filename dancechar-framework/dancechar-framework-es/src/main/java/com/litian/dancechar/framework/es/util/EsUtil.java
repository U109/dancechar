package com.litian.dancechar.framework.es.util;

import com.litian.dancechar.framework.common.util.DCMapUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.es.dto.QueryAnalysisDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 类描述: Es操作工具类
 *
 * @author 01406831
 * @date 2021/08/20 13:46
 */
@Component
@Slf4j
public class EsUtil {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    public  RestHighLevelClient getRestHighLevelClient(){
        return restHighLevelClient;
    }

    /**
     * 创建索引
     *
     * @param indexName    索引名
     * @param mappingsJson mapping
     */
    public void createIndex(String indexName, String mappingsJson) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        if (DCObjectUtil.isNotEmpty(mappingsJson)) {
            request.mapping(mappingsJson, XContentType.JSON);
        }
        try {
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            log.info("创建索引成功,{}", createIndexResponse);
        } catch (IOException e) {
            log.error("创建es索引失败,{}", e.getMessage(), e);
        }
    }

    /**
     * 查看索引是否存在
     *
     * @param indexName 索引名
     */
    public boolean indexExists(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        try {
            return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询es索引失败,{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名
     */
    public void deleteIndex(String indexName) {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        try {
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            log.info("删除索引成功,{}", acknowledgedResponse);
        } catch (IOException e) {
            log.error("删除索引失败,{}", e.getMessage(), e);
        }

    }

    /**
     * 以map的形式新增数据
     *
     * @param req
     * @param indexName
     * @param id
     */
    public void addInMap(String indexName, String id, Map<String, Object> req) {
        IndexRequest indexRequest = new IndexRequest(indexName)
                .id(id).source(req);

        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("利用Map格式添加数据失败失败,{}", e.getMessage(), e);
        }
    }


    /**
     * 以json的形式新增数据
     *
     * @param reqJson   JSON形式的字符串
     * @param indexName
     * @param id
     */
    public void addInJson(String indexName, String id, String reqJson) {
        IndexRequest request = new IndexRequest(indexName);
        request.id(id);
        request.source(reqJson, XContentType.JSON);
        try {
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("利用JSON格式添加数据失败失败,{}", e.getMessage(), e);
        }
    }

    /**
     * 利用XContentBuilder 方式插入
     *
     * @param contentBuilder
     * @param indexName
     * @param id
     */
    public void addInContentBuilder(String indexName, String id, Map<String, String> contentBuilder) {
        if (DCMapUtil.isEmpty(contentBuilder)) {
            log.error("入参contentBuilder不能为空");
            return;
        }
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            for (Map.Entry<String, String> entry : contentBuilder.entrySet()) {
                builder.field(entry.getKey(), entry.getValue());
            }
            builder.endObject();
            IndexRequest indexRequest = new IndexRequest(indexName)
                    .id(id).source(builder);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("利用XContentBuilder添加数据失败失败,{}", e.getMessage(), e);
        }
    }


    /**
     * 修改索引数据 入参形式map
     *
     * @param indexName
     * @param id
     * @param updateJson
     */
    public void updateInMap(String indexName, String id, Map<String, Object> updateJson) {

        UpdateRequest request = new UpdateRequest(indexName, id)
                .doc(updateJson);

        try {
            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            log.info("update in map 's result ={}", updateResponse);
        } catch (IOException e) {
            log.error("updateInMap修改数据失败,{}", e.getMessage(), e);
        }
    }

    /**
     * 以json的形式修改数据
     *
     * @param reqJson   JSON形式的字符串
     * @param indexName
     * @param id
     */
    public void updateInJson(String indexName, String id, String reqJson) {
        UpdateRequest request = new UpdateRequest(indexName, id).doc(reqJson, XContentType.JSON);

        try {
            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            log.info("update in json 's result ={}", updateResponse);
        } catch (IOException e) {
            log.error("利用JSON格式修改数据失败,{}", e.getMessage(), e);
        }
    }

    /**
     * 利用XContentBuilder 方式修改
     *
     * @param contentBuilder
     * @param indexName
     * @param id
     */
    public void updateInContentBuilder(String indexName, String id, Map<String, String> contentBuilder) {
        if (DCMapUtil.isEmpty(contentBuilder)) {
            log.error("入参contentBuilder不能为空");
            return;
        }
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            for (Map.Entry<String, String> entry : contentBuilder.entrySet()) {
                builder.field(entry.getKey(), entry.getValue());
            }
            builder.endObject();
            IndexRequest indexRequest = new IndexRequest(indexName)
                    .id(id).source(builder);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("利用XContentBuilder添加数据失败,{}", e.getMessage(), e);
        }
    }

    /**
     * 根据id删除index中的数据
     *
     * @param indexName
     * @param id
     */
    public void deleteById(String indexName, String id) {

        DeleteRequest request = new DeleteRequest(indexName, id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            log.info("deleteById结果:{}", deleteResponse);
        } catch (IOException e) {
            log.error("deleteById删除数据失败,{}", e.getMessage(), e);
        }

    }

    /**
     * 根据id查询数据
     *
     * @param indexName
     * @param id
     * @return map
     */
    public Map<String, Object> getById(String indexName, String id) {
        GetRequest getRequest = new GetRequest(indexName, id);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                return getResponse.getSourceAsMap();
            }
        } catch (IOException e) {
            log.error("getById查询数据失败,{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * QueryBuilders.termQuery(“key”, “vaule”); // 完全匹配
     * QueryBuilders.termsQuery(“key”, “vaule1”, “vaule2”) ; //一次匹配多个值
     * QueryBuilders.matchQuery(“key”, “vaule”) //单个匹配, field不支持通配符, 前缀具高级特性
     * QueryBuilders.multiMatchQuery(“text”, “field1”, “field2”); //匹配多个字段, field有通配符忒行
     * QueryBuilders.matchAllQuery(); // 匹配所有文件
     * 组合查询
     * // Bool Query 用于组合多个叶子或复合查询子句的默认查询
     * // must 相当于 与 & =
     * // must not 相当于 非 ~ ！=
     * // should 相当于 或 | or
     * // filter 过滤
     * QueryBuilders.boolQuery()
     * .must(QueryBuilders.termQuery(“key”, “value1”))
     * .must(QueryBuilders.termQuery(“key”, “value2”))
     * .mustNot(QueryBuilders.termQuery(“key”, “value3”))
     * .should(QueryBuilders.termQuery(“key”, “value4”))
     * .filter(QueryBuilders.termQuery(“key”, “value5”));
     *
     * @param indexName
     * @param
     * @return 单个匹配结果
     */
    public List<Map<String, Object>> query(String indexName, Map<String, String> matchQuery, Map<String, Boolean> sortFieldsToAsc, String[] showFields, int from, int size) {
        List<Map<String, Object>> result = new ArrayList<>();
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if (DCMapUtil.isNotEmpty(matchQuery)) {
            for (Map.Entry<String, String> entry : matchQuery.entrySet()) {
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(entry.getKey(), entry.getValue());//这里可以根据字段进行搜索，must表示符合条件的，相反的mustnot表示不符合条件的
                // RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("fields_timestamp"); //新建range条件
                // rangeQueryBuilder.gte("2019-03-21T08:24:37.873Z"); //开始时间
                // rangeQueryBuilder.lte("2019-03-21T08:24:37.873Z"); //结束时间
                // boolBuilder.must(rangeQueryBuilder);
                boolBuilder.must(matchQueryBuilder);
            }
        }
        sourceBuilder.query(boolBuilder); //设置查询，可以是任何类型的QueryBuilder。
        sourceBuilder.from(from); //设置确定结果要从哪个索引开始搜索的from选项，默认为0
        sourceBuilder.size(size); //设置确定搜素命中返回数的size选项，默认为10
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); //设置一个可选的超时，控制允许搜索的时间。
        //展示指定字段
        if (showFields.length > 0) {
            sourceBuilder.fetchSource(showFields, new String[]{}); //第一个是获取字段，第二个是过滤的字段，默认获取全部
        }

        SearchRequest searchRequest = new SearchRequest(indexName); //索引
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();  //SearchHits提供有关所有匹配的全局信息，例如总命中数或最高分数：
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                log.info("search -> {}", hit.getSourceAsString());
            }
            Arrays.stream(searchHits).forEach(hit -> result.add(hit.getSourceAsMap()));
            return result;
        } catch (Exception e) {
            log.error("查询失败,{}", e.getMessage(), e);
        }
        return null;
    }


    public List<Map<String, Object>> queryRange(String indexName, Map<String, String> matchQuery, Map<String, Map<String, String>> rangeQuery, String[] showFields, int from, int size) {
        List<Map<String, Object>> result = new ArrayList<>();
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if (DCMapUtil.isNotEmpty(matchQuery)) {
            for (Map.Entry<String, String> entry : matchQuery.entrySet()) {
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(entry.getKey(), entry.getValue());//这里可以根据字段进行搜索，must表示符合条件的，相反的mustnot表示不符合条件的
                // RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("fields_timestamp"); //新建range条件
                // rangeQueryBuilder.gte("2019-03-21T08:24:37.873Z"); //开始时间
                // rangeQueryBuilder.lte("2019-03-21T08:24:37.873Z"); //结束时间
                // boolBuilder.must(rangeQueryBuilder);
                boolBuilder.must(matchQueryBuilder);
            }
        }
        if (DCMapUtil.isNotEmpty(rangeQuery)) {
            for (Map.Entry<String, Map<String, String>> entry : rangeQuery.entrySet()) {
                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(entry.getKey()); //新建range条件
                Map<String, String> rangeValue = entry.getValue();
                for (Map.Entry<String, String> entryCondition : rangeValue.entrySet()) {
                    if ("gte".equals(entryCondition.getKey())) {
                        rangeQueryBuilder.gte(entryCondition.getValue()); //开始时间
                    }
                    if ("lte".equals(entryCondition.getKey())) {
                        rangeQueryBuilder.lte(entryCondition.getValue()); //结束时间
                    }
                }
                boolBuilder.must(rangeQueryBuilder);
            }
        }
        //设置查询，可以是任何类型的QueryBuilder。
        sourceBuilder.query(boolBuilder);
        //设置确定结果要从哪个索引开始搜索的from选项，默认为0
        sourceBuilder.from(from);
        sourceBuilder.size(size); //设置确定搜素命中返回数的size选项，默认为10
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); //设置一个可选的超时，控制允许搜索的时间。
        //展示指定字段
        if (showFields.length > 0) {
            sourceBuilder.fetchSource(showFields, new String[]{}); //第一个是获取字段，第二个是过滤的字段，默认获取全部
        }

        SearchRequest searchRequest = new SearchRequest(indexName); //索引
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();  //SearchHits提供有关所有匹配的全局信息，例如总命中数或最高分数：
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                log.info("search -> {}", hit.getSourceAsString());
            }
            Arrays.stream(searchHits).forEach(hit -> result.add(hit.getSourceAsMap()));
            return result;
        } catch (Exception e) {
            log.error("查询失败,{}", e.getMessage(), e);
        }
        return null;
    }


    public long countRange(String indexName, Map<String, String> matchQuery, Map<String, Map<String, String>> rangeQuery, int from, int size) {
        List<Map<String, Object>> result = new ArrayList<>();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolBuilder = concreteBoolQueryBuilder(matchQuery,rangeQuery);
        //设置查询，可以是任何类型的QueryBuilder。
        sourceBuilder.query(boolBuilder);
        //设置确定结果要从哪个索引开始搜索的from选项，默认为0
        sourceBuilder.from(from);
        sourceBuilder.size(size); //设置确定搜素命中返回数的size选项，默认为10
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); //设置一个可选的超时，控制允许搜索的时间。


        CountRequest countRequest = new CountRequest(indexName); //索引
        countRequest.source(sourceBuilder);
        try {
            CountResponse response = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
            return response.getCount();
        } catch (Exception e) {
            log.error("查询失败,{}", e.getMessage(), e);
        }
        return 0;
    }

    public BoolQueryBuilder concreteBoolQueryBuilder(Map<String, String> matchQuery, Map<String, Map<String, String>> rangeQuery) {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        if (DCMapUtil.isNotEmpty(matchQuery)) {
            for (Map.Entry<String, String> entry : matchQuery.entrySet()) {
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(entry.getKey(), entry.getValue());//这里可以根据字段进行搜索，must表示符合条件的，相反的mustnot表示不符合条件的
                boolBuilder.must(matchQueryBuilder);
            }
        }
        if (DCMapUtil.isNotEmpty(rangeQuery)) {
            for (Map.Entry<String, Map<String, String>> entry : rangeQuery.entrySet()) {
                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(entry.getKey()); //新建range条件
                Map<String, String> rangeValue = entry.getValue();
                for (Map.Entry<String, String> entryCondition : rangeValue.entrySet()) {
                    if ("start".equals(entryCondition.getKey())) {
                        rangeQueryBuilder.gte(entryCondition.getValue()); //开始时间
                    }
                    if ("end".equals(entryCondition.getKey())) {
                        rangeQueryBuilder.lt(entryCondition.getValue()); //结束时间
                    }
                }
                boolBuilder.must(rangeQueryBuilder);
            }
        }
        return boolBuilder;
    }

    public Aggregations aggregateSumAfterGroup(QueryAnalysisDTO queryESVo, BoolQueryBuilder defaultQueryBuilder) {
        Map<String, Object> rst = new HashMap<>();
        //根据 任务id分组进行求和
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //SearchRequestBuilder sbuilder = client.prepareSearch(queryESVo.getIndex()).setTypes(queryESVo.getType());
        if (defaultQueryBuilder != null) {
            sourceBuilder.query(defaultQueryBuilder);
        }

        SumAggregationBuilder sumAgg = AggregationBuilders.sum("total_send").field("sendCount");
        sourceBuilder.aggregation(sumAgg);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(queryESVo.getIndex());
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            /**
             * 解析数据，获取tag_tr的指标聚合参数。
             */
            Aggregations aggregations = response.getAggregations();
            /*if (aggregations != null) {
                List<Aggregation> aggregationList = aggregations.asList();
                if (CollectionUtils.isNotEmpty(aggregationList)) {
                    for (Aggregation aggregation : aggregationList) {
                    InternalSum sum = (InternalSum) aggregation;
                    //return (long)sum.value();
                    //rst.put(kafkaReqDTO.getActivityId()+"-"+kafkaReqDTO.getMobile()+"-"+kafkaReqDTO.getCurDay(), (long) sum.value());
                    }
                }
            }*/
            return aggregations;
        }catch (Exception e){
            log.error("聚合查询{}",e.getMessage(),e);
        }
        return null;
    }


}
