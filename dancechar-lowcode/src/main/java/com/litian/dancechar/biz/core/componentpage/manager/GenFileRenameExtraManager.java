package com.litian.dancechar.biz.core.componentpage.manager;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.core.componentpage.common.enums.ComponentPageEnums;
import com.litian.dancechar.biz.core.componentpage.dto.GenFileRenameExtraDTO;
import com.litian.dancechar.biz.core.componentpage.dto.GenFileRenameExtraQueryReqDTO;
import com.litian.dancechar.biz.core.componentpage.repository.dataobject.GenFileRenameExtraDO;
import com.litian.dancechar.biz.core.componentpage.repository.mapper.GenFileRenameExtraMapper;
import com.litian.dancechar.common.common.constants.DBConstants;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：genFileRenameExtra manager
 *
 * @author fcoder
 * @date 2021-06-30 16:42:34
 */
@Component
@Slf4j
@DS("cempfcode")
public class GenFileRenameExtraManager extends ServiceImpl<GenFileRenameExtraMapper, GenFileRenameExtraDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<GenFileRenameExtraDTO> listPage(GenFileRenameExtraQueryReqDTO genFileRenameExtraQueryReqDTO) {
        PageHelper.startPage(genFileRenameExtraQueryReqDTO.getPageNo(), genFileRenameExtraQueryReqDTO.getPageSize());
        QueryWrapper<GenFileRenameExtraDO> queryWrapper = Wrappers.query();
        // cloumn query example:
        // queryWrapper.eq("id", 1);
        List<GenFileRenameExtraDO> list = getBaseMapper().selectList(queryWrapper);
        PageResp<GenFileRenameExtraDTO> pageResp = PageRespUtil.buildPageResult(list, GenFileRenameExtraDTO.class);
        return pageResp;
    }

    /**
     * 查询生成记录关联的重命名
     * @param genInfoId
     * @return
     */
    public List<GenFileRenameExtraDTO> listByGenInfoId(Long genInfoId){
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("gen_info_id", genInfoId);
        queryWrapper.eq("delete_flag", DBConstants.DELETE_FLAG_N);
        List<GenFileRenameExtraDO> list = getBaseMapper().selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            return DCBeanUtil.copyList(list, GenFileRenameExtraDTO.class);
        }
        return null;
    }

    /**
     * 清理所有重命名记录
     * @param genInfoId
     * @return
     */
    public Boolean deleteByGenInfoId(Long genInfoId) {
        return lambdaUpdate().set(GenFileRenameExtraDO::getDeleteFlag, DBConstants.DELETE_FLAG_Y).eq(GenFileRenameExtraDO::getGenInfoId, genInfoId).update();
    }


    /**
     * 功能：新增保存记录
     */
    public Boolean save(GenFileRenameExtraDTO genFileRenameExtraDTO) {
        GenFileRenameExtraDO genFileRenameExtraDO = new GenFileRenameExtraDO();
        DCBeanUtil.copyNotNull(genFileRenameExtraDTO, genFileRenameExtraDO);
        return save(genFileRenameExtraDO);
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(GenFileRenameExtraDTO genFileRenameExtraDTO) {
        if (DCObjectUtil.isNotNull(genFileRenameExtraDTO.getId())) {
            GenFileRenameExtraDO genFileRenameExtraDO = this.baseMapper.selectById(genFileRenameExtraDTO.getId());
            if (DCObjectUtil.isNotNull(genFileRenameExtraDO)) {
                DCBeanUtil.copyNotNull(genFileRenameExtraDTO, genFileRenameExtraDO);
                return this.baseMapper.updateById(genFileRenameExtraDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(GenFileRenameExtraDTO genFileRenameExtraDTO) {
        GenFileRenameExtraDO genFileRenameExtraDO = this.baseMapper.selectById(genFileRenameExtraDTO.getId());
        if (DCObjectUtil.isNotNull(genFileRenameExtraDO)) {
            return super.removeById(genFileRenameExtraDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public GenFileRenameExtraDTO getById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new GenFileRenameExtraDTO());
    }

    /**
     * 批量插入
     * @param genFileRenameExtraDTOList
     * @return
     */
    public Boolean saveBatch(List<GenFileRenameExtraDTO> genFileRenameExtraDTOList){
        if(DCCollectionUtil.isEmpty(genFileRenameExtraDTOList)){
            return true;
        }
        List<GenFileRenameExtraDO> genFileRenameExtraDOList = DCBeanUtil.copyList(genFileRenameExtraDTOList, GenFileRenameExtraDO.class);
        return saveBatch(genFileRenameExtraDOList);
    }

    /**
     * 根据对象入参查询
     * @param genFileRenameExtraDTO
     * @return
     */
    public GenFileRenameExtraDTO getByObj(GenFileRenameExtraDTO genFileRenameExtraDTO){
        genFileRenameExtraDTO.setDeleteFlag(DBConstants.DELETE_FLAG_N);
        GenFileRenameExtraDO genFileRenameExtraDO = DCBeanUtil.copyProperties(genFileRenameExtraDTO, GenFileRenameExtraDO.class);
        QueryWrapper<GenFileRenameExtraDO> queryWrapper = Wrappers.query(genFileRenameExtraDO);
        GenFileRenameExtraDO genFileRenameExtraDOQuery = getBaseMapper().selectOne(queryWrapper);
        if(DCObjectUtil.isNull(genFileRenameExtraDOQuery)){
            return null;
        }
        return DCBeanUtil.copyProperties(genFileRenameExtraDOQuery, GenFileRenameExtraDTO.class);
    }

    // 获取重命名表对应的key
    public String getRenameKey(String filePathName){
        for(ComponentPageEnums.GenFileRenameEnum genFileRenameEnum : ComponentPageEnums.GenFileRenameEnum.values()){
            if(StringUtils.endsWith(filePathName, genFileRenameEnum.getTplName())){
                return genFileRenameEnum.getCode();
            }
        }
        return null;
    }
}
