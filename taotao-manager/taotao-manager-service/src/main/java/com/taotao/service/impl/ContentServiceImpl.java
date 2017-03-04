package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by h on 2017-03-03.
 */
@Service
public class ContentServiceImpl  implements ContentService{

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public EUDataGridResult getContentListById(Integer page, Integer rows, Long categoryId) {

        // 分页处理
        PageHelper.startPage(page, rows);
        // 执行查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        List<TbContent> list = contentMapper.selectByExample(example);
        // 取分页信息
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        // 返回处理结果
        EUDataGridResult result = new EUDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);

        return result;
    }

    @Override
    public TaotaoResult insertContent(TbContent content) {
        //补全pojo内容
        content.setCreated(new Date());
        content.setUpdated(new Date());
        // 插入数据
        contentMapper.insert(content);

        return TaotaoResult.ok();
    }
}
