package com.litian.dancechar.biz.core.codegen.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litian.dancechar.biz.core.codegen.common.constants.Config;
import com.litian.dancechar.biz.core.codegen.common.enums.CodeGenParamEnums;
import com.litian.dancechar.biz.core.codegen.common.enums.CodeGenerateExceptionEnum;
import com.litian.dancechar.biz.core.codegen.common.factory.PageFactory;
import com.litian.dancechar.biz.core.codegen.common.page.PageResult;
import com.litian.dancechar.biz.core.codegen.common.tool.StringDateTool;
import com.litian.dancechar.biz.core.codegen.common.util.Util;
import com.litian.dancechar.biz.core.codegen.common.util.ZipCompress;
import com.litian.dancechar.biz.core.codegen.context.XnVelocityContext;
import com.litian.dancechar.biz.core.codegen.dto.*;
import com.litian.dancechar.biz.core.codegen.manager.conn.ConnectionPoolUtil;
import com.litian.dancechar.biz.core.codegen.repository.dataobject.CodeGenBaseConfigDO;
import com.litian.dancechar.biz.core.codegen.repository.dataobject.CodeGenDetailConfigDO;
import com.litian.dancechar.biz.core.codegen.repository.mapper.CodeGenBaseConfigMapper;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.DynamicDBInfo;
import com.litian.dancechar.biz.sysmgr.dbmgr.manager.DynamicDBManager;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTable;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.exception.ServiceException;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成基础配置service接口实现类
 */
@Service
public class CodeGenBaseConfigManager extends ServiceImpl<CodeGenBaseConfigMapper, CodeGenBaseConfigDO> {

    /**
     * 模板后缀
     */
    private static String TEMP_SUFFIX = ".vm";

    /**
     * 转换的编码
     */
    private static String ENCODED = "UTF-8";

    /**
     * 转换模板名称所需变量
     */
    private static String ADD_FORM_PAGE_NAME = "addForm.vue";
    private static String EDIT_FORM_PAGE_NAME = "editForm.vue";
    private static String INDEX_PAGE_NAME = "index.vue";
    private static String MANAGE_JS_NAME = "Manage.js";
    private static String SQL_NAME = ".sql";
    private static String JAVA_SUFFIX = ".java";
    private static String TEMP_ENTITY_NAME = "entity";

    @Resource
    private CodeGenDetailConfigManager codeGenDetailConfigManager;

    @Resource
    private CodeGenDBManager codeGenDBManager;

    @Resource
    private RedisHelper sfRedisUtil;

