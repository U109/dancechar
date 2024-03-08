package com.litian.dancechar.biz.core.tplgen.dto;

import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelBaseInfoDTO;
import com.litian.dancechar.common.common.dto.BaseDTO;
import com.litian.dancechar.framework.common.util.DCDateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VelContextProjectDTO extends BaseDTO {
    // 系统编码
    private String sysCode;
    // 系统名称
    private String sysName;
    // 系统版本
    private String versionNo;
    /**
     * 代码包名 com.sf.cemp.fcode
     */
    private String packageName;
    /**
     * restful contextPath
     */
    private String contextPath;
    /**
     * 作者姓名
     */
    private String authorName;
    /**
     * 创建时间
     */
    private String createDateString = DCDateUtil.formatDateTime(new Date());

    /**
     * groupId
     */
    private String groupId;

    /**
     * artifactId
     */
    private String artifactId;
    /**
     * 所含表组件
     */
    private List<VelContextTableDTO> tableList;
    /**
     * 所含db信息 构建配置
     */
    private List<VelContextDBInfoDTO> dbInfoList;
    /**
     * 所含中间件
     */
    private List<String> middleareList;

    /**
     * redis部署组件
     */
    private RedisComponentDTO redisComponentDTO;

    /**
     * 启动类名
     */
    private String startupName;

    /**
     *  kafka配置信息
     */
    private List<FcodeKafkaBaseInfoDTO> fcodeKafkaBaseInfoList;

    /**
     *  sentinel配置信息
     */
    private FcodeSentinelBaseInfoDTO fcodeSentinelBaseInfoDTO;

    /**
     *  mongodb配置信息
     */
    private FcodeMongodbBaseInfoDTO fcodeMongodbBaseInfoDTO;


    /**
     *  es配置信息
     */
    private EsBaseInfoDTO esBaseInfoDTO;

    /**
     * 工程中的功能集
     */
    private String functionCollect;

}
