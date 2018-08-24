package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.search.service.SearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("search")
public class SearchController {

    @Reference
    private SearchService searchService;

    @RequestMapping("/searchMap")
    public Map<String,Object> searchMap(@RequestBody Map map){
        return searchService.search(map);
    }

}