    public PageResult<CodeGenBaseConfigDO> page(CodeGenerateParam codeGenerateParam) {
        QueryWrapper<CodeGenBaseConfigDO> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(codeGenerateParam)) {
            //根据表名模糊查询
            if (ObjectUtil.isNotEmpty(codeGenerateParam.getTableName())) {
                queryWrapper.lambda().like(CodeGenBaseConfigDO::getTableName,
                        codeGenerateParam.getTableName());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    public void add(CodeGenerateParam codeGenerateParam) {
        DCValidatorUtil.validateModel(codeGenerateParam, BaseParam.add.class);
        CodeGenBaseConfigDO codeGenerate = new CodeGenBaseConfigDO();
        BeanUtil.copyProperties(codeGenerateParam, codeGenerate);
//        if (!vldTablePri(codeGenerate.getTableName())) {
//            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_TABLE_NOT_PRI);
//        }
        this.save(codeGenerate);

        // 加入配置表中
        codeGenerateParam.setId(codeGenerate.getId());
        DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder()
                .tableName(codeGenerate.getTableName())
                .build().initDBInfo(codeGenerateParam.getCodeGenerateDBParam().getDbUrl(),
                codeGenerateParam.getCodeGenerateDBParam().getUsername(), codeGenerateParam.getCodeGenerateDBParam().getPassword());
        this.codeGenDetailConfigManager.addList(dynamicDBManager.getInformationSchemaTableColumnList(dynamicDBInfo), codeGenerate);
    }

    public void delete(List<CodeGenerateParam> codeGenerateParamList) {
        codeGenerateParamList.forEach(codeGenerateParam -> {
            this.removeById(codeGenerateParam.getId());
            SysCodeGenerateConfigParam sysCodeGenerateConfigParam = new SysCodeGenerateConfigParam();
            sysCodeGenerateConfigParam.setCodeGenId(codeGenerateParam.getId());
            this.codeGenDetailConfigManager.delete(sysCodeGenerateConfigParam);
        });
    }

    public void edit(CodeGenerateParam codeGenerateParam) {
        CodeGenBaseConfigDO codeGenerate = this.queryCodeGenerate(codeGenerateParam);
        BeanUtil.copyProperties(codeGenerateParam, codeGenerate);
//        if (!vldTablePri(codeGenerate.getTableName())) {
//            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_TABLE_NOT_PRI);
//        }
        this.updateById(codeGenerate);
    }

    public CodeGenBaseConfigDO detail(CodeGenerateParam codeGenerateParam) {
        return this.queryCodeGenerate(codeGenerateParam);
    }

    /**
     * 获取代码生成基础配置
     */
    private CodeGenBaseConfigDO queryCodeGenerate(CodeGenerateParam codeGenerateParam) {
        CodeGenBaseConfigDO codeGenerate = this.getById(codeGenerateParam.getId());
        if (ObjectUtil.isNull(codeGenerate)) {
            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_NOT_EXIST);
        }
        return codeGenerate;
    }


    @Autowired
    private DynamicDBManager dynamicDBManager;

    /**
     * 查询数据库所有表
     * @param codeGenerateDBParam
     * @return
     */
    public List<String> listDBTable(CodeGenerateDBParam codeGenerateDBParam) {
        DCValidatorUtil.validateModel(codeGenerateDBParam, BaseParam.add.class);
        DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder().build().initDBInfo(codeGenerateDBParam.getDbUrl(), codeGenerateDBParam.getUsername(), codeGenerateDBParam.getPassword());
        String dsKey = dynamicDBInfo.getDsKey();
        // 查询本地缓存
        List<String> tableList = sfRedisUtil.getList(dsKey, String.class);
        if(CollectionUtils.isEmpty(tableList)){
            // 查询db所有表
            List<InformationSchemaTable> informationResultList = dynamicDBManager.listInformationTable(dynamicDBInfo);
            tableList = informationResultList.stream().map(informationResult -> informationResult.getTableName()).collect(Collectors.toList());
            sfRedisUtil.set(dsKey, tableList, 900L);
        }
        return tableList;
    }

    /**
     * 查询数据库所有表
     * @param codeGenerateDBParam
     * @return
     */
    public void refreshTableList(CodeGenerateDBParam codeGenerateDBParam) {
        String dsKey = ConnectionPoolUtil.getDSKey(codeGenerateDBParam.getDbUrl());
        sfRedisUtil.remove(dsKey);
    }

    public void runLocal(CodeGenerateParam codeGenerateParam) {
        XnCodeGenParam xnCodeGenParam = copyParams(codeGenerateParam);
        codeGenLocal(xnCodeGenParam);
    }

    public void runDown(CodeGenerateParam codeGenerateParam, HttpServletResponse response) {
        XnCodeGenParam xnCodeGenParam = copyParams(codeGenerateParam);
        downloadCode(xnCodeGenParam, response);
    }

    public void runDown4J(CodeGenerateParam codeGenerateParam, HttpServletResponse response) {
        DCValidatorUtil.validateModel(codeGenerateParam, BaseParam.detail.class);
        XnCodeGenParam xnCodeGenParam = copyParams4J(codeGenerateParam);
        downloadCode4J(xnCodeGenParam, response);
    }

    /**
     * 下载方式组装代码基础
     *
     * @author yubaoshan
     * @date 2020年12月23日 00点32分
     */
    private void downloadCode4J(XnCodeGenParam xnCodeGenParam, HttpServletResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(outputStream);

        Util.initVelocity();
        XnVelocityContext xnVelocityContext = new XnVelocityContext();
        VelocityContext velocityContext = xnVelocityContext.createVelContext(xnCodeGenParam);
        ZipCompress.zip(zos, "templates/"+xnCodeGenParam.getTemplateTypeEnum().getCode(), velocityContext);
        IOUtils.closeQuietly(zos);
        outputStream.toByteArray();
        try {
            Util.DownloadGen(response, outputStream.toByteArray(), xnCodeGenParam.getBusName());
        } catch (Exception e) {
            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_NOT_PATH);
        }
    }



