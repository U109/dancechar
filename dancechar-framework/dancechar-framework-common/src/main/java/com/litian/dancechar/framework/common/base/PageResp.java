package com.litian.dancechar.framework.common.base;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * TODO
 *
 * @Description
 * @Author mac
 * @Date 2023/1/20 3:21 下午
 **/
public class PageResp<T> extends BasePage{
    /**
     * 总的记录数
     */
    private Integer total=0;
    /**
     * 列表
     */
    private List<T> list;
    private Object otherObj;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Object getOtherObj() {
        return otherObj;
    }

    public void setOtherObj(Object otherObj) {
        this.otherObj = otherObj;
    }

    public PageResp() {
    }

    public PageResp(Integer pageNo, Integer pageSize, Integer total, List<T> list) {
        super(pageNo, pageSize);
        this.total = total;
        this.list = list;
    }
    public PageResp(IPage<T> page) {
        super((int)page.getCurrent(), (int)page.getSize());
        this.total = (int)page.getTotal();
        this.list = page.getRecords();
        this.otherObj = otherObj;
    }
    public void setIpage(IPage<T> page) {
        this.total = (int)page.getTotal();
        this.list = page.getRecords();
        this.setPageNo((int)page.getCurrent());
        this.setPageSize((int)page.getSize());
    }
}
