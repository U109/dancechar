package com.litian.dancechar.biz.core.componentpage.service.impl;

import com.litian.dancechar.biz.core.componentpage.dto.GenFileInfoDTO;
import com.litian.dancechar.biz.core.componentpage.dto.GenFileInfoQueryReqDTO;
import com.litian.dancechar.biz.core.componentpage.dto.GenFileMultiTableDTO;
import com.litian.dancechar.biz.core.componentpage.manager.GenFileInfoManager;
import com.litian.dancechar.biz.core.componentpage.service.ComponentPageService;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldMultiTableReqDTO;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenInfoManager;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenParamDTO;
import com.litian.dancechar.biz.core.tplgen.manager.TplGenManager;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ComponentPageServiceImpl implements ComponentPageService {

    @Autowired
    private TplGenManager tplGenManager;

    @Autowired
    private GenFileInfoManager genFileInfoManager;

    @Autowired
    private ScaffoldGenInfoManager scaffoldGenInfoManager;

    /**
     * 显示目录树
     */
    @Override
    public Result<TplGenParamDTO> initTplGenParamDTO(TplGenParamDTO tplGenParamDTO)throws Exception{
        return DCResultUtil.success(this.scaffoldGenInfoManager.initTplGenParamDTO(tplGenParamDTO.getGenInfoId()));
    }

    /**
     * 显示目录树
     */
    @Override
    public Result<List<GenFileInfoDTO>> loadPage(TplGenParamDTO tplGenParamDTO, @Context HttpServletResponse response)throws Exception{
        return DCResultUtil.success(this.tplGenManager.loadComponentPage(tplGenParamDTO));
    }

    /**
     * 预览文件
     */
    @Override
    public Result<String> previewFile(TplGenParamDTO tplGenParamDTO, @Context HttpServletResponse response)throws Exception{
        return DCResultUtil.success(this.tplGenManager.previewFile(tplGenParamDTO));
    }

    /**
     * 单工程预览文件
     */
    @Override
    public Result<String> previewProjectFile(TplGenParamDTO tplGenParamDTO, HttpServletResponse response) throws Exception {
        return DCResultUtil.success(this.tplGenManager.previewProjectFile(tplGenParamDTO));
    }

    /**
     * 重命名文件
     */
    @Override
    public Result<Boolean> renameClassName(GenFileInfoDTO genFileInfoDTO, @Context HttpServletResponse response)throws Exception{
        return DCResultUtil.success(genFileInfoManager.renameClassName(genFileInfoDTO));
    }

    @Override
    public Result<List<GenFileInfoDTO>> loadMultiTableFile(ScaffoldMultiTableReqDTO scaffoldMultiTableReqDTO, HttpServletResponse response) throws Exception {
        if(DCCollectionUtil.isEmpty(scaffoldMultiTableReqDTO.getIds())){
            return DCResultUtil.error("工程id不能为空");
        }
        List<GenFileInfoDTO> files = new ArrayList<>();
        for (int i = 0; i < scaffoldMultiTableReqDTO.getIds().size(); i++) {
            Long genInfoId = scaffoldMultiTableReqDTO.getIds().get(i);
            TplGenParamDTO tplGenParamDTO = scaffoldGenInfoManager.initTplGenParamDTO(genInfoId);
            List<GenFileInfoDTO> fileList = tplGenManager.loadComponentPage(tplGenParamDTO);
            //如果有多个spring-restful-service.xml.vm,只返回一个
            if( i==0 ){
                files.addAll(fileList);
            }else{
                fileList.forEach(file->{
                    if(!file.getFilePathName().contains("spring-restful-service.xml.vm")){
                        files.add(file);
                    }
                });
            }
        }
        return DCResultUtil.success(files);
    }

    @Override
    public Result<String> newPreviewFile(GenFileMultiTableDTO multiTableDTO, @Context HttpServletResponse response)throws Exception{
        GenFileInfoQueryReqDTO previewFileDTO = multiTableDTO.getPreviewFileDTO();
        TplGenParamDTO tplGenParamDTO = scaffoldGenInfoManager.initTplGenParamDTO(previewFileDTO.getGenInfoId());
        if(previewFileDTO.getFilePathName().contains("spring-restful-service.xml.vm")){
            //根据id获取各自信息
            return DCResultUtil.success(this.tplGenManager.previewOnlyFile(multiTableDTO,tplGenParamDTO.getTemplateType()));
        }
        tplGenParamDTO.setPreviewFileDTO(previewFileDTO);
        return DCResultUtil.success(this.tplGenManager.previewFile(tplGenParamDTO));
    }
}