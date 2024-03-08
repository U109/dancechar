package com.litian.dancechar.biz.core.tplgen.common.enums;

import com.google.common.collect.Lists;
import com.litian.dancechar.biz.core.tplgen.manager.TplCacheManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 代码生成入参枚举对应类
 */
public class TplGenParamEnums {

    @Getter
    @AllArgsConstructor
    public enum MiddlewarePluginEnum{

        REDIS("REDIS", "redis插件"),
        KAFKA("KAFKA", "KAFKA插件"),
        SATURN("SATURN", "SATURN插件"),
        SENTINEL("SENTINEL", "SENTINEL插件"),
        ES("ELASTICSEARCH", "ES插件"),
        MONGODB("MONGODB", "MONGODB插件"),
        ;

        private String code;

        private String desc;

        public MiddlewarePluginEnum getByCode(String code){
            for(MiddlewarePluginEnum redisFlag : values()){
                if(code.equals(redisFlag.getCode())){
                    return redisFlag;
                }
            }
            return null;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum RedisDeployTypeEnum{

        SENTINEL("sentinel", "哨兵模式"),
        CLUSTER("cluster", "集群"),
        ;

        private String code;

        private String desc;

        public RedisDeployTypeEnum getByCode(String code){
            for(RedisDeployTypeEnum redisFlag : values()){
                if(code.equals(redisFlag.getCode())){
                    return redisFlag;
                }
            }
            return null;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum FunctionCollectEnum{

        SYSCONFIG("sysConfig", "公共配置"),
        IDGEN("idGen", "分布式id"),
        SERVERTIME("serverTime", "系统时间"),
        ;

        private String code;

        private String desc;

        public FunctionCollectEnum getByCode(String code){
            for(FunctionCollectEnum redisFlag : values()){
                if(code.equals(redisFlag.getCode())){
                    return redisFlag;
                }
            }
            return null;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum SingleFucPluginEnum{

        SERVICE("service", "dto", "service"),
        MANAGER("manager", null,"manager"),
        REPOSITORY("repository", "mapper", "repository"),
        ;

        private String code;
        // 关联目录
        private String relationDir;
        private String desc;

        public SingleFucPluginEnum getByCode(Integer code){
            for(SingleFucPluginEnum singleFucPluginEnum : values()){
                if(code.equals(singleFucPluginEnum.getCode())){
                    return singleFucPluginEnum;
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

        FNEW("fnew", "新框架模板", 1, TplCacheManager.fnewTplFile),
        SQLFUNC("sqlfunc", "Sql工具模板",4, TplCacheManager.sqlfuncTplFile),
        DFUNC("dfunc", "单功能模板",2, TplCacheManager.dfuncTplFile),
        WDFUNC("wdfunc", "多表单功能模板", 3,TplCacheManager.wdfuncTplFile),
        MANAGEMENTNEW("managementnew", "管理后台框架生成",5, TplCacheManager.managementnewTplFile),
        BOOTNEW("bootnew", "纯springboot框架生成",6, TplCacheManager.bootnewTplFile),
        BOOTDFUNC("bootdfunc", "纯springboot单功能模板",7, TplCacheManager.bootdfuncTplFile),
        BOOTWDFUNC("bootwdfunc", "纯springboot多表单功能模板", 8,TplCacheManager.bootwdfuncTplFile),
        ;

        private String code;

        private String desc;

        private Integer type;

        private Map<String, File> tplPathFileMap;

        public static TemplateTypeEnum getByCode(String code){
            for(TemplateTypeEnum templateTypeEnum : values()){
                if(code.equals(templateTypeEnum.getCode())){
                    return templateTypeEnum;
                }
            }
            return null;
        }

        public static TemplateTypeEnum getByType(Integer type){
            for(TemplateTypeEnum templateTypeEnum : values()){
                if(type == templateTypeEnum.getType()){
                    return templateTypeEnum;
                }
            }
            return null;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum FileMethodEnum{
        CONTROLLER("controller.java", "controller", Arrays.asList("listPage", "save", "update", "deleteById", "getById")),
        SERVICE("service.java", "service", Arrays.asList("listPage", "save", "update", "deleteById", "getById")),
        MANAGER("manager.java", "manager", Arrays.asList("listPage", "save", "update", "deleteById", "getById")),
        REPOSITORY("mapper.java", "repository", Lists.newArrayList()),
        ;
        private String fileName;

        private String dirName;

        private List<String> methodNameList;

        /**
         * 根据目录获取方法列表 用于统计
         * @param dirName
         * @return
         */
        public static List<String> getMethodNameListByDirName(String dirName){
            return Arrays.stream(FileMethodEnum.values()).filter(fileMethodEnum -> fileMethodEnum.getDirName().equals(dirName)).map(FileMethodEnum::getMethodNameList).findFirst().get();
        }
    }

}
