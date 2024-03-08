package com.litian.dancechar.biz.core.tplgen.manager;

import com.google.common.collect.Maps;
import com.litian.dancechar.biz.core.tplgen.common.ConfigCenter;
import com.litian.dancechar.biz.core.tplgen.common.enums.TplGenParamEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.collect.FluentIterable;
import org.apache.curator.shaded.com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Map;

@Slf4j
@Component
public class TplCacheManager {

    @Autowired
    private ConfigCenter configCenter;

    // 模板文件加入内存减少反复io文件读取
    public static Map<String, File> newTplFile = Maps.newConcurrentMap();
    public static Map<String, File> demoTplFile = Maps.newConcurrentMap();

    public static Map<String, File> fnewTplFile = Maps.newConcurrentMap();
    public static Map<String, File> sqlfuncTplFile = Maps.newConcurrentMap();
    public static Map<String, File> wdfuncTplFile = Maps.newConcurrentMap();
    public static Map<String, File> dfuncTplFile = Maps.newConcurrentMap();
    public static Map<String, File> managementnewTplFile = Maps.newConcurrentMap();
    public static Map<String, File> bootnewTplFile = Maps.newConcurrentMap();
    public static Map<String, File> bootdfuncTplFile = Maps.newConcurrentMap();
    public static Map<String, File> bootwdfuncTplFile = Maps.newConcurrentMap();

    @PostConstruct
    public void init(){
        for(TplGenParamEnums.TemplateTypeEnum templateTypeEnum : TplGenParamEnums.TemplateTypeEnum.values()){
            try {
                initTplFileCache(templateTypeEnum);
            } catch (Exception e) {
                log.error("initTplFileCache:fail!", e);
            }
        }
    }

    /**
     * 加载模板
     * @param templateTypeEnum
     */
    private void initTplFileCache(TplGenParamEnums.TemplateTypeEnum templateTypeEnum)throws Exception{
        // 加载模板缓存 tpl inputStream
        String rootPath = configCenter.tplRootPath;
        String tplPath = rootPath + templateTypeEnum.getCode();
        log.info("initTplFileCache:tplPath={}", tplPath);
        File sourceFile = new File(tplPath);
//        URL resource = Thread.currentThread().getContextClassLoader().getResource("templates/"+templateTypeEnum.getCode());
//        log.info("initTplFileCache:path={},file={}", resource.getPath(), resource.getFile());
//        File sourceFile = new File(resource.toURI());

        FluentIterable<File> fileFluentIterable = Files.fileTreeTraverser().preOrderTraversal(sourceFile).filter(input -> {
            return input.isFile();  //只要文件
        });


        for(File file : fileFluentIterable){
            String filePath = file.getAbsolutePath().substring(tplPath.length()+1);
            log.info("initTplFileCache:filePath={}", filePath);
            templateTypeEnum.getTplPathFileMap().put(filePath.replaceAll("\\\\", "/"), file);
//            if(templateTypeEnum.getTplPathFileMap() != null){
//                // 相对路径 渲染替换路径及文件流处理
//                templateTypeEnum.getTplPathFileMap().put(filePath, file);
//            }
        }
    }

}
