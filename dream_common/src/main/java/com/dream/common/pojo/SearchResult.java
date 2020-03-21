package com.dream.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    /** 总页数，需要根据总数量和页码来计算得到*/
    private Long totalPages;
    /** 记录的总数量*/
    private Long totalNum;
    /** 查询结果列表*/
    private List<SearchItem> itemList;

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
