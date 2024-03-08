package com.litian.dancechar.biz.core.tplgen.dto;

import com.litian.dancechar.biz.core.componentpage.dto.GenFileInfoQueryReqDTO;
import com.litian.dancechar.biz.core.tplgen.common.enums.TplGenParamEnums;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelBaseInfoDTO;
import com.litian.dancechar.common.common.dto.BaseDTO;
import com.litian.dancechar.common.common.dto.BaseParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TplGenParamDTO extends BaseDTO {

    private static final long serialVersionUID = -4721996745065326353L;

    /**
     * 每次生成脚手架的id scaffoldGenInfo id 用于生成文件集合表
     */
    @NotNull(message = "生成ID不能为空", groups = {BaseParam.gen.class, BaseParam.funcGen.class})
    private Long genInfoId;

    /**
     * 系统编码
     */
    @NotNull(message = "系统编码不能为空", groups = {BaseParam.gen.class, BaseParam.funcGen.class})
    private String sysCode;

    /**
     * contextPath restful
     */
    @NotNull(message = "restful-contextPath不能为空", groups = {BaseParam.gen.class})
    @Builder.Default
    private String contextPath = "genpath";

    /**
     * 系统名称
     */
    @Builder.Default
    private String sysName = "系统描述说明";

    /**
     * 版本编码
     */
    @Builder.Default
    private String versionNo = "1.0.0-SNAPSHOT";

    /**
     * 作者姓名
     */
    @Builder.Default
    private String authorName = "fcoder";


    /**
     * 生成方式 忽略 暂时只支持 zip 暂时忽略
     */
    @Builder.Default
    private String generateType = "zip";


    /**
     * 代码包名 com.sf.cemp.fcode
     */
    @Builder.Default
    private String packageName = "com.sf";

    /**
     * 插件名称集合选择 kafka redis
     */
    @Builder.Default
    private List<String> middleareList = Arrays.asList("REDIS", "KAFKA", "SENTINEL","SATURN","ELASTICSEARCH","MONGODB");

    /**
     * redis组件配置 支持多源 多部署模式
     */
    private RedisComponentDTO redisComponentDTO;

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
     * 生成组件  service manager repository
     */
    @Builder.Default
    private List<String> dirList = Arrays.asList("service", "manager", "repository");

//    /**
//     * 模板类型 demo new old
//     */
//    private TplGenParamEnums.TemplateTypeEnum templateTypeEnum;
    @Builder.Default
    private String templateType = TplGenParamEnums.TemplateTypeEnum.FNEW.getCode();

    /**
     * groupId
     */
    @Builder.Default
    private String groupId = "com.sf";

    /**
     * artifactId
     */
    @NotNull(message = "artifactId不能为空", groups = BaseParam.gen.class)
    private String artifactId;

    /**
     * 挂载数据库信息
     */
    @NotNull(message = "db信息不能空", groups = {BaseParam.funcGen.class})
    private List<TplGenDBInfoDTO> tplGenDBInfoDTOList;

    /**
     * 文件预览所需数据
     */
    @NotNull(message = "预览对象不能空", groups = {BaseParam.previewGen.class})
    private GenFileInfoQueryReqDTO previewFileDTO;

    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 生成功能
     */
    private String genFunctions;

    private String sql;

    private List<Map<String, List<String>>> allTableNameBySQL;

    /**
     * 用户选择的表信息 一个数据库可以选择多个表
     */
    private TplGenDBTableDTO tplGenDBTableDTO;

    private String instance;

    private String showColumnStr;
    /**
     * 工程中的功能集
     */
    private String functionCollect;

}
