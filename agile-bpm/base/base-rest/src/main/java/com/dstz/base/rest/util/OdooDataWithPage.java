package com.dstz.base.rest.util;


import com.dstz.base.api.query.QueryFilter;
import com.github.pagehelper.Page;

/**
 * Description 条件查询时用此对象解析返回数据
 * author hj
 * date 2020/9/11-11:50
 */
public class OdooDataWithPage<T> {
    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    private Page<T> page;
    /**
     * 起始行
     */
    private int startRow;
    /**
     * 末行
     */
    private int endRow;
    /**
     * 总数
     */
    private long total;

    /*
    * 转换为page对象
    * */
    public Page<T> toPage(QueryFilter queryFilter){
        if (this.page != null){
            this.page.setPageSize(queryFilter.getPage().getPageSize());
            this.page.setPageNum(queryFilter.getPage().getPageNo());
            this.page.setStartRow(this.startRow);
            this.page.setEndRow(this.endRow);
            this.page.setTotal(this.total);
        }
        return  this.page;

    }
}
