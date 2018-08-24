package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map map) {
        //创建搜索对象
        SimpleHighlightQuery query = new SimpleHighlightQuery();
        //设置高亮域
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");
        highlightOptions.setSimplePrefix("<em style=\"color:red\">");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);

        //获取map中的关键字
        String keywords = (String) map.get("keywords");
        //定义一个条件
        Criteria criteria=null;
        //判断关键字 不等于null 且不为空 进行条件查询，否者查询全部
        if (keywords!=null && !"".equals(keywords)){
            criteria=new Criteria("item_keywords").contains(keywords);
        }else {
            criteria=new Criteria().expression("*:*");
        }
        //添加条件对象
        query.addCriteria(criteria);
        //添加分类过滤
        String category = (String) map.get("category");
        if(category!=null && !"".equals(category)){
            SimpleFilterQuery filterQuery = new SimpleFilterQuery();
            Criteria criteria1 = new Criteria("item_category").contains(category);
            filterQuery.addCriteria(criteria1);
            query.addFilterQuery(filterQuery);
        }
        //添加品牌过滤条件
        String brand = (String) map.get("brand");
        if (brand!=null&&!"".equals(brand)){
            SimpleFilterQuery filterQuery = new SimpleFilterQuery();
            Criteria item_brand = new Criteria("item_brand").contains(brand);
            filterQuery.addCriteria(item_brand);
            query.addFilterQuery(filterQuery);
        }
        //添加规格过滤
        Map<String,String> specList = (Map<String, String>) map.get("spec");
        if (specList!=null){
            for (String key:specList.keySet()) {
                SimpleFilterQuery simpleFilterQuery = new SimpleFilterQuery();
                Criteria criteria1 = new Criteria("item_spec_" + key).contains(specList.get(key));
                simpleFilterQuery.addCriteria(criteria1);
                query.addFilterQuery(simpleFilterQuery);
            }
        }
        //添加价格过滤
        String priceStr = (String) map.get("price");
        if (priceStr!=null&&!"".equals(priceStr)){
            String[] priceArray = priceStr.split("-");
            if (!"0".equals(priceArray[0])) {
                SimpleFilterQuery simpleFilterQuery = new SimpleFilterQuery();
                Criteria criteria1 = new Criteria("item_price").greaterThanEqual(priceArray[0]);
                simpleFilterQuery.addCriteria(criteria1);
                query.addFilterQuery(simpleFilterQuery);
            }
            if (!"*".equals(priceArray[1])){
                SimpleFilterQuery simpleFilterQuery = new SimpleFilterQuery();
                Criteria criteria1 = new Criteria("item_price").lessThanEqual(priceArray[1]);
                simpleFilterQuery.addCriteria(criteria1);
                query.addFilterQuery(simpleFilterQuery);
            }

        }
        //执行分页查询：默认查询每页10条
        HighlightPage<TbItem> items = solrTemplate.queryForHighlightPage(query, TbItem.class);
        //获取查询内容
        List<HighlightEntry<TbItem>> itemsHighlighted = items.getHighlighted();
        for (HighlightEntry<TbItem> h:itemsHighlighted) {
            TbItem item = h.getEntity();
            if(h.getHighlights().size()>0&&h.getHighlights().get(0).getSnipplets().size()>0){
                item.setTitle(h.getHighlights().get(0).getSnipplets().get(0));//设置高亮的结果
            }
        }
        List<TbItem> list = items.getContent();
        //创建Map集合封装返回的内容数据
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("rows",list);
        return resultMap;
    }
}
