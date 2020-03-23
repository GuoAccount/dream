package com.dream.search.mapper;

import com.dream.common.pojo.SearchItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchItemMapper {
    /**
     *
     * @return
     */
    List<SearchItem> getSearchItemMapper();

    /**
     *
     * @param itemId
     * @return
     */
    SearchItem getItemById(@Param("itemId") Long itemId);
}
