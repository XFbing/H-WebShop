package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by h on 2017-02-27.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;



    @Override
    public TbItem getItemById(Long itemId) {

        //itemMapper.selectByPrimaryKey(itemId);

        TbItemExample example = new TbItemExample();
        //添加查询条件
        TbItemExample.Criteria criteria =example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list=itemMapper.selectByExample(example);
        if(list !=null && list.size()>0){
            TbItem item = list.get(0);
            return item;
        }
        return null;
    }

    /**
     * 商品列表查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(page,rows);
      List<TbItem> list =  itemMapper.selectByExample(example);
      //创建一个返回值对象
      EUDataGridResult result =new EUDataGridResult();
      result.setRows(list);
      //取记录总条数
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
      result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception {
        //item补全
        //生成商品ID
        Long itemId= IDUtils.genItemId();
        item.setId(itemId);
        //商品状态,1正常,2下架,3移除
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());

        //插入到数据库
        itemMapper.insert(item);
        //添加商品描述
      TaotaoResult result=  insertItemDesc(itemId,desc);
      if(result.getStatus()!=200){
          throw  new Exception();
      }
      //添加规格参数
        result = insertItemParamItem(itemId, itemParam);
        if(result.getStatus()!=200){
            throw  new Exception();
        }

        return TaotaoResult.ok();
    }

    /**
     * 添加商品描述
     * @param desc
     */
    private TaotaoResult insertItemDesc(long itemId,String desc){
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    private TaotaoResult insertItemParamItem(long itemId,String itemParam){
        //创建一个Pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        //向表中插入数据
        itemParamItemMapper.insert(itemParamItem);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateItem(TbItem item, String desc, String itemParam) throws Exception {
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        item.setStatus((byte) 1);

        itemMapper.updateByPrimaryKeySelective(item);

        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);

        // 添加商品规格参数,插入到tb_item_param_item表中
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);

        // 插入商品描述数据
        itemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
        itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);

        return TaotaoResult.ok();
    }

    @Override
    public String getItemParaMHtml(long itemId) {
        // 根据商品id查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        com.taotao.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        // 执行查询,务必使用WithBLOBs
        // List<TbItemParamItem> list =
        // itemParamItemMapper.selectByExample(example);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list == null || list.isEmpty()) {
            return "";
        }
        // 取规格参数
        TbItemParamItem itemParamItem = list.get(0);
        // 取json数据
        String paramData = itemParamItem.getParamData();
        // 转换成java对象
        List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
        // 遍历list，生成html
        StringBuffer sb = new StringBuffer();

        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
        sb.append("	<tbody>\n");
        for (Map map : mapList) {
            sb.append("		<tr>\n");
            sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
            sb.append("		</tr>\n");
            // 取规格项
            List<Map> mapList2 = (List<Map>) map.get("params");
            for (Map map2 : mapList2) {
                sb.append("		<tr>\n");
                sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
                sb.append("			<td>" + map2.get("v") + "</td>\n");
                sb.append("		</tr>\n");
            }
        }
        sb.append("	</tbody>\n");
        sb.append("</table>");

        return sb.toString();
    }
}
