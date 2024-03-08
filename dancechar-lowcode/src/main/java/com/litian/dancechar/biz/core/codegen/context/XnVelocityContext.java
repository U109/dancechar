package com.litian.dancechar.biz.core.codegen.context;

import com.litian.dancechar.biz.core.codegen.common.enums.YesOrNotEnum;
import com.litian.dancechar.biz.core.codegen.dto.XnCodeGenParam;
import com.litian.dancechar.biz.core.codegen.repository.dataobject.CodeGenDetailConfigDO;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 设置上下文缓存
 *
 * @author yubaoshan
 * @date 2020年12月17日02:04:56
 */
public class XnVelocityContext {

    /**h
     * 创建上下文用到的参数
     *
     * @author yubaoshan
     * @date 2020年12月17日02:04:56
     */
    public VelocityContext createVelContext (XnCodeGenParam xnCodeGenParam) {
        VelocityContext velocityContext = new VelocityContext();
        // 取得类名
        String DomainName = xnCodeGenParam.getClassName();
        String domainName = DomainName.substring(0,1).toLowerCase()+DomainName.substring(1);
        // 类名称
        velocityContext.put("ClassName",DomainName);
        // 类名（首字母小写）
        velocityContext.put("className",domainName);

        // 功能名
        velocityContext.put("functionName",xnCodeGenParam.getFunctionName());

        // 包名称
        velocityContext.put("packageName",xnCodeGenParam.getPackageName());
        // 模块名称
        velocityContext.put("modularName",xnCodeGenParam.getModularNane());
        // 业务名
        velocityContext.put("busName",xnCodeGenParam.getBusName());
        // 子业务名
        velocityContext.put("childBusName",xnCodeGenParam.getChildBusName());
        // 作者姓名
        velocityContext.put("authorName", xnCodeGenParam.getAuthorName());
        // 代码生成时间
        velocityContext.put("createDateString", xnCodeGenParam.getCreateTimeString());

        // 数据库表名
        velocityContext.put("tableName", xnCodeGenParam.getTableName());
        // 数据库字段
        velocityContext.put("tableField", xnCodeGenParam.getConfigList());

        // 前端查询所有
        List<CodeGenDetailConfigDO> codeGenerateConfigList = new ArrayList<>();
        xnCodeGenParam.getConfigList().forEach(item -> {
            if (item.getQueryWhether().equals(YesOrNotEnum.Y.getCode())) {
                codeGenerateConfigList.add(item);
            }
        });
        velocityContext.put("queryWhetherList", codeGenerateConfigList);

        // sql中id的创建
        List<Long> idList = new ArrayList<>();
        for (int a = 0; a <= 6; a++) {
            idList.add(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        }
        velocityContext.put("sqlMenuId", idList);

        // 插件涵盖 redis kafka
        velocityContext.put("pluginList", xnCodeGenParam.getPluginList());
        velocityContext.put("groupId", xnCodeGenParam.getGroupId());
        velocityContext.put("artifactId", xnCodeGenParam.getArtifactId());
        return velocityContext;
    }
}
