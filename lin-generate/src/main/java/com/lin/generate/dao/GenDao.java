package com.lin.generate.dao;

import java.util.List;
import java.util.Map;


public interface GenDao {

    /**
     * 获取全部表
     * @param name
     * @return
     */
    List<Map> list(String name);

    List<Map> queryColumnByTableName(String name);
}
