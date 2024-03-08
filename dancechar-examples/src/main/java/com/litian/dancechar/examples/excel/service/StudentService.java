package com.litian.dancechar.examples.excel.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litian.dancechar.examples.excel.dao.entity.StudentDO;
import com.litian.dancechar.examples.excel.dao.inf.StudentDao;
import com.litian.dancechar.framework.common.base.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 学生服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StudentService extends ServiceImpl<StudentDao, StudentDO> {

    @Resource
    private StudentDao studentDao;

    public List<StudentDO> listPage(Map<String, Object> queryCondition, Integer pageNo, Integer pageSize){
        String no = Convert.toStr(queryCondition.get("no"));
        String name = Convert.toStr(queryCondition.get("name"));
        return studentDao.findByPage(no, name, (pageNo-1)*pageSize, pageSize);
    }

    public List<StudentDO> findList(Map<String, Object> queryCondition){
        LambdaQueryWrapper<StudentDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        String no = Convert.toStr(queryCondition.get("no"));
        if(StrUtil.isNotEmpty(no)){
            lambdaQueryWrapper.eq(StudentDO::getNo, no);
        }
        String name = Convert.toStr(queryCondition.get("name"));
        if(StrUtil.isNotEmpty(name)){
            lambdaQueryWrapper.eq(StudentDO::getName, name);
        }
        lambdaQueryWrapper.orderByDesc(StudentDO::getId);
        return studentDao.selectList(lambdaQueryWrapper);
    }

    public Integer getTotalCount(){
        return this.count();
    }

    /**
     * 功能：批量插入数据
     */
    public RespResult<Boolean> saveStuListWithBatch(List<StudentDO> studentList) {
        this.saveBatch(studentList);
        return RespResult.success(true);
    }
}