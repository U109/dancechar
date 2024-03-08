package com.litian.dancechar.biz.sysmgr.esmgr.manager;

import com.litian.dancechar.framework.es.dto.QueryAnalysisDTO;
import com.litian.dancechar.framework.es.util.EsUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类描述：ES操作类
 *
 * @author 01396106
 * @date 2021/10/28 11:22
 */
@Service
@Slf4j
public class EsOperateManager {

    @Resource
    private EsUtil esUtil;


    /**
     * 创建索引
     *
     * @param indexName    索引名
     * @param mappingsJson mapping
     */
    public void createIndex(String indexName, String mappingsJson) {
        esUtil.createIndex(indexName,mappingsJson);
    }

    /**
     * 查看索引是否存在
     *
     * @param indexName 索引名
     */
    public boolean indexExists(String indexName) {
        return esUtil.indexExists(indexName);
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名
     */
    public void deleteIndex(String indexName){
        esUtil.deleteIndex(indexName);
    }

    /**
     * 以map的形式新增数据
     *
     * @param req
     * @param indexName
     * @param id
     */
    public void addInMap(String indexName, String id, Map<String, Object> req) {
        esUtil.addInMap(indexName,id,req);
    }

    /**
     * 以json的形式新增数据
     *
     * @param reqJson   JSON形式的字符串
     * @param indexName
     * @param id
     */
    public void addInJson(String indexName, String id, String reqJson) {
        esUtil.addInJson(indexName,id,reqJson);
    }

    /**
     * 利用XContentBuilder 方式插入
     *
     * @param contentBuilder
     * @param indexName
     * @param id
     */
    public void addInContentBuilder(String indexName, String id, Map<String, String> contentBuilder) {
        esUtil.addInContentBuilder(indexName,id,contentBuilder);
    }

    /**
     * 修改索引数据 入参形式map
     *
     * @param indexName
     * @param id
     * @param updateJson
     */
    public void updateInMap(String indexName, String id, Map<String, Object> updateJson) {
        esUtil.updateInMap(indexName,id,updateJson);
    }

    /**
     * 以json的形式修改数据
     *
     * @param reqJson   JSON形式的字符串
     * @param indexName
     * @param id
     */
    public void updateInJson(String indexName, String id, String reqJson) {
        esUtil.updateInJson(indexName,id,reqJson);
    }

    /**
     * 利用XContentBuilder 方式修改
     *
     * @param contentBuilder
     * @param indexName
     * @param id
     */
    public void updateInContentBuilder(String indexName, String id, Map<String, String> contentBuilder) {
        esUtil.updateInContentBuilder(indexName,id,contentBuilder);
    }

    /**
     * 根据id删除index中的数据
     *
     * @param indexName
     * @param id
     */
    public void deleteById(String indexName, String id) {
        esUtil.deleteById(indexName,id);
    }

    /**
     * 根据id查询数据
     *
     * @param indexName
     * @param id
     * @return map
     */
    public Map<String, Object> getById(String indexName, String id) {
        return esUtil.getById(indexName,id);
    }


    public List<Map<String, Object>> query(String indexName, Map<String, String> matchQuery, Map<String, Boolean> sortFieldsToAsc, String[] showFields, int from, int size) {
        return esUtil.query(indexName,matchQuery,sortFieldsToAsc,showFields,from,size);
    }


    public List<Map<String, Object>> queryRange(String indexName, Map<String, String> matchQuery, Map<String, Map<String, String>> rangeQuery, String[] showFields, int from, int size) {
        return esUtil.queryRange(indexName,matchQuery,rangeQuery,showFields,from,size);
    }

    public long countRange(String indexName, Map<String, String> matchQuery, Map<String, Map<String, String>> rangeQuery, int from, int size) {
        return esUtil.countRange(indexName,matchQuery,rangeQuery,from,size);
    }

    public BoolQueryBuilder concreteBoolQueryBuilder(Map<String, String> matchQuery, Map<String, Map<String, String>> rangeQuery) {
        return esUtil.concreteBoolQueryBuilder(matchQuery,rangeQuery);
    }

    public Aggregations aggregateSumAfterGroup(QueryAnalysisDTO queryESVo, BoolQueryBuilder defaultQueryBuilder) {
        return esUtil.aggregateSumAfterGroup(queryESVo,defaultQueryBuilder);
    }


}
