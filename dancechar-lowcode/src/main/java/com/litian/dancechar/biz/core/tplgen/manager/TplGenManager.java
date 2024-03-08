package com.litian.dancechar.biz.core.tplgen.manager;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.litian.dancechar.biz.core.codegen.common.enums.YesOrNotEnum;
import com.litian.dancechar.biz.core.componentpage.common.enums.ComponentPageEnums;
import com.litian.dancechar.biz.core.componentpage.dto.*;
import com.litian.dancechar.biz.core.componentpage.manager.GenFileInfoManager;
import com.litian.dancechar.biz.core.componentpage.manager.GenFileRenameExtraManager;
import com.litian.dancechar.biz.core.scaffold.dto.FunctionGenSqlDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldGenDbExampleInfoDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldGenDbInfoDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldGenInfoDTO;
import com.litian.dancechar.biz.core.scaffold.manager.*;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ChoosedTableColumnsDO;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenDbExampleInfoDO;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenDbInfoDO;
import com.litian.dancechar.biz.core.tabledetailinfo.dto.TableDetailInfoDTO;
import com.litian.dancechar.biz.core.tabledetailinfo.manager.TableDetailInfoManager;
import com.litian.dancechar.biz.core.tplgen.common.enums.TplGenParamEnums;
import com.litian.dancechar.biz.core.tplgen.common.utils.TplGenUtil;
import com.litian.dancechar.biz.core.tplgen.dto.*;
import com.litian.dancechar.biz.sysmgr.dbmgr.common.enums.SystemDBInfoEnums;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.DynamicDBInfo;
import com.litian.dancechar.biz.sysmgr.dbmgr.manager.DynamicDBManager;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTableColumn;
import com.litian.dancechar.biz.sysmgr.esmgr.manager.EsInfoManager;
import com.litian.dancechar.biz.sysmgr.kafkamgr.manager.FcodeKafkaInfoManager;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.manager.FcodeMongodbInfoManager;
import com.litian.dancechar.biz.sysmgr.redismgr.dto.FcodeRedisInfoDTO;
import com.litian.dancechar.biz.sysmgr.redismgr.manager.FcodeRedisInfoManager;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.manager.FcodeSentinelInfoManager;
import com.litian.dancechar.common.common.constants.DBConstants;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.common.common.enums.ColumnDisabledEnum;
import com.litian.dancechar.framework.common.exception.BizException;
import com.litian.dancechar.framework.common.exception.FcodeResponseResultCodeEnum;
import com.litian.dancechar.framework.common.exception.BusinessException;
import com.litian.dancechar.framework.common.util.*;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class TplGenManager {
    @Resource
    private DynamicDBManager dynamicDBManager;
    @Resource
    private GenFileInfoManager genFileInfoManager;
    @Resource
    private GenFileRenameExtraManager genFileRenameExtraManager;
   // @Resource
  //  private CalculateManager calculateManager;
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;
    @Resource
    private ChoosedTableColumnsManager choosedTableColumnsManager;
    @Resource
    private FunctionGenSqlManager functionGenSqlManager;
    @Autowired
    private TableDetailInfoManager tableDetailInfoManager;
    @Autowired
    private FcodeSentinelInfoManager fcodeSentinelInfoManager;
    @Autowired
    private FcodeKafkaInfoManager fcodeKafkaInfoManager;

    @Resource
    private FcodeRedisInfoManager fcodeRedisInfoManager;
    @Resource
    private ScaffoldGenDbInfoManager scaffoldGenDbInfoManager;
    @Autowired
    private FcodeMongodbInfoManager fcodeMongodbInfoManager;
    @Autowired
    private EsInfoManager esInfoManager;
    @Resource
    private ScaffoldGenDbExampleInfoManager scaffoldGenDbExampleInfoManager;

    /**
     * 模板渲染
     */
    public ByteArrayOutputStream tplGen(TplGenParamDTO tplGenParamDTO) {
        // 校验脚手架还是单功能
        Class group = BaseParam.gen.class;
        if (tplGenParamDTO != null && TplGenParamEnums.TemplateTypeEnum.DFUNC.getCode().equals(tplGenParamDTO.getTemplateType()) ||
                TplGenParamEnums.TemplateTypeEnum.WDFUNC.getCode().equals(tplGenParamDTO.getTemplateType())) {
            group = BaseParam.funcGen.class;
        }
        DCValidatorUtil.validateModel(tplGenParamDTO, group);
//        initDBTableColumn(tplGenParamDTO);
        // 模板获取
        ByteArrayOutputStream outputStream = renderTpl(tplGenParamDTO);
        return outputStream;
    }

    public ByteArrayOutputStream tplGenList(List<TplGenParamDTO> tplGenParamList) {
        // 校验脚手架还是单功能
        tplGenParamList.forEach(tplGenParamDTO -> {
            Class group = BaseParam.gen.class;
            if (tplGenParamDTO != null && TplGenParamEnums.TemplateTypeEnum.DFUNC.getCode().equals(tplGenParamDTO.getTemplateType()) ||
                    TplGenParamEnums.TemplateTypeEnum.WDFUNC.getCode().equals(tplGenParamDTO.getTemplateType())) {
                group = BaseParam.funcGen.class;
            }
            DCValidatorUtil.validateModel(tplGenParamDTO, group);
        });
        // 模板获取
        ByteArrayOutputStream outputStream = renderTplList(tplGenParamList);
        return outputStream;
    }


    /**
     * 模板 Sql工具渲染
     */
    public ByteArrayOutputStream tplGenSql(TplGenParamDTO tplGenParamDTO) {
        initDBTableColumnSql(tplGenParamDTO);
        // 模板获取
        ByteArrayOutputStream outputStream = renderTplSql(tplGenParamDTO);
        return outputStream;
    }

    /**
     * 预览文件
     *
     * @param tplGenParamDTO
     */
    public String previewFile(TplGenParamDTO tplGenParamDTO) throws Exception {
        GenFileInfoQueryReqDTO genFileInfoQueryReqDTO = tplGenParamDTO.getPreviewFileDTO();
        if (DCObjectUtil.isEmpty(genFileInfoQueryReqDTO)) {
            throw new BizException(FcodeResponseResultCodeEnum.GEN_FILE_NOT_FOUND);
        }
        // 根据文件标记提取数据
        TplFilePreviewDTO tplFilePreviewDTO = createTplFilePreviewDTO(tplGenParamDTO, genFileInfoQueryReqDTO);

        // 预览单个文件 直接渲染
//        initDBTableColumn(tplFilePreviewDTO);
        StringWriter sw = new StringWriter();
        File file = TplGenParamEnums.TemplateTypeEnum.getByCode(tplGenParamDTO.getTemplateType()).getTplPathFileMap().get(tplFilePreviewDTO.getFilePathName());
        if (DCObjectUtil.isEmpty(file)) {
            throw new BusinessException(FcodeResponseResultCodeEnum.GEN_FILE_NOT_FOUND.getMsg());
        }
        Reader reader = new InputStreamReader(new FileInputStream(file));
        VelContextFuncDTO velContextFuncDTO = DCBeanUtil.copyProperties(tplFilePreviewDTO, VelContextFuncDTO.class);
        initFileRenameClassName(tplFilePreviewDTO.getFilePathName(), velContextFuncDTO);
        if (tplFilePreviewDTO.getFilePathName().endsWith("Mapper.xml.vm")) {
            FunctionGenSqlDTO genSqlDTO = functionGenSqlManager.getByGenInfoId(tplGenParamDTO.getGenInfoId());
            if (genSqlDTO != null) {
                velContextFuncDTO.setPreviewSql(genSqlDTO.getPreviewSql());
            }
        }
        VelocityContext velocityContext = new VelocityContext(DCBeanUtil.beanToMap(velContextFuncDTO));
        Velocity.evaluate(velocityContext, sw, "logTag" , reader);
        String fileString = sw.toString();
        IOUtils.closeQuietly(sw);
        return fileString;
    }

    public String previewOnlyFile(GenFileMultiTableDTO multiTableDTO, String templateType) throws Exception {
        List<Long> ids = multiTableDTO.getIds();
        GenFileInfoQueryReqDTO previewFileDTO = multiTableDTO.getPreviewFileDTO();
        // 根据工程id获取table信息
        List<ScaffoldGenInfoDTO> genInfoList = scaffoldGenInfoManager.getListByIds(ids);
        List<ScaffoldGenDbInfoDO> dbList = scaffoldGenDbInfoManager.getDbListByGenInfoIds(ids);
        List<Long> dbIds = dbList.stream().map(ScaffoldGenDbInfoDO::getId).collect(Collectors.toList());
        List<ScaffoldGenDbExampleInfoDO> tableList = scaffoldGenDbExampleInfoManager.getTableListByDbIds(dbIds);
        List<MultiTableClassDTO> multiTableClassList = new ArrayList<>();
        tableList.forEach(tableInfo->{
            MultiTableClassDTO multiTableClassDTO = new MultiTableClassDTO();
            multiTableClassDTO.setClassName(tableInfo.getClassName());
            multiTableClassDTO.setFunctionDir(tableInfo.getCatalog());
            multiTableClassList.add(multiTableClassDTO);
        });

        VelContextFuncDTO velContextFuncDTO = new VelContextFuncDTO();
        DCBeanUtil.copyNotNull(previewFileDTO,velContextFuncDTO);

        velContextFuncDTO.setPackageName(genInfoList.get(0).getProjectPackageName());
        velContextFuncDTO.setContextPath(genInfoList.get(0).getContextPath());
        velContextFuncDTO.setMultiTableClassList(multiTableClassList);

        StringWriter sw = new StringWriter();

        File file = TplGenParamEnums.TemplateTypeEnum.getByCode(templateType).getTplPathFileMap().get(previewFileDTO.getFilePathName());
        if (DCObjectUtil.isEmpty(file)) {
            throw new BusinessException(FcodeResponseResultCodeEnum.GEN_FILE_NOT_FOUND.getMsg());
        }
        Reader reader = new InputStreamReader(new FileInputStream(file));
        VelocityContext velocityContext = new VelocityContext(DCBeanUtil.beanToMap(velContextFuncDTO));
        Velocity.evaluate(velocityContext, sw, "logTag" , reader);
        String fileString = sw.toString();
        IOUtils.closeQuietly(sw);
        return fileString;
    }



    /**
     * 工程文件预览
     *
     * @param tplGenParamDTO
     * @return
     * @throws Exception
     */
    public String previewProjectFile(TplGenParamDTO tplGenParamDTO) throws Exception {
        ScaffoldGenInfoDTO genInfoDTO = scaffoldGenInfoManager.getById(tplGenParamDTO.getGenInfoId());
        DCBeanUtil.copyNotNull(genInfoDTO, tplGenParamDTO);
        tplGenParamDTO.setSysCode(genInfoDTO.getSystemCode());

        if (StringUtils.isNotBlank(genInfoDTO.getMiddleware())) {
            setMiddleware(genInfoDTO,tplGenParamDTO);
        }

        GenFileInfoQueryReqDTO genFileInfoQueryReqDTO = tplGenParamDTO.getPreviewFileDTO();
        if (DCObjectUtil.isEmpty(genFileInfoQueryReqDTO)) {
            throw new BusinessException(FcodeResponseResultCodeEnum.GEN_FILE_NOT_FOUND.getMsg());
        }
        StringWriter sw = new StringWriter();
        TplGenParamEnums.TemplateTypeEnum fileEnum;
        if (genInfoDTO.getScaffoldType() == 5) {
            fileEnum = TplGenParamEnums.TemplateTypeEnum.MANAGEMENTNEW;
        } else if (genInfoDTO.getScaffoldType() == 6) {
            fileEnum = TplGenParamEnums.TemplateTypeEnum.BOOTNEW;
        } else {
            fileEnum = TplGenParamEnums.TemplateTypeEnum.FNEW;
        }
        File file = fileEnum.getTplPathFileMap().get(genFileInfoQueryReqDTO.getFilePathName());
        if (DCObjectUtil.isEmpty(file)) {
            throw new BusinessException(FcodeResponseResultCodeEnum.GEN_FILE_NOT_FOUND.getMsg());
        }
        Reader reader = new InputStreamReader(new FileInputStream(file));
        //如果预览的文件里包含application文件，则去设置数据库信息
        if(genFileInfoQueryReqDTO.getFilePathName().contains("application.properties.vm")){
            this.setDBInfoList(genInfoDTO.getId(),tplGenParamDTO);
        }
        VelContextProjectDTO velContextProjectDTO = createVelContextProject(tplGenParamDTO);
        // velContextProjectDTO.setPackageName(tplGenParamDTO.getPackageName());
        // velContextProjectDTO.setAuthorName(tplGenParamDTO.getCreateUser());

        Map<String, Object> stringObjectMap = DCBeanUtil.beanToMap(velContextProjectDTO);
        VelocityContext velocityContext = new VelocityContext(stringObjectMap);
        Velocity.evaluate(velocityContext, sw, "logTag" , reader);
        String fileString = sw.toString();
        IOUtils.closeQuietly(sw);
        return fileString;
    }

    public void setDBInfoList(Long genInfoId,TplGenParamDTO tplGenParamDTO){
        // 根据scaffoldGenInfoId获取记录列表
        List<ScaffoldGenDbInfoDTO> scaffoldGenDbInfoDTOList = scaffoldGenDbInfoManager.listByScaffoldGenInfoId(genInfoId);
        if (DCCollectionUtil.isNotEmpty(scaffoldGenDbInfoDTOList)) {
            List<TplGenDBInfoDTO> tplGenTOList = Lists.newArrayList();
            scaffoldGenDbInfoDTOList.stream().forEach(vo -> {
                TplGenDBInfoDTO tplGenDBInfoDTO = new TplGenDBInfoDTO();
                String[] ipPortArr = DCStrUtil.split(vo.getIpPort(), "/");
                if (DCArrayUtil.isNotEmpty(ipPortArr) && ipPortArr.length > 1) {
                    tplGenDBInfoDTO.setHost(ipPortArr[0]);
                    tplGenDBInfoDTO.setPort(Convert.toInt(ipPortArr[1]));
                }
                tplGenDBInfoDTO.setGenDbId(vo.getId());
                tplGenDBInfoDTO.setUsername(vo.getUserName());
                tplGenDBInfoDTO.setPassword(vo.getPassword());
                tplGenDBInfoDTO.setDbName(vo.getDbName());
                tplGenDBInfoDTO.setDbTag(vo.getDbName());
                tplGenDBInfoDTO.setDriverName(vo.getDriverClass());
                tplGenDBInfoDTO.setPrimary(DCObjectUtil.equals(vo.getPrimaryDb(), 1));
                List<ScaffoldGenDbExampleInfoDTO> exampleInfoDTOList = vo.getScaffoldGenDbExampleInfoList();
                if (DCCollectionUtil.isNotEmpty(exampleInfoDTOList)) {
                    List<TplGenDBTableDTO> tplGenDBTableDTOList = Lists.newArrayList();
                    exampleInfoDTOList.stream().forEach(exampleInfoDTO -> {
                        TplGenDBTableDTO tplGenDBTableDTO = new TplGenDBTableDTO();
                        tplGenDBTableDTO.setGenDbExampleId(exampleInfoDTO.getId());
                        if (exampleInfoDTO.getClassName().contains("_")) {
                            String classNameHump = TplGenUtil.underlineToHump(exampleInfoDTO.getClassName());
                            tplGenDBTableDTO.setClassName(StringUtils.replaceFirst(classNameHump, classNameHump.substring(0, 1), classNameHump.substring(0, 1).toUpperCase()));
                        } else {
                            tplGenDBTableDTO.setClassName(exampleInfoDTO.getClassName());
                        }
                        tplGenDBTableDTO.setTableName(exampleInfoDTO.getTableName());
                        tplGenDBTableDTO.setFunctionDir(exampleInfoDTO.getCatalog());
                        this.setTableFileds(tplGenDBTableDTO, tplGenDBInfoDTO, tplGenParamDTO);
                        tplGenDBTableDTOList.add(tplGenDBTableDTO);
                    });
                    tplGenDBInfoDTO.setTplGenDBTableDTOList(tplGenDBTableDTOList);
                }
                tplGenTOList.add(tplGenDBInfoDTO);
            });
            tplGenParamDTO.setTplGenDBInfoDTOList(tplGenTOList);
        }
    }

    public void setMiddleware(ScaffoldGenInfoDTO genInfoDTO, TplGenParamDTO tplGenParamDTO){
        String[] plugins = genInfoDTO.getMiddleware().split("#");
        for (String plugin: plugins) {
            if (TplGenParamEnums.MiddlewarePluginEnum.SATURN.getCode().equals(StringUtils.upperCase(plugin))) {
                continue ;
            }else if(TplGenParamEnums.MiddlewarePluginEnum.KAFKA.getCode().equals(StringUtils.upperCase(plugin)) && DCStrUtil.isNotEmpty(genInfoDTO.getKafkaId())){
                List<Long> ids = Arrays.stream(genInfoDTO.getKafkaId().split(",")).map(kafkaId->Long.parseLong(kafkaId)).collect(Collectors.toList());
                tplGenParamDTO.setFcodeKafkaBaseInfoList(fcodeKafkaInfoManager.getListByIds(ids));
            }else if(TplGenParamEnums.MiddlewarePluginEnum.SENTINEL.getCode().equals(StringUtils.upperCase(plugin)) && DCObjectUtil.isNotEmpty(genInfoDTO.getSentinelId())){
                tplGenParamDTO.setFcodeSentinelBaseInfoDTO(DCBeanUtil.copyNotNull(fcodeSentinelInfoManager.getWrapperById(genInfoDTO.getSentinelId()),new FcodeSentinelBaseInfoDTO()));
            }else if(TplGenParamEnums.MiddlewarePluginEnum.ES.getCode().equals(StringUtils.upperCase(plugin)) && DCObjectUtil.isNotEmpty(genInfoDTO.getEsId())){
                tplGenParamDTO.setEsBaseInfoDTO(esInfoManager.getBaseInfoById(genInfoDTO.getEsId()));
            }else if(TplGenParamEnums.MiddlewarePluginEnum.MONGODB.getCode().equals(StringUtils.upperCase(plugin)) && DCObjectUtil.isNotEmpty(genInfoDTO.getMongodbId())){
                tplGenParamDTO.setFcodeMongodbBaseInfoDTO(fcodeMongodbInfoManager.getInfoById(genInfoDTO.getMongodbId()));
            }else{
                setRedisInfo(plugin,tplGenParamDTO);
            }
        }
    }

    public void setRedisInfo(String plugin,TplGenParamDTO tplGenParamDTO){
        FcodeRedisInfoDTO fcodeRedisInfoDTO = FcodeRedisInfoDTO.builder().name(plugin).build();
        List<FcodeRedisInfoDTO> fcodeRedisInfoDTOS = fcodeRedisInfoManager.selectList(fcodeRedisInfoDTO);
        //如果FcodeRedisInfoDTOs是空，说明没有Redis配置
        if (DCCollectionUtil.isNotEmpty(fcodeRedisInfoDTOS)){
            RedisComponentDTO redisComponentDTO = new RedisComponentDTO();
            redisComponentDTO.setDeployType(fcodeRedisInfoDTOS.get(0).getInstallType());
            redisComponentDTO.setPassword(fcodeRedisInfoDTOS.get(0).getPassword());
            redisComponentDTO.setTimeout(fcodeRedisInfoDTOS.get(0).getTimeOut().longValue());
            redisComponentDTO.setRedisNodeList(concreteRedisNode(fcodeRedisInfoDTOS.get(0).getConnectInfo()));
            if ("sentinel".equalsIgnoreCase(redisComponentDTO.getDeployType())){
                redisComponentDTO.setSentinelMasterList(Arrays.asList(fcodeRedisInfoDTOS.get(0).getClusterName().split("\n")));
            }else  if ("cluster".equalsIgnoreCase(redisComponentDTO.getDeployType())){
                redisComponentDTO.setClusterName(fcodeRedisInfoDTOS.get(0).getClusterName());
            }
            tplGenParamDTO.setRedisComponentDTO(redisComponentDTO);
            if(!tplGenParamDTO.getMiddleareList().contains(TplGenParamEnums.MiddlewarePluginEnum.REDIS.getCode())){
                tplGenParamDTO.getMiddleareList().add(TplGenParamEnums.MiddlewarePluginEnum.REDIS.getCode());
            }
        }
    }


    /**
     * 组装RedisNode
     * @param connectInfo
     * @return
     */
    public List<RedisNode> concreteRedisNode(String connectInfo) {
        List<RedisNode> redisNodeList = new ArrayList<>();
        List<String> collectInfoList = Arrays.asList(connectInfo.split("\n"));
        for (int i=0;i<collectInfoList.size();i++){
            RedisNode nodeInfo = new RedisNode();
            String[] linkInfo = collectInfoList.get(i).split(":");
            nodeInfo.setId(i+1);
            nodeInfo.setHost(linkInfo[0]);
            nodeInfo.setPort(linkInfo[1]);
            redisNodeList.add(nodeInfo);
        }
        return redisNodeList;
    }

    /**
     * 重建文件树
     *
     * @param scaffoldGenInfoDTO
     */
    public void rebuildGenFileInfo(ScaffoldGenInfoDTO scaffoldGenInfoDTO) {
        ScaffoldGenInfoDTO dbScaffoldGenInfoDTO = scaffoldGenInfoManager.getById(scaffoldGenInfoDTO.getId());
        if (DCObjectUtil.isNull(dbScaffoldGenInfoDTO)) {
            return;
        }
        TplGenParamDTO tplGenParamDTO = scaffoldGenInfoManager.convertScaffoldGenInfoDTOToTplGenParamDTO(dbScaffoldGenInfoDTO);
        if(DCStrUtil.isNotBlank(scaffoldGenInfoDTO.getDirNames())){
            tplGenParamDTO.setDirList(DCCollectionUtil.toList(DCStrUtil.split(scaffoldGenInfoDTO.getDirNames(), "#")));
        }
        initGenFileInfo(tplGenParamDTO);
    }

    /**
     * 初始化此次生成动作所对应的文件信息表
     *
     * @param tplGenParamDTO
     * @throws Exception
     */
    public List<GenFileInfoDTO> initGenFileInfo(TplGenParamDTO tplGenParamDTO) {
        //工程
        if (TplGenParamEnums.TemplateTypeEnum.FNEW.getCode().equals(tplGenParamDTO.getTemplateType())
            || TplGenParamEnums.TemplateTypeEnum.MANAGEMENTNEW.getCode().equals(tplGenParamDTO.getTemplateType())
            || TplGenParamEnums.TemplateTypeEnum.BOOTNEW.getCode().equals(tplGenParamDTO.getTemplateType())) {
            DCValidatorUtil.validateModel(tplGenParamDTO, BaseParam.gen.class);
            return genProject(tplGenParamDTO);
        }
        //单功能
        DCValidatorUtil.validateModel(tplGenParamDTO, BaseParam.funcGen.class);
        return genFunction(tplGenParamDTO);
    }

    /**
     * 功能描述:单功能文件生成
     * @param tplGenParamDTO
     * @return java.util.List<com.sf.cemp.fcode.biz.core.componentpage.dto.GenFileInfoDTO>
     */
    private List<GenFileInfoDTO> genFunction(TplGenParamDTO tplGenParamDTO){
        initDBTableColumn(tplGenParamDTO);
        // 渲染右侧文件树 只需渲染单功能文件列表
        List<VelContextFuncDTO> velContextFuncDTOList = createVelContextFunc(tplGenParamDTO);
        // 参数构造三种velocityContext 功能模板渲染、文件名路径渲染、脚手架渲染
        List<GenFileInfoDTO> genFileInfoDTOList = Lists.newArrayList();
        Set<GenFileRenameExtraDTO> genFileRenameExtraDTOSet = Sets.newHashSet();
        for (Map.Entry<String, File> entry : TplGenParamEnums.TemplateTypeEnum.getByCode(tplGenParamDTO.getTemplateType()).getTplPathFileMap().entrySet()) {
            // 文件名包含路径
            String filePathName = entry.getKey();
            String className = velContextFuncDTOList.get(0).getClassName();
            Long genInfoId = velContextFuncDTOList.get(0).getGenInfoId();
            Long dbInfoId = velContextFuncDTOList.get(0).getGenDbId();
            Long exampleId = velContextFuncDTOList.get(0).getGenDbExampleId();
            // 区分功能模块渲染及其他
            if (filePathName.contains("${functionDir}")) {
                // 组装非配置文件
                getFileList(filePathName,velContextFuncDTOList,genFileRenameExtraDTOSet,genFileInfoDTOList);
            }else{
                // 组装配置文件
                GenFileInfoDTO genFileInfoDTO = GenFileInfoDTO.builder()
                        .filePathName(filePathName.replaceAll("\\\\" , "/"))
                        .realPathName(filePathName.replaceAll("\\\\" , "/"))
                        .className(className)
                        .genInfoId(genInfoId)
                        .genDbId(dbInfoId)
                        .genDbExampleId(exampleId)
                        .methodListExtra(JSON.toJSONString(listMethodName(filePathName)))
                        .build();
                genFileInfoDTOList.add(genFileInfoDTO);
            }
        }
        //处理生成的文件
        handleFile(tplGenParamDTO,genFileInfoDTOList,genFileRenameExtraDTOSet);
        return genFileInfoDTOList;
    }

    /**
     * 功能描述:处理生成的文件
     * @param tplGenParamDTO
     * @param genFileInfoDTOList
     * @param genFileRenameExtraDTOSet
     * @return void
     */
    private void handleFile(TplGenParamDTO tplGenParamDTO,List<GenFileInfoDTO> genFileInfoDTOList,Set<GenFileRenameExtraDTO> genFileRenameExtraDTOSet){
        //保存生成的文件
        this.saveFileInfo(genFileInfoDTOList, tplGenParamDTO);
        List<GenFileRenameExtraDTO> genFileRenameExtraDTOList = Lists.newArrayList();
        genFileRenameExtraDTOList.addAll(genFileRenameExtraDTOSet);
        // 覆盖老的重命名格式
        List<GenFileRenameExtraDTO> genFileRenameExtraDTOListQuery = genFileRenameExtraManager.listByGenInfoId(tplGenParamDTO.getGenInfoId());
        if (DCCollectionUtil.isNotEmpty(genFileRenameExtraDTOListQuery)) {
            boolean genRenameFlag = genFileRenameExtraManager.deleteByGenInfoId(tplGenParamDTO.getGenInfoId());
            if (!genRenameFlag) {
                throw new BizException(FcodeResponseResultCodeEnum.GEN_FILE_CREATE_FAIL);
            }
        }
        boolean genFileFlag = genFileRenameExtraManager.saveBatch(genFileRenameExtraDTOList);
        if (!genFileFlag) {
            throw new BizException(FcodeResponseResultCodeEnum.GEN_FILE_CREATE_FAIL);
        }
    }

    /**
     * 功能描述:组装非配置文件
     * @param filePathName
     * @param velContextFuncDTOList
     * @param genFileRenameExtraDTOSet
     * @param genFileInfoDTOList
     * @return void
     */
    private void getFileList(String filePathName,List<VelContextFuncDTO> velContextFuncDTOList,Set<GenFileRenameExtraDTO> genFileRenameExtraDTOSet,List<GenFileInfoDTO> genFileInfoDTOList){
        for (VelContextFuncDTO velContextFuncDTO : velContextFuncDTOList) {
            boolean canContinue = checkDirGen(filePathName, velContextFuncDTO);
            if (!canContinue) {
                break;
            }
            // 绑定预览所需信息 先冗余存储，可考虑查询gen_info表等重新组装信息
            TplFilePreviewDTO tplFilePreviewDTO = DCBeanUtil.copyProperties(velContextFuncDTO, TplFilePreviewDTO.class);
            tplFilePreviewDTO.setFilePathName(filePathName);
            // 转化为真实路径
            String realPathName = renderFileName(filePathName, velContextFuncDTO.getPackageName(), velContextFuncDTO.getFunctionDir(), velContextFuncDTO.getClassName(), null);
            GenFileInfoDTO genFileInfoDTO = GenFileInfoDTO.builder()
                    .filePathName(filePathName.replaceAll("\\\\" , "/"))
                    .realPathName(realPathName.replaceAll("\\\\" , "/"))
                    .className(velContextFuncDTO.getClassName())
                    .genInfoId(velContextFuncDTO.getGenInfoId())
                    .genDbId(velContextFuncDTO.getGenDbId())
                    .genDbExampleId(velContextFuncDTO.getGenDbExampleId())
                    .methodListExtra(JSON.toJSONString(listMethodName(filePathName)))
                    .build();
            GenFileRenameDTO genFileRenameDTO = GenFileRenameDTO.builder()
                    .doName(velContextFuncDTO.getClassName())
                    .dtoName(velContextFuncDTO.getClassName())
                    .queryReqDTOName(velContextFuncDTO.getClassName())
                    .managerName(velContextFuncDTO.getClassName())
                    .mapperName(velContextFuncDTO.getClassName())
                    .mapperXmlName(velContextFuncDTO.getClassName())
                    .serviceName(velContextFuncDTO.getClassName())
                    .serviceImplName(velContextFuncDTO.getClassName())
                    .build();
            GenFileRenameExtraDTO genFileRenameExtraDTO = GenFileRenameExtraDTO.builder()
                    .genInfoId(velContextFuncDTO.getGenInfoId())
                    .genDbId(velContextFuncDTO.getGenDbId())
                    .genDbExampleId(velContextFuncDTO.getGenDbExampleId())
                    .renameExtra(JSON.toJSONString(genFileRenameDTO))
                    .build();
            genFileRenameExtraDTOSet.add(genFileRenameExtraDTO);
            genFileInfoDTOList.add(genFileInfoDTO);
        }
    }

    /**
     * 功能描述:生成工程
     * @param tplGenParamDTO
     * @return java.util.List<com.sf.cemp.fcode.biz.core.componentpage.dto.GenFileInfoDTO>
     */
    private List<GenFileInfoDTO> genProject(TplGenParamDTO tplGenParamDTO) {
        List<GenFileInfoDTO> genFileInfoDTOList = Lists.newArrayList();
        for (Map.Entry<String, File> entry : TplGenParamEnums.TemplateTypeEnum.getByCode(tplGenParamDTO.getTemplateType()).getTplPathFileMap().entrySet()) {
            String filePathName = entry.getKey();
            genFileInfoDTOList.add(getProjectConstruct(filePathName, tplGenParamDTO));
        }
        return this.saveFileInfo(genFileInfoDTOList, tplGenParamDTO);
    }

    /**
     * 功能描述:保存生成文件
     * @param genFileInfoDTOList
     * @param tplGenParamDTO
     * @return void
     */
    private List<GenFileInfoDTO> saveFileInfo(List<GenFileInfoDTO> genFileInfoDTOList, TplGenParamDTO tplGenParamDTO) {
        if (DCCollectionUtil.isEmpty(genFileInfoDTOList)) {
            throw new BusinessException(FcodeResponseResultCodeEnum.GEN_FILE_CREATE_FAIL.getMsg());
        }
        // 根据单次生成id能查到数据，但删除失败则跑错，说明未清理干净 公用方法多消耗一次查询
        List<GenFileInfoDTO> genFileInfoDTOListQuery = genFileInfoManager.listAll(tplGenParamDTO.getGenInfoId());
        if (DCCollectionUtil.isNotEmpty(genFileInfoDTOListQuery)) {
            boolean genFileFlag = genFileInfoManager.deleteAllFileInfoByGenInfoId(tplGenParamDTO.getGenInfoId());
            if (!genFileFlag) {
                throw new BusinessException(FcodeResponseResultCodeEnum.GEN_FILE_CREATE_FAIL.getMsg());
            }
        }
        List<GenFileInfoDTO> newList = genFileInfoManager.saveBatchReturnList(genFileInfoDTOList);
        if (DCCollectionUtil.isEmpty(newList)) {
            throw new BusinessException(FcodeResponseResultCodeEnum.GEN_FILE_CREATE_FAIL.getMsg());
        }
        return newList;
    }

    /**
     * 功能描述:生成工程文件
     * @param filePathName
     * @param tplGenParamDTO
     * @return com.sf.cemp.fcode.biz.core.componentpage.dto.GenFileInfoDTO
     */
    public GenFileInfoDTO getProjectConstruct(String filePathName, TplGenParamDTO tplGenParamDTO) {
        VelContextProjectDTO velContextProjectDTO = this.createVelContextProject(tplGenParamDTO);
        String realPathName = renderFileName(filePathName, tplGenParamDTO.getPackageName(), null, null, velContextProjectDTO.getStartupName());
        GenFileInfoDTO genFileInfoDTO = GenFileInfoDTO.builder()
                .filePathName(tplGenParamDTO.getSysCode() + "/" + filePathName.replaceAll("\\\\" , "/"))
                .realPathName(tplGenParamDTO.getSysCode() + "/" + realPathName.replaceAll("\\\\" , "/"))
                .genInfoId(tplGenParamDTO.getGenInfoId())
                .methodListExtra(JSON.toJSONString(listMethodName(filePathName)))
                .build();
        return genFileInfoDTO;
    }


    /**
     * 列出文件Tree
     *
     * @param tplGenParamDTO
     * @return
     * @throws Exception
     */
    public List<GenFileInfoDTO> loadComponentPage(TplGenParamDTO tplGenParamDTO) throws Exception {
        List<GenFileInfoDTO> genFileInfoDTOList = genFileInfoManager.listAll(tplGenParamDTO.getGenInfoId());
        if (DCCollectionUtil.isEmpty(genFileInfoDTOList)) {
            // 没有数据则初始化进入
            genFileInfoDTOList = initGenFileInfo(tplGenParamDTO);
        }
        if (TplGenParamEnums.TemplateTypeEnum.FNEW.getCode().equals(tplGenParamDTO.getTemplateType())
            || TplGenParamEnums.TemplateTypeEnum.MANAGEMENTNEW.getCode().equals(tplGenParamDTO.getTemplateType())
            || TplGenParamEnums.TemplateTypeEnum.BOOTNEW.getCode().equals(tplGenParamDTO.getTemplateType())) {
            //初始化中间件
            List<String> middleareListOrg = Arrays.stream(TplGenParamEnums.MiddlewarePluginEnum.values()).map(TplGenParamEnums.MiddlewarePluginEnum::getCode).collect(Collectors.toList());
            List<String> middleareList = tplGenParamDTO.getMiddleareList();
            List<String> ignoreMiddleareListRst = middleareListOrg.stream().filter(dto -> !middleareList.contains(dto)).collect(Collectors.toList());
            if (DCCollectionUtil.isNotEmpty(ignoreMiddleareListRst)) {
                for(String middleare: ignoreMiddleareListRst) {
                    genFileInfoDTOList = genFileInfoDTOList.stream().filter(dto -> !dto.getRealPathName().contains("/"+middleare.toLowerCase()))
                            .filter(dto -> !("KAFKA".equals(middleare) && dto.getRealPathName().contains("KafkaConfig")))
                            .filter(dto ->!("ELASTICSEARCH".equals(middleare) && dto.getRealPathName().contains("EsDemo"))).collect(Collectors.toList());
                }
            }
            // 哨兵模式不生成 DemoCacheService
            genFileInfoDTOList = genFileInfoDTOList.stream().filter(dto -> !(tplGenParamDTO.getRedisComponentDTO() != null
                    && TplGenParamEnums.RedisDeployTypeEnum.SENTINEL.getCode().equals(tplGenParamDTO.getRedisComponentDTO().getDeployType())
                    && dto.getRealPathName().contains("DemoCacheService"))).collect(Collectors.toList());
            //初始化工程-功能集合
            if(DCStrUtil.isNotBlank(tplGenParamDTO.getFunctionCollect())){
                List<String> functionList = Arrays.asList("sysConfig","idGen","serverTime");
                List<String> functionCollectList = Arrays.asList(tplGenParamDTO.getFunctionCollect().split("#").clone());
                List<String>  ignoreFunctionListRst = functionList.stream().filter(dto -> !functionCollectList.contains(dto)).collect(Collectors.toList());
                if(DCCollectionUtil.isNotEmpty(ignoreFunctionListRst)){
                    for(String functionCollect: ignoreFunctionListRst) {
                        genFileInfoDTOList = genFileInfoDTOList.stream().filter(dto -> !dto.getRealPathName().contains("/"+functionCollect.toLowerCase())).collect(Collectors.toList());
                    }
                }
            }

            for (GenFileInfoDTO genFileInfoDTO : genFileInfoDTOList) {
                String fileSuffix = ComponentPageEnums.GenFileRenameEnum.getFileSuffix(genFileInfoDTO.getFilePathName());
                genFileInfoDTO.setFileSuffix(fileSuffix);
                genFileInfoDTO.setShowRealPathName(genFileInfoDTO.getRealPathName());
            }
            return genFileInfoDTOList;
        }
        String javaPackageName = "src/main/" + tplGenParamDTO.getPackageName().replaceAll("\\." , "/") + "/";
        String resourcePackageName = "src/main/";
        for (GenFileInfoDTO genFileInfoDTO : genFileInfoDTOList) {
            String fileSuffix = ComponentPageEnums.GenFileRenameEnum.getFileSuffix(genFileInfoDTO.getFilePathName());
            genFileInfoDTO.setFileSuffix(fileSuffix);
            // 简化通用包路径 packageName
            String showRealPathName = genFileInfoDTO.getRealPathName().replace(javaPackageName, "");
            showRealPathName = showRealPathName.replace(resourcePackageName, "");
            genFileInfoDTO.setShowRealPathName(showRealPathName);
        }


        return genFileInfoDTOList;
    }

    // 查询文件对应的方法
    private List<String> listMethodName(String filePathName) {
        // 将文件与方法列表绑定
        for (TplGenParamEnums.FileMethodEnum fileMethodEnum : TplGenParamEnums.FileMethodEnum.values()) {
            if (StringUtils.containsIgnoreCase(filePathName, fileMethodEnum.getFileName())) {
                return fileMethodEnum.getMethodNameList();
            }
        }
        return null;
    }

    /**
     * 模板与变量渲染
     */
    public ByteArrayOutputStream renderTpl(TplGenParamDTO tplGenParamDTO) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(outputStream);
        Integer lineCount = doRender(tplGenParamDTO, zos);
        // 更新统计数据
        updateCodeCalculate(tplGenParamDTO, lineCount);
        IOUtils.closeQuietly(zos);
        return outputStream;
    }

    public ByteArrayOutputStream renderTplList(List<TplGenParamDTO> tplGenParamList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(outputStream);
        VelContextFuncDTO dataDTO = new VelContextFuncDTO();
        List<MultiTableClassDTO> instanceNameList = new ArrayList<>();
        tplGenParamList.forEach(tplGenParamDTO -> {
            Integer lineCount = doMultiTableRender(tplGenParamDTO, zos,dataDTO,instanceNameList);
            // 更新统计数据
            updateCodeCalculate(tplGenParamDTO, lineCount);
        });
        //单独处理配置文件
        String filePath = "src/main/resources/spring-restful-service.xml.vm";
        File file = TplGenParamEnums.TemplateTypeEnum.getByCode(tplGenParamList.get(0).getTemplateType()).getTplPathFileMap().get(filePath);
        onlyFileGen(filePath, file, dataDTO, zos);
        IOUtils.closeQuietly(zos);
        return outputStream;
    }

    /**
     * Sql工具模板与变量渲染
     */
    public ByteArrayOutputStream renderTplSql(TplGenParamDTO tplGenParamDTO) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(outputStream);
        doRenderSql(tplGenParamDTO, zos);
       // updateCodeCalculate(tplGenParamDTO, lineCount);
        IOUtils.closeQuietly(zos);
        return outputStream;
    }

    /**
     * 生成统计
     *
     * @param tplGenParamDTO
     * @param lineCount
     */
    public void updateCodeCalculate(TplGenParamDTO tplGenParamDTO, Integer lineCount) {
        try {
            ScaffoldGenInfoDTO scaffoldGenInfoDTO = ScaffoldGenInfoDTO.builder()
                    .id(tplGenParamDTO.getGenInfoId())
                    .codeLines(lineCount)
                    .build();
            // 更新代码生成基础信息表 的代码行数
            boolean updateFlag = scaffoldGenInfoManager.updateByCodeLine(scaffoldGenInfoDTO);
            if (!updateFlag) {
                log.error("scaffoldGenInfoManager.update fail!, id={}, lineCount={}" , tplGenParamDTO.getGenInfoId(), lineCount);
                return;
            }
            // 统计数据库 表逗号分割
            String databaseName = "";
            String tableName = "";
            Long toolMethodNum = 0L;
            List<String> dirList = tplGenParamDTO.getDirList();
            if (DCCollectionUtil.isNotEmpty(tplGenParamDTO.getTplGenDBInfoDTOList())) {
                for (TplGenDBInfoDTO tplGenDBInfoDTO : tplGenParamDTO.getTplGenDBInfoDTOList()) {
                    databaseName += tplGenDBInfoDTO.getDbName() + ",";
                    if (DCCollectionUtil.isNotEmpty(tplGenDBInfoDTO.getTplGenDBTableDTOList())) {
                        for (TplGenDBTableDTO tplGenDBTableDTO : tplGenDBInfoDTO.getTplGenDBTableDTOList()) {
                            tableName += tplGenDBTableDTO.getTableName() + ",";
                            for (String dirName : dirList) {
                                // 查看生成目录有无 service manager 主要这两个类有方法
                                toolMethodNum += TplGenParamEnums.FileMethodEnum.getMethodNameListByDirName(dirName).size();
                            }
                        }
                    }
                }
            }
            databaseName = StringUtils.removeEnd(databaseName, ",");
            tableName = StringUtils.removeEnd(tableName, ",");
            //注释统计代码
            /*UpdateCalculateReqDTO updateCalculateReqDTO = UpdateCalculateReqDTO.builder()
                    .systemCode(tplGenParamDTO.getSysCode())
                    .downloadTimes(1L)
                    .codeTotalNum(NumberUtils.toLong(lineCount + ""))
                    .databaseName(databaseName)
                    .tableName(tableName)
                    .toolMethodNum(toolMethodNum)
                    .createUser(tplGenParamDTO.getCreateUser())
                    .build();
            calculateManager.updateCodeCalculate(updateCalculateReqDTO);*/
        } catch (Exception e) {
            log.error("updateCodeCalculate:error!genInfoId={}" , tplGenParamDTO.getGenInfoId(), e);
        }
    }

    /**
     * 分类渲染
     */
    private Integer doRender(TplGenParamDTO tplGenParamDTO, ZipOutputStream zos) {
        List<VelContextFuncDTO> velContextFuncDTOList = createVelContextFunc(tplGenParamDTO);
        VelContextProjectDTO velContextProjectDTO = createVelContextProject(tplGenParamDTO);
        // 参数构造三种velocityContext 功能模板渲染、文件名路径渲染、脚手架渲染
        Integer lineCount = 0;
        for (Map.Entry<String, File> entry : TplGenParamEnums.TemplateTypeEnum.getByCode(tplGenParamDTO.getTemplateType()).getTplPathFileMap().entrySet()) {
            // 文件名包含路径
            String filePathName = entry.getKey();
            File file = entry.getValue();
            // 区分功能模块渲染及其他
            if (filePathName.contains("${functionDir}")
                    || (    ((!TplGenParamEnums.TemplateTypeEnum.FNEW.getCode().equals(tplGenParamDTO.getTemplateType()))
                    && (filePathName.contains("spring-restful-service"))))) {
                lineCount += renderFuncDir(filePathName, file, velContextFuncDTOList, zos);
            } else {
                lineCount += renderProject(filePathName, file, velContextProjectDTO, zos);
            }
        }
        return lineCount;
    }

    private Integer doMultiTableRender(TplGenParamDTO tplGenParamDTO, ZipOutputStream zos,VelContextFuncDTO dataDTO,List<MultiTableClassDTO> instanceNameList) {
        List<VelContextFuncDTO> velContextFuncDTOList = createVelContextFunc(tplGenParamDTO);
        VelContextProjectDTO velContextProjectDTO = createVelContextProject(tplGenParamDTO);
        // 参数构造三种velocityContext 功能模板渲染、文件名路径渲染、脚手架渲染
        Integer lineCount = 0;

        for (Map.Entry<String, File> entry : TplGenParamEnums.TemplateTypeEnum.getByCode(tplGenParamDTO.getTemplateType()).getTplPathFileMap().entrySet()) {
            // 文件名包含路径
            String filePathName = entry.getKey();
            File file = entry.getValue();
            // 区分功能模块渲染及其他
            if (filePathName.contains("${functionDir}")
                    || (    ((!TplGenParamEnums.TemplateTypeEnum.FNEW.getCode().equals(tplGenParamDTO.getTemplateType()))
                    && (filePathName.contains("spring-restful-service"))))) {
                lineCount += renderMultiTableFuncDir(filePathName, file, velContextFuncDTOList, zos,dataDTO,instanceNameList);
            } else {
                lineCount += renderProject(filePathName, file, velContextProjectDTO, zos);
            }
        }
        return lineCount;
    }


    /**
     * SQL渲染
     */
    private void doRenderSql(TplGenParamDTO tplGenParamDTO, ZipOutputStream zos) {
        List<VelContextFuncDTO> velContextFuncDTOList = createVelContextSqlTool(tplGenParamDTO);
        VelContextProjectDTO velContextProjectDTO = createVelContextProject(tplGenParamDTO);
        // 参数构造三种velocityContext 功能模板渲染、文件名路径渲染、脚手架渲染
        Integer lineCount = 0;
        for (Map.Entry<String, File> entry : TplGenParamEnums.TemplateTypeEnum.getByCode(tplGenParamDTO.getTemplateType()).getTplPathFileMap().entrySet()) {
            // 文件名包含路径
            String filePathName = entry.getKey();
            File file = entry.getValue();
            // 区分功能模块渲染及其他
            if (filePathName.contains("${functionDir}")
                    || (((!TplGenParamEnums.TemplateTypeEnum.FNEW.getCode().equals(tplGenParamDTO.getTemplateType()))
                    && (filePathName.contains("spring-restful-service"))))) {
                lineCount += renderFuncDir(filePathName, file, velContextFuncDTOList, zos);
            } else {
                lineCount += renderProject(filePathName, file, velContextProjectDTO, zos);
            }
        }
    }

    /**
     * 构建功能模块对象
     *
     * @param tplGenParamDTO
     * @return
     */
    private TplFilePreviewDTO createTplFilePreviewDTO(TplGenParamDTO tplGenParamDTO, GenFileInfoQueryReqDTO genFileInfoQueryReqDTO) {
        // 判断是否包含表功能
        // 构造VelContextFuncDTO
        String createDateString = DCDateUtil.formatDateTime(new Date());
        for (TplGenDBInfoDTO tplGenDBInfoDTO : tplGenParamDTO.getTplGenDBInfoDTOList()) {
            Long genDbId = genFileInfoQueryReqDTO.getGenDbId();
            if (tplGenDBInfoDTO.getGenDbId().longValue() != genDbId.longValue()) {
                continue;
            }
            for (TplGenDBTableDTO tplGenDBTableDTO : tplGenDBInfoDTO.getTplGenDBTableDTOList()) {
                Long genDbExampleId = genFileInfoQueryReqDTO.getGenDbExampleId();
                if (tplGenDBTableDTO.getGenDbExampleId().longValue() != genDbExampleId.longValue()) {
                    continue;
                }
                TplFilePreviewDTO tplFilePreviewDTO = TplFilePreviewDTO.builder()
                        .filePathName(genFileInfoQueryReqDTO.getFilePathName())
                        .genInfoId(genFileInfoQueryReqDTO.getGenInfoId())
                        .genDbId(genFileInfoQueryReqDTO.getGenDbId())
                        .genDbExampleId(genFileInfoQueryReqDTO.getGenDbExampleId())
                        .build();
                DCBeanUtil.copyProperties(tplGenParamDTO, tplFilePreviewDTO);
                DCBeanUtil.copyProperties(tplGenDBTableDTO, tplFilePreviewDTO);
                DCBeanUtil.copyProperties(tplGenDBInfoDTO, tplFilePreviewDTO);
                // 标记用dbName代替
                tplFilePreviewDTO.setDbTag(tplGenDBInfoDTO.getDbName());
                tplFilePreviewDTO.setTableField(tplGenDBTableDTO.getTableField());
                tplFilePreviewDTO.setCreateDateString(createDateString);

                initFileRenameExtra(tplFilePreviewDTO);
                return tplFilePreviewDTO;
            }
        }
        return null;
    }


    private TplFilePreviewDTO createTplFileProjectPreviewDTO(GenFileInfoQueryReqDTO genFileInfoQueryReqDTO) {
        // 判断是否包含表功能
        String createDateString = DCDateUtil.formatDateTime(new Date());
        // 构造VelContextFuncDTO
        TplFilePreviewDTO tplFilePreviewDTO = TplFilePreviewDTO.builder()
                .filePathName(genFileInfoQueryReqDTO.getFilePathName())
                .genInfoId(genFileInfoQueryReqDTO.getGenInfoId())
                .build();
        tplFilePreviewDTO.setCreateDateString(createDateString);
        return tplFilePreviewDTO;
    }


    /**
     * 构建功能模块对象
     */
    private List<VelContextFuncDTO> createVelContextFunc(TplGenParamDTO tplGenParamDTO) {
        // 判断是否包含表功能
        // 构造VelContextFuncDTO
        String createDateString = DCDateUtil.formatDateTime(new Date());
        List<VelContextFuncDTO> velContextFuncDTOList = Lists.newArrayList();
        // 分组减少查询
        Map<GenFileRenameExtraDTO, GenFileRenameDTO> renameGroup = Maps.newHashMap();
        if (DCCollectionUtil.isNotEmpty(tplGenParamDTO.getTplGenDBInfoDTOList())) {
            for (TplGenDBInfoDTO tplGenDBInfoDTO : tplGenParamDTO.getTplGenDBInfoDTOList()) {
                if (DCCollectionUtil.isNotEmpty(tplGenDBInfoDTO.getTplGenDBTableDTOList())) {
                    for (TplGenDBTableDTO tplGenDBTableDTO : tplGenDBInfoDTO.getTplGenDBTableDTOList()) {
                        VelContextFuncDTO velContextFuncDTO = VelContextFuncDTO.builder().build();
                        DCBeanUtil.copyProperties(tplGenParamDTO, velContextFuncDTO);
//                DCBeanUtil.copyProperties(tplGenDBInfoDTO, velContextFuncDTO);
                        velContextFuncDTO.setGenDbId(tplGenDBInfoDTO.getGenDbId());
                        DCBeanUtil.copyProperties(tplGenDBTableDTO, velContextFuncDTO);
                        // 标记用dbName代替
                        velContextFuncDTO.setDbTag(tplGenDBInfoDTO.getDbName());
                        velContextFuncDTO.setTableField(tplGenDBTableDTO.getTableField());
                        velContextFuncDTO.setCreateDateString(createDateString);
                        initFileRenameExtra(velContextFuncDTO, renameGroup);
                        // 多表关联查询处理
                        if (TplGenParamEnums.TemplateTypeEnum.WDFUNC.getCode().equals(tplGenParamDTO.getTemplateType())) {
                            FunctionGenSqlDTO genSqlDTO = functionGenSqlManager.getByGenInfoId(tplGenParamDTO.getGenInfoId());
                            if (genSqlDTO != null) {
                                velContextFuncDTO.setPreviewSql(genSqlDTO.getPreviewSql());
                            }
                        }
                        velContextFuncDTOList.add(velContextFuncDTO);
                    }
                }
            }
        }
        return velContextFuncDTOList;
    }


    /**
     * 构建功能模块对象
     */
    private List<VelContextFuncDTO> createVelContextSqlFunc(TplGenParamDTO tplGenParamDTO) {
        // 判断是否包含表功能
        // 构造VelContextFuncDTO
        String createDateString = DCDateUtil.formatDateTime(new Date());
        List<VelContextFuncDTO> velContextFuncDTOList = Lists.newArrayList();
        // 分组减少查询
        Map<GenFileRenameExtraDTO, GenFileRenameDTO> renameGroup = Maps.newHashMap();
        if (DCCollectionUtil.isNotEmpty(tplGenParamDTO.getTplGenDBInfoDTOList())) {
            for (TplGenDBInfoDTO tplGenDBInfoDTO : tplGenParamDTO.getTplGenDBInfoDTOList()) {
                if (DCCollectionUtil.isNotEmpty(tplGenDBInfoDTO.getTplGenDBTableDTOList())) {
                    for (TplGenDBTableDTO tplGenDBTableDTO : tplGenDBInfoDTO.getTplGenDBTableDTOList()) {
                        VelContextFuncDTO velContextFuncDTO = VelContextFuncDTO.builder().build();
                        DCBeanUtil.copyProperties(tplGenParamDTO, velContextFuncDTO);
//                DCBeanUtil.copyProperties(tplGenDBInfoDTO, velContextFuncDTO);
                        velContextFuncDTO.setGenDbId(tplGenDBInfoDTO.getGenDbId());
                        DCBeanUtil.copyProperties(tplGenDBTableDTO, velContextFuncDTO);
                        // 标记用dbName代替
                        velContextFuncDTO.setDbTag(tplGenDBInfoDTO.getDbName());
                        velContextFuncDTO.setTableField(tplGenDBTableDTO.getTableField());
                        velContextFuncDTO.setCreateDateString(createDateString);
                        initFileRenameExtra(velContextFuncDTO, renameGroup);
                        // 多表关联查询处理
                        velContextFuncDTO.setPreviewSql(tplGenParamDTO.getSql());
                        velContextFuncDTOList.add(velContextFuncDTO);
                    }
                }
            }
        }
        return velContextFuncDTOList;
    }


    /**
     * 构建功能模块对象
     */
    private List<VelContextFuncDTO> createVelContextSqlTool(TplGenParamDTO tplGenParamDTO) {
        // 判断是否包含表功能
        // 构造VelContextFuncDTO
        String createDateString = DCDateUtil.formatDateTime(new Date());
        List<VelContextFuncDTO> velContextFuncDTOList = Lists.newArrayList();
        // 分组减少查询
        Map<GenFileRenameExtraDTO, GenFileRenameDTO> renameGroup = Maps.newHashMap();
        if (DCCollectionUtil.isNotEmpty(tplGenParamDTO.getTplGenDBInfoDTOList())) {
            for (TplGenDBInfoDTO tplGenDBInfoDTO : tplGenParamDTO.getTplGenDBInfoDTOList()) {
                VelContextFuncDTO velContextFuncDTO = new VelContextFuncDTO();
                DCBeanUtil.copyProperties(tplGenParamDTO, velContextFuncDTO);
//                DCBeanUtil.copyProperties(tplGenDBInfoDTO, velContextFuncDTO);
                velContextFuncDTO.setGenDbId(tplGenDBInfoDTO.getGenDbId());
                DCBeanUtil.copyProperties(tplGenParamDTO.getTplGenDBTableDTO(), velContextFuncDTO);
                // 标记用dbName代替
                velContextFuncDTO.setDbTag(tplGenDBInfoDTO.getDbName());
                velContextFuncDTO.setTableField(tplGenParamDTO.getTplGenDBTableDTO().getTableField());
                velContextFuncDTO.setCreateDateString(createDateString);
               // initFileRenameExtra(velContextFuncDTO, renameGroup);
                // 多表关联查询处理
                velContextFuncDTO.setPreviewSql(tplGenParamDTO.getSql());
                velContextFuncDTOList.add(velContextFuncDTO);
            }
        }
        return velContextFuncDTOList;
    }

    /**
     * 生成目录控制
     *
     * @param velContextFuncDTO
     */
    private boolean checkDirGen(String filePathName, VelContextFuncDTO velContextFuncDTO) {
        // 简单处理，硬编码进去控制目录
        List<String> dirList = velContextFuncDTO.getDirList();
        // 没有勾选则全量生成
        if (DCCollectionUtil.isNotEmpty(dirList)) {
            // 包含service则增加dto
            if (dirList.contains(TplGenParamEnums.SingleFucPluginEnum.SERVICE.getCode())) {
                dirList.add(TplGenParamEnums.SingleFucPluginEnum.SERVICE.getRelationDir());
            }
            if (dirList.contains(TplGenParamEnums.SingleFucPluginEnum.REPOSITORY.getCode())) {
                dirList.add(TplGenParamEnums.SingleFucPluginEnum.REPOSITORY.getRelationDir());
            }
            // 处理目录控制 service dto manager 匹配目录处理
            for (String dir : dirList) {
                if (StringUtils.containsIgnoreCase(filePathName, dir)) {
                    // 包含则要生成 service跟dto repository跟mapper绑定
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 创建工程渲染对象
     *
     * @param tplGenParamDTO
     * @return
     */
    private VelContextProjectDTO createVelContextProject(TplGenParamDTO tplGenParamDTO) {
        String createDateString = DCDateUtil.formatDateTime(new Date());
        VelContextProjectDTO velContextProjectDTO = VelContextProjectDTO.builder().build();
        DCBeanUtil.copyProperties(tplGenParamDTO, velContextProjectDTO);
        List<VelContextTableDTO> tableList = Lists.newArrayList();
        List<VelContextDBInfoDTO> dbInfoList = Lists.newArrayList();
        if (DCCollectionUtil.isNotEmpty(tplGenParamDTO.getTplGenDBInfoDTOList())) {
            for (TplGenDBInfoDTO tplGenDBInfoDTO : tplGenParamDTO.getTplGenDBInfoDTOList()) {
                VelContextDBInfoDTO velContextDBInfoDTO = VelContextDBInfoDTO.builder().build();
                DCBeanUtil.copyProperties(tplGenDBInfoDTO, velContextDBInfoDTO);
                // 标记用dbName代替
                velContextDBInfoDTO.setDbTag(tplGenDBInfoDTO.getDbName());
                dbInfoList.add(velContextDBInfoDTO);
                if (DCCollectionUtil.isNotEmpty(tplGenDBInfoDTO.getTplGenDBTableDTOList())) {
                    for (TplGenDBTableDTO tplGenDBTableDTO : tplGenDBInfoDTO.getTplGenDBTableDTOList()) {
                        VelContextTableDTO velContextTableDTO = VelContextTableDTO.builder().build();
                        DCBeanUtil.copyProperties(tplGenDBTableDTO, velContextTableDTO);
                        tableList.add(velContextTableDTO);
                    }
                }
            }
        }
        // 特殊处理Application启动类名
        String startupName = "";
        if (DCStrUtil.isNotBlank(velContextProjectDTO.getSysCode())) {
            startupName = velContextProjectDTO.getSysCode();
            startupName = startupName.substring(startupName.lastIndexOf("-")+1);
            startupName = TplGenUtil.upperCaseWithSeparatorChars(startupName, "-");
        }
        velContextProjectDTO.setStartupName(startupName);
        String artifactId = velContextProjectDTO.getArtifactId() == null ? velContextProjectDTO.getSysCode() : velContextProjectDTO.getArtifactId();
        velContextProjectDTO.setArtifactId(artifactId);
        velContextProjectDTO.setCreateDateString(createDateString);
        velContextProjectDTO.setDbInfoList(dbInfoList);
        velContextProjectDTO.setTableList(tableList);
        return velContextProjectDTO;
    }


    /**
     * 工程渲染
     */
    private Integer renderProject(String filePathName, File file, VelContextProjectDTO velContextProjectDTO, ZipOutputStream zos) {
        Integer lineCount = 0;
        // 组件控制
        boolean middleareFlag = checkMiddleare(filePathName, velContextProjectDTO.getMiddleareList(), velContextProjectDTO.getRedisComponentDTO());
        if (!middleareFlag) {
            return lineCount;
        }
        //初始化工程-功能集合
        boolean functionFlag = checkFunctionCollect(filePathName,velContextProjectDTO.getFunctionCollect());
        if (!functionFlag) {
            return lineCount;
        }
        try {
            StringWriter sw = new StringWriter();
            Reader reader = new InputStreamReader(new FileInputStream(file));
            VelocityContext velocityContext = new VelocityContext(DCBeanUtil.beanToMap(velContextProjectDTO));
            Velocity.evaluate(velocityContext, sw, "logTag" , reader);
            // 转化为真实路径
            String realPathName = renderFileName(filePathName, velContextProjectDTO.getPackageName(), null, null, velContextProjectDTO.getStartupName());
            zos.putNextEntry(new ZipEntry(realPathName));
            String fileString = sw.toString();
            String lineSeparator = System.getProperty("line.separator");
            lineCount = StringUtils.countMatches(fileString, lineSeparator);
            IOUtils.write(sw.toString(), zos, "UTF-8");
            IOUtils.closeQuietly(sw);
            zos.flush();
            zos.closeEntry();
        } catch (Exception e) {
            log.error("工程渲染系统异常!errMsg：{}" , e.getMessage(), e);
            throw new BizException(FcodeResponseResultCodeEnum.GEN_FILE_RENDER_FAIL);
        }
        return lineCount;
    }

    /**
     * 控制中间件相关文件生成
     *
     * @param filePathName
     * @param middleareList
     * @return
     */
    private boolean checkMiddleare(String filePathName, List<String> middleareList, RedisComponentDTO redisComponentDTO) {
        if (middleareList == null) {
            middleareList = Lists.newArrayList();
        }
        final List<String> middList = middleareList;
        // 默认都生成 取差集不生产
        List<String> allMiddleareList = Arrays.stream(TplGenParamEnums.MiddlewarePluginEnum.values()).map(TplGenParamEnums.MiddlewarePluginEnum::getCode).collect(Collectors.toList());
        List<String> deleteMiddleareList = allMiddleareList.stream().filter(middleare -> !middList.contains(middleare)).collect(Collectors.toList());
        for (String middleare : deleteMiddleareList) {
            if (StringUtils.containsIgnoreCase(filePathName, middleare)) {
                // 确认是否生成该文件
                return false;
            }
        }
        // 特殊处理 redis sentinel 无需redis.xml配置
        if (redisComponentDTO != null && TplGenParamEnums.RedisDeployTypeEnum.SENTINEL.getCode().equals(redisComponentDTO.getDeployType())) {
            if (StringUtils.containsIgnoreCase(filePathName, "spring-redis-config.xml")) {
                return false;
            }
            if (StringUtils.containsIgnoreCase(filePathName, "/framework/notify/")) {
                return false;
            }
        }
        return true;
    }

    private boolean checkFunctionCollect(String filePathName, String functionCollect) {
        List<String> functionList = null;
        if (DCStrUtil.isBlank(functionCollect)) {
            functionList = Lists.newArrayList();
        }else{
            functionList = Arrays.asList(functionCollect.split("#"));
        }
        final List<String> functionCollectList = functionList;
        // 默认都生成 取差集不生产
        List<String> allFunctionList = Arrays.stream(TplGenParamEnums.FunctionCollectEnum.values()).map(TplGenParamEnums.FunctionCollectEnum::getCode).collect(Collectors.toList());
        List<String> deleteFunctionList = allFunctionList.stream().filter(function -> !functionCollectList.contains(function)).collect(Collectors.toList());
        for (String function : deleteFunctionList) {
            if (StringUtils.containsIgnoreCase(filePathName, function)) {
                // 确认是否生成该文件
                return false;
            }
        }
        return true;
    }

    /**
     * 非功能文件路径名
     *
     * @param fileName
     * @return
     */
    private String renderFileName(String fileName, String packageName, String functionDir, String className, String startupName) {
        Map<String, String> replaceValue = Maps.newHashMap();
        String tmpPkgName = null;
        if (StringUtils.isNotBlank(packageName)) {
            tmpPkgName = packageName.replaceAll("\\." , "/");
        }
        String tmpFuncDir = null;
        if (StringUtils.isNotBlank(functionDir)) {
            tmpFuncDir = functionDir.replaceAll("\\." , "/");
        }
        replaceValue.put("packageName" , tmpPkgName);
        replaceValue.put("functionDir" , tmpFuncDir);
        replaceValue.put("className" , className);
        replaceValue.put("startupName" , startupName);
        StrSubstitutor strSubstitutor = new StrSubstitutor(replaceValue);
        return StringUtils.removeEnd(strSubstitutor.replace(fileName), ".vm");
    }

    /**
     * 单功能渲染
     *
     * @param filePathName
     * @param file
     * @param zos
     * @throws Exception
     */
    private Integer renderFuncDir(String filePathName, File file, List<VelContextFuncDTO> velocityContextList, ZipOutputStream zos) {
        // functionDir className 替换 根据模板文件生成多个
        Integer lineCount = 0;
        try {
            for (VelContextFuncDTO velContextFuncDTO : velocityContextList) {
                if (filePathName.contains("spring-restful-service") && (!velContextFuncDTO.getDirList().contains(TplGenParamEnums.SingleFucPluginEnum.SERVICE.getCode()))){
                    break;
                }
                boolean canContinue = checkDirGen(filePathName, velContextFuncDTO);
                if (!canContinue) {
                    break;
                }
                StringWriter sw = new StringWriter();
                Reader reader = new InputStreamReader(new FileInputStream(file));
                //Rename操作
                initFileRenameClassName(filePathName, velContextFuncDTO);
                VelocityContext velocityContext = new VelocityContext(DCBeanUtil.beanToMap(velContextFuncDTO));
                Velocity.evaluate(velocityContext, sw, "logTag" , reader);
                // 转化为真实路径
                String realPathName = renderFileName(filePathName, velContextFuncDTO.getPackageName(), velContextFuncDTO.getFunctionDir(), velContextFuncDTO.getClassName(), null);
                zos.putNextEntry(new ZipEntry(realPathName));
                String fileString = sw.toString();
                String lineSeparator = System.getProperty("line.separator");
                lineCount = StringUtils.countMatches(fileString, lineSeparator);

                IOUtils.write(fileString, zos, "UTF-8");
                IOUtils.closeQuietly(sw);
                zos.flush();
                zos.closeEntry();
            }
        } catch (Exception e) {
            log.error("单功能系统异常!errMsg:{}" , e.getMessage(), e);
            throw new BizException(FcodeResponseResultCodeEnum.GEN_FILE_RENDER_FAIL);
        }
        return lineCount;
    }

    private Integer renderMultiTableFuncDir(String filePathName, File file, List<VelContextFuncDTO> velocityContextList, ZipOutputStream zos,VelContextFuncDTO dataDTO,List<MultiTableClassDTO> instanceNameList) {
        // functionDir className 替换 根据模板文件生成多个
        Integer lineCount = 0;
        try {
            for (VelContextFuncDTO velContextFuncDTO : velocityContextList) {
                if (filePathName.contains("spring-restful-service") && (!velContextFuncDTO.getDirList().contains(TplGenParamEnums.SingleFucPluginEnum.SERVICE.getCode()))){
                    break;
                }
                if(filePathName.contains("spring-restful-service")){
                    DCBeanUtil.copyNotNull(velContextFuncDTO,dataDTO);
                    MultiTableClassDTO multiTableClassDTO = new MultiTableClassDTO();
                    multiTableClassDTO.setClassName(dataDTO.getClassName());
                    multiTableClassDTO.setInstanceName(dataDTO.getInstanceName());
                    multiTableClassDTO.setFunctionDir(dataDTO.getFunctionDir());
                    instanceNameList.add(multiTableClassDTO);
                    dataDTO.setMultiTableClassList(instanceNameList);
                    break;
                }
                boolean canContinue = checkDirGen(filePathName, velContextFuncDTO);
                if (!canContinue) {
                    break;
                }
                StringWriter sw = new StringWriter();
                Reader reader = new InputStreamReader(new FileInputStream(file));
                //Rename操作
                initFileRenameClassName(filePathName, velContextFuncDTO);
                VelocityContext velocityContext = new VelocityContext(DCBeanUtil.beanToMap(velContextFuncDTO));
                Velocity.evaluate(velocityContext, sw, "logTag" , reader);
                // 转化为真实路径
                String realPathName = renderFileName(filePathName, velContextFuncDTO.getPackageName(), velContextFuncDTO.getFunctionDir(), velContextFuncDTO.getClassName(), null);
                zos.putNextEntry(new ZipEntry(realPathName));
                String fileString = sw.toString();
                String lineSeparator = System.getProperty("line.separator");
                lineCount = StringUtils.countMatches(fileString, lineSeparator);

                IOUtils.write(fileString, zos, "UTF-8");
                IOUtils.closeQuietly(sw);
                zos.flush();
                zos.closeEntry();
            }
        } catch (Exception e) {
            log.error("单功能系统异常!errMsg:{}" , e.getMessage(), e);
            throw new BizException(FcodeResponseResultCodeEnum.GEN_FILE_RENDER_FAIL);
        }
        return lineCount;
    }

    private void onlyFileGen(String filePathName, File file, VelContextFuncDTO velContextFuncDTO, ZipOutputStream zos){
        StringWriter sw = new StringWriter();
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            //Rename操作
            initFileRenameClassName(filePathName, velContextFuncDTO);
            VelocityContext velocityContext = new VelocityContext(DCBeanUtil.beanToMap(velContextFuncDTO));
            Velocity.evaluate(velocityContext, sw, "logTag" , reader);
            // 转化为真实路径
            String realPathName = renderFileName(filePathName, velContextFuncDTO.getPackageName(), velContextFuncDTO.getFunctionDir(), velContextFuncDTO.getClassName(), null);
            zos.putNextEntry(new ZipEntry(realPathName));
            String fileString = sw.toString();
            IOUtils.write(fileString, zos, "UTF-8");
            IOUtils.closeQuietly(sw);
            zos.flush();
            zos.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化重命名className
     *
     * @param filePathName
     * @param velContextFuncDTO
     */
    private void initFileRenameClassName(String filePathName, VelContextFuncDTO velContextFuncDTO) {
        // 判断className 是否有重命名 file_info记录 有则 覆盖
        GenFileInfoQueryReqDTO genFileInfoQueryReqDTO = GenFileInfoQueryReqDTO.builder()
                .genInfoId(velContextFuncDTO.getGenInfoId())
                .genDbId(velContextFuncDTO.getGenDbId())
                .genDbExampleId(velContextFuncDTO.getGenDbExampleId())
                .filePathName(filePathName)
                .deleteFlag(DBConstants.DELETE_FLAG_N)
                .build();
        GenFileInfoDTO genFileInfoDTO = genFileInfoManager.getOneGenFileInfo(genFileInfoQueryReqDTO);
        if (genFileInfoDTO == null) {
            return;
        }
        if (DCStrUtil.isNotBlank(genFileInfoDTO.getClassName()) && genFileInfoDTO.getClassName().contains("_")) {
            String classNameHump = TplGenUtil.underlineToHump(genFileInfoDTO.getClassName());
            genFileInfoDTO.setClassName(StringUtils.replaceFirst(classNameHump, classNameHump.substring(0, 1), classNameHump.substring(0, 1).toUpperCase()));
        }
        String className = genFileInfoDTO.getClassName();
        if (!StringUtils.equals(className, velContextFuncDTO.getClassName())) {
            velContextFuncDTO.setClassName(className);
        }
    }


    /**
     * 初始化重命名引用扩展 预览
     *
     * @param tplFilePreviewDTO
     */
    public void initFileRenameExtra(TplFilePreviewDTO tplFilePreviewDTO) {
        // 解决重命名引用问题 读取renameextra表数据
        GenFileRenameExtraDTO genFileRenameExtraDTOQuery = GenFileRenameExtraDTO.builder()
                .genInfoId(tplFilePreviewDTO.getGenInfoId())
                .genDbId(tplFilePreviewDTO.getGenDbId())
                .genDbExampleId(tplFilePreviewDTO.getGenDbExampleId())
                .build();
        GenFileRenameExtraDTO genFileRenameExtraDTO = genFileRenameExtraManager.getByObj(genFileRenameExtraDTOQuery);
        GenFileRenameDTO genFileRenameDTO = null;
        if (Objects.isNull(genFileRenameExtraDTO)) {
            // 初始化一个
            genFileRenameDTO = GenFileRenameDTO.builder()
                    .doName(tplFilePreviewDTO.getClassName())
                    .dtoName(tplFilePreviewDTO.getClassName())
                    .queryReqDTOName(tplFilePreviewDTO.getClassName())
                    .managerName(tplFilePreviewDTO.getClassName())
                    .mapperName(tplFilePreviewDTO.getClassName())
                    .mapperXmlName(tplFilePreviewDTO.getClassName())
                    .serviceName(tplFilePreviewDTO.getClassName())
                    .serviceImplName(tplFilePreviewDTO.getClassName())
                    .build();
        } else {
            genFileRenameDTO = JSON.parseObject(genFileRenameExtraDTO.getRenameExtra(), GenFileRenameDTO.class);
        }
        tplFilePreviewDTO.setGenFileRenameDTO(genFileRenameDTO);
    }

    /**
     * 初始化重命名引用扩展
     *
     * @param velContextFuncDTO
     */
    public void initFileRenameExtra(VelContextFuncDTO velContextFuncDTO, Map<GenFileRenameExtraDTO, GenFileRenameDTO> renameGroup) {
        // 解决重命名引用问题 读取renameextra表数据
        GenFileRenameExtraDTO genFileRenameExtraDTOQuery = GenFileRenameExtraDTO.builder()
                .genInfoId(velContextFuncDTO.getGenInfoId())
                .genDbId(velContextFuncDTO.getGenDbId())
                .genDbExampleId(velContextFuncDTO.getGenDbExampleId())
                .build();
        GenFileRenameDTO genFileRenameDTOCache = renameGroup.get(genFileRenameExtraDTOQuery);
        if (genFileRenameDTOCache != null) {
            velContextFuncDTO.setGenFileRenameDTO(genFileRenameDTOCache);
            return;
        }
        GenFileRenameExtraDTO genFileRenameExtraDTO = genFileRenameExtraManager.getByObj(genFileRenameExtraDTOQuery);
        GenFileRenameDTO genFileRenameDTO = null;
        if (Objects.isNull(genFileRenameExtraDTO)) {
            // 初始化一个
            genFileRenameDTO = GenFileRenameDTO.builder()
                    .doName(velContextFuncDTO.getClassName())
                    .dtoName(velContextFuncDTO.getClassName())
                    .queryReqDTOName(velContextFuncDTO.getClassName())
                    .managerName(velContextFuncDTO.getClassName())
                    .mapperName(velContextFuncDTO.getClassName())
                    .mapperXmlName(velContextFuncDTO.getClassName())
                    .serviceName(velContextFuncDTO.getClassName())
                    .serviceImplName(velContextFuncDTO.getClassName())
                    .build();
        } else {
            genFileRenameDTO = JSON.parseObject(genFileRenameExtraDTO.getRenameExtra(), GenFileRenameDTO.class);
        }
        velContextFuncDTO.setGenFileRenameDTO(genFileRenameDTO);
        renameGroup.put(genFileRenameExtraDTOQuery, genFileRenameDTO);
    }


    /**
     * 初始化表字段
     *
     * @param tplGenParamDTO
     */
    private void initDBTableColumn(TplGenParamDTO tplGenParamDTO) {
        if (DCCollectionUtil.isNotEmpty(tplGenParamDTO.getTplGenDBInfoDTOList())) {
            for (TplGenDBInfoDTO tplGenDBInfoDTO : tplGenParamDTO.getTplGenDBInfoDTOList()) {
                DCValidatorUtil.validateModel(tplGenDBInfoDTO, BaseParam.dbGen.class);
                if (DCCollectionUtil.isNotEmpty(tplGenDBInfoDTO.getTplGenDBTableDTOList())) {
                    for (TplGenDBTableDTO tplGenDBTableDTO : tplGenDBInfoDTO.getTplGenDBTableDTOList()) {
                        setTableFileds(tplGenDBTableDTO, tplGenDBInfoDTO, tplGenParamDTO);
                    }
                }
            }
        }
    }

    /**
     * Sql工具初始化表字段
     *
     * @param tplGenParamDTO
     */
    private void initDBTableColumnSql(TplGenParamDTO tplGenParamDTO) {
        if (DCCollectionUtil.isNotEmpty(tplGenParamDTO.getTplGenDBInfoDTOList())) {
            for (TplGenDBInfoDTO tplGenDBInfoDTO : tplGenParamDTO.getTplGenDBInfoDTOList()) {
                setTableFiledSqlTool(tplGenDBInfoDTO, tplGenParamDTO);
            }
        }
    }

    public void setTableFileds(TplGenDBTableDTO tplGenDBTableDTO, TplGenDBInfoDTO tplGenDBInfoDTO, TplGenParamDTO tplGenParamDTO) {
        Map<String,List<TplGenTableColumnDTO>> map = getColumns(tplGenDBTableDTO.getTableName(),tplGenDBInfoDTO,tplGenParamDTO);
        tplGenDBTableDTO.setTableField(map.get("tableField"));
        tplGenDBTableDTO.setPrimaryKeyInfos(map.get("primaryKeyInfos"));
    }

    public Map<String,List<TplGenTableColumnDTO>> getColumns(String tableName,TplGenDBInfoDTO tplGenDBInfoDTO,TplGenParamDTO tplGenParamDTO){
        Map<String,List<TplGenTableColumnDTO>> map = new HashMap<>();
        //1.无配置，设置默认值
        List<TableDetailInfoDTO> tableDetailInfoList = tableDetailInfoManager.getColumnConfigByGenInfoId(tplGenParamDTO.getGenInfoId());
        if(DCCollectionUtil.isEmpty(tableDetailInfoList)){
            return getNoSetColumns(tableName,tplGenDBInfoDTO,tplGenParamDTO);
        }
        //2.如果有配置，读取配置,oracle的主键需要单独查询一次，mysql的不用
        List<TableDetailInfoDTO> primaryKeyInfos = new ArrayList<>();
        if (TplGenParamEnums.TemplateTypeEnum.DFUNC.getCode().equals(tplGenParamDTO.getTemplateType())
                || TplGenParamEnums.TemplateTypeEnum.SQLFUNC.getCode().equals(tplGenParamDTO.getTemplateType())
                || TplGenParamEnums.TemplateTypeEnum.BOOTDFUNC.getCode().equals(tplGenParamDTO.getTemplateType())){
            List<String>  primaryKeys ;
            DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder().build().initDBInfo(tplGenDBInfoDTO.getDbUrl(), tplGenDBInfoDTO.getUsername(), tplGenDBInfoDTO.getPassword());
            dynamicDBInfo.setTableName(tableName);
            if(tplGenDBInfoDTO.getDbUrl().contains(SystemDBInfoEnums.DriverEnum.ORACLE.getCode())){
                primaryKeys = dynamicDBManager.selectOraclePrimaryKeys(dynamicDBInfo);
            }else{
                // 数据选项不为空则选择获取db表字段
                primaryKeys = dynamicDBManager.selectMysqlPrimaryKeys(dynamicDBInfo);
            }
            tableDetailInfoList.forEach(column->{
                if(primaryKeys.contains(column.getColumnName())){
                    column.setColumnKey("PRI");
                    primaryKeyInfos.add(column);
                }
            });
        }
        map.put("tableField",DCBeanUtil.copyList(tableDetailInfoList,TplGenTableColumnDTO.class));
        map.put("primaryKeyInfos",DCBeanUtil.copyList(primaryKeyInfos,TplGenTableColumnDTO.class));
        return map;
    }

    private Map<String,List<TplGenTableColumnDTO>> getNoSetColumns(String tableName,TplGenDBInfoDTO tplGenDBInfoDTO,TplGenParamDTO tplGenParamDTO){
        List<InformationSchemaTableColumn> primaryKeyInfos = new ArrayList<>();
        Map<String,List<TplGenTableColumnDTO>> map = new HashMap<>();
        List<InformationSchemaTableColumn> informationSchemaTableColumnList;
        if (TplGenParamEnums.TemplateTypeEnum.WDFUNC.getCode().equals(tplGenParamDTO.getTemplateType()) && DCStrUtil.isNotBlank(tableName) && tableName.split(",").length > 0) {
            // 多表关联查询的时候，需处理查询的字段
            informationSchemaTableColumnList = getMultiTableColumns(tplGenParamDTO.getGenInfoId());
        } else {
            DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder().build().initDBInfo(tplGenDBInfoDTO.getDbUrl(), tplGenDBInfoDTO.getUsername(), tplGenDBInfoDTO.getPassword());
            dynamicDBInfo.setTableName(tableName);
            if (dynamicDBInfo.getDbUrl().contains(SystemDBInfoEnums.DriverEnum.ORACLE.getCode())) {
                informationSchemaTableColumnList = dynamicDBManager.selectOracleColumns(dynamicDBInfo);
                List<String>  primaryKeys = dynamicDBManager.selectOraclePrimaryKeys(dynamicDBInfo);
                for(InformationSchemaTableColumn column:informationSchemaTableColumnList){
                    if(primaryKeys.contains(column.getColumnName())){
                        column.setColumnKey("PRI");
                        primaryKeyInfos.add(column);
                    }
                }
            } else {
                // 数据选项不为空则选择获取db表字段
                informationSchemaTableColumnList = dynamicDBManager.listTableColumn(dynamicDBInfo);
                primaryKeyInfos = informationSchemaTableColumnList.stream().filter(key->"PRI".equals(key)).collect(Collectors.toList());
            }
        }
        map.put("tableField",addList(tplGenParamDTO.getGenInfoId(),tplGenParamDTO.getTemplateType(),informationSchemaTableColumnList));
        map.put("primaryKeyInfos",columnToColumnDetail(tplGenParamDTO.getGenInfoId(),tplGenParamDTO.getTemplateType(),primaryKeyInfos));
        return map;
    }


    public void setTableFiledSqlTool(TplGenDBInfoDTO tplGenDBInfoDTO, TplGenParamDTO tplGenParamDTO) {
        // 多表关联查询的时候，需处理查询的字段
        //待展示的字段
        TplGenDBTableDTO tplGenDBTableDTO = new TplGenDBTableDTO();
        tplGenDBTableDTO.setClassName(tplGenParamDTO.getInstance().substring(0,1).toUpperCase()+tplGenParamDTO.getInstance().substring(1));
        tplGenDBTableDTO.setFunctionDir(tplGenParamDTO.getInstance().toLowerCase());
        List<InformationSchemaTableColumn> informationSchemaTableColumnList = new ArrayList<>();
        if (DCCollectionUtil.isNotEmpty(tplGenParamDTO.getAllTableNameBySQL())){
            tplGenParamDTO.getAllTableNameBySQL().stream().forEach(allName->{
                for (Map.Entry<String, List<String>> entry : allName.entrySet()) {
                    log.info("Key = {},value={}" , entry.getKey() , entry.getValue());
                    DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder().tableName(entry.getKey()).host(tplGenDBInfoDTO.getHost()).dsKey(tplGenDBInfoDTO.getDbName()).dbName(tplGenDBInfoDTO.getDbName()).username(tplGenDBInfoDTO.getUsername()).password(tplGenDBInfoDTO.getPassword()).port(tplGenDBInfoDTO.getPort()).dbUrl(tplGenDBInfoDTO.getDbUrl()).driverName(tplGenDBInfoDTO.getDriverName()).build();
                    List<InformationSchemaTableColumn> informationSchemaTableColumns = dynamicDBManager.listTableColumn(dynamicDBInfo);
                    for (InformationSchemaTableColumn informationSchemaTableColumn: informationSchemaTableColumns){
                        if (DCCollectionUtil.isNotEmpty(informationSchemaTableColumnList)){
                            List<InformationSchemaTableColumn> judgeExist = informationSchemaTableColumnList.stream().filter(o -> o.getColumnName().equalsIgnoreCase(informationSchemaTableColumn.getColumnName())).collect(Collectors.toList());
                            if (DCCollectionUtil.isNotEmpty(judgeExist)){
                                informationSchemaTableColumn.setColumnName(entry.getKey()+"_"+informationSchemaTableColumn.getColumnName());
                                informationSchemaTableColumnList.add(informationSchemaTableColumn);
                            }
                        }
                    }
                    // 判断
                    informationSchemaTableColumnList.addAll(informationSchemaTableColumns.stream().filter(obj->entry.getValue().contains(obj.getColumnName())).collect(Collectors.toList()));

                    informationSchemaTableColumnList.stream().forEach(tc->{
                        // 当存在相同id的时候，去选择alais的值
                        List<String> showColumn = Arrays.asList(tplGenParamDTO.getShowColumnStr().split("," ));
                        for (String sc: showColumn){
                            if (sc.contains(entry.getKey()+"."+tc.getColumnName())){
                                if (sc.split(" as ").length>1){
                                    tc.setColumnName(sc.split(" as ")[1]);
                                }
                            }
                        }
                    });
                }
            });

        }
        // 转化为 tableColumn对象 tableList -> n cloumn
        List<TplGenTableColumnDTO> tplGenTableColumnDTOList = addList(tplGenParamDTO.getGenInfoId(),tplGenParamDTO.getTemplateType(),informationSchemaTableColumnList);
        tplGenDBTableDTO.setTableField(tplGenTableColumnDTOList);
        tplGenParamDTO.setTplGenDBTableDTO(tplGenDBTableDTO);

    }

    public void setTableFiledsBySql(TplGenDBTableDTO tplGenDBTableDTO, TplGenDBInfoDTO tplGenDBInfoDTO, TplGenParamDTO tplGenParamDTO) {
        List<InformationSchemaTableColumn> informationSchemaTableColumnList;
        if (TplGenParamEnums.TemplateTypeEnum.WDFUNC.getCode().equals(tplGenParamDTO.getTemplateType()) && DCStrUtil.isNotBlank(tplGenDBTableDTO.getTableName()) && tplGenDBTableDTO.getTableName().split(",").length > 0) {
            // 多表关联查询的时候，需处理查询的字段
            informationSchemaTableColumnList = getMultiTableColumns(tplGenParamDTO.getGenInfoId());
        } else {
            DCValidatorUtil.validateModel(tplGenDBTableDTO, BaseParam.tableGen.class);
            DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder().build().initDBInfo(tplGenDBInfoDTO.getDbUrl(), tplGenDBInfoDTO.getUsername(), tplGenDBInfoDTO.getPassword());
            dynamicDBInfo.setTableName(tplGenDBTableDTO.getTableName());
            // 数据选项不为空则选择获取db表字段
            informationSchemaTableColumnList = dynamicDBManager.listTableColumn(dynamicDBInfo);
        }
        // 转化为 tableColumn对象 tableList -> n cloumn
        List<TplGenTableColumnDTO> tplGenTableColumnDTOList = addList(tplGenParamDTO.getGenInfoId(),tplGenParamDTO.getTemplateType(),informationSchemaTableColumnList);
        tplGenDBTableDTO.setTableField(tplGenTableColumnDTOList);
    }

    /**
     * 功能描述:单功能多表字段获取
     *
     * @param genInfoId
     * @return java.util.List<com.sf.cemp.fcode.biz.sysmgr.dbinfo.repository.dataobject.InformationSchemaTableColumn>
     */
    private List<InformationSchemaTableColumn> getMultiTableColumns(Long genInfoId) {
        //获取设置的所有列，并找出重复列
        List<ChoosedTableColumnsDO> columnsDOList = choosedTableColumnsManager.getColumnByGenId(genInfoId);
        Map<String, Long> map = columnsDOList.stream().collect(Collectors.groupingBy(vo -> vo.getColumnName(), Collectors.counting()));
        List<String> repeatColumn = new ArrayList<>();
        map.keySet().forEach(key -> {
            if (map.get(key) > 1) {
                repeatColumn.add(key);
            }
        });
        List<InformationSchemaTableColumn> informationSchemaTableColumnList = new ArrayList<>();
        for (ChoosedTableColumnsDO columnsDO : columnsDOList) {
            InformationSchemaTableColumn tableColumn = new InformationSchemaTableColumn();
            tableColumn.setColumnName(columnsDO.getAliasName());
            tableColumn.setColumnComment(columnsDO.getColumnComment());
            tableColumn.setTableName(columnsDO.getTableName());
            tableColumn.setDataType(columnsDO.getColumnType());
            informationSchemaTableColumnList.add(tableColumn);
        }
        return informationSchemaTableColumnList;
    }

    /**
     * 转化为TplGenTableColumnDTO 列记录对象
     *
     * @param informationSchemaTableColumnList
     * @return
     */
    public List<TplGenTableColumnDTO> addList(Long scaffoldGenInfoId,String templateType,List<InformationSchemaTableColumn> informationSchemaTableColumnList) {
        //无配置，设置默认值,并保存
        List<TplGenTableColumnDTO> tplGenTableColumnDTOList = columnToColumnDetail(scaffoldGenInfoId,templateType,informationSchemaTableColumnList);
        if(DCCollectionUtil.isNotEmpty(tplGenTableColumnDTOList)){
            List<TableDetailInfoDTO> tableDetailInfos = DCBeanUtil.copyList(tplGenTableColumnDTOList,TableDetailInfoDTO.class);
            tableDetailInfoManager.insertBatch(tableDetailInfos);
        }
        return tplGenTableColumnDTOList;
    }

    private List<TplGenTableColumnDTO> columnToColumnDetail(Long scaffoldGenInfoId,String templateType,List<InformationSchemaTableColumn> informationSchemaTableColumnList){
        List<TplGenTableColumnDTO> tplGenTableColumnDTOList = Lists.newArrayList();
        for (InformationSchemaTableColumn informationSchemaTableColumn : informationSchemaTableColumnList) {
            TplGenTableColumnDTO tplGenTableColumnDTO = new TplGenTableColumnDTO();
            for(ColumnDisabledEnum columnDisabledEnum:ColumnDisabledEnum.values()){
                if(columnDisabledEnum.getJavaColumn().equals(informationSchemaTableColumn.getColumnName())){
                    tplGenTableColumnDTO.setDisabled(YesOrNotEnum.Y.getCode());
                }
            }
            tplGenTableColumnDTO.setColumnName(informationSchemaTableColumn.getColumnName());
            tplGenTableColumnDTO.setColumnComment(informationSchemaTableColumn.getColumnComment());
            // 设置get set方法使用的名称
            if (TplGenParamEnums.TemplateTypeEnum.WDFUNC.getCode().equals(templateType)){
                tplGenTableColumnDTO.setJavaColumns(informationSchemaTableColumn.getColumnName());
                tplGenTableColumnDTO.setColumnKeyName(informationSchemaTableColumn.getColumnName().substring(0, 1).toUpperCase() + informationSchemaTableColumn.getColumnName().substring(1));
            }else{
                tplGenTableColumnDTO.setJavaColumns(TplGenUtil.underlineToHump(informationSchemaTableColumn.getColumnName()));
                String columnName = TplGenUtil.underlineToHump(tplGenTableColumnDTO.getColumnName());
                tplGenTableColumnDTO.setColumnKeyName(columnName.substring(0, 1).toUpperCase() + columnName.substring(1));
            }
            // 特殊处理Id 直接用Long
            String javaType = null;
            if ("id".equals(informationSchemaTableColumn.getColumnName()) ||
                    "ID".equals(informationSchemaTableColumn.getColumnName())) {
                javaType = "Long";
            } else {
                javaType = TplGenUtil.sqlToJava(informationSchemaTableColumn.getDataType());
            }
            tplGenTableColumnDTO.setJavaType(javaType);
            tplGenTableColumnDTO.setColumnKey(informationSchemaTableColumn.getColumnKey());
            tplGenTableColumnDTO.setDataType(informationSchemaTableColumn.getDataType());
//            tplGenTableColumnDTO.setQueryType(QueryTypeEnum.eq.getCode());
            tplGenTableColumnDTO.setAddUpdateNo(0);
            tplGenTableColumnDTO.setAddUpdateRequire(YesOrNotEnum.N.getCode());
            tplGenTableColumnDTO.setAddUpdateValidateTips("");
            tplGenTableColumnDTO.setQueryCondition(YesOrNotEnum.N.getCode());
            tplGenTableColumnDTO.setQueryConditionLike(YesOrNotEnum.N.getCode());
            tplGenTableColumnDTO.setQueryConditionNo(0);
            tplGenTableColumnDTO.setQueryListNo(0);
            tplGenTableColumnDTO.setQueryListShow(YesOrNotEnum.N.getCode());
            tplGenTableColumnDTO.setShowType(TplGenUtil.javaToEff(javaType));
            tplGenTableColumnDTO.setShowAddUpdate(YesOrNotEnum.N.getCode());
            tplGenTableColumnDTO.setScaffoldGenInfoId(scaffoldGenInfoId);
            tplGenTableColumnDTOList.add(tplGenTableColumnDTO);
        }
        return tplGenTableColumnDTOList;
    }


    /**
     * 创建上下文用到的参数
     */
    public VelocityContext createCommonVelContext(TplGenParamDTO tplGenParamDTO) {
        VelocityContext velocityContext = new VelocityContext();
        List<TplGenDBTableDTO> tableList = Lists.newArrayList();
        List<DynamicDBInfo> dbInfoList = Lists.newArrayList();
        for (TplGenDBInfoDTO tplGenDBInfoDTO : tplGenParamDTO.getTplGenDBInfoDTOList()) {
            DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder().build();
            DCBeanUtil.copyNotNull(tplGenDBInfoDTO, dynamicDBInfo);
            dbInfoList.add(dynamicDBInfo);
            tableList.addAll(tplGenDBInfoDTO.getTplGenDBTableDTOList());
        }
        // 系统编码
        velocityContext.put("sysCode" , tplGenParamDTO.getSysCode());
        // 系统名称
        velocityContext.put("sysName" , tplGenParamDTO.getSysName());
        // 系统版本
        velocityContext.put("versionNo" , tplGenParamDTO.getVersionNo());
        // 选取的表信息
        velocityContext.put("tableList" , tableList);
        // 数据库连接信息
        velocityContext.put("dbInfoList" , dbInfoList);
//        // 中间件列表
//        velocityContext.put("middleareList", tplGenParamDTO.getMiddleareList());
//        // 目录列表
//        velocityContext.put("dirList", tplGenParamDTO.getDirList());
        // 包名称
        velocityContext.put("packageName" , tplGenParamDTO.getPackageName());
        // 作者姓名
        velocityContext.put("authorName" , tplGenParamDTO.getAuthorName());
        // 代码生成时间
        velocityContext.put("createDateString" , DCDateUtil.formatDateTime(new Date()));
        // mavengroupId
        velocityContext.put("groupId" , tplGenParamDTO.getGroupId());
        // mavenartifactId
        velocityContext.put("artifactId" , tplGenParamDTO.getArtifactId());

        return velocityContext;
    }
}
