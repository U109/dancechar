package com.litian.dancechar.base.biz.snowflake.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.litian.dancechar.base.biz.snowflake.dto.GenIdReqDTO;
import com.litian.dancechar.base.biz.snowflake.util.SnowflakeHelper;
import com.litian.dancechar.framework.common.base.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Id生成处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */

@Api(tags = "id生成相关api")
@RestController
@Slf4j
@RequestMapping(value = "/snowflake/")
public class IdGenController {

    @ApiOperation(value = "雪花算法生成id", notes = "雪花算法生成id")
    @PostMapping("genId")
    public RespResult<List<String>> genId(@RequestBody GenIdReqDTO reqDTO) {
        if(ObjectUtil.isNull(reqDTO.getNum())){
            reqDTO.setNum(1);
        }
        String prefix = reqDTO.getPrefix();
        List<String> rsList = Lists.newArrayList();
        for(int i=0; i< reqDTO.getNum(); i++){
            rsList.add(StrUtil.isNotEmpty(prefix) ? prefix + SnowflakeHelper.genString() :
                    SnowflakeHelper.genString());
        }
        return RespResult.success(rsList);
    }
}
