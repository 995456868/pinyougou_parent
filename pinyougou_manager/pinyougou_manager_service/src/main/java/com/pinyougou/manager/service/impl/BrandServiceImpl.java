package com.pinyougou.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.common.PageResult;
import com.pinyougou.manager.service.BrandService;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper brandMapper;

    public List<TbBrand> findAll() {
        //通过Mapper来获取数据
        List<TbBrand> list = brandMapper.findAll();

        return list;
    }

    public PageResult findPage(Integer pageNum, Integer pageSize) {
        //设置分页属性
        PageHelper.startPage(pageNum,pageSize);
        //进行查询
        Page<TbBrand> page = (Page<TbBrand>) brandMapper.findAll();

        return new PageResult(page.getTotal(),page.getResult());
    }

    public void add(TbBrand tbBrand) {
        brandMapper.add(tbBrand);
    }

    public TbBrand findOne(Long id) {
        return brandMapper.findOne(id);
    }

    public void update(TbBrand tbBrand) {
        brandMapper.update(tbBrand);
    }

    public void delete(Long[] ids) {
        for (Long id :
                ids) {
        brandMapper.delete(id);
        }
    }

    public PageResult search(Integer pageNum, Integer pageSize, TbBrand tbBrand) {
        PageHelper.startPage(pageNum,pageSize);
        Page<TbBrand> page = brandMapper.search(tbBrand);
        return new PageResult(page.getTotal(),page.getResult());
    }

    public List<Map> findBrandList() {

        return brandMapper.findBrandList();
    }
}
