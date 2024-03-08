package com.litian.dancechar.biz.core.tabledetailinfo.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.core.scaffold.dto.FuncMethodListRespDTO;
import com.litian.dancechar.biz.core.scaffold.dto.FuncMethodReqDTO;
import com.litian.dancechar.biz.core.scaffold.manager.FuncMethodInfoManager;
import com.litian.dancechar.biz.core.tabledetailinfo.dto.TableDetailInfoDTO;
import com.litian.dancechar.biz.core.tabledetailinfo.dto.TableDetailInfoQueryReqDTO;
import com.litian.dancechar.biz.core.tabledetailinfo.repository.dataobject.TableDetailInfoDO;
import com.litian.dancechar.biz.core.tabledetailinfo.repository.mapper.TableDetailInfoMapper;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenTableColumnDTO;
import com.litian.dancechar.common.common.enums.ColumnDisabledEnum;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：代码生成-表关联字段信息表manager
 *
 * @author 853523
 * @date 2021-09-22 10:30:53
 */
@Component
@Slf4j
public class TableDetailInfoManager extends ServiceImpl<TableDetailInfoMapper, TableDetailInfoDO> {

    @Autowired
    private FuncMethodInfoManager funcMethodInfoManager;

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<TableDetailInfoDTO> listPage(TableDetailInfoQueryReqDTO tableDetailInfoQueryReqDTO) {
        PageHelper.startPage(tableDetailInfoQueryReqDTO.getPageNo(), tableDetailInfoQueryReqDTO.getPageSize());
        LambdaQueryWrapper<TableDetailInfoDO> queryWrapper = Wrappers.lambdaQuery();
        // cloumn query example:
        // queryWrapper.eq("id", 1);
        List<TableDetailInfoDO> list = getBaseMapper().selectList(queryWrapper);
        PageResp<TableDetailInfoDTO> pageResp = PageRespUtil.buildPageResult(list, TableDetailInfoDTO.class);
        return pageResp;
    }

    /**
     * 功能：新增保存记录
     */
    public Boolean save(TableDetailInfoDTO tableDetailInfoDTO) {
        TableDetailInfoDO tableDetailInfoDO = new TableDetailInfoDO();
        DCBeanUtil.copyNotNull(tableDetailInfoDTO, tableDetailInfoDO);
        return save(tableDetailInfoDO);
    }

    public Boolean insertBatch(List<TableDetailInfoDTO> tableDetailInfos) {
        List<TableDetailInfoDO> tableDetailInfoList = DCBeanUtil.copyList(tableDetailInfos, TableDetailInfoDO.class);
        return saveBatch(tableDetailInfoList);
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(TableDetailInfoDTO tableDetailInfoDTO) {
        if (DCObjectUtil.isNotNull(tableDetailInfoDTO.getId())) {
            TableDetailInfoDO tableDetailInfoDO = this.baseMapper.selectById(tableDetailInfoDTO.getId());
            if (DCObjectUtil.isNotNull(tableDetailInfoDO)) {
                DCBeanUtil.copyNotNull(tableDetailInfoDTO, tableDetailInfoDO);
                return this.baseMapper.updateById(tableDetailInfoDO) > 0;
            }
        }
        return false;
    }

    public Boolean updateBatch(List<TableDetailInfoDTO> tableDetailInfos) {
        tableDetailInfos.forEach(tableDetailInfoDTO -> {
            this.update(tableDetailInfoDTO);
        });
        return true;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(TableDetailInfoDTO tableDetailInfoDTO) {
        TableDetailInfoDO tableDetailInfoDO = this.baseMapper.selectById(tableDetailInfoDTO.getId());
        if (DCObjectUtil.isNotNull(tableDetailInfoDO)) {
            return super.removeById(tableDetailInfoDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public TableDetailInfoDTO getWrapperById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new TableDetailInfoDTO());
    }

    public List<TableDetailInfoDTO> getColumnConfigByGenInfoId(Long scaffoldGenInfoId){
        QueryWrapper query = Wrappers.query();
        query.eq("scaffold_gen_info_id",scaffoldGenInfoId);
        query.eq("delete_flag",0);
        return DCBeanUtil.copyList(this.baseMapper.selectList(query), TableDetailInfoDTO.class);
    }

    public List<TplGenTableColumnDTO> getConfigListByGenInfoId(Long scaffoldGenInfoId){
        return getConfigList(scaffoldGenInfoId);
    }

    /**
     * 获取工程的配置列表
     */
    public List<TplGenTableColumnDTO> getConfigList(Long scaffoldGenInfoId){
        QueryWrapper query = Wrappers.query();
        query.eq("scaffold_gen_info_id",scaffoldGenInfoId);
        query.eq("delete_flag",0);
        query.orderByDesc("query_list_no");
        List<TableDetailInfoDO> detailList = this.baseMapper.selectList(query);
        if(DCCollectionUtil.isNotEmpty(detailList)) {
            List<TplGenTableColumnDTO> configList = DCBeanUtil.copyList(detailList, TplGenTableColumnDTO.class);
                configList.forEach(config -> {
                    for (ColumnDisabledEnum columnDisabledEnum : ColumnDisabledEnum.values()) {
                        if (columnDisabledEnum.getJavaColumn().equals(config.getJavaColumns())) {
                            config.setDisabled(1);
                        }
                    }
                });
            return configList;
        }
        FuncMethodReqDTO funcMethodReqDTO = new FuncMethodReqDTO();
        funcMethodReqDTO.setGenInfoId(scaffoldGenInfoId);
        FuncMethodListRespDTO funcMethodListRespDTO = funcMethodInfoManager.getMethodInfoList(funcMethodReqDTO);
        if(DCObjectUtil.isEmpty(funcMethodListRespDTO) || DCCollectionUtil.isEmpty(funcMethodListRespDTO.getMethodList()) ||
                DCCollectionUtil.isEmpty(funcMethodListRespDTO.getMethodList().get(0).getTableField())){
            return new ArrayList<>();
        }
        return funcMethodListRespDTO.getMethodList().get(0).getTableField();
    }

    public int deleteByGenInfoId(Long genInfoId){
        return baseMapper.deleteByGenInfoId(genInfoId);
    }


}
