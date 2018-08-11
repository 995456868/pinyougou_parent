package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.PageResult;
import com.pinyougou.common.Result;
import com.pinyougou.manager.service.BrandService;
import com.pinyougou.pojo.TbBrand;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;

	//http://localhost:8081/brand/findAll
	@RequestMapping("/findAll")
	public List<TbBrand> findAll() {
		List<TbBrand> list = brandService.findAll();
		return list;
	}

	//查询分页列表
	@RequestMapping("/findPage")
	public PageResult findPage(Integer pageNum, Integer pageSize) {
		return brandService.findPage(pageNum,pageSize);
	}

	//添加品牌
	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand tbBrand){
		try {
			brandService.add(tbBrand);
			return new Result(true,"操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"操作失败！");
		}
	}

	//根据id查询品牌
	@RequestMapping("/findOne")
	public TbBrand findOne(Long id) {
		return brandService.findOne(id);
	}

	//修改品牌
	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand tbBrand) {
		try {
			brandService.update(tbBrand);
			return new Result(true,"修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"修改失败！");
		}
	}

	//删除品牌
	@RequestMapping("/dele")
	public Result delete(Long[] ids) {
		try {
			brandService.delete(ids);
			return new Result(true,"操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"操作失败！");
		}
	}

	//条件查询
	@RequestMapping("/search")
	public PageResult search(Integer pageNum,Integer pageSize,@RequestBody TbBrand tbBrand) {
		return brandService.search(pageNum,pageSize,tbBrand);
	}

	//查询品牌集合
	@RequestMapping("/findBrandList")
	public List<Map> findBrandList() {
		return brandService.findBrandList();
	}
}
