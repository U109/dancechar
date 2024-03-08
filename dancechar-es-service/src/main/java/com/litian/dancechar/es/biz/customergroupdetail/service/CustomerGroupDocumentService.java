package com.litian.dancechar.es.biz.customergroupdetail.service;

import cn.easyes.core.conditions.select.LambdaEsQueryWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.litian.dancechar.es.biz.customergroupdetail.dao.document.CustomerGroupDetailDocument;
import com.litian.dancechar.es.biz.customergroupdetail.dao.inf.CustomerGroupDetailDocumentDao;
import com.litian.dancechar.es.biz.customergroupdetail.dto.CustomerGroupDetailDTO;
import com.litian.dancechar.es.biz.snowflake.util.SnowflakeHelper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 客群详情es服务
 *
 * @author tojson
 * @date 2021/6/19 15:13
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CustomerGroupDocumentService {
    @Resource
    private CustomerGroupDetailDocumentDao customerGroupDocumentDao;

    public Boolean insert(CustomerGroupDetailDTO customerGroupDetailDTO) {
        CustomerGroupDetailDocument document = new CustomerGroupDetailDocument();
        DCBeanUtil.copyNotNull(customerGroupDetailDTO, document);
        document.setId(SnowflakeHelper.genString());
        return customerGroupDocumentDao.insert(document) > 0;
    }

    public Boolean batchInsert(List<CustomerGroupDetailDTO> detailDTOS) {
        List<CustomerGroupDetailDocument> cgList  = Lists.newArrayList();
        detailDTOS.forEach(vo->{
            CustomerGroupDetailDocument document = new CustomerGroupDetailDocument();
            DCBeanUtil.copyNotNull(vo, document);
            document.setId(SnowflakeHelper.genString());
            cgList.add(document);
        });
        return customerGroupDocumentDao.insertBatch(cgList) > 0;
    }

    public Boolean delete(String code) {
        LambdaEsQueryWrapper<CustomerGroupDetailDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(CustomerGroupDetailDocument::getCustomerGroupCode, code);
        return customerGroupDocumentDao.delete(wrapper) > 0;
    }

    public Boolean clearExpireData(CustomerGroupDetailDTO detailDTO) {
        LambdaEsQueryWrapper<CustomerGroupDetailDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.lt(CustomerGroupDetailDocument::getExpireTime, new Date());
        if(ObjectUtil.isNotNull(detailDTO) && StrUtil.isNotEmpty(detailDTO.getCustomerGroupCode())){
            wrapper.lt(CustomerGroupDetailDocument::getCustomerGroupCode, detailDTO.getCustomerGroupCode());
        }
        return customerGroupDocumentDao.delete(wrapper) > 0;
    }

    public CustomerGroupDetailDocument queryByCodeAndMobile(String code, String mobile) {
        LambdaEsQueryWrapper<CustomerGroupDetailDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(CustomerGroupDetailDocument::getCustomerGroupCode, code);
        wrapper.eq(CustomerGroupDetailDocument::getMobile, mobile);
        return customerGroupDocumentDao.selectOne(wrapper);
    }

    public List<CustomerGroupDetailDocument> listByCode(String code) {
        LambdaEsQueryWrapper<CustomerGroupDetailDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(CustomerGroupDetailDocument::getCustomerGroupCode, code);
        return customerGroupDocumentDao.selectList(wrapper);
    }
}