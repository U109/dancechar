package com.litian.dancechar.biz.sysmgr.system.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.*;


/**
 * 类描述：systemInfo DO对象
 *
 * @author fcoder
 * @date 2021-06-26 18:49:31
 */
@Data
@TableName("fcode_system_info")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 系统编码
     */
    private String systemCode;
    /**
     * 系统打包包名
     */
    private String packageName;
    /**
     * 系统访问前缀
     */
    private String contextPath;
    /**
     * 打包包名
     */
    private String groupId;

    /**
     * 系统artifactId
     */
    private String artifactId;
    /**
     * 版本号
     */
    private String version;
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 系统描述信息
     */
    private String systemDesc;
    /**
     * 系统维护的团队
     */
    private String teamName;
    /**
     * 系统负责人
     */
    private String leader;
}
