package com.litian.dancechar.biz.core.componentpage.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.core.componentpage.common.enums.ComponentPageEnums;
import com.litian.dancechar.biz.core.componentpage.dto.GenFileInfoDTO;
import com.litian.dancechar.biz.core.componentpage.dto.GenFileInfoQueryReqDTO;
import com.litian.dancechar.biz.core.componentpage.dto.GenFileRenameExtraDTO;
import com.litian.dancechar.biz.core.componentpage.repository.dataobject.GenFileInfoDO;
import com.litian.dancechar.biz.core.componentpage.repository.mapper.GenFileInfoMapper;
import com.litian.dancechar.biz.core.tplgen.common.utils.TplGenUtil;
import com.litian.dancechar.common.common.constants.DBConstants;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：genFileInfo manager
 *
 * @author fcoder
 * @date 2021-06-29 14:24:32
 */
@Component
@Slf4j
public class GenFileInfoManager extends ServiceImpl<GenFileInfoMapper, GenFileInfoDO> {

    @Autowired
    private GenFileRenameExtraManager genFileRenameExtraManager;

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<GenFileInfoDTO> listPage(GenFileInfoQueryReqDTO genFileInfoQueryReqDTO) {
        PageHelper.startPage(genFileInfoQueryReqDTO.getPageNo(), genFileInfoQueryReqDTO.getPageSize());
        QueryWrapper<GenFileInfoDO> queryWrapper = Wrappers.query();
        // cloumn query example:
        // queryWrapper.eq("id", 1);
        List<GenFileInfoDO> list = getBaseMapper().selectList(queryWrapper);
        PageResp<GenFileInfoDTO> pageResp = PageRespUtil.buildPageResult(list, GenFileInfoDTO.class);
        return pageResp;
    }

