package com.litian.dancechar.framework.oss.dto;

import lombok.Data;

@Data
public class UploadResultDTO {

    /**
     * oss完整的访问地址（https://zenu-oss.oss-cn-shenzhen.aliyuncs.com/json/customer/20231125/9aae4986d30c487a9fbc5d15f82c56ea.txt）
     */
    private String ossAccessUrl;

    /**
     * oss文件券路径，包括文件名
     */
    private String objectName;

    /**
     * 文件中总的记录数
     */
    private Long totalNum;
}
