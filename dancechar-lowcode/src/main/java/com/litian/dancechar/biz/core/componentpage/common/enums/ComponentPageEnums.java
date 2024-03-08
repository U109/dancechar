package com.litian.dancechar.biz.core.componentpage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 组件页枚举
 */
public class ComponentPageEnums {

    /**
     * 固定文件重命名枚举
     */
    @Getter
    @AllArgsConstructor
    public enum GenFileRenameEnum{
        DTO("dtoName", "${className}DTO.java.vm", "DTO.java"),
        QUERYREQDTO("queryReqDTOName", "${className}QueryReqDTO.java.vm", "QueryReqDTO.java"),
        MANAGER("managerName", "${className}Manager.java.vm", "Manager.java"),
        DO("doName", "${className}DO.java.vm", "DO.java"),
        MAPPER("mapperName", "${className}Mapper.java.vm", "Mapper.java"),
        MAPPERXML("mapperName", "${className}Mapper.xml.vm", "Mapper.xml"),
        SERVICE("serviceName", "${className}Service.java.vm", "Service.java"),
        SERVICEIMPL("serviceImplName", "${className}ServiceImpl.java.vm", "ServiceImpl.java"),
        ;

        private String code;
        private String tplName;
        private String fileSuffix;

        public static String getFileSuffix(String filePathName){
            for(GenFileRenameEnum genFileRenameEnum : GenFileRenameEnum.values()){
                if(org.apache.commons.lang.StringUtils.endsWith(filePathName, genFileRenameEnum.getTplName())){
                    return genFileRenameEnum.getFileSuffix();
                }
            }
            return null;
        }
    }
}
