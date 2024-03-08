package com.litian.dancechar.framework.common.exception;

import com.alibaba.fastjson.JSON;
import com.litian.dancechar.framework.common.base.DCResponseResultCodeEnum;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Set;

/**
 * 类描述: 全局异常处理
 *
 * @author 01406831
 * @date 2021/04/02 18:30
 */
@Slf4j
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        log.error("system error!errMsg:{},exceptionClass:{}", e.getMessage(), e.getStackTrace()[0].toString(), e);
        if (e instanceof ConstraintViolationException) {
            StringBuilder builder = new StringBuilder();
            ConstraintViolationException exception = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                builder.append(constraintViolation.getMessageTemplate()).append(";");
            }
            builder.deleteCharAt(builder.length() - 1);
            return Response.ok().entity(JSON.toJSONString(DCResultUtil
                    .error(DCResponseResultCodeEnum.ERR_PARAM_ILLEGAL.getCode(), builder.toString()))).build();
        } else if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            return Response.ok().entity(JSON.toJSONString(DCResultUtil
                    .error(bizException.getCode(), bizException.getMessage()))).build();
        } /*else if (e instanceof LockActionException) {
            return Response.ok().entity(JSON.toJSONString(DCResultUtil
                    .error("SYSTEM_ERROR", e.getMessage()))).build();
        }*/ else {
            return Response.ok().entity(JSON.toJSONString(DCResultUtil
                    .error(DCResponseResultCodeEnum.ERR_UNKNOWN))).build();
        }
    }
}
