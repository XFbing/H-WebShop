package com.taotao.service.impl;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EUTreeNode> getCategoryList(long parentId) {
        //根据parentid查询节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EUTreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            //创建一个节点
            EUTreeNode node = new EUTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");

            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult insertContentCategory(long parentId, String name) {

        //创建一个pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setIsParent(false);
        contentCategory.setStatus(1);
        contentCategory.setParentId(parentId);
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //添加记录
        contentCategoryMapper.insert(contentCategory);
        //查看父节点的isParent是否为true，如果不是true更改为true
        TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
        //判断是否为true
        if (!parentCat.getIsParent()) {
            parentCat.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentCat);
        }
        //返回结果
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContentCategory(Long parentId,Long id) {

        // 根据id查询要删除的node
        TbContentCategory node = contentCategoryMapper.selectByPrimaryKey(id);

        // 该节点是否为叶子节点
        Boolean isParent = node.getIsParent();

        // 保存父节点的ID
        // 如果是第一次调用，参数中的parentId为空，需要将parentId查询出来，从第二次开始，这里的parentId与参数中的一样
        parentId = node.getParentId();

        // 根据node的parentId找到父节点
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);

        // 如果是叶子节点
        if (!isParent) {
            // 根据ID删除节点
           contentCategoryMapper.deleteByPrimaryKey(id);

            // 查询是否还有父节点为parentId的节点(node节点是否还有兄弟节点)
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();

            // 查询条件为parentId
            criteria.andParentIdEqualTo(parentId);

            // 执行查询
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

            // 如果list不为空，说明还有node的父节点还有其他子节点,则不作其他操作
            // 如果list为空，则将node的父节点更新为子节点
            if (list == null) {
                parentNode.setIsParent(false);
                contentCategoryMapper.updateByPrimaryKey(parentNode);
            }
        } else {
            // 不是叶子节点，而是一个有子节点的根节点,要使用递归删除
            // 根据parentId查询所有子节点，先删除子节点，再删除自己
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();

            // 查询条件为parentId
            criteria.andParentIdEqualTo(id);

            // 执行查询
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

            // 先把自己的所有子节点删除
            for (TbContentCategory tbContentCategory : list) {
                deleteContentCategory(id, tbContentCategory.getId());
            }
            // 删除自己
            contentCategoryMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.ok();

    }

    @Override
    public TaotaoResult updateContentCategory(long id, String name) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKey(contentCategory);
        return TaotaoResult.ok();
    }
}