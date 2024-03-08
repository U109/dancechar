package com.litian.dancechar.biz.core.scaffold.manager;

import com.litian.dancechar.biz.core.scaffold.dto.FuncMethodListRespDTO;
import com.litian.dancechar.biz.core.scaffold.dto.FuncMethodReqDTO;
import com.litian.dancechar.biz.core.scaffold.dto.FuncMethodRespDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldGenInfoDTO;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenParamDTO;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenTableColumnDTO;
import com.litian.dancechar.common.common.enums.MethodEnum;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述：方法详情
 *
 * @author 01410001
 * @date 2021/09/10 16:10
 */
@Component
public class FuncMethodInfoManager {

    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;

    public FuncMethodListRespDTO getMethodInfoList(FuncMethodReqDTO funcMethodReqDTO){
        FuncMethodListRespDTO funcMethodListRespDTO = new FuncMethodListRespDTO();
        //1.获取工程信息
        ScaffoldGenInfoDTO dbScaffoldGenInfoDTO = scaffoldGenInfoManager.getById(funcMethodReqDTO.getGenInfoId());
        if (DCObjectUtil.isNull(dbScaffoldGenInfoDTO)) {
            return funcMethodListRespDTO;
        }
        //2.获取字段信息
        TplGenParamDTO genParamDTO = scaffoldGenInfoManager.convertScaffoldGenInfoDTOToTplGenParamDTO(dbScaffoldGenInfoDTO);
        List<TplGenTableColumnDTO> tableField = DCCollectionUtil.newArrayList();
        String methodUrl = "/commonRoutePost/";
        if(DCCollectionUtil.isNotEmpty(genParamDTO.getTplGenDBInfoDTOList()) && DCCollectionUtil.isNotEmpty(genParamDTO.getTplGenDBInfoDTOList().get(0).getTplGenDBTableDTOList())){
            tableField = genParamDTO.getTplGenDBInfoDTOList().get(0).getTplGenDBTableDTOList().get(0).getTableField();
            funcMethodListRespDTO.setFunctionDir(genParamDTO.getTplGenDBInfoDTOList().get(0).getTplGenDBTableDTOList().get(0).getFunctionDir());
            funcMethodListRespDTO.setGenFunctions(genParamDTO.getGenFunctions());
            String className = genParamDTO.getTplGenDBInfoDTOList().get(0).getTplGenDBTableDTOList().get(0).getClassName();
            methodUrl = methodUrl + genParamDTO.getContextPath() + "/" + (className.substring(0, 1).toLowerCase() + className.substring(1) + "Service") + "/";
        }
        List<FuncMethodRespDTO> methodList = DCCollectionUtil.newArrayList();
        //3.遍历方法枚举，组装每个方法及参数
        for(MethodEnum methodEnum : MethodEnum.values()){
            FuncMethodRespDTO funcMethodRespDTO = new FuncMethodRespDTO(methodEnum.getMethodType(),methodEnum.getMethodName(),methodUrl+methodEnum.getMethodName(),tableField);
            methodList.add(funcMethodRespDTO);
        }
        funcMethodListRespDTO.setMethodList(methodList);
        return funcMethodListRespDTO;
    }

}
