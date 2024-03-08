package com.litian.dancechar.biz.core.tplgen.dto;

import com.litian.dancechar.common.common.dto.BaseDTO;
import com.litian.dancechar.common.common.dto.BaseParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TplGenDBTableDTO  extends BaseDTO {

    private static final long serialVersionUID = -6768304162427926623L;

    /**
     * 关联的scaffoldGenDbExampleInfo的主键id 用于文件重命名
     */
    @NotNull(message = "数据库示例表ID不能为空", groups = BaseParam.tableGen.class)
    private Long genDbExampleId;
    /**
     * 类名
     */
    @NotNull(message = "数据库示例表ID不能为空", groups = BaseParam.tableGen.class)
    private String className;

//    /**
//     * 实例名 首字母小写
//     */
//    private String instanceName;

    /**
     * 功能目录 tplgen.xxx
     */
    @NotNull(message = "数据库示例表功能目录不能为空", groups = BaseParam.tableGen.class)
    private String functionDir;

    /**
     * 数据库表名
     */
    @NotNull(message = "数据库示例表表名不能为空", groups = BaseParam.tableGen.class)
    private String tableName;


    /**
     * 关联的字段列表 无需填写
     */
    private List<TplGenTableColumnDTO> tableField;

    /**
     * 主键对象
     */
    private List<TplGenTableColumnDTO> primaryKeyInfos;

//    public String getInstanceName(){
//        return className.substring(0,1).toLowerCase()+className.substring(1);
//    }
}
