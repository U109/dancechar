package com.litian.dancechar.biz.core.codegen.common.util;

import com.litian.dancechar.biz.core.codegen.common.enums.ServerExceptionEnum;
import com.litian.dancechar.framework.common.exception.BusinessException;
import com.litian.dancechar.framework.common.exception.ServiceException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpServlet工具类，获取当前request和response
 */
public class HttpServletUtil {

    /**
     * 获取当前请求的request对象
     *
     * @author xuyuxiang
     * @date 2020/3/30 15:10
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new BusinessException(ServerExceptionEnum.REQUEST_EMPTY.getMessage());
        } else {
            return requestAttributes.getRequest();
        }
    }

    /**
     * 获取当前请求的response对象
     *
     * @author xuyuxiang
     * @date 2020/3/30 15:10
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new BusinessException(ServerExceptionEnum.REQUEST_EMPTY.getMessage());
        } else {
            return requestAttributes.getResponse();
        }
    }
}
