package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.PageResult;
import com.pinyougou.common.Result;
import com.pinyougou.manager.service.SpecificationService;
import com.pinyougou.pojo.SpecificationVo;
import com.pinyougou.pojo.TbSpecification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    @RequestMapping("/search")
    public PageResult search(Integer pageNum, Integer pageSize, @RequestBody TbSpecification tbSpecification) {
        PageResult pageResult = specificationService.search(pageNum, pageSize, tbSpecification);
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationVo specificationVo) {
        try {
            specificationService.add(specificationVo);
            return new Result(true,"操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("/findOne")
    public SpecificationVo findOne(Long id) {
        return specificationService.findOne(id);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody SpecificationVo specificationVo) {
        try {
            specificationService.update(specificationVo);
            return new Result(true,"操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败！");
        }
    }

    @RequestMapping("/dele")
    public Result dele(Long[] ids) {
        try {
            specificationService.dele(ids);
            return new Result(true,"操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败！");
        }
    }

    @RequestMapping("findSpecList")
    public List<Map> findSpecList(){
        return specificationService.findSpecList();
    }
}
