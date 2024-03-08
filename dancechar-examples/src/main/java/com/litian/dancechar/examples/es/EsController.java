package com.litian.dancechar.examples.es;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.es.dto.EsTestDTO;
import com.litian.dancechar.framework.es.util.ElasticsearchUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * es业务处理
 *
 * @author tojson
 * @date 2022/7/9 11:26
 */
@Api(tags = "es相关api")
@RestController
@Slf4j
@RequestMapping(value = "/es/")
public class EsController {
    @Resource
    private ElasticsearchUtil elasticsearchUtil;

    @ApiOperation(value = "创建索引", notes = "创建索引")
    @PostMapping("createIndex")
    public RespResult<Boolean> createIndex(@RequestBody Map<String,String> dataMap){
        String indexName = dataMap.get("indexName");
        elasticsearchUtil.createIndex(indexName);
        return RespResult.success(elasticsearchUtil.isIndexExist(indexName));
    }

    @ApiOperation(value = "添加索引数据指定Id", notes = "添加索引数据指定Id")
    @PostMapping("addIndexDataAssignId")
    public RespResult<String> addIndexDataAssignId(@RequestBody EsTestDTO esTestDTO){
        return RespResult.success(elasticsearchUtil
                .addData(esTestDTO, esTestDTO.getIndexName(), esTestDTO.getId()));
    }

    @ApiOperation(value = "添加索引数据不指定Id", notes = "添加索引数据不指定Id")
    @PostMapping("addIndexData")
    public RespResult<String> addIndexData(@RequestBody EsTestDTO esTestDTO){
        return RespResult.success(elasticsearchUtil.addData(esTestDTO, esTestDTO.getIndexName()));
    }

    @ApiOperation(value = "删除索引", notes = "删除索引")
    @PostMapping("deleteIndex")
    public RespResult<Boolean> deleteIndex(@RequestBody EsTestDTO esTestDTO){
        return RespResult.success(elasticsearchUtil.deleteIndex(esTestDTO.getIndexName()));
    }

    @ApiOperation(value = "查询索引数据", notes = "查询索引数据")
    @PostMapping("searchDataById")
    public RespResult<Map<String,Object>> searchDataById(@RequestBody EsTestDTO esTestDTO){
        return RespResult.success(elasticsearchUtil.searchDataById(esTestDTO.getIndexName(),
                esTestDTO.getId()));
    }

    @ApiOperation(value = "更新数据", notes = "更新数据")
    @PostMapping("updateDataById")
    public RespResult<Void> updateDataById(@RequestBody EsTestDTO esTestDTO){
        elasticsearchUtil.updateDataById(esTestDTO, esTestDTO.getIndexName(),esTestDTO.getId());
        return RespResult.success();
    }

    @ApiOperation(value = "删除数据", notes = "删除数据")
    @PostMapping("deleteDataById")
    public RespResult<Void> deleteDataById(@RequestBody EsTestDTO esTestDTO){
        elasticsearchUtil.deleteDataById(esTestDTO.getIndexName(),esTestDTO.getId());
        return RespResult.success();
    }

    @ApiOperation(value = "判断数据是否存在", notes = "判断数据是否存在")
    @PostMapping("existsById")
    public RespResult<Boolean> existsById(@RequestBody EsTestDTO esTestDTO){
        return RespResult.success(elasticsearchUtil.existsById(esTestDTO.getIndexName(),esTestDTO.getId()));
    }

    @ApiOperation(value = "批量提交数据", notes = "批量提交数据")
    @PostMapping("bulkPost")
    public RespResult<Boolean> bulkPost(@RequestBody EsTestDTO esTestDTO){
        List<EsTestDTO> esList = Lists.newArrayList();
        List<String> idList = Lists.newArrayList();
        for(int i=0;i<5;i++){
            EsTestDTO tempEsTestDTO = new EsTestDTO();
            String id = RandomUtil.randomString(10);
            tempEsTestDTO.setId(id);
            tempEsTestDTO.setName("name"+i);
            tempEsTestDTO.setNo("no"+i);
            esList.add(tempEsTestDTO);
            idList.add(id);
        }
        log.info("bulkPost:{}", JSONUtil.toJsonStr(esList));
        return RespResult.success(elasticsearchUtil.bulkPost(esTestDTO.getIndexName(), esList, idList));
    }
}