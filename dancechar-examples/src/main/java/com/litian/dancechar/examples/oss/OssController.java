package com.litian.dancechar.examples.oss;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.oss.enums.AliyunFolderTypeEnum;
import com.litian.dancechar.framework.oss.util.OssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * oss业务处理
 *
 * @author tojson
 * @date 2022/7/9 11:26
 */
@Api(tags = "oss相关api")
@RestController
@Slf4j
@RequestMapping(value = "/oss/")
public class OssController {
    @Resource
    private OssUtil ossUtil;

    @ApiOperation(value = "推送json数据到oss", notes = "推送json数据到oss")
    @PostMapping("pushJsonDataToOSS")
    public RespResult<Boolean> pushJsonDataToOSS(){
        List<TestOss> testOssList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            TestOss testOss = new TestOss();
            testOss.setUserNo("100"+RandomUtil.randomString(5));
            testOss.setUserName("用户"+i);
            testOssList.add(testOss);
        }
        ossUtil.pushJsonDataToOSS(AliyunFolderTypeEnum.CUSTOMER, testOssList,
                "测试"+RandomUtil.randomString(3));
        return RespResult.success(true);
    }
}