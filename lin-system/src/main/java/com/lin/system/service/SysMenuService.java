package com.lin.system.service;

import java.util.List;
import java.util.Map;

import com.lin.system.pojo.SysMenu;


public interface SysMenuService {


    public List<SysMenu> List(SysMenu sysMenu);

    public SysMenu query(Long id);

    public int add(SysMenu sysMenu);

    public int edit(SysMenu sysMenu);

    public int delete(Long id);

    Map<String, Object> getRouters();
}
