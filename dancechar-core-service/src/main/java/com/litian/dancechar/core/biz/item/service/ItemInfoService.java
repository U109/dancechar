package com.litian.dancechar.core.biz.item.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.core.biz.item.dao.entity.ItemInfoDO;
import com.litian.dancechar.core.biz.item.dao.inf.ItemInfoDao;
import com.litian.dancechar.core.biz.item.dto.ItemInfoReqDTO;
import com.litian.dancechar.core.biz.item.dto.ItemInfoRespDTO;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 物品信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ItemInfoService extends ServiceImpl<ItemInfoDao, ItemInfoDO> {
    @Resource
    private ItemInfoDao itemInfoDao;

    /**
     * 功能: 分页查询事务补偿消息列表
     */
    public RespResult<PageWrapperDTO<ItemInfoRespDTO>> listPaged(ItemInfoReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<ItemInfoRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(itemInfoDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：根据Id-查询物品消息
     */
    public ItemInfoDO findById(String id) {
        return itemInfoDao.selectById(id);
    }

    /**
     * 功能：根据物品code-查询物品消息
     */
    public ItemInfoDO findByCode(String code) {
        LambdaQueryWrapper<ItemInfoDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(ItemInfoDO::getItemCode, code);
        return itemInfoDao.selectOne(lambdaQueryWrapper);
    }

    /**
     * 功能：查询物品列表
     */
    public List<ItemInfoRespDTO> findList(ItemInfoReqDTO req) {
        return itemInfoDao.findList(req);
    }

    /**
     * 功能：新增物品消息
     */
    public void insert(ItemInfoReqDTO req) {
        ItemInfoDO itemInfoDO = new ItemInfoDO();
        DCBeanUtil.copyNotNull(itemInfoDO, req);
        itemInfoDao.insert(itemInfoDO);
    }

    /**
     * 功能：扣减物品库存
     */
    public RespResult<Boolean> updateStock(ItemInfoReqDTO req) {
        if(StrUtil.isEmpty(req.getItemCode())){
            return RespResult.error("物品code不能为空");
        }
        if(ObjectUtil.isNull(req.getAcquire())){
            return RespResult.error("扣减库存数量不能为空");
        }
        return RespResult.success(itemInfoDao.updateStock(req) > 0);
    }

    /**
     * 功能：更新物品消息
     */
    public boolean update(ItemInfoReqDTO req) {
        ItemInfoDO itemInfoDO = null;
        if(StrUtil.isNotEmpty(req.getId())){
            itemInfoDO = this.findById(req.getId());
        } else if(StrUtil.isNotEmpty(req.getItemCode())){
            itemInfoDO = this.findByCode(req.getItemCode());
        }
        if(ObjectUtil.isNotNull(itemInfoDO)){
            DCBeanUtil.copyNotNull(itemInfoDO, req);
            return itemInfoDao.updateById(itemInfoDO) > 0;
        }
        return true;
    }
}