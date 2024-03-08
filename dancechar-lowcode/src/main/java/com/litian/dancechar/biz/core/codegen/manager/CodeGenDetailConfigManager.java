package com.litian.dancechar.biz.core.codegen.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litian.dancechar.biz.core.codegen.common.constants.Config;
import com.litian.dancechar.biz.core.codegen.common.enums.QueryTypeEnum;
import com.litian.dancechar.biz.core.codegen.common.enums.SysCodeGenerateConfigExceptionEnum;
import com.litian.dancechar.biz.core.codegen.common.enums.TableFilteredFieldsEnum;
import com.litian.dancechar.biz.core.codegen.common.enums.YesOrNotEnum;
import com.litian.dancechar.biz.core.codegen.common.tool.JavaEffTool;
import com.litian.dancechar.biz.core.codegen.common.tool.JavaSqlTool;
import com.litian.dancechar.biz.core.codegen.common.tool.NamingConTool;
import com.litian.dancechar.biz.core.codegen.dto.SysCodeGenerateConfigParam;
import com.litian.dancechar.biz.core.codegen.repository.dataobject.CodeGenBaseConfigDO;
import com.litian.dancechar.biz.core.codegen.repository.dataobject.CodeGenDetailConfigDO;
import com.litian.dancechar.biz.core.codegen.repository.mapper.CodeGenDetailConfigMapper;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTableColumn;
import com.litian.dancechar.framework.common.exception.BusinessException;
import com.litian.dancechar.framework.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 代码生成详细配置service接口实现类
 */
@Service
public class CodeGenDetailConfigManager extends ServiceImpl<CodeGenDetailConfigMapper, CodeGenDetailConfigDO> {

    public List<CodeGenDetailConfigDO> list(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        LambdaQueryWrapper<CodeGenDetailConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysCodeGenerateConfigParam)) {

            // 根据代码生成主表ID 模糊查询
            if (ObjectUtil.isNotEmpty(sysCodeGenerateConfigParam.getCodeGenId())) {
                queryWrapper.eq(CodeGenDetailConfigDO::getCodeGenId, sysCodeGenerateConfigParam.getCodeGenId());
            }
        }
        return this.list(queryWrapper);
    }

    public void add(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        CodeGenDetailConfigDO sysCodeGenerateConfig = new CodeGenDetailConfigDO();
        BeanUtil.copyProperties(sysCodeGenerateConfigParam, sysCodeGenerateConfig);
        this.save(sysCodeGenerateConfig);
    }

    public void addList(List<InformationSchemaTableColumn> informationSchemaTableColumnList, CodeGenBaseConfigDO codeGenerate) {
        for (InformationSchemaTableColumn informationSchemaTableColumn : informationSchemaTableColumnList) {
            CodeGenDetailConfigDO sysCodeGenerateConfig = new CodeGenDetailConfigDO();
            String YesOrNo = YesOrNotEnum.Y.getDesc();
            if (ObjectUtil.isNotNull(informationSchemaTableColumn.getColumnKey())
                    && informationSchemaTableColumn.getColumnKey().equals(Config.DB_TABLE_COM_KRY) ||
                    TableFilteredFieldsEnum.contains(informationSchemaTableColumn.getColumnName())) {
                YesOrNo = YesOrNotEnum.N.getDesc();
            }
            if (TableFilteredFieldsEnum.contains(informationSchemaTableColumn.getColumnName())) {
                sysCodeGenerateConfig.setWhetherCommon(YesOrNotEnum.Y.getDesc());
            } else {
                sysCodeGenerateConfig.setWhetherCommon(YesOrNotEnum.N.getDesc());
            }

            sysCodeGenerateConfig.setCodeGenId(codeGenerate.getId());
            sysCodeGenerateConfig.setColumnName(informationSchemaTableColumn.getColumnName());
            sysCodeGenerateConfig.setColumnComment(informationSchemaTableColumn.getColumnComment());
            sysCodeGenerateConfig.setJavaName(NamingConTool.UnderlineToHump(informationSchemaTableColumn.getColumnName(), codeGenerate.getTablePrefix()));
            sysCodeGenerateConfig.setJavaType(JavaSqlTool.sqlToJava(informationSchemaTableColumn.getDataType()));
            sysCodeGenerateConfig.setWhetherRetract(YesOrNotEnum.N.getDesc());

            sysCodeGenerateConfig.setWhetherRequired(YesOrNo);
            sysCodeGenerateConfig.setQueryWhether(YesOrNo);
            sysCodeGenerateConfig.setWhetherAddUpdate(YesOrNo);
            sysCodeGenerateConfig.setWhetherTable(YesOrNo);

            sysCodeGenerateConfig.setColumnKey(informationSchemaTableColumn.getColumnKey());

            // 设置get set方法使用的名称
            String columnName = NamingConTool.UnderlineToHump(sysCodeGenerateConfig.getColumnName(), "");
            sysCodeGenerateConfig.setColumnKeyName(columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length()));

            sysCodeGenerateConfig.setDataType(informationSchemaTableColumn.getDataType());
            sysCodeGenerateConfig.setEffectType(JavaEffTool.javaToEff(sysCodeGenerateConfig.getJavaType()));
            sysCodeGenerateConfig.setQueryType(QueryTypeEnum.eq.getCode());

            this.save(sysCodeGenerateConfig);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        LambdaQueryWrapper<CodeGenDetailConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CodeGenDetailConfigDO::getCodeGenId, sysCodeGenerateConfigParam.getCodeGenId());
        this.remove(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(List<SysCodeGenerateConfigParam> sysCodeGenerateConfigParamList) {
        for (SysCodeGenerateConfigParam sysCodeGenerateConfigParam : sysCodeGenerateConfigParamList) {
            CodeGenDetailConfigDO sysCodeGenerateConfig = this.querySysCodeGenerateConfig(sysCodeGenerateConfigParam);
            BeanUtil.copyProperties(sysCodeGenerateConfigParam, sysCodeGenerateConfig);
            this.updateById(sysCodeGenerateConfig);
        }
    }

    public CodeGenDetailConfigDO detail(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        return this.querySysCodeGenerateConfig(sysCodeGenerateConfigParam);
    }

    /**
     * 获取代码生成详细配置
     *
     * @author yubaoshan
     * @date 2021-02-06 20:19:49
     */
    private CodeGenDetailConfigDO querySysCodeGenerateConfig(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        CodeGenDetailConfigDO sysCodeGenerateConfig = this.getById(sysCodeGenerateConfigParam.getId());
        if (ObjectUtil.isNull(sysCodeGenerateConfig)) {
            throw new BusinessException(SysCodeGenerateConfigExceptionEnum.NOT_EXIST.getMessage());
        }
        return sysCodeGenerateConfig;
    }
}