    /**
     * 功能：新增保存记录
     */
    public Boolean save(GenFileInfoDTO genFileInfoDTO) {
        GenFileInfoDO genFileInfoDO = new GenFileInfoDO();
        DCBeanUtil.copyNotNull(genFileInfoDTO, genFileInfoDO);
        return save(genFileInfoDO);
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(GenFileInfoDTO genFileInfoDTO) {
        if (DCObjectUtil.isNotNull(genFileInfoDTO.getId())) {
            GenFileInfoDO genFileInfoDO = this.baseMapper.selectById(genFileInfoDTO.getId());
            if (DCObjectUtil.isNotNull(genFileInfoDO)) {
                DCBeanUtil.copyNotNull(genFileInfoDTO, genFileInfoDO);
                return this.baseMapper.updateById(genFileInfoDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(GenFileInfoDTO genFileInfoDTO) {
        GenFileInfoDO genFileInfoDO = this.baseMapper.selectById(genFileInfoDTO.getId());
        if (DCObjectUtil.isNotNull(genFileInfoDO)) {
            return super.removeById(genFileInfoDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public GenFileInfoDTO getById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new GenFileInfoDTO());
    }

    /**
     * 功能：修改文件名
     */
    public Boolean renameClassName(GenFileInfoDTO genFileInfoDTO) {
        if (DCObjectUtil.isNotNull(genFileInfoDTO.getId())) {
            GenFileInfoDO genFileInfoDO = this.baseMapper.selectById(genFileInfoDTO.getId());
            if (DCObjectUtil.isNotNull(genFileInfoDO)) {
                // 组装文件名
                String fileSuffix = ComponentPageEnums.GenFileRenameEnum.getFileSuffix(genFileInfoDTO.getFilePathName());
                String fileName = genFileInfoDTO.getClassName()+fileSuffix;
                String realPathName = StringUtils.substring(genFileInfoDO.getRealPathName(),0, StringUtils.lastIndexOf(genFileInfoDO.getRealPathName(), "\\")+1)+fileName;
                boolean updateFlag =  lambdaUpdate().eq(GenFileInfoDO::getId, genFileInfoDTO.getId())
                        .set(GenFileInfoDO::getClassName, genFileInfoDTO.getClassName())
                        .set(GenFileInfoDO::getRealPathName, realPathName)
                        .update();
                if(updateFlag){
                    // 更新 重命名扩展表
                    GenFileRenameExtraDTO genFileRenameExtraDTOQuery = GenFileRenameExtraDTO.builder()
                            .genInfoId(genFileInfoDO.getGenInfoId())
                            .genDbId(genFileInfoDO.getGenDbId())
                            .genDbExampleId(genFileInfoDO.getGenDbExampleId())
                            .build();
                    GenFileRenameExtraDTO genFileRenameExtraDTO = genFileRenameExtraManager.getByObj(genFileRenameExtraDTOQuery);
                    String renameExtra = genFileRenameExtraDTO.getRenameExtra();
                    JSONObject jsonObject = JSON.parseObject(renameExtra);
                    // 查找key名称
                    jsonObject.put(genFileRenameExtraManager.getRenameKey(genFileInfoDO.getFilePathName()), genFileInfoDTO.getClassName());
                    // 更新
                    genFileRenameExtraDTO.setRenameExtra(jsonObject.toJSONString());
                    updateFlag = genFileRenameExtraManager.update(genFileRenameExtraDTO);
                    if(!updateFlag){
                        log.error("rename fail!genFileInfoDTO={}", JSON.toJSONString(genFileInfoDTO));
                    }
                    return updateFlag;
                }
            }
        }
        return false;
    }


    /**
     * 清理之前生成的文件集合
     * @param genInfoId
     * @return
     */
    public Boolean deleteAllFileInfoByGenInfoId(Long genInfoId){
        UpdateWrapper<GenFileInfoDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("gen_info_id", genInfoId).set("delete_flag", DBConstants.DELETE_FLAG_Y);
        int rowCount = this.getBaseMapper().update(null, updateWrapper);
        return rowCount>0?true:false;
    }

    public Boolean deleteFileByGenInfoIds(List<Long> genInfoIds){
        UpdateWrapper<GenFileInfoDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("gen_info_id", genInfoIds).set("delete_flag", DBConstants.DELETE_FLAG_Y);
        int rowCount = this.getBaseMapper().update(null, updateWrapper);
        return rowCount>0?true:false;
    }

    /**
     * 批量插入
     * @param genFileInfoDTOList
     * @return
     */
    public Boolean saveBatch(List<GenFileInfoDTO> genFileInfoDTOList){
        List<GenFileInfoDO> genFileInfoDOList = DCBeanUtil.copyList(genFileInfoDTOList, GenFileInfoDO.class);
        return saveBatch(genFileInfoDOList);
    }

    public List<GenFileInfoDTO> saveBatchReturnList(List<GenFileInfoDTO> genFileInfoDTOList){
        List<GenFileInfoDO> genFileInfoDOList = DCBeanUtil.copyList(genFileInfoDTOList, GenFileInfoDO.class);
        Boolean flag = saveBatch(genFileInfoDOList);
        List<GenFileInfoDTO> newList = new ArrayList<>();
        if(flag){
            //将生成的id赋值给入参
            newList = DCBeanUtil.copyList(genFileInfoDOList, GenFileInfoDTO.class);
        }
        return newList;
    }

    /**
     * 展示所有文件集合
     * @param genInfoId
     * @return
     */
    public List<GenFileInfoDTO> listAll(Long genInfoId){
        QueryWrapper queryWrapper = Wrappers.query().eq("gen_info_id", genInfoId).eq("delete_flag", DBConstants.DELETE_FLAG_N);
        List<GenFileInfoDO> genFileInfoDOList = this.getBaseMapper().selectList(queryWrapper);
        return DCBeanUtil.copyList(genFileInfoDOList, GenFileInfoDTO.class);
    }

    /**
     * 根据 genInfoId genDbId genDbExampleId filePathName 查询
     * @param genFileInfoQueryReqDTO
     * @return
     */
    public GenFileInfoDTO getOneGenFileInfo(GenFileInfoQueryReqDTO genFileInfoQueryReqDTO){
        GenFileInfoDO genFileInfoDO = DCBeanUtil.copyProperties(genFileInfoQueryReqDTO, GenFileInfoDO.class);
        QueryWrapper<GenFileInfoDO> queryWrapper = Wrappers.query(genFileInfoDO);
        GenFileInfoDO genFileInfoDOQuery = getBaseMapper().selectOne(queryWrapper);
        if(genFileInfoDOQuery == null){
            return null;
        }
        if (DCStrUtil.isNotBlank(genFileInfoDOQuery.getClassName()) && genFileInfoDOQuery.getClassName().contains("_")) {
            String classNameHump = TplGenUtil.underlineToHump(genFileInfoDOQuery.getClassName());
            genFileInfoDOQuery.setClassName(StringUtils.replaceFirst(classNameHump, classNameHump.substring(0, 1), classNameHump.substring(0, 1).toUpperCase()));
        }
        return DCBeanUtil.copyProperties(genFileInfoDOQuery, GenFileInfoDTO.class);
    }
}
