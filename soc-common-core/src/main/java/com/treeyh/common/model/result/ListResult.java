package com.treeyh.common.model.result;

import java.io.Serializable;
import java.util.List;

/**
 * @author TreeYH
 * @version 1.0
 * @description 列表返回对象
 * @create 2019-05-17 19:07
 */
public class ListResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long total;
    private List<T> data;

    public ListResult() {
    }

    public ListResult(Long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public ListResult(List<T> data) {
        this(null == data ? 0 : data.size(), data);
    }

    public ListResult(Integer total, List<T> data) {
        this.total = total.longValue();
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}