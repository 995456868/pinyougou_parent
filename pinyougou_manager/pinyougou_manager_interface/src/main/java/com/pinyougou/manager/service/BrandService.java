package com.pinyougou.manager.service;

import com.pinyougou.common.PageResult;
import com.pinyougou.pojo.TbBrand;

import java.util.List;
import java.util.Map;

public interface BrandService {

    //查询品牌列表
    List<TbBrand> findAll();

    //分页查询品牌列表
    public PageResult findPage(Integer pageNum,Integer pageSize);

    //添加品牌
    void add(TbBrand tbBrand);

    //根据id查询品牌
    TbBrand findOne(Long id);

    //修改品牌
    void update(TbBrand tbBrand);

    //删除品牌
    void delete(Long[] ids);

    //条件查询
    PageResult search(Integer pageNum, Integer pageSize, TbBrand tbBrand);

    //查询品牌集合
    List<Map> findBrandList();
}
