package com.litian.dancechar.biz.core.componentpage.dto;

import com.litian.dancechar.common.common.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GenFileRenameDTO extends BaseDTO {

    private static final long serialVersionUID = 8504073627659495155L;
    private String dtoName;
    private String queryReqDTOName;
    private String doName;
    private String managerName;
    private String mapperName;
    private String mapperXmlName;
    private String serviceName;
    private String serviceImplName;

}
