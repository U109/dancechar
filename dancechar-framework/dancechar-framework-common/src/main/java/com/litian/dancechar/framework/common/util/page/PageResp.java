package com.litian.dancechar.framework.common.util.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 类描述: 公共的分页返回封装
 *
 * @author 01406831
 * @date 2021/03/31 16:17
 */
@ApiModel("公共的分页返回封装")
public class PageResp<T> extends BasePage {
    /**
     * 总记录数
     */
    @ApiModelProperty("总记录数")
    private Integer total = 0;

    /**
     * 总记录数(兼容之前的)
     */
    @ApiModelProperty("总记录数(兼容之前的)")
    private Integer count = 0;

    @ApiModelProperty("列表")
    private List<T> list;

    /**
     * 附带信息
     */
    private Object otherObj;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return total;
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
        super();
    }

    public PageResp(Integer pageNo, Integer pageSize, Integer total, List<T> list) {
        super(pageNo, pageSize);
        this.list = list;
        this.total = total;
        this.count = total;
    }

    public PageResp(IPage<T> page) {
        super((int) page.getCurrent(), (int) page.getSize());
        this.list = page.getRecords();
        this.total = (int) page.getTotal();
        this.count = this.total;
    }

    public void setIPage(IPage<T> page) {
        this.list = page.getRecords();
        this.total = (int) page.getTotal();
        this.count = this.total;
        setPageNo((int) page.getCurrent());
        setPageSize((int) page.getSize());
    }
}
