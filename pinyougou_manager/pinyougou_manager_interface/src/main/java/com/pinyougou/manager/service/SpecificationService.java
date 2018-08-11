package com.pinyougou.manager.service;

import com.pinyougou.common.PageResult;
import com.pinyougou.pojo.SpecificationVo;
import com.pinyougou.pojo.TbSpecification;

import java.util.List;
import java.util.Map;

public interface SpecificationService {

    //查询列表
    PageResult search(Integer pageNum, Integer pageSize, TbSpecification tbSpecification);

    //添加规格及规格选项
    void add(SpecificationVo specificationVo);

    //根据规格ID查询规格数据及规格选项数据
    SpecificationVo findOne(Long id);

    //修改规格及规格选项
    void update(SpecificationVo specificationVo);

    //删除规格
    void dele(Long[] ids);

    //查询规格集合
    List<Map> findSpecList();
}
