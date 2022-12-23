package com.lin.system.dao;

import java.util.List;

import com.lin.system.pojo.SysMenu;


public interface SysMenuDao
{


    public List<SysMenu> list(SysMenu sysMenu);

    public SysMenu query(Long id);

    public int add(SysMenu sysMenu);

    public int edit(SysMenu sysMenu);

    public int delete(Long id);

    List<SysMenu> getRouters(Long id);
}
