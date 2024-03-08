package com.litian.dancechar.base.biz.staff.controller;

import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.base.biz.staff.dto.StaffReqDTO;
import com.litian.dancechar.base.biz.staff.dto.StaffRespDTO;
import com.litian.dancechar.base.biz.staff.service.StaffService;
import com.litian.dancechar.framework.common.base.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 员工业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "员工相关api")
@RestController
@Slf4j
@RequestMapping(value = "/staff/")
public class StaffController {
    @Resource
    private StaffService staffService;

    @ApiOperation(value = "根据员工工号查询员工信息", notes = "根据员工工号查询员工信息")
    @PostMapping("getByNo")
    public RespResult<StaffRespDTO> getByNo(@RequestBody StaffReqDTO req) {
        if(StrUtil.isEmpty(req.getNo())){
            return RespResult.error("员工工号no不能为空");
        }
        return RespResult.success(staffService.getByNo(req.getNo()));
    }

    @ApiOperation(value = "将员工DB数据刷新到redis", notes = "将员工DB数据刷新到redis")
    @PostMapping("refreshDBToRedis")
    public RespResult<Boolean> refreshDBToRedis() {
        return staffService.refreshDBToRedis();
    }
}