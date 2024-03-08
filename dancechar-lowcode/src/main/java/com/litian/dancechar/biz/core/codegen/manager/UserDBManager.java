package com.litian.dancechar.biz.core.codegen.manager;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.core.codegen.common.constants.CommonConstant;
import com.litian.dancechar.biz.core.codegen.dto.BaseParam;
import com.litian.dancechar.biz.core.codegen.dto.UserDBDTO;
import com.litian.dancechar.biz.core.codegen.dto.UserDBQueryReqDTO;
import com.litian.dancechar.biz.core.codegen.repository.dataobject.UserDBDO;
import com.litian.dancechar.biz.core.codegen.repository.mapper.UserDBMapper;
import com.litian.dancechar.framework.common.exception.BusinessException;
import com.litian.dancechar.framework.common.util.AESUtils;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：userDB manager
 *
 * @author terryhl
 * @date 2021-06-15 14:43:47
 */
@Component
@Slf4j
public class UserDBManager extends ServiceImpl<UserDBMapper, UserDBDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<UserDBDTO> listPage(UserDBQueryReqDTO userDBQueryReqDTO) {
        PageHelper.startPage(userDBQueryReqDTO.getPageNo(), userDBQueryReqDTO.getPageSize());
        PageResp<UserDBDTO> pageResp = new PageResp<>();
        QueryWrapper<UserDBDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id", userDBQueryReqDTO.getUserId());
        List<UserDBDO> list = getBaseMapper().selectList(queryWrapper);
        PageRespUtil.buildPageResult(DCBeanUtil.copyList(list, UserDBDTO.class), pageResp);
        return pageResp;
    }


    /**
     * 查询我的db信息
     * @param userDBQueryReqDTO
     * @return
     */
    public PageResp<UserDBDTO> listMyUserDB(UserDBQueryReqDTO userDBQueryReqDTO) {
        PageHelper.startPage(userDBQueryReqDTO.getPageNo(), userDBQueryReqDTO.getPageSize());
        PageResp<UserDBDTO> pageResp = new PageResp<>();
        UserDBDO userDBDO = UserDBDO.builder().build();
        DCBeanUtil.copyNotNull(userDBQueryReqDTO, userDBDO);
        QueryWrapper<UserDBDO> queryWrapper = Wrappers.query(userDBDO);
        List<UserDBDO> list = getBaseMapper().selectList(queryWrapper);
        PageRespUtil.buildPageResult(DCBeanUtil.copyList(list, UserDBDTO.class), pageResp);
        return pageResp;
    }

    /**
     * 功能：新增保存记录
     */
    public Boolean save(UserDBDTO userDBDTO) {
        DCValidatorUtil.validateModel(userDBDTO, BaseParam.add.class);
        // TODO 获取session userId
        String userId = userDBDTO.getUserId();
        // 生成数据库编码
        String dbCode = UUID.fastUUID().toString().replaceAll("-", "");
        UserDBDO userDBDO = new UserDBDO();
        userDBDTO.setDbCode(dbCode);
        userDBDTO.setUserId(userId);
        // 用户名密码加密
        String username = null;
        String password = null;
        try {
            username = AESUtils.encrypt(userDBDTO.getDbUsername(), CommonConstant.DB_ENC_KEY);
            password = AESUtils.encrypt(userDBDTO.getDbPassword(), CommonConstant.DB_ENC_KEY);
        } catch (Exception e) {
            throw new BusinessException("加密错误！");
        }
        DCBeanUtil.copyNotNull(userDBDTO, userDBDO);
        userDBDO.setDbUsername(username);
        userDBDO.setDbPassword(password);
        return save(userDBDO);
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(UserDBDTO userDBDTO) {
        DCValidatorUtil.validateModel(userDBDTO, BaseParam.edit.class);
        // 用户名密码加密
        String username = null;
        String password = null;
        try {
            username = AESUtils.encrypt(userDBDTO.getDbUsername(), CommonConstant.DB_ENC_KEY);
            password = AESUtils.encrypt(userDBDTO.getDbPassword(), CommonConstant.DB_ENC_KEY);
        } catch (Exception e) {
            throw new BusinessException("加密错误！");
        }
        boolean updateFlag = lambdaUpdate().eq(UserDBDO::getDbCode, userDBDTO.getDbCode())
                .set(UserDBDO::getDbHost, userDBDTO.getDbHost())
                .set(UserDBDO::getDbDriver, userDBDTO.getDbDriver())
                .set(UserDBDO::getDbPort, userDBDTO.getDbPort())
                .set(UserDBDO::getDbName, userDBDTO.getDbName())
                .set(UserDBDO::getDbUsername, username)
                .set(UserDBDO::getDbPassword, password)
                .set(UserDBDO::getDbDesc, userDBDTO.getDbDesc())
                .update();
        return updateFlag;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(UserDBDTO userDBDTO) {
        UserDBDO userDBDO = this.baseMapper.selectById(userDBDTO.getId());
        if (DCObjectUtil.isNotNull(userDBDO)) {
            userDBDO.setDeleteFlag(1);
            return this.baseMapper.updateById(userDBDO) > 0;
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteByDBCode(UserDBDTO userDBDTO) {
        QueryWrapper queryWrapper = Wrappers.query().eq("db_code", userDBDTO.getDbCode());
        UserDBDO userDBDO = baseMapper.selectOne(queryWrapper);
        if (DCObjectUtil.isNotNull(userDBDO)) {
            userDBDO.setDeleteFlag(CommonConstant.DELETE_FLAG_Y);
            return this.baseMapper.updateById(userDBDO) > 0;
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public UserDBDTO getById(Long id) {
        return DCBeanUtil.copyNotNull(getBaseMapper().selectById(id), new UserDBDTO());
    }

    /**
     * 根据dbCode查询
     * @param userDBDTO
     * @return
     */
    public UserDBDTO getByDBCode(UserDBDTO userDBDTO){
        String userId = userDBDTO.getUserId();
        String dbCode = userDBDTO.getDbCode();
        QueryWrapper queryWrapper = Wrappers.query().eq("db_code", dbCode).eq("user_id", userId);
        UserDBDO userDBDO = baseMapper.selectOne(queryWrapper);
        UserDBDTO tmpUserDBDTO = new UserDBDTO();
        DCBeanUtil.copyNotNull(baseMapper.selectOne(queryWrapper), tmpUserDBDTO);
        String username = null;
        String password = null;
        try {
            username = AESUtils.decrypt(userDBDO.getDbUsername(), CommonConstant.DB_ENC_KEY);
            password = AESUtils.decrypt(userDBDO.getDbPassword(), CommonConstant.DB_ENC_KEY);
        } catch (Exception e) {
            throw new BusinessException("加密错误！");
        }
        userDBDTO.setDbUsername(username);
        userDBDTO.setDbPassword(password);
        return tmpUserDBDTO;
    }
}
