package com.litian.dancechar.biz.core.tplgen.dto;

import com.litian.dancechar.biz.sysmgr.dbmgr.common.enums.SystemDBInfoEnums;
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
public class TplGenDBInfoDTO  extends BaseDTO {

    private static final long serialVersionUID = 1031059061657624745L;
    /**
     * 关联gen_db_info id
     */
    @NotNull(message = "数据库信息id不能为空", groups = BaseParam.dbGen.class)
    private Long genDbId;
    /**
     * 数据库url
     */
    private String dbUrl;
    /**
     * host ip
     */
    @NotNull(message = "数据库host不能为空", groups = BaseParam.dbGen.class)
    private String host;
    /**
     * 数据库端口
     */
    @NotNull(message = "数据库port不能为空", groups = BaseParam.dbGen.class)
    private Integer port;
    /**
     * 用户名
     */
    @NotNull(message = "数据库username不能为空", groups = BaseParam.dbGen.class)
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "数据库password不能为空", groups = BaseParam.dbGen.class)
    private String password;
    /**
     * 数据库名
     */
    @NotNull(message = "数据库dbName不能为空", groups = BaseParam.dbGen.class)
    private String dbName;
    /**
     * 驱动名
     */
    @Builder.Default
    private String driverName = SystemDBInfoEnums.DriverEnum.MYSQL.getCode();

    /**
     * 数据库标记
     */
    private String dbTag;

    public String getDbTag(){
        if(this.dbTag == null){
            return this.dbName;
        }
        return this.dbTag;
    }

    /**
     * 是否为主库
     */
    @Builder.Default
    private Boolean primary = false;

    /**
     * 用户选择的表信息 一个数据库可以选择多个表
     */
    private List<TplGenDBTableDTO> tplGenDBTableDTOList;

    public String getDbUrl(){
        if(this.dbUrl != null){
            return this.dbUrl;
        }
        if(SystemDBInfoEnums.DriverEnum.MYSQL.getCode().equals(this.driverName)){
            return "jdbc:mysql://"+this.host+":"+this.port+"/"+this.dbName;
        }else if(SystemDBInfoEnums.DriverEnum.ORACLE.getCode().equals(this.driverName)){
            return "jdbc:oracle:thin:@" + this.host + ":" + this.port + ":" + this.dbName;
        }
        return null;
    }
}

