package com.dream.common.pojo;

import java.io.Serializable;
import java.util.List;

public class EasyUiDataGridResult implements Serializable {
    private Long total;
    private List<?> rows;//?泛型通配符 相当于 Object
    public EasyUiDataGridResult(Long total,List<?> rows){
        this.total=total;
        this.rows=rows;
    }
    public EasyUiDataGridResult(){}

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
