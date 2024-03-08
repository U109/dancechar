package com.litian.dancechar.framework.common.util.page;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.litian.dancechar.framework.common.util.DCBeanUtil;

import java.util.Collections;
import java.util.List;

/**
 * 类描述: 分页帮助类
 *
 * @author 01406831
 * @date 2021/03/31 16:27
 */
public class PageRespUtil {
    private PageRespUtil() {
    }

    /**
     * 功能描述: 对业务查询结果包装处理
     *
     * @param resultList 业务查询列表
     * @param pageResp   返回结果
     */
    public static <T> void buildPageResult(List<T> resultList, PageResp<T> pageResp) {
        if (CollUtil.isNotEmpty(resultList) && resultList instanceof Page) {
            Page page = (Page) resultList;
            pageResp.setPageNo(page.getPageNum());
            pageResp.setTotal((int) page.getTotal());
            pageResp.setPageSize(page.getPageSize());
            pageResp.setList(resultList);
        } else {
            if (pageResp == null) {
                pageResp = new PageResp<>();
            }
            pageResp.setList(Collections.emptyList());
            pageResp.setTotal(0);
            pageResp.setPageNo(BasePage.DEFAULT_NO);
            pageResp.setPageSize(BasePage.DEFAULT_SIZE);
        }
    }

    /**
     * 功能描述: 对业务查询结果包装处理
     *
     * @param resultList 业务查询列表
     * @param classP     业务转换的类
     */
    public static <T, P> PageResp<P> buildPageResult(List<T> resultList, Class<P> classP) {
        PageResp<P> pageRespR = new PageResp<>();
        if (CollUtil.isNotEmpty(resultList) && resultList instanceof Page) {
            Page page = (Page) resultList;
            pageRespR.setPageNo(page.getPageNum());
            pageRespR.setPageSize(page.getPageSize());
            pageRespR.setTotal((int) page.getTotal());
            pageRespR.setList(DCBeanUtil.copyList(resultList, classP));
        } else {
            pageRespR.setList(Collections.emptyList());
            pageRespR.setTotal(0);
            pageRespR.setPageNo(BasePage.DEFAULT_NO);
            pageRespR.setPageSize(BasePage.DEFAULT_SIZE);
        }
        return pageRespR;
    }
}