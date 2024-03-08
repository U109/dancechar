package com.litian.dancechar.biz.core.codegen.service.impl;

import com.litian.dancechar.biz.core.codegen.common.response.ResponseData;
import com.litian.dancechar.biz.core.codegen.common.response.SuccessResponseData;
import com.litian.dancechar.biz.core.codegen.dto.BaseParam;
import com.litian.dancechar.biz.core.codegen.dto.CodeGenerateParam;
import com.litian.dancechar.biz.core.codegen.manager.CodeGenBaseConfigManager;
import com.litian.dancechar.biz.core.codegen.manager.CodeGenDBManager;
import com.litian.dancechar.biz.core.codegen.service.CodeGenBaseConfigService;
import com.litian.dancechar.biz.core.tplgen.manager.TplGenManager;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * 类描述：代码生成详细配置服务实现
 *
 * @author 01406831
 * @date 2021/3/31 11:18
 */
@Component
@Slf4j
public class CodeGenBaseConfigServiceImpl implements CodeGenBaseConfigService {

    @Resource
    private CodeGenBaseConfigManager codeGenBaseConfigManager;

    @Autowired
    private CodeGenDBManager codeGenDBManager;

    /**
     * 代码生成基础数据
     */
    @Override
    public ResponseData page(CodeGenerateParam codeGenerateParam) {
        return new SuccessResponseData(codeGenBaseConfigManager.page(codeGenerateParam));
    }

    /**
     * 代码生成基础配置保存
     */
    @Override
    public Result<Long> add(CodeGenerateParam codeGenerateParam) {
        this.codeGenBaseConfigManager.add(codeGenerateParam);
        return DCResultUtil.success(codeGenerateParam.getId());
    }

    /**
     * 代码生成基础配置编辑
     */
    @Override
    public Result<Boolean> edit(CodeGenerateParam codeGenerateParam) {
        DCValidatorUtil.validateModel(codeGenerateParam, BaseParam.edit.class);
        codeGenBaseConfigManager.edit(codeGenerateParam);
        return DCResultUtil.success(true);
    }

    /**
     * 删除代码生成基础配置
     */
    @Override
    public ResponseData delete(List<CodeGenerateParam> codeGenerateParamList) {
        DCValidatorUtil.validateModel(codeGenerateParamList, BaseParam.delete.class);
        codeGenBaseConfigManager.delete(codeGenerateParamList);
        return new SuccessResponseData();
    }



    /**
     * 代码生成基础配置生成
     */
    @Override
    public ResponseData runLocal(CodeGenerateParam codeGenerateParam) {
        DCValidatorUtil.validateModel(codeGenerateParam, BaseParam.detail.class);
        this.codeGenBaseConfigManager.runLocal(codeGenerateParam);
        return new SuccessResponseData();
    }

    /**
     * 代码生成基础配置生成
     */
    @Override
    public void runDown(CodeGenerateParam codeGenerateParam, HttpServletResponse response) {
        this.codeGenBaseConfigManager.runDown(codeGenerateParam, response);
    }

    @Autowired
    private TplGenManager tplGenManager;

    /**
     * 代码生成基础配置生成
     */
    @Override
    public void runDown4J(CodeGenerateParam codeGenerateParam, @Context HttpServletResponse response) {
//        try {
//            String jsonStr = "{\"sysCode\":\"esg-cemp-core-demo\",\"sysName\":\"代码生成器\",\"versionNo\":\"1.0.0\",\"authorName\":\"terryhl\",\"packageName\":\"com.sf.core.demo\",\"middleareList\":[],\"dirList\":[\"service\",\"manager\",\"repository\"],\"templateType\":\"fnew\",\"groupId\":\"com.sf\",\"artifactId\":\"esg-cemp-core-demo\",\"tplGenDBInfoDTOList\":[{\"host\":\"cempbase-m.dbsit.sfcloud.local\",\"port\":3306,\"dbUrl\":\"jdbc:mysql://cempbase-m.dbsit.sfcloud.local:3306/cempbase\",\"username\":\"cempbase\",\"password\":\"qazwsx1234!\",\"dbName\":\"cempbase\",\"driverName\":\"mysql\",\"dbTag\":\"cempbase\",\"primary\":true,\"tplGenDBTableDTOList\":[{\"className\":\"SystemInfo\",\"functionDir\":\"sys\",\"tableName\":\"fcode_system_info\"},{\"className\":\"Feedback\",\"functionDir\":\"fb.demo\",\"tableName\":\"fcode_feedback\"}]},{\"host\":\"sstbase-m.dbsit.sfcloud.local\",\"port\":3306,\"dbUrl\":\"jdbc:mysql://sstbase-m.dbsit.sfcloud.local:3306/sstbase\",\"username\":\"sstbase\",\"password\":\"3wInb7QjnF\",\"dbName\":\"sstbase\",\"driverName\":\"mysql\",\"dbTag\":\"sstbase\",\"primary\":false,\"tplGenDBTableDTOList\":[{\"className\":\"MemberGift\",\"functionDir\":\"mem\",\"tableName\":\"t_member_gift\"},{\"className\":\"MemNotice\",\"functionDir\":\"mem\",\"tableName\":\"t_mem_notice\"}]}]}";
//            TplGenParamDTO tplGenParamDTO = JSON.parseObject(jsonStr, TplGenParamDTO.class);
//            ByteArrayOutputStream baos = this.tplGenManager.tplGen(tplGenParamDTO);
//            Util.DownloadGen(response, baos.toByteArray(), tplGenParamDTO.getSysCode());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        this.codeGenBaseConfigManager.runDown4J(codeGenerateParam, response);
    }


}
