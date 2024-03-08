package com.litian.dancechar.base.biz.oplog.controller;

import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.base.biz.oplog.dto.SysOpLogReqDTO;
import com.litian.dancechar.base.biz.oplog.dto.SysOpLogRespDTO;
import com.litian.dancechar.base.biz.oplog.service.SysOpLogService;
import com.litian.dancechar.base.common.constants.CommConstants;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.es.util.ElasticsearchUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统操作日志处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "系统操作日志相关api")
@RestController
@Slf4j
@RequestMapping(value = "/sys/oplog/")
public class SysOpLogController {
    @Resource
    private SysOpLogService sysOpLogService;

    @Resource
    private ElasticsearchUtil elasticsearchUtil;

    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<SysOpLogRespDTO>> listPaged(@RequestBody SysOpLogReqDTO req) {
        return sysOpLogService.listPaged(req);
    }

    @ApiOperation(value = "通过es分页查询列表", notes = "通过es分页查询列表")
    @PostMapping("listPagedByEs")
    public RespResult<PageWrapperDTO<SysOpLogRespDTO>> listPagedByEs(@RequestBody SysOpLogReqDTO req) {
        // 构造搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        if(StrUtil.isNotEmpty(req.getMenuName())){
            // 模糊查询
            sourceBuilder.query(QueryBuilders.multiMatchQuery(req.getMenuName(),"menuName"));
        }
        if(StrUtil.isNotEmpty(req.getOpAccount())){
            // 精准查询
            sourceBuilder.query(QueryBuilders.termsQuery(req.getOpContent(),"opContent"));
        }
        PageWrapperDTO<SysOpLogRespDTO> opLogList =  elasticsearchUtil.queryListPageDataByCondition(
                CommConstants.EsIndex.INDEX_SYS_OP_LOG, sourceBuilder, req.getPageSize(), req.getPageNo(),
                "id,menuName,className", "updateDate", SortOrder.DESC, SysOpLogRespDTO.class);
        return RespResult.success(opLogList);
    }

    @ApiOperation(value = "通过es查询列表", notes = "通过es查询列表")
    @PostMapping("findOpLogListByEs")
    public RespResult<List<SysOpLogRespDTO>> findOpLogList(@RequestBody SysOpLogReqDTO req) {
        // 构造搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        if(StrUtil.isNotEmpty(req.getMenuName())){
            // 模糊查询
            sourceBuilder.query(QueryBuilders.multiMatchQuery(req.getMenuName(),"menuName"));
        }
        if(StrUtil.isNotEmpty(req.getOpAccount())){
            // 精准查询
            sourceBuilder.query(QueryBuilders.termsQuery(req.getOpContent(),"opContent"));
        }
        List<SysOpLogRespDTO> opLogList =  elasticsearchUtil.queryListDataByCondition(
                CommConstants.EsIndex.INDEX_SYS_OP_LOG,
                sourceBuilder, null, null, SysOpLogRespDTO.class);
        return RespResult.success(opLogList);
    }

    @ApiOperation(value = "根据Id查询信息", notes = "根据Id查询信息")
    @PostMapping("findById")
    public RespResult<SysOpLogRespDTO> findById(@RequestBody SysOpLogReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(sysOpLogService.findById(req.getId()),
                new SysOpLogRespDTO()));
    }

    @ApiOperation(value = "新增保存", notes = "新增保存")
    @PostMapping("saveWithInsert")
    public RespResult<Boolean> saveWithInsert(@RequestBody SysOpLogReqDTO req) {
        log.info("新增保存数据....");
        return sysOpLogService.saveWithInsert(req);
    }
}