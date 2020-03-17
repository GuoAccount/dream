package com.dream.content.service.impl;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.content.service.TbContentService;
import com.dream.mapper.TbContentMapper;
import com.dream.pojo.TbContent;
import com.dream.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TbContentServiceImpl implements TbContentService {
    @Autowired
    private TbContentMapper tbContentMapper;
    @Override
    public EasyUiDataGridResult list(int page, int rows, long categoryId) {
        //1.先调用分页插件
        //page当前页码 rows每页显示的行数
        PageHelper.startPage(page,rows);
        //2.设查询条件
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //通过当前页码与每页显示行数，得到当前页结果的集合 limt (page-1)*rows,rows
        List<TbContent> tbContents = tbContentMapper.selectByExample(tbContentExample);
        //取出分页信息，封装到指定对象
        PageInfo<TbContent> tbContentPageInfo = new PageInfo<>(tbContents);
        //通过pageInfo来的到所有的查询结果的--总数，页码，每页条数
        //返回结果总数和当前页的结果集
        return new EasyUiDataGridResult(tbContentPageInfo.getTotal(),tbContents);
    }

    @Override
    public DreamResult save(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(tbContent.getCreated());
        tbContentMapper.insert(tbContent);
        return DreamResult.ok();
    }

    @Override
    public List<TbContent> selectContentByCategoryId(Long ad1_category_id) {
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(ad1_category_id);
        List<TbContent> tbContents = tbContentMapper.selectByExample(tbContentExample);
        return tbContents;
    }

    @Override
    public DreamResult delete(Long[] ids) {
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        tbContentMapper.deleteByExample(tbContentExample);
        return DreamResult.ok();
    }

    @Override
    public DreamResult update(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        tbContentMapper.updateByPrimaryKeySelective(tbContent);
        return DreamResult.ok();
    }
}
