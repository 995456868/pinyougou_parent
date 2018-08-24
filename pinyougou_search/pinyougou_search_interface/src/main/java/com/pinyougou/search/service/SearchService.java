package com.pinyougou.search.service;

import java.util.Map;

public interface SearchService {
    /**
     * 根据Map条件对象 查询Map数据
     */
    public Map<String,Object> search(Map map);

}
