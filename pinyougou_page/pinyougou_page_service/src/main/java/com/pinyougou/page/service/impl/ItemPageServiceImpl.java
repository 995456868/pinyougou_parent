package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

    private String pageDir = "E:\\item\\";
    @Autowired
    private FreeMarkerConfig freeMarkerConfig;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Override
    public Boolean getItemHtml(Long goodsId) {
        try {
            //创建一个FreeMarker的config配置文件；
            Configuration configuration = freeMarkerConfig.getConfiguration();
            //根据模板名称获取Template对象
            Template template = configuration.getTemplate("item.ftl");
            //创建一个存数据的Map集合
            Map itemModel = new HashMap<>();
            //根据商品的ID获取商品对象和商品描述对象
            TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
            TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            //把获取到的商品对象放到MAP集合中；
            itemModel.put("goods",goods);
            itemModel.put("goodsDesc",goodsDesc);
            //获取面包屑中的数据
            itemModel.put("category1Name", itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName());
            itemModel.put("category2Name", itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName());
            itemModel.put("category3Name", itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName());

            //获取SKU列表数据
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(goodsId);
            criteria.andStatusEqualTo("1");
            example.setOrderByClause("is_default desc");//根据是否默认降序排序
            List<TbItem> itemList = itemMapper.selectByExample(example);
            itemModel.put("itemList", itemList);
            //创建一个输出流 来存放生成的静态页面
            Writer out = new FileWriter(pageDir+goodsId+".html");
            //生成静态页面
            template.process(itemModel,out);
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
