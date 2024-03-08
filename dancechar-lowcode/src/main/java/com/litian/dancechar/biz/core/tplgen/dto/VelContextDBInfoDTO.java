package com.litian.dancechar.biz.core.tplgen.dto;

import com.litian.dancechar.common.common.dto.BaseDTO;
import com.litian.dancechar.common.common.util.JasyptUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VelContextDBInfoDTO extends BaseDTO {

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
     * 加密用户名
     */
    private String encUsername;
    /**
     * 密码
     */
    private String password;
    /**
     * 加密密码
     */
    private String encPassword;
    /**
     * 数据库名
     */
    private String dbName;
    /**
     * 驱动名
     */
    private String driverName;
    /**
     * 数据库标记
     */
    private String dbTag;

    /**
     * 是否为主库
     */
    private Boolean primary;

    /**
     * 数据库url
     */
    private String dbUrl;

    /**
     * 驱动类名 暂未使用 固定mysql
     */
    private String driverClassName;

    public String getEncUsername(){
        return "ENC("+ JasyptUtils.encryptPwd("PWDSalt", this.username) +")";
    }

    public String getEncPassword(){
        return "ENC("+ JasyptUtils.encryptPwd("PWDSalt", this.password) +")";
    }
}
