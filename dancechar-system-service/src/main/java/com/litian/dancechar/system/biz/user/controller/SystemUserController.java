package com.litian.dancechar.system.biz.user.controller;

import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.system.biz.user.dto.SystemUserReqDTO;
import com.litian.dancechar.system.biz.user.dto.SystemUserRespDTO;
import com.litian.dancechar.system.biz.user.service.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统用户业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "系统用户相关api")
@RestController
@Slf4j
@RequestMapping(value = "/user/")
public class SystemUserController {
    @Resource
    private SystemUserService systemUserService;

    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<SystemUserRespDTO>> listPaged(@RequestBody SystemUserReqDTO req) {
        return systemUserService.listPaged(req);
    }

    @ApiOperation(value = "根据Id查询信息", notes = "根据Id查询信息")
    @PostMapping("findById")
    public RespResult<SystemUserRespDTO> findById(@RequestBody SystemUserReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(systemUserService.findById(req.getId()),new SystemUserRespDTO()));
    }

    @ApiOperation(value = "根据账号查询信息", notes = "根据账号查询信息")
    @PostMapping("getByAccountNo")
    public RespResult<SystemUserRespDTO> getByAccountNo(@RequestBody SystemUserReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(
                systemUserService.getByAccountNo(req.getAccountNo()),new SystemUserRespDTO()));
    }

    @ApiOperation(value = "获取黑名单账号列表", notes = "获取黑名单账号列表")
    @PostMapping("findBlackList")
    public RespResult<List<SystemUserRespDTO>> findBlackList() {
        return RespResult.success(DCBeanUtil.copyList(systemUserService.findBlackList(),
                SystemUserRespDTO.class));
    }

    @ApiOperation(value = "用户加入黑名单", notes = "用户加入黑名单")
    @PostMapping("addBlackList")
    public RespResult<Boolean> addBlackList(@RequestBody SystemUserReqDTO req) {
        if(StrUtil.isEmpty(req.getId())){
            return RespResult.error("userId不能为空");
        }
        return systemUserService.addOrDeleteBlackList(req, 1);
    }

    @ApiOperation(value = "用户剔除黑名单", notes = "用户剔除黑名单")
    @PostMapping("deleteBlackList")
    public RespResult<Boolean> deleteBlackList(@RequestBody SystemUserReqDTO req) {
        if(StrUtil.isEmpty(req.getId())){
            return RespResult.error("userId不能为空");
        }
        return systemUserService.addOrDeleteBlackList(req, 0);
    }

    @ApiOperation(value = "判断黑名单信息", notes = "判断黑名单信息")
    @PostMapping("isBlackList")
    public RespResult<Boolean> isBlackList(@RequestBody SystemUserReqDTO req) {
        if(StrUtil.isEmpty(req.getId())){
            return RespResult.error("userId不能为空");
        }
        return RespResult.success(systemUserService.isBlackList(req.getId()));
    }

    @ApiOperation(value = "新增保存", notes = "新增保存")
    @PostMapping("saveWithInsert")
    public RespResult<Boolean> saveWithInsert(@RequestBody SystemUserReqDTO req) {
        log.info("新增保存数据....");
        return systemUserService.saveWithInsert(req);
    }

    @ApiOperation(value = "修改保存", notes = "修改保存")
    @PostMapping("saveStuListWithThreadPoolBatch")
    public RespResult<Boolean> saveWithEdit(@RequestBody SystemUserReqDTO req) {
        log.info("修改保存数据....");
        return systemUserService.saveWithEdit(req);
    }
}