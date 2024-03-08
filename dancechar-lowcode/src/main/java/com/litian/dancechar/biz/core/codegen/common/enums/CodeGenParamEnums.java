package com.litian.dancechar.biz.core.codegen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 代码生成入参枚举对应类
 */
public class CodeGenParamEnums {

    @Getter
    @AllArgsConstructor
    public enum PluginEnum{

        REDIS("REDIS", "redis插件"),
        KAFKA("KAFKA", "KAFKA插件"),
        ;

        private String code;

        private String desc;

        public PluginEnum getByCode(Integer code){
            for(PluginEnum redisFlag : values()){
                if(code.equals(redisFlag.getCode())){
                    return redisFlag;
                }
            }
            return null;
        }
    }


    /**
     * 新老模板涵盖所有插件，是否引用对应插件用redisflag kafkaflag等参数控制
     */
    @Getter
    @AllArgsConstructor
    public enum TemplateTypeEnum{

        DEMO("demo", "示例模板curd"),
        NEW("new", "新框架模板"),
        OLD("old", "老框架模板"),
        ;

        private String code;

        private String desc;

        public static TemplateTypeEnum getByCode(String code){
            for(TemplateTypeEnum templateTypeEnum : values()){
                if(code.equals(templateTypeEnum.getCode())){
                    return templateTypeEnum;
                }
            }
            return null;
        }
    }

}
