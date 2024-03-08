package com.litian.dancechar.biz.core.tplgen.service.impl;

import com.litian.dancechar.biz.core.codegen.common.util.Util;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenParamDTO;
import com.litian.dancechar.biz.core.tplgen.manager.TplGenManager;
import com.litian.dancechar.biz.core.tplgen.service.TplGenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 类描述：代码生成详细配置服务实现
 *
 * @author 01406831
 * @date 2021/3/31 11:18
 */
@Component
@Slf4j
public class TplGenServiceImpl implements TplGenService {
    @Autowired
    private TplGenManager tplGenManager;


    /**
     * 代码生成基础配置生成
     */
    @Override
    public void tplGen(TplGenParamDTO tplGenParamDTO, @Context HttpServletResponse response) {
        //模板渲染
        ByteArrayOutputStream baos = this.tplGenManager.tplGen(tplGenParamDTO);
        // 生成压缩包文件
        Util.DownloadGen(response, baos.toByteArray(), tplGenParamDTO.getSysCode());
    }

    @Override
    public void tplGenList(List<TplGenParamDTO> tplGenParamList, HttpServletResponse response) {
        //模板渲染
        ByteArrayOutputStream baos = this.tplGenManager.tplGenList(tplGenParamList);
        // 生成压缩包文件
        Util.DownloadGen(response, baos.toByteArray(), tplGenParamList.get(0).getSysCode());
    }

    @Override
    public void tplGenSql(TplGenParamDTO tplGenParamDTO, @Context HttpServletResponse response) {
        ByteArrayOutputStream baos = this.tplGenManager.tplGenSql(tplGenParamDTO);
        Util.DownloadGen(response, baos.toByteArray(), tplGenParamDTO.getSysCode());
    }


}