    /**
     * 下载方式组装代码基础
     */
    private void downloadCode(XnCodeGenParam xnCodeGenParam, HttpServletResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        codeGenDown(xnCodeGenParam, zipOutputStream);
        IOUtils.closeQuietly(zipOutputStream);
        outputStream.toByteArray();
        try {
            Util.DownloadGen(response, outputStream.toByteArray(), xnCodeGenParam.getBusName());
        } catch (Exception e) {
            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_NOT_PATH);
        }
    }


    private XnCodeGenParam copyParams(CodeGenerateParam codeGenerateParam) {
        CodeGenBaseConfigDO codeGenerate = this.queryCodeGenerate(codeGenerateParam);
        SysCodeGenerateConfigParam sysCodeGenerateConfigParam = new SysCodeGenerateConfigParam();
        sysCodeGenerateConfigParam.setCodeGenId(codeGenerateParam.getId());
        List<CodeGenDetailConfigDO> configList = this.codeGenDetailConfigManager.list(sysCodeGenerateConfigParam);
        XnCodeGenParam param = new XnCodeGenParam();
        BeanUtil.copyProperties(codeGenerate, param);
        // 功能名
        param.setFunctionName(codeGenerate.getTableComment());
        param.setConfigList(configList);
        param.setCreateTimeString(StringDateTool.getStringDate());
        return param;
    }

    private XnCodeGenParam copyParams4J(CodeGenerateParam codeGenerateParam) {
        CodeGenBaseConfigDO codeGenerate = this.queryCodeGenerate(codeGenerateParam);
        SysCodeGenerateConfigParam sysCodeGenerateConfigParam = new SysCodeGenerateConfigParam();
        sysCodeGenerateConfigParam.setCodeGenId(codeGenerateParam.getId());
        List<CodeGenDetailConfigDO> configList = this.codeGenDetailConfigManager.list(sysCodeGenerateConfigParam);
        XnCodeGenParam param = new XnCodeGenParam();
        BeanUtil.copyProperties(codeGenerate, param);
        param.setGroupId(codeGenerateParam.getGroupId());
        param.setArtifactId(codeGenerateParam.getArtifactId());
        // 功能名
        param.setFunctionName(codeGenerate.getTableComment());
        param.setConfigList(configList);
        param.setCreateTimeString(StringDateTool.getStringDate());
        param.setTemplateTypeEnum(CodeGenParamEnums.TemplateTypeEnum.getByCode(codeGenerateParam.getTemplateType()));
        return param;
    }

    /**
     * 本地项目生成
     */
    private void codeGenLocal(XnCodeGenParam xnCodeGenParam) {
        XnVelocityContext context = new XnVelocityContext();
        //初始化参数
        Properties properties = new Properties();
        //设置velocity资源加载方式为class
        properties.setProperty("resource.loader", "class");
        //设置velocity资源加载方式为file时的处理类
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        //实例化一个VelocityEngine对象
        VelocityEngine velocityEngine = new VelocityEngine(properties);

        String[] filePath = Config.xnCodeGenFilePath(xnCodeGenParam.getBusName(), xnCodeGenParam.getPackageName());
        for (int i = 0; i < filePath.length; i++) {
            String templateName = Config.xnCodeGenTempFile[i];

            String fileBaseName = ResetFileBaseName(xnCodeGenParam.getClassName(),
                    templateName.substring(templateName.indexOf(Config.FILE_SEP) + 1, templateName.lastIndexOf(TEMP_SUFFIX)));
            String path = Config.getLocalPath();
            // 前端VUE位置有所变化, sql同样根目录
            if (fileBaseName.contains(INDEX_PAGE_NAME) || fileBaseName.contains(ADD_FORM_PAGE_NAME) ||
                    fileBaseName.contains(EDIT_FORM_PAGE_NAME) || fileBaseName.contains(MANAGE_JS_NAME) ||
                    fileBaseName.contains(SQL_NAME)) {
                path = Config.getLocalFrontPath();
            }

            File file = new File(path + filePath[i] + fileBaseName);

            //判断是否覆盖存在的文件
            if (file.exists() && !Config.FLAG) {
                continue;
            }

            //获取父目录
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                Writer writer = new FileWriter(file);
                velocityEngine.mergeTemplate(Config.templatePath + templateName, ENCODED, context.createVelContext(xnCodeGenParam), writer);
                writer.close();
            } catch (Exception e) {
                throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_NOT_PATH);
            }
        }
    }

    /**
     * 下载ZIP方式
     */
    private void codeGenDown(XnCodeGenParam xnCodeGenParam, ZipOutputStream zipOutputStream) {
        Util.initVelocity();
        XnVelocityContext context = new XnVelocityContext();

        String[] filePath = Config.xnCodeGenFilePath(xnCodeGenParam.getBusName(), xnCodeGenParam.getPackageName());
        for (int a = 0; a < filePath.length; a++) {
            String templateName = Config.xnCodeGenTempFile[a];

            String fileBaseName = ResetFileBaseName(xnCodeGenParam.getClassName(),
                    templateName.substring(templateName.indexOf(Config.FILE_SEP) + 1, templateName.lastIndexOf(TEMP_SUFFIX)));
            XnZipOutputStream(context.createVelContext(xnCodeGenParam),
                    Config.templatePath + templateName,
                    filePath[a] + fileBaseName,
                    zipOutputStream);
        }
    }

    /**
     * 重置文件名称
     */
    private static String ResetFileBaseName(String className, String fileName) {
        String fileBaseName = className + fileName;
        // 实体类名称单独处理
        if (fileBaseName.contains(TEMP_ENTITY_NAME)) {
            return className + JAVA_SUFFIX;
        }
        // 首页index.vue界面
        if (fileBaseName.contains(INDEX_PAGE_NAME)) {
            return INDEX_PAGE_NAME;
        }
        // 表单界面名称
        if (fileBaseName.contains(ADD_FORM_PAGE_NAME)) {
            return ADD_FORM_PAGE_NAME;
        }
        if (fileBaseName.contains(EDIT_FORM_PAGE_NAME)) {
            return EDIT_FORM_PAGE_NAME;
        }
        // js名称
        if (fileBaseName.contains(MANAGE_JS_NAME)) {
            return className.substring(0, 1).toLowerCase() + className.substring(1) + MANAGE_JS_NAME;
        }
        return fileBaseName;
    }

    /**
     * 生成ZIP
     */
    private void XnZipOutputStream(VelocityContext velContext, String tempName, String fileBaseName, ZipOutputStream zipOutputStream) {
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(tempName, ENCODED);
        tpl.merge(velContext, sw);
        try {
            // 添加到zip
            zipOutputStream.putNextEntry(new ZipEntry(fileBaseName));
            IOUtils.write(sw.toString(), zipOutputStream, ENCODED);
            IOUtils.closeQuietly(sw);
            zipOutputStream.flush();
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_NOT_PATH);
        }
    }

}
