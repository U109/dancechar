package com.litian.dancechar.system.biz.user.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.token.TokenHelper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCJSONUtil;
import com.litian.dancechar.framework.common.util.DCMd5Util;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import com.litian.dancechar.system.biz.user.dao.entity.SystemUserDO;
import com.litian.dancechar.system.biz.user.dao.inf.SystemUserDao;
import com.litian.dancechar.system.biz.user.dto.SystemUserAuthRespDTO;
import com.litian.dancechar.system.biz.user.dto.SystemUserReqDTO;
import com.litian.dancechar.system.biz.user.dto.SystemUserRespDTO;
import com.litian.dancechar.system.common.constants.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 系统用户服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SystemUserService extends ServiceImpl<SystemUserDao, SystemUserDO> {
    @Resource
    private SystemUserDao systemUserDao;
    @Resource
    private RedisHelper redisHelper;

    /**
     * 功能: 分页查询列表
     */
    public RespResult<PageWrapperDTO<SystemUserRespDTO>> listPaged(SystemUserReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<SystemUserRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(systemUserDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：查询信息
     */
    public SystemUserDO findById(String id) {
        return systemUserDao.selectById(id);
    }

    /**
     * 功能：获取账号信息
     */
    public SystemUserDO getByAccountNo(String accountNo){
        LambdaQueryWrapper<SystemUserDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(SystemUserDO::getAccountNo, accountNo);
        return baseMapper.selectOne(lambdaQueryWrapper);
    }

    /**
     * 功能：获取黑名单账号列表
     */
    public List<SystemUserDO> findBlackList(){
        LambdaQueryWrapper<SystemUserDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(SystemUserDO::getBlackList, 1);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    /**
     * 功能：验证账号密码是否正确
     */
    public RespResult<SystemUserAuthRespDTO> validAccountNo(String accountNo, String pwd){
        SystemUserDO systemUserDO = getByAccountNo(accountNo);
        if(ObjectUtil.isNull(systemUserDO) ){
            log.error("账号信息不存在！accountNo:{}", accountNo);
            return RespResult.error("账号密码不正确");
        }
        if(!StrUtil.equals(systemUserDO.getPwd(), DCMd5Util.getMd5(pwd))){
            log.error("账号密码不正确！");
            return RespResult.error("账号密码不正确");
        }
        if(isBlackList(systemUserDO.getId())){
            log.error("黑名单用户！accountNo:{}", accountNo);
            return RespResult.error("账号密码不正确");
        }
        SystemUserAuthRespDTO systemUserAuthRespDTO = new SystemUserAuthRespDTO();
        // 构造用户鉴权的accessToken和refreshToken
        systemUserAuthRespDTO.setSystemUserRespDTO(DCBeanUtil.copyNotNull(systemUserDO,new SystemUserRespDTO()));
        systemUserAuthRespDTO.setAccessToken(TokenHelper.buildAdminToken(systemUserDO.getId(),
                systemUserDO.getAccountNo()));
        systemUserAuthRespDTO.setRefreshToken(TokenHelper.buildAdminRefreshToken(systemUserDO.getId(),
                systemUserDO.getAccountNo()));
        return RespResult.success(systemUserAuthRespDTO);
    }

    /**
     * 功能：新增信息
     */
    public RespResult<Boolean> saveWithInsert(SystemUserReqDTO studentReqDTO) {
        SystemUserDO systemUserDO = new SystemUserDO();
        DCBeanUtil.copyNotNull(systemUserDO, studentReqDTO);
        if(StrUtil.isNotEmpty(studentReqDTO.getPwd())){
            String pwd = DCMd5Util.getMd5(studentReqDTO.getPwd());
            systemUserDO.setPwd(pwd);
        }
        save(systemUserDO);
        String redisKey = String.format(RedisKeyConstants.User.USER_INFO, systemUserDO.getId());
        redisHelper.set(redisKey, DCJSONUtil.toJsonStr(systemUserDO),
                RedisKeyConstants.User.USER_INFO_EXPIRE_TIME, TimeUnit.SECONDS);
        return RespResult.success(true);
    }

    /**
     * 功能：加入/剔除黑名单信息
     */
    public RespResult<Boolean> addOrDeleteBlackList(SystemUserReqDTO studentReqDTO, Integer blackList) {
        SystemUserDO systemUserDO = systemUserDao.selectById(studentReqDTO.getId());
        if(ObjectUtil.isNull(systemUserDO)){
            return RespResult.error("用户不存在！id:{}", studentReqDTO.getId());
        }
        systemUserDO.setBlackList(blackList);
        this.updateById(systemUserDO);
        String redisKey = String.format(RedisKeyConstants.User.USER_INFO, systemUserDO.getId());
        redisHelper.set(redisKey, DCJSONUtil.toJsonStr(systemUserDO),
                RedisKeyConstants.User.USER_INFO_EXPIRE_TIME, TimeUnit.SECONDS);
        return RespResult.success(true);
    }

    /**
     * 功能：判断黑名单信息
     */
    public Boolean isBlackList(String userId){
        if(StrUtil.isEmpty(userId)){
            return false;
        }
        String redisKey = String.format(RedisKeyConstants.User.USER_INFO, userId);
        String userInfoStr = redisHelper.getString(redisKey);
        if(StrUtil.isNotEmpty(userInfoStr)){
            SystemUserDO systemUserDO = DCJSONUtil.toBean(userInfoStr, SystemUserDO.class);
            return ObjectUtil.equals(systemUserDO.getBlackList(), 1);
        }
        synchronized(this){
            SystemUserDO systemUserDO = findById(userId);
            if(ObjectUtil.isNotNull(systemUserDO)){
                redisHelper.set(redisKey, DCJSONUtil.toJsonStr(systemUserDO),
                        RedisKeyConstants.User.USER_INFO_EXPIRE_TIME, TimeUnit.SECONDS);
                return ObjectUtil.equals(systemUserDO.getBlackList(), 1);
            }
        }
        return false;
    }

    /**
     * 从缓存中获取用户信息
     */
    public SystemUserDO findByIdFromCache(String userId){
        String redisKey = String.format(RedisKeyConstants.User.USER_INFO, userId);
        String userInfoStr = redisHelper.getString(redisKey);
        if(StrUtil.isNotEmpty(userInfoStr)){
           return DCJSONUtil.toBean(userInfoStr, SystemUserDO.class);
        }
        synchronized(this){
            SystemUserDO systemUserDO = findById(userId);
            if(ObjectUtil.isNotNull(systemUserDO)){
                redisHelper.set(redisKey, DCJSONUtil.toJsonStr(systemUserDO),
                        RedisKeyConstants.User.USER_INFO_EXPIRE_TIME, TimeUnit.SECONDS);
                return systemUserDO;
            }
        }
        return null;
    }

    /**
     * 功能：修改信息
     */
    public RespResult<Boolean> saveWithEdit(SystemUserReqDTO studentReqDTO) {
        SystemUserDO systemUserDO = systemUserDao.selectById(studentReqDTO.getId());
        if(ObjectUtil.isNull(systemUserDO)){
            return RespResult.error("用户不存在！id:{}", studentReqDTO.getId());
        }
        DCBeanUtil.copyNotNull(systemUserDO, studentReqDTO);
        this.updateById(systemUserDO);
        String redisKey = String.format(RedisKeyConstants.User.USER_INFO, systemUserDO.getId());
        redisHelper.set(redisKey, DCJSONUtil.toJsonStr(systemUserDO),
                RedisKeyConstants.User.USER_INFO_EXPIRE_TIME, TimeUnit.SECONDS);
        return RespResult.success(true);
    }
}