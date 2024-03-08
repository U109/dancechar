package com.litian.dancechar.canal;

import cn.hutool.json.JSONUtil;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogInfoReqDTO;
import com.litian.dancechar.member.biz.integral.service.IntegralLogInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {
    @Resource
    private IntegralLogInfoService integralInfoService;

    @Test
    public void testFindList() {
        System.out.print(JSONUtil.toJsonStr(integralInfoService.findList(new IntegralLogInfoReqDTO())));
    }
}
