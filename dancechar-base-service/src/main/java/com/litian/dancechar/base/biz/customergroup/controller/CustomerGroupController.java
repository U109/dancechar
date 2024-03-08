package com.litian.dancechar.base.biz.customergroup.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupMobileDTO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupReqDTO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupRespDTO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupSaveDTO;
import com.litian.dancechar.base.biz.customergroup.enums.ImportTypeEnum;
import com.litian.dancechar.base.biz.customergroup.service.CustomerGroupService;
import com.litian.dancechar.base.common.constants.CommConstants;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.oss.dto.UploadResultDTO;
import com.litian.dancechar.framework.oss.enums.AliyunFolderTypeEnum;
import com.litian.dancechar.framework.oss.util.OssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.typeconverter.Convert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 客群业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "客群相关api")
@RestController
@Slf4j
@RequestMapping(value = "/customerGroup/")
public class CustomerGroupController {
    @Resource
    private CustomerGroupService customerGroupService;
    @Resource
    private OssUtil ossUtil;

    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<CustomerGroupRespDTO>> listPaged(@RequestBody CustomerGroupReqDTO req) {
        return customerGroupService.listPaged(req);
    }

    @ApiOperation(value = "上传客群明细到oss", notes = "上传客群明细到oss")
    @PostMapping("uploadCustomerGroupToOss")
    public RespResult<UploadResultDTO> uploadCustomerGroupToOss(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if(file == null || StrUtil.isEmpty(file.getOriginalFilename()) || file.getSize() == 0){
            return RespResult.error("上传文件不存在,请检查");
        }
        if(!StrUtil.endWith(file.getOriginalFilename(), "txt")){
            return RespResult.error("请上传txt格式的文件");
        }
        if(file.getSize() > CommConstants.UploadFile.MAX_FILE_SIZE){
            return RespResult.error("上传的文件过大");
        }
        try{
            List<String> totalRecordList = Lists.newArrayList();
            // 校验总的记录数
            LineIterator it = IOUtils.lineIterator(file.getInputStream(), "utf-8");
            try {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    totalRecordList.add(line);
                }
            } finally {
                it.close();
            }
            if(CollUtil.isEmpty(totalRecordList)){
                return RespResult.error("上传的文件为空，请检查");
            }
            // 导入类型为单个导入，考虑文件比较大，所以限制单个文件的总记录数
            if(totalRecordList.size() > CommConstants.UploadFile.TOTAL_RECORD_LIMIT){
                return RespResult.error("超过允许的最大条数");
            }
            UploadResultDTO uploadResultDTO = ossUtil.pushTxtDataToOSS(AliyunFolderTypeEnum.CUSTOMER_GROUP, file);
            if(uploadResultDTO != null){
                uploadResultDTO.setTotalNum(Convert.toLong(totalRecordList.size()));
            }
            return RespResult.success(uploadResultDTO);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return RespResult.error("上传客群明细失败！");
        }
    }

    @ApiOperation(value = "保存客群", notes = "保存客群")
    @PostMapping("saveCustomerGroup")
    public RespResult<Boolean> saveCustomerGroup(@RequestBody CustomerGroupSaveDTO req) {
        if(ImportTypeEnum.MANY.getCode().equals(req.getImportType())){
            if(req.getSplitTotalCount() == null){
                return RespResult.error("分拆的数量不能为空");
            }
            int splitTotalCountMin = CommConstants.UploadFile.SPLIT_TOTAL_COUNT_MIN;
            if(req.getSplitTotalCount() < splitTotalCountMin){
                return RespResult.error("拆分客群数量不能小于" + splitTotalCountMin);
            }
        }
        return customerGroupService.saveCustomerGroup(req);
    }

    @ApiOperation(value = "删除客群", notes = "删除客群")
    @PostMapping("deleteCustomerGroup")
    public RespResult<Boolean> deleteCustomerGroup(@RequestBody CustomerGroupReqDTO req) {
        if(StrUtil.isEmpty(req.getId())){
            return RespResult.error("客群Id不能为空");
        }
        if(StrUtil.isEmpty(req.getCustomerGroupParentId())){
            return RespResult.error("父客群Id不能为空");
        }
        return RespResult.success(customerGroupService.deleteCustomerGroup(req));
    }

    @ApiOperation(value = "判断手机号是否在客群内", notes = "判断手机号是否在客群内")
    @PostMapping("isInCustomerGroup")
    public RespResult<Boolean> isInCustomerGroup(@RequestBody CustomerGroupMobileDTO req) {
        if(StrUtil.isEmpty(req.getCustomerGroupCode())){
            return RespResult.error("客群不能为空");
        }
        if(StrUtil.isEmpty(req.getMobile())){
            return RespResult.error("手机号不能为空");
        }
        return RespResult.success(customerGroupService.isInCustomerGroup(
                req.getCustomerGroupCode(), req.getMobile()));
    }
}