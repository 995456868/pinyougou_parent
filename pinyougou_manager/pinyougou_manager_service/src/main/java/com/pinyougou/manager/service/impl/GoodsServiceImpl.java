package com.pinyougou.manager.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.pojo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import com.pinyougou.manager.service.GoodsService;

import com.pinyougou.common.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Autowired
	private TbBrandMapper brandMapper;

	@Autowired
	private TbSellerMapper sellerMapper;


	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(GoodsVo goodsVo) {
		TbGoods goods = goodsVo.getGoods();
		goods.setAuditStatus("0");
		goods.setIsDelete("0");
		goodsMapper.insertSelective(goods);
		goodsVo.getGoodsDesc().setGoodsId(goods.getId());
		goodsDescMapper.insertSelective(goodsVo.getGoodsDesc());
		List<TbItem> itemList = goodsVo.getItemList();
		for (TbItem item:itemList) {
			String title = "";
			title = goods.getGoodsName();
			Map<String,String> specList = JSON.parseObject(item.getSpec(), Map.class);
			for (String key:specList.keySet()) {
					title += "  "+specList.get(key);
			}
			item.setTitle(title);
			item.setCategoryid(goods.getCategory3Id());
			item.setCreateTime(new Date());
			item.setUpdateTime(new Date());
			item.setGoodsId(goods.getId());
			item.setSellerId(goods.getSellerId());

			//获取商品叶子节点分类
			String categoryName = itemCatMapper.selectByPrimaryKey(item.getCategoryid()).getName();
			item.setCategory(categoryName);
			//获取商品品牌
			String brandName = brandMapper.findOne(goods.getBrandId()).getName();
			item.setBrand(brandName);
			//获取商品所属的商家
			String nickName = sellerMapper.selectByPrimaryKey(goods.getSellerId()).getNickName();
			item.setSeller(nickName);
			//图片
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goods.getId());
			List<Map> imageList = JSON.parseArray(goodsDesc.getItemImages(), Map.class);
			if(imageList.size() > 0){
				item.setImage(String.valueOf(imageList.get(0).get("url")));
			}
			//添加
			itemMapper.insertSelective(item);
		}

	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbGoods goods){
		goodsMapper.updateByPrimaryKey(goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbGoods findOne(Long id){
		return goodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			goodsMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		
		if(goods!=null){			
						if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		for (Long id : ids) {
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setAuditStatus(status);
			goodsMapper.updateByPrimaryKey(goods);

		}
	}

	@Override
	public void updateMaketable(Long[] ids, String isMaketable) {
		for (Long id :ids) {
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setIsMarketable(isMaketable);
			goodsMapper.updateByPrimaryKey(goods);
		}
	}

}
