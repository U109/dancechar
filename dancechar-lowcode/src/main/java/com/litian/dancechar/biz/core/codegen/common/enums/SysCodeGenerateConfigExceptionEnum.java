package com.litian.dancechar.biz.core.codegen.common.enums;

import com.litian.dancechar.biz.core.codegen.common.annotion.ExpEnumType;
import com.litian.dancechar.biz.core.codegen.common.constants.SysExpEnumConstant;
import com.litian.dancechar.biz.core.codegen.common.factory.ExpEnumCodeFactory;

/**
 * 代码生成详细配置
 */
@ExpEnumType(module = SysExpEnumConstant.SNOWY_SYS_MODULE_EXP_CODE, kind = SysExpEnumConstant.SYS_POS_EXCEPTION_ENUM)
public enum SysCodeGenerateConfigExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 数据不存在
     */
    NOT_EXIST(1, "此数据不存在");

    private final Integer code;

    private final String message;
        SysCodeGenerateConfigExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeFactory.getExpEnumCode(this.getClass(), code);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
