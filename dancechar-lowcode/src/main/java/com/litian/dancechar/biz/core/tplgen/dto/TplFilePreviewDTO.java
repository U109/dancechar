package com.litian.dancechar.biz.core.tplgen.dto;

import com.litian.dancechar.biz.core.componentpage.dto.GenFileRenameDTO;
import com.litian.dancechar.common.common.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * 文件预览DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TplFilePreviewDTO  extends BaseDTO {

    private static final long serialVersionUID = -7296430216011789830L;

    /**
     * 关联的scaffoldGenInfo的主键id 用于文件重命名
     */
    private Long genInfoId;
    /**
     * 关联的scaffoldGenDbInfo的主键id 用于文件重命名
     */
    private Long genDbId;

    /**
     * 关联的scaffoldGenDbExampleInfo的主键id 用于文件重命名
     */
    private Long genDbExampleId;

    // 用于重命名
    private GenFileRenameDTO genFileRenameDTO;

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
     * contextPath restful
     */
    private String contextPath;
    /**
     * 作者姓名
     */
    private String authorName;
    /**
     * 创建时间
     */
    private String createDateString;

    /**
     * groupId
     */
    private String groupId;

    /**
     * artifactId
     */
    private String artifactId;

    /**
     * 类名
     */
    private String className;

    /**
     * 实例名
     */
    private String instanceName;

    /**
     * 功能目录 tplgen.xxx
     */
    private String functionDir;

    /**
     * 目录控制 service manager repository
     */
    private List<String> dirList;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * db tag 用户@DS
     */
    private String dbTag;

    /**
     * 是否主库
     */
    private Boolean primary;
    /**
     * 关联的字段列表
     */
    private List<TplGenTableColumnDTO> tableField;

    /**
     * 渲染的文件路径
     */
    private String filePathName;

    /**
     * 以下为DB冗余信息存储 暂时转化为给预览文件使用
     */
    /**
     * 数据库url
     */
    private String dbUrl;
    /**
     * host ip
     */
    private String host;
    /**
     * 数据库端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库名
     */
    private String dbName;
    /**
     * 驱动名
     */
    private String driverName;
    /**
     * 生成功能
     */
    private String genFunctions;
    /**
     * 主键对象
     */
    private List<TplGenTableColumnDTO> primaryKeyInfos;
}
