package com.pinyougou.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.common.PageResult;
import com.pinyougou.manager.service.SpecificationService;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper tbSpecificationMapper;

    @Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;

    public PageResult search(Integer pageNum, Integer pageSize, TbSpecification tbSpecification) {
        PageHelper.startPage(pageNum, pageSize);
        TbSpecificationExample example = new TbSpecificationExample();

        TbSpecificationExample.Criteria criteria = example.createCriteria();
        if (tbSpecification.getSpecName() != null) {
            criteria.andSpecNameLike("%" + tbSpecification.getSpecName() + "%");
        }

        Page<TbSpecification> page = (Page<TbSpecification>) tbSpecificationMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public void add(SpecificationVo specificationVo) {
        TbSpecification tbSpecification = specificationVo.getSpecification();
        tbSpecificationMapper.insertSelective(tbSpecification);
        List<TbSpecificationOption> specificationOptionList = specificationVo.getSpecificationOptionList();
        //遍历
        for (TbSpecificationOption tbSpecificationOption :
                specificationOptionList) {
            tbSpecificationOption.setSpecId(tbSpecification.getId());
            //System.out.println(tbSpecification.getId());
            tbSpecificationOptionMapper.insertSelective(tbSpecificationOption);
        }
    }

    public SpecificationVo findOne(Long id) {
        //查询规格
        TbSpecification tbSpecification = tbSpecificationMapper.selectByPrimaryKey(id);
        //查询规格选项
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        example.createCriteria().andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptionList = tbSpecificationOptionMapper.selectByExample(example);
        //封装对象
        SpecificationVo specificationVo = new SpecificationVo();
        specificationVo.setSpecification(tbSpecification);
        specificationVo.setSpecificationOptionList(tbSpecificationOptionList);
        return specificationVo;
    }

    public void update(SpecificationVo specificationVo) {
        //修改规格
        TbSpecification tbSpecification = specificationVo.getSpecification();
        tbSpecificationMapper.updateByPrimaryKeySelective(tbSpecification);
        //修改规格选项，先删除后添加----删除
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        example.createCriteria().andSpecIdEqualTo(tbSpecification.getId());
        tbSpecificationOptionMapper.deleteByExample(example);
        //----添加
        List<TbSpecificationOption> specificationOptionList = specificationVo.getSpecificationOptionList();
        for (TbSpecificationOption tbSpecificationOption :
                specificationOptionList) {
            //设置规格选项id
            tbSpecificationOption.setSpecId(tbSpecification.getId());
            tbSpecificationOptionMapper.insertSelective(tbSpecificationOption);
        }
    }

    public void dele(Long[] ids) {
        for (Long id :
                ids) {
            //删除规格
            tbSpecificationMapper.deleteByPrimaryKey(id);
            //删除规格选项
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            example.createCriteria().andSpecIdEqualTo(id);
            tbSpecificationOptionMapper.deleteByExample(example);
        }
    }

    public List<Map> findSpecList() {
        return tbSpecificationMapper.findSpecList();
    }
}
