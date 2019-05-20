package com.treeyh.common.model.result;

import java.io.Serializable;
import java.util.List;

/**
 * @author TreeYH
 * @version 1.0
 * @description 分页结果对象
 * @create 2019-05-17 19:07
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long total;

    private Long page;

    private Long size;

    private List<T> data;

    public PageResult(){}

    public PageResult(Long total, Long page, Long size, List<T> data){
        this.total = total;
        this.page = page;
        this.size = size;
        this.data = data;
    }

    public PageResult(Long total, Integer page, Integer size, List<T> data){
        this.total = total;
        this.page = page.longValue();
        this.size = size.longValue();
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
